package com.heroku.myapp.hawtiosample;

import java.util.Arrays;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ChoiceDefinition;
import org.springframework.stereotype.Component;

@Component
public class MyRoute extends RouteBuilder {

    NumberRangeUtil util;

    @Override
    public void configure() throws Exception {
        int min = 1;
        int max = 10;
        onException(Exception.class)
                .handled(true)
                .routeId("onExceptionRoute")
                .to("seda:lucky");

        from("seda:lucky")
                .to("mock:lucky")
                .id("I'm feeling lucky!");

        util = new NumberRangeUtil(min, max);
        ChoiceDefinition parentChoice
                = from("timer:foo?period=500&delay=0")
                .routeId("hawtioSampleRoute")
                .process((exchange)
                        -> exchange.getIn().setBody(util.getRandomNumberInRange()))
                .id("process:setRandomNumber" + util.getRange())
                .choice();
        addOddNumberRoute(parentChoice);
        addEvenNumberRoute(parentChoice);
        parentChoice.end().to("mock:end");
    }

    private void addOddNumberRoute(ChoiceDefinition parentChoice) {
        ChoiceDefinition nestedOddNumberChoice = parentChoice
                .when((exchange) -> exchange.getIn().getBody(Integer.class) % 2 == 1)
                .id("when:numberIsOdd")
                .choice().id("oddNumber" + util.getRange());
        addNumberRoutes(nestedOddNumberChoice, util.getOddNumbers());
        parentChoice.endChoice();
    }

    private void addEvenNumberRoute(ChoiceDefinition parentChoice) {
        ChoiceDefinition nestedEvenNumberChoice = parentChoice
                .when((exchange) -> exchange.getIn().getBody(Integer.class) % 2 == 0)
                .id("when:numberIsEven")
                .choice().id("evenNumber" + util.getRange());
        addNumberRoutes(nestedEvenNumberChoice, util.getEvenNumbers());
        parentChoice.endChoice();
    }

    private void addNumberRoutes(ChoiceDefinition nestedChoice, int[] intArray) {
        Arrays.stream(intArray).forEach((i) -> {
            nestedChoice.when(body().isEqualTo(i)).id("bodyIs" + i)
                    .toF("mock:is%s", i);
            util.getPrimes(i).stream().distinct()
                    .forEach((j) -> nestedChoice.toF("mock:prime%s", j));
        });
    }
}

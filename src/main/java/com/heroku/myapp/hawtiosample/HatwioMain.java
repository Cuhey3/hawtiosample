package com.heroku.myapp.hawtiosample;

import io.hawt.embedded.Main;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class HatwioMain {

    public HatwioMain() throws Exception {
        Main main = new Main();
        System.setProperty("hawtio.authenticationEnabled", "false");
        String port = Optional.ofNullable(System.getenv("PORT")).orElse("2525");
        main.setPort(Integer.parseInt(port));
        main.setContextPath("/foo");
        main.setWarLocation("./");
        main.run();
    }
}

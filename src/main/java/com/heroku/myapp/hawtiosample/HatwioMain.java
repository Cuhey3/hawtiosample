package com.heroku.myapp.hawtiosample;

import io.hawt.embedded.Main;
import org.springframework.stereotype.Component;

@Component
public class HatwioMain {

    public HatwioMain() throws Exception {
        Main main = new Main();
        System.setProperty("hawtio.authenticationEnabled", "false");
        main.setPort(Integer.parseInt("2525"));
        main.setContextPath("/foo");
        main.setWarLocation("./");
        main.run();
    }
}

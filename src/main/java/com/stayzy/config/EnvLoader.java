package com.stayzy.config;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvLoader {

    static {
        Dotenv dotenv = Dotenv.configure()
                .directory("./")
                .ignoreIfMissing()
                .load();

        String url = dotenv.get("SPRING_DATASOURCE_URL");
        String user = dotenv.get("SPRING_DATASOURCE_USERNAME");
        String pass = dotenv.get("SPRING_DATASOURCE_PASSWORD");

        if (url != null) System.setProperty("spring.datasource.url", url);
        if (user != null) System.setProperty("spring.datasource.username", user);
        if (pass != null) System.setProperty("spring.datasource.password", pass);
    }
}

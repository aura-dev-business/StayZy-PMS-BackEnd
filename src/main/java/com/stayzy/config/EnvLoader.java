package com.stayzy.config;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvLoader {

    static {
        String activeProfile = System.getProperty("spring.profiles.active", "default");

        if (!"test".equals(activeProfile)) { // skip in test
            Dotenv dotenv = Dotenv.configure()
                                   .directory("./")
                                   .ignoreIfMissing()
                                   .load();

            System.setProperty("spring.datasource.url", dotenv.get("SPRING_DATASOURCE_URL"));
            System.setProperty("spring.datasource.username", dotenv.get("SPRING_DATASOURCE_USERNAME"));
            System.setProperty("spring.datasource.password", dotenv.get("SPRING_DATASOURCE_PASSWORD"));
        }
    }
}

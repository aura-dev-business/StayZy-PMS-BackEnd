package com.stayzy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;
import com.stayzy.config.EnvLoader;

@SpringBootApplication
public class StayzyBackendApplication {
    public static void main(String[] args) {

        new EnvLoader();

        SpringApplication.run(StayzyBackendApplication.class, args);
    }
    }




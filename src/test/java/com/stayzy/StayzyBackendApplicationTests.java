package com.stayzy;

import com.stayzy.config.EnvLoader; // import it
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StayzyBackendApplicationTests {

    @BeforeAll
    static void loadEnv() {
        new EnvLoader();
    }

    @Test
    void contextLoads() {
    }
}

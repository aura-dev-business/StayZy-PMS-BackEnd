package com.stayzy;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test") // <-- use H2
class StayzyBackendApplicationTests {

    @Test
    void contextLoads() {
    }

}

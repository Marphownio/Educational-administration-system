package com.example.lab;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class LabApplicationTests {

    @Test
    public void test() {
        assertEquals(2+2, 4);
    }

}

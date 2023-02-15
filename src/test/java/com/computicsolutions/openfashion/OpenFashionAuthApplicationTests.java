package com.computicsolutions.openfashion;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class tests {@link OpenFashionAuthApplication} class
 */
@SpringBootTest
public class OpenFashionAuthApplicationTests {

    /**
     * This method tests Spring application main run method
     */
    @Test
    public void Should_RunSpringApplication() {
        OpenFashionAuthApplication.main(new String[]{});

        assertTrue(true, "Spring Application Context Loaded Successfully");
    }
}

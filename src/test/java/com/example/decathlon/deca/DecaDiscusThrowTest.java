package com.example.decathlon.deca;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DecaDiscusThrowTest {

    private final DecaDiscusThrow decaDiscusThrow = new DecaDiscusThrow();

    @Test
    void calculateResultWithValidDistance() {

        double[] Distance = {
                8.0,
                16.0,
                32.0,
                64.0
        };

        int[] expectedScores = {
                59,
                198,
                504,
                1166
        };

        for (int i = 0; i < Distance.length; i++) {

            int result = decaDiscusThrow.calculateResult(Distance[i]);

            System.out.println(
                    "Time: " + Distance[i] +
                            " Expected: " + expectedScores[i] +
                            " Actual: " + result
            );

            assertEquals(expectedScores[i], result);
        }
    }

    @Test
    void calculateResultWithTooLowDistance() {
        assertThrows(
                IllegalArgumentException.class,
                () -> decaDiscusThrow.calculateResult(-1)
        );
    }

    @Test
    void calculateResultWithTooHighDistance() {
        assertThrows(
                IllegalArgumentException.class,
                () -> decaDiscusThrow.calculateResult(86)
        );
    }
}
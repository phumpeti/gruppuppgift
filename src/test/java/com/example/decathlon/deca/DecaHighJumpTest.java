package com.example.decathlon.deca;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DecaHighJumpTest {

    private final DecaHighJump decaHighJump = new DecaHighJump();

    @Test
    void calculateResultWithValidDistance() {

        double[] Distance = {
                100.0,
                150.0,
                200.0,
                250.0
        };

        int[] expectedScores = {
                81,
                389,
                803,
                1296
        };

        for (int i = 0; i < Distance.length; i++) {

            int result = decaHighJump.calculateResult(Distance[i]);

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
                () -> decaHighJump.calculateResult(-1)
        );
    }

    @Test
    void calculateResultWithTooHighDistance() {
        assertThrows(
                IllegalArgumentException.class,
                () -> decaHighJump.calculateResult(301)
        );
    }
}
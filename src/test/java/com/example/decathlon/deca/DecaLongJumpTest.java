package com.example.decathlon.deca;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DecaLongJumpTest {

    private final DecaLongJump decaLongJump = new DecaLongJump();

    @Test
    void calculateResultWithValidDistance() {

        double[] Distance = {
                250.0,
                450.0,
                600.0,
                800.0
        };

        int[] expectedScores = {
                16,
                290,
                587,
                1061
        };

        for (int i = 0; i < Distance.length; i++) {

            int result = decaLongJump.calculateResult(Distance[i]);

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
                () -> decaLongJump.calculateResult(-1)
        );
    }

    @Test
    void calculateResultWithTooHighDistance() {
        assertThrows(
                IllegalArgumentException.class,
                () -> decaLongJump.calculateResult(1001)
        );
    }
}
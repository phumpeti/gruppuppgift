package com.example.decathlon.deca;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Deca1500MTest {

    private final Deca1500M deca1500M = new Deca1500M();

    @Test
    void calculateResultWithValidTime() {

        double[] runningTimes = {
                150.0,
                175.0,
                200.0,
                225.0
        };

        int[] expectedScores = {
                1719,
                1486,
                1268,
                1067
        };

        for (int i = 0; i < runningTimes.length; i++) {

            int result = deca1500M.calculateResult(runningTimes[i]);

            System.out.println(
                    "Time: " + runningTimes[i] +
                            " Expected: " + expectedScores[i] +
                            " Actual: " + result
            );

            assertEquals(expectedScores[i], result);
        }
    }

    @Test
    void calculateResultWithTooLowTime() {
        assertThrows(
                IllegalArgumentException.class,
                () -> deca1500M.calculateResult(149.9)
        );
    }

    @Test
    void calculateResultWithTooHighTime() {
        assertThrows(
                IllegalArgumentException.class,
                () -> deca1500M.calculateResult(400.1)
        );
    }


}
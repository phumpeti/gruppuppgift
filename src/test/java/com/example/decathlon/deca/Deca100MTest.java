package com.example.decathlon.deca;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Deca100MTest {

private final Deca100M deca100M = new Deca100M();

    @Test
    void calculateResultWithValidTime() {

        double[] runningTimes = {
                6.0,
                8.0,
                10.0,
                12.0
        };

        int[] expectedScores = {
                2284,
                1642,
                1096,
                651
        };

        for (int i = 0; i < runningTimes.length; i++) {

            int result = deca100M.calculateResult(runningTimes[i]);

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
                () -> deca100M.calculateResult(4.9)
        );
    }

    @Test
    void calculateResultWithTooHighTime() {
        assertThrows(
                IllegalArgumentException.class,
                () -> deca100M.calculateResult(20.1)
        );
    }


}

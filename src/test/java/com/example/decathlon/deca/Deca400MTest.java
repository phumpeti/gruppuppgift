package com.example.decathlon.deca;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Deca400MTest {

    private final Deca400M deca400M = new Deca400M();

    @Test
    void calculateResultWithValidTime() {

        double[] runningTimes = {
                20.0,
                35.0,
                50.0,
                75.0
        };

        int[] expectedScores = {
                2698,
                1634,
                815,
                52
        };

        for (int i = 0; i < runningTimes.length; i++) {

            int result = deca400M.calculateResult(runningTimes[i]);

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
                () -> deca400M.calculateResult(19.9)
        );
    }

    @Test
    void calculateResultWithTooHighTime() {
        assertThrows(
                IllegalArgumentException.class,
                () -> deca400M.calculateResult(100.1)
        );
    }


}
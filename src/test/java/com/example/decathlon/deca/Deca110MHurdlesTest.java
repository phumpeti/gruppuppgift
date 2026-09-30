package com.example.decathlon.deca;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Deca110MHurdlesTest {

    private final Deca110MHurdles deca110MHurdles = new Deca110MHurdles();

    @Test
    void calculateResultWithValidTime() {

        double[] runningTimes = {
                10.0,
                12.0,
                14.0,
                16.0
        };

        int[] expectedScores = {
                1556,
                1249,
                975,
                733
        };

        for (int i = 0; i < runningTimes.length; i++) {

            int result = deca110MHurdles.calculateResult(runningTimes[i]);

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
                () -> deca110MHurdles.calculateResult(9.9)
        );
    }

    @Test
    void calculateResultWithTooHighTime() {
        assertThrows(
                IllegalArgumentException.class,
                () -> deca110MHurdles.calculateResult(30.1)
        );
    }


}


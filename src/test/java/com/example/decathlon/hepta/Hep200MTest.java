package com.example.decathlon.hepta;

import com.example.decathlon.heptathlon.Hep200M;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Hep200MTest {

    private final Hep200M hep200M = new Hep200M();

    @Test
    void calculateResultWithValidTime() {

        double[] runningTime = {
                20.0,
                25.0,
                35.0,
                40.0
        };

        int[] expectedScores = {
                1398,
                887,
                191,
                26,
        };

        for (int i = 0; i < runningTime.length; i++) {

            int result = hep200M.calculateResult(runningTime[i]);

            System.out.println(
                    "Time: " + runningTime[i] +
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
                () -> hep200M.calculateResult(19.9)
        );
    }

    @Test
    void calculateResultWithTooHighTime() {
        assertThrows(
                IllegalArgumentException.class,
                () -> hep200M.calculateResult(100.1)
        );
    }
}
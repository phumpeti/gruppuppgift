package com.example.decathlon.hepta;

import com.example.decathlon.heptathlon.Hep100MHurdles;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Hep100MHurdlesTest {

    private final Hep100MHurdles hep100MHurdles = new Hep100MHurdles();

    @Test
    void calculateResultWithValidDistance() {

        double[] runningTime = {
                10.0,
                15.0,
                20.0,
                25.0
        };

        int[] expectedScores = {
                1617,
                842,
                302,
                24
        };

        for (int i = 0; i < runningTime.length; i++) {

            int result = hep100MHurdles.calculateResult(runningTime[i]);

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
                () -> hep100MHurdles.calculateResult(9.9)
        );
    }

    @Test
    void calculateResultWithTooHighTime() {
        assertThrows(
                IllegalArgumentException.class,
                () -> hep100MHurdles.calculateResult(30.1)
        );
    }
}
package com.example.decathlon.deca;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DecaJavelinThrowTest {

    private final DecaJavelinThrow decaJavelinThrow = new DecaJavelinThrow();

    @Test
    void calculateResultWithValidDistance() {

        double[] Distance = {
                20.0,
                60.0,
                80.0,
                100.0
        };

        int[] expectedScores = {
                161,
                738,
                1043,
                1355
        };

        for (int i = 0; i < Distance.length; i++) {

            int result = decaJavelinThrow.calculateResult(Distance[i]);

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
                () -> decaJavelinThrow.calculateResult(-1)
        );
    }

    @Test
    void calculateResultWithTooHighDistance() {
        assertThrows(
                IllegalArgumentException.class,
                () -> decaJavelinThrow.calculateResult(111)
        );
    }
}
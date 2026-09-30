package com.example.decathlon.deca;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DecaShotPutTest {

    private final DecaShotPut decaShotPut = new DecaShotPut();

    @Test
    void calculateResultWithValidDistance() {

        double[] Distance = {
                10.0,
                15.0,
                20.0,
                25.0
        };

        int[] expectedScores = {
                486    ,
                790,
                1100,
                1414
        };

        for (int i = 0; i < Distance.length; i++) {

            int result = decaShotPut.calculateResult(Distance[i]);

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
                () -> decaShotPut.calculateResult(-1)
        );
    }

    @Test
    void calculateResultWithTooHighDistance() {
        assertThrows(
                IllegalArgumentException.class,
                () -> decaShotPut.calculateResult(31)
        );
    }
}
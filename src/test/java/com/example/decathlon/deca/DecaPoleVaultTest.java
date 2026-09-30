package com.example.decathlon.deca;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DecaPoleVaultTest {

    private final DecaPoleVault decaPoleVault = new DecaPoleVault();

    @Test
    void calculateResultWithValidDistance() {

        double[] Distance = {
                200.0,
                400.0,
                600.0,
                800.0
        };

        int[] expectedScores = {
                140,
                617,
                1231,
                1938
        };

        for (int i = 0; i < Distance.length; i++) {

            int result = decaPoleVault.calculateResult(Distance[i]);

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
                () -> decaPoleVault.calculateResult(-1)
        );
    }

    @Test
    void calculateResultWithTooHighDistance() {
        assertThrows(
                IllegalArgumentException.class,
                () -> decaPoleVault.calculateResult(1001)
        );
    }
}
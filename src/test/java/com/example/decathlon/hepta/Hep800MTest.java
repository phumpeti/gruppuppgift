package com.example.decathlon.hepta;

import com.example.decathlon.heptathlon.Hep100MHurdles;
import com.example.decathlon.heptathlon.Hep800M;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Hep800MTest {

    private final Hep800M hep800M = new Hep800M();

    @Test
    void calculateResultWithValidTime() {

        double[] Distance = {
                70.0,
                120.0,
                170.0,
                220.0
        };

        int[] expectedScores = {
                2026,
                1116,
                464,
                84
        };

        for (int i = 0; i < Distance.length; i++) {

            int result = hep800M.calculateResult(Distance[i]);

            System.out.println(
                    "Time: " + Distance[i] +
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
                () -> hep800M.calculateResult(69.9)
        );
    }

    @Test
    void calculateResultWithTooHighTime() {
        assertThrows(
                IllegalArgumentException.class,
                () -> hep800M.calculateResult(250.1)
        );
    }
}
package com.example.decathlon.hepta;

import com.example.decathlon.heptathlon.HeptShotPut;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HepShotPutTest {

    private final HeptShotPut hepJavelinThrow= new HeptShotPut();

    @Test
    void calculateResultWithValidDistance() {

        double[] Distance = {
                4.0,
                16.0,
                22.0,
                30.0
        };

        int[] expectedScores = {
                146,
                928,
                1335,
                1887
        };

        for (int i = 0; i < Distance.length; i++) {

            int result = hepJavelinThrow.calculateResult(Distance[i]);

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
                () -> hepJavelinThrow.calculateResult(-1)
        );
    }

    @Test
    void calculateResultWithTooHighDistance() {
        assertThrows(
                IllegalArgumentException.class,
                () -> hepJavelinThrow.calculateResult(30.1)
        );
    }
}
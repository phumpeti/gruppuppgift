package com.example.decathlon.hepta;

import com.example.decathlon.heptathlon.HeptJavelinThrow;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HepJavelinThrowTest {

    private final HeptJavelinThrow hepJavelinThrow= new HeptJavelinThrow();

    @Test
    void calculateResultWithValidDistance() {

        double[] Distance = {
                4.0,
                41.0,
                72.0,
                90.0
        };

        int[] expectedScores = {
                2,
                686,
                1290,
                1646
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
                () -> hepJavelinThrow.calculateResult(110.1)
        );
    }
}
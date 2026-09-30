package com.example.decathlon.hepta;

import com.example.decathlon.heptathlon.HeptLongJump;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HepLongJumpTest {

    private final HeptLongJump hepLongJump= new HeptLongJump();

    @Test
    void calculateResultWithValidDistance() {

        double[] Distance = {
                220.0,
                430.0,
                650.0,
                880.0
        };

        int[] expectedScores = {
                4,
                379,
                1007,
                1822
        };

        for (int i = 0; i < Distance.length; i++) {

            int result = hepLongJump.calculateResult(Distance[i]);

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
                () -> hepLongJump.calculateResult(-1)
        );
    }

    @Test
    void calculateResultWithTooHighDistance() {
        assertThrows(
                IllegalArgumentException.class,
                () -> hepLongJump.calculateResult(1000.1)
        );
    }
}
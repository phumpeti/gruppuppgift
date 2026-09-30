package com.example.decathlon.hepta;

import com.example.decathlon.heptathlon.HeptHightJump;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HepHighJumpTest {

    private final HeptHightJump hepHighJump= new HeptHightJump();

    @Test
    void calculateResultWithValidDistance() {

        double[] Distance = {
                100.0,
                180.0,
                250.0,
                290.0
        };

        int[] expectedScores = {
                141,
                978,
                1948,
                2571
        };

        for (int i = 0; i < Distance.length; i++) {

            int result = hepHighJump.calculateResult(Distance[i]);

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
                () -> hepHighJump.calculateResult(-1)
        );
    }

    @Test
    void calculateResultWithTooHighDistance() {
        assertThrows(
                IllegalArgumentException.class,
                () -> hepHighJump.calculateResult(300.1)
        );
    }
}
package com.example.decathlon.heptathlon;

import com.example.decathlon.common.CalcTrackAndField;

public class HeptHightJump {

	private int score;
	private double A = 1.84523;
	private double B = 75;
	private double C = 1.348;
	CalcTrackAndField calc = new CalcTrackAndField();

	public int calculateResult(double distance) {

		// Limits.pdf anger 0 till 300, var 75.7-270

		if (distance < 0) {
			throw new IllegalArgumentException("Value too low");
		} else if (distance > 300) {
			throw new IllegalArgumentException("Value too high");
		}

		score = calc.calculateField(A, B, C, distance);
		System.out.println("The result is: " + score);

		return score;
	}

}

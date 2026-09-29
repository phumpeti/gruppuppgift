package com.example.decathlon.deca;

import com.example.decathlon.common.CalcTrackAndField;

public class DecaLongJump {

	private int score;
	private double A = 0.13454;
	private double B = 220;
	private double C = 1.4;
	CalcTrackAndField calc = new CalcTrackAndField();

	public int calculateResult(double distance) {

		// Limits.pdf anger 0 till 1000 så den undre gränsen ändrades, var 250 - 1000

		if (distance < 0) {
			throw new IllegalArgumentException("Value too low");
		} else if (distance > 1000) {
			throw new IllegalArgumentException("Value too high");
		}

		score = calc.calculateField(A, B, C, distance);
		System.out.println("The result is: " + score);

		return score;
	}

}

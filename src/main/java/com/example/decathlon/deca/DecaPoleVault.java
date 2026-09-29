package com.example.decathlon.deca;

import com.example.decathlon.common.CalcTrackAndField;

public class DecaPoleVault {

	private int score;
	private double A = 0.2797;
	private double B = 100;
	private double C = 1.35;
	CalcTrackAndField calc = new CalcTrackAndField();

	public int calculateResult(double distance) {

		// Limits.pdf anger 0 till 1000 så undre gräns har ändrats, var 2-1000

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

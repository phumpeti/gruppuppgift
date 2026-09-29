package com.example.decathlon.heptathlon;

import com.example.decathlon.common.CalcTrackAndField;

public class HeptJavelinThrow {

	private int score;
	private double A = 15.9803;
	private double B = 3.8;
	private double C = 1.04;
	CalcTrackAndField calc = new CalcTrackAndField();

	public int calculateResult(double distance) {

		// Limits.pdf anger 0 till 110, var 0-100

		if (distance < 0) {
			throw new IllegalArgumentException("Value too low");
		} else if (distance > 110) {
			throw new IllegalArgumentException("Value too high");
		}

		score = calc.calculateField(A, B, C, distance);
		System.out.println("The result is: " + score);

		return score;
	}

}

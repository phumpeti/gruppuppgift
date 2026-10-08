package com.example.decathlon.heptathlon;

import com.example.decathlon.common.CalcTrackAndField;

public class HeptLongJump {

	private int score;
	// Var A = 0.188807 ska vara A = 0.188807 enl. limits.pdf
	private double A = 0.188807;
	private double B = 210;
	private double C = 1.41;
	CalcTrackAndField calc = new CalcTrackAndField();

	public int calculateResult(double distance) {

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

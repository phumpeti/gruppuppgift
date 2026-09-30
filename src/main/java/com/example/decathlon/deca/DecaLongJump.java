package com.example.decathlon.deca;

import com.example.decathlon.common.CalcTrackAndField;

public class DecaLongJump {

	private int score;
	// A var 0.13454
	private double A = 0.14354;
	private double B = 220;
	private double C = 1.4;
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

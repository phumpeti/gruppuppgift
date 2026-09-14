package com.example.decathlon.heptathlon;

import com.example.decathlon.common.CalcTrackAndField;

public class HeptHightJump {

	private int score;
	private double A = 1.84523;
	private double B = 75;
	private double C = 1.348;
	CalcTrackAndField calc = new CalcTrackAndField();

	public int calculateResult(double distance) {

		if (distance < 75.7) {
			throw new IllegalArgumentException("Value too low");
		} else if (distance > 270) {
			throw new IllegalArgumentException("Value too high");
		}

		score = calc.calculateField(A, B, C, distance);
		System.out.println("The result is: " + score);

		return score;
	}

}

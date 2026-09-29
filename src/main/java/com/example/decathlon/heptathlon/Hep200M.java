package com.example.decathlon.heptathlon;

import com.example.decathlon.common.CalcTrackAndField;

public class Hep200M {

	private int score;
	private double A = 4.99087;
	private double B = 42.5;
	private double C = 1.81;
	CalcTrackAndField calc = new CalcTrackAndField();

	public int calculateResult(double runningTime) {

		// Limits.pdf anger 20 till 100, var 14-42.08

		if (runningTime < 20) {
			throw new IllegalArgumentException("Value too low");
		} else if (runningTime > 100) {

			throw new IllegalArgumentException("Value too high");
		}

		score = calc.calculateTrack(A, B, C, runningTime);
		System.out.println("The result is " + score);

		return score;
	}

}

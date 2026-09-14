package com.example.decathlon.deca;

import com.example.decathlon.common.CalcTrackAndField;

public class Deca1500M {

	private int score;
	private double A = 0.03768;
	private double B = 480;
	private double C = 18.5;
	CalcTrackAndField calc = new CalcTrackAndField();

	public int calculateResult(double runningTime) {

		if (runningTime < 2) {
			throw new IllegalArgumentException("Value too low");
		} else if (runningTime > 7) {
			throw new IllegalArgumentException("Value too high");
		}

		score = calc.calculateTrack(A, B, C, runningTime);
		System.out.println("The result is: " + score);

		return score;
	}

}

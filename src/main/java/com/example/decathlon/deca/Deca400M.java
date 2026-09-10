package com.example.decathlon.deca;

import com.example.decathlon.common.CalcTrackAndField;

public class Deca400M {

	private int score;
	private double A = 1.53775;
	private double B = 82;
	private double C = 1.81;
	CalcTrackAndField calc = new CalcTrackAndField();

	public int calculateResult(double runningTime) {

		if (runningTime < 20) {
			throw new IllegalArgumentException("Value too low");
		} else if (runningTime > 100) {
			throw new IllegalArgumentException("Value too high");
		}

		score = calc.calculateTrack(A, B, C, runningTime);
		System.out.println("The result is: " + score);

		return score;
	}

}

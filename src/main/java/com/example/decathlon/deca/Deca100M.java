package com.example.decathlon.deca;

import com.example.decathlon.common.CalcTrackAndField;

public class Deca100M {

	private int score;
	private double A = 25.4347;
	private double B = 18;
	private double C = 1.81;
	CalcTrackAndField calc = new CalcTrackAndField();

	public int calculateResult(double runningTime) {

		if (runningTime < 5) {
			throw new IllegalArgumentException("Value too low");
		} else if (runningTime > 17.8) {
			throw new IllegalArgumentException("Value too high");
		}

		score = calc.calculateTrack(A, B, C, runningTime);
		System.out.println("The result is " + score);

		return score;
	}

}

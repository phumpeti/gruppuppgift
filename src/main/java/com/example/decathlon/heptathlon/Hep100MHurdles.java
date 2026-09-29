package com.example.decathlon.heptathlon;

import com.example.decathlon.common.CalcTrackAndField;

public class Hep100MHurdles {

	private int score;
	private double A = 9.23076;
	private double B = 26.7;
	private double C = 18.35;
	CalcTrackAndField calc = new CalcTrackAndField();

	public int calculateResult(double runningTime) {

		// Limits.pdf anger 10 till 30 så bägge har ändrats, var 5-26.4

		if (runningTime < 10) {
			throw new IllegalArgumentException("Value too low");
		} else if (runningTime > 30) {
			throw new IllegalArgumentException("Value too high");
		}

		score = calc.calculateTrack(A, B, C, runningTime);
		System.out.println("The result is " + score);

		return score;
	}

}

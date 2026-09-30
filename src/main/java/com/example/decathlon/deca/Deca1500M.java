package com.example.decathlon.deca;

import com.example.decathlon.common.CalcTrackAndField;

public class Deca1500M {

	private int score;
	private double A = 0.03768;
	private double B = 480;
	// Den här var felaktigt angiven som 18.5
	private double C = 1.85;
	CalcTrackAndField calc = new CalcTrackAndField();

	public int calculateResult(double runningTime) {

		if (runningTime < 150) {
			throw new IllegalArgumentException("Value too low");
		} else if (runningTime > 400) {
			throw new IllegalArgumentException("Value too high");
		}

		score = calc.calculateTrack(A, B, C, runningTime);
		System.out.println("The result is: " + score);

		return score;
	}

}

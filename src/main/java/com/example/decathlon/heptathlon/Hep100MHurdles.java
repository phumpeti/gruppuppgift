package com.example.decathlon.heptathlon;

import com.example.decathlon.common.CalcTrackAndField;

public class Hep100MHurdles {

	private int score;
	private double A = 9.23076;
	private double B = 26.7;
	// C var 18.35 och ska vara 1.835
	private double C = 1.835;
	CalcTrackAndField calc = new CalcTrackAndField();

	public int calculateResult(double runningTime) {

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

package com.maxcheung.controllers;

public class Workout {

	private long elapsedTime;

	public Workout(long elapsedTime) {
		super();
		this.elapsedTime = elapsedTime;
	}

	public long getElapsedTime() {
		return elapsedTime;
	}
}

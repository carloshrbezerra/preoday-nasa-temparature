package com.preodaynasatemparature.domain;

public class Temperature {
	
	private double averageTemperature;
	
	public Temperature(double averageTemperature) {
		super();
		this.averageTemperature = averageTemperature;
	}

	public double getAverageTemperature() {
		return averageTemperature;
	}

	public void setAverageTemperature(double averageTemperature) {
		this.averageTemperature = averageTemperature;
	}
	
}
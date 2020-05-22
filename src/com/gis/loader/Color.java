package com.gis.loader;

public class Color {
	private double blue;

	private double green;

	private double red;

	public Color(double red, double green, double blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public Color(String red, String green, String blue) {
		this.red = Double.parseDouble(red);
		this.green = Double.parseDouble(green);
		this.blue = Double.parseDouble(blue);
	}

	@Override
	public String toString() {
		return red+" "+green+" "+blue;
	}

	public double getBlue() {
		return blue;
	}

	public void setBlue(double blue) {
		this.blue = blue;
	}

	public double getGreen() {
		return green;
	}

	public void setGreen(double green) {
		this.green = green;
	}

	public double getRed() {
		return red;
	}

	public void setRed(double red) {
		this.red = red;
	}
}

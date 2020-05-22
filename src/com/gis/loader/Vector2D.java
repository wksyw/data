package com.gis.loader;

public class Vector2D {
	public float x;
	public float y;

	public Vector2D() {
		this(0, 0);
	}

	/**
	 * Creates a new Vector3D with the same values as the specified Vector2D.
	 */
	public Vector2D(Vector2D v) {
		this(v.x, v.y);
	}

	/**
	 * Creates a new Vector2D with the specified (x, y) values.
	 */
	public Vector2D(float x, float y) {
		setTo(x, y);
	}

	/**
	 * Checks if this Vector2D is equal to the specified Object. They are equal
	 * only if the specified Object is a Vector2D and the two Vector2D's x, y,
	 * and z coordinates are equal.
	 */
	public boolean equals(Object obj) {
		Vector2D v = (Vector2D) obj;
		return (v.x == x && v.y == y);
	}

	/**
	 * Checks if this Vector2D is equal to the specified x, y, and z
	 * coordinates.
	 */
	public boolean equals(float x, float y) {
		return (this.x == x && this.y == y);
	}

	/**
	 * Sets the vector to the same values as the specified Vector2D.
	 */
	public void setTo(Vector2D v) {
		setTo(v.x, v.y);
	}

	/**
	 * Sets this vector to the specified (x, y) values.
	 */
	public void setTo(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Adds the specified (x, y) values to this vector.
	 */
	public void add(float x, float y) {
		this.x += x;
		this.y += y;
	}

	/**
	 * Subtracts the specified (x, y) values to this vector.
	 */
	public void subtract(float x, float y) {
		add(-x, -y);
	}

	/**
	 * Adds the specified vector to this vector.
	 */
	public void add(Vector2D v) {
		add(v.x, v.y);
	}

	/**
	 * Subtracts the specified vector from this vector.
	 */
	public void subtract(Vector2D v) {
		add(-v.x, -v.y);
	}

	/**
	 * Multiplies this vector by the specified value. The new length of this
	 * vector will be length()*s.
	 */
	public void multiply(float s) {
		x *= s;
		y *= s;
	}

	/**
	 * Divides this vector by the specified value. The new length of this vector
	 * will be length()/s.
	 */
	public void divide(float s) {
		x /= s;
		y /= s;
	}

	/**
	 * Returns the length of this vector as a float.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	/**
	 * Converts this Vector2D to a unit vector, or, in other words, a vector of
	 * length 1. Same as calling v.divide(v.length()).
	 */
	public void normalize() {
		divide(length());
	}

	/**
	 * Converts this Vector2D to a String representation.
	 */
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}

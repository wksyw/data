package com.gis.loader;

/**
 * The Vector3D class implements a 3D vector with the floating-point values x,
 * y, and z. Vectors can be thought of either as a (x,y,z) point or as a vector
 * from (0,0,0) to (x,y,z).
 */
public class Vector3D {

	public float x;
	public float y;
	public float z;

	/**
	 * Creates a new Vector3D at (0,0,0).
	 */
	public Vector3D() {
		this(0, 0, 0);
	}

	/**
	 * Creates a new Vector3D with the same values as the specified Vector3D.
	 */
	public Vector3D(Vector3D v) {
		this(v.x, v.y, v.z);
	}

	/**
	 * Creates a new Vector3D with the specified (x, y, z) values.
	 */
	public Vector3D(float x, float y, float z) {
		setTo(x, y, z);
	}

	/**
	 * Checks if this Vector3D is equal to the specified Object. They are equal
	 * only if the specified Object is a Vector3D and the two Vector3D's x, y,
	 * and z coordinates are equal.
	 */
	public boolean equals(Object obj) {
		Vector3D v = (Vector3D) obj;
		return (v.x == x && v.y == y && v.z == z);
	}

	/**
	 * Checks if this Vector3D is equal to the specified x, y, and z
	 * coordinates.
	 */
	public boolean equals(float x, float y, float z) {
		return (this.x == x && this.y == y && this.z == z);
	}

	/**
	 * Sets the vector to the same values as the specified Vector3D.
	 */
	public void setTo(Vector3D v) {
		setTo(v.x, v.y, v.z);
	}

	/**
	 * Sets this vector to the specified (x, y, z) values.
	 */
	public void setTo(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Adds the specified (x, y, z) values to this vector.
	 */
	public void add(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
	}

	/**
	 * Subtracts the specified (x, y, z) values to this vector.
	 */
	public void subtract(float x, float y, float z) {
		add(-x, -y, -z);
	}

	/**
	 * Adds the specified vector to this vector.
	 */
	public void add(Vector3D v) {
		add(v.x, v.y, v.z);
	}

	/**
	 * Subtracts the specified vector from this vector.
	 */
	public void subtract(Vector3D v) {
		add(-v.x, -v.y, -v.z);
	}

	/**
	 * Multiplies this vector by the specified value. The new length of this
	 * vector will be length()*s.
	 */
	public void multiply(float s) {
		x *= s;
		y *= s;
		z *= s;
	}

	/**
	 * Divides this vector by the specified value. The new length of this vector
	 * will be length()/s.
	 */
	public void divide(float s) {
		x /= s;
		y /= s;
		z /= s;
	}

	/**
	 * Returns the length of this vector as a float.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * Converts this Vector3D to a unit vector, or, in other words, a vector of
	 * length 1. Same as calling v.divide(v.length()).
	 */
	public void normalize() {
		divide(length());
	}

	/**
	 * Converts this Vector3D to a String representation.
	 */
	public String toString() {
		return "(" + x + "," + y + ", " + z + ")";
	}
}

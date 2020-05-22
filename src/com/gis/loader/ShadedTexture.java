package com.gis.loader;

import java.util.ArrayList;
import java.util.List;

public class ShadedTexture {

	private Vector2D[] coordinates;

	public Vector2D[] getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Vector2D[] coordinates) {
		this.coordinates = coordinates;
	}

	public void setCoordinates(List<Vector2D> coordinates) {
		this.coordinates = new Vector2D[coordinates.size()];
		coordinates.toArray(this.coordinates);
	}

	public List<Double> toList() {
		List<Double> list = new ArrayList<Double>(8);
		for (Vector2D v : coordinates) {
			list.add(new Double(v.x));
			list.add(new Double(v.y));
		}
		list.add(new Double(coordinates[0].x));
		list.add(new Double(coordinates[0].y));
		return list;
	}

}

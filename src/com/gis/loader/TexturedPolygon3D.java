package com.gis.loader;

public class TexturedPolygon3D extends Polygon3D {

	private Material material;// 材质

	private Vector3D normal; // 法线

	public Vector3D getNormal() {
		return normal;
	}

	public void setNormal(Vector3D normal) {
		this.normal = normal;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public TexturedPolygon3D() {
		super();
	}

	/**
	 * 通过点创建一个多边形
	 */
	public TexturedPolygon3D(Vector3D v0, Vector3D v1, Vector3D v2) {
		super(new Vector3D[] { v0, v1, v2 });
	}

	/**
	 * 通过点创建一个多边形
	 */
	public TexturedPolygon3D(Vector3D v0, Vector3D v1, Vector3D v2, Vector3D v3) {
		super(new Vector3D[] { v0, v1, v2, v3 });
	}

	/**
	 * 通过顶点数组创建一个多边形
	 */
	public TexturedPolygon3D(Vector3D[] vertices) {
		super(vertices);
	}
}

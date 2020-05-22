package com.gis.loader;

public class TexturedPolygon3D extends Polygon3D {

	private Material material;// ����

	private Vector3D normal; // ����

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
	 * ͨ���㴴��һ�������
	 */
	public TexturedPolygon3D(Vector3D v0, Vector3D v1, Vector3D v2) {
		super(new Vector3D[] { v0, v1, v2 });
	}

	/**
	 * ͨ���㴴��һ�������
	 */
	public TexturedPolygon3D(Vector3D v0, Vector3D v1, Vector3D v2, Vector3D v3) {
		super(new Vector3D[] { v0, v1, v2, v3 });
	}

	/**
	 * ͨ���������鴴��һ�������
	 */
	public TexturedPolygon3D(Vector3D[] vertices) {
		super(vertices);
	}
}

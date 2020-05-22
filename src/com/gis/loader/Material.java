package com.gis.loader;

import java.util.HashMap;
import java.util.Map;

public class Material {
	private Color DiffuseColor;

	private Double AmbientIntensity;

	private Color specularColor;

	private Color emissiveColor;

	private String imageType;
	
	private String uri;

	private Map<String, ShadedTexture> targets = new HashMap<String, ShadedTexture>();

	public Color getDiffuseColor() {
		return DiffuseColor;
	}

	public void setDiffuseColor(Color diffuseColor) {
		DiffuseColor = diffuseColor;
	}

	public Double getAmbientIntensity() {
		return AmbientIntensity;
	}

	public void setAmbientIntensity(Double ambientIntensity) {
		AmbientIntensity = ambientIntensity;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Color getSpecularColor() {
		return specularColor;
	}

	public void setSpecularColor(Color specularColor) {
		this.specularColor = specularColor;
	}

	public Color getEmissiveColor() {
		return emissiveColor;
	}

	public void setEmissiveColor(Color emissiveColor) {
		this.emissiveColor = emissiveColor;
	}

	public Map<String, ShadedTexture> getTargets() {
		return targets;
	}

	public void setTargets(Map<String, ShadedTexture> targets) {
		this.targets = targets;
	}

}

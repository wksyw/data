package com.gis.data;

import com.gis.loader.PolygonGroup;

public class PublicData {
	private static PublicData publicDate;

	static{
		publicDate=new PublicData();
	}
	
	public PolygonGroup group;

	public PolygonGroup getGroup() {
		return group;
	}

	public void setGroup(PolygonGroup group) {
		this.group = group;
	}

	public static PublicData getInstance() {
		return publicDate;
	}

	private PublicData() {
	}
}

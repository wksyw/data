package com.gis.util;

import org.citygml4j.CityGMLContext;
import org.citygml4j.builder.jaxb.JAXBBuilder;
import org.citygml4j.factory.CityGMLFactory;
import org.citygml4j.factory.GMLFactory;
import org.citygml4j.factory.geometry.GMLGeometryFactory;
import org.citygml4j.xml.io.CityGMLOutputFactory;

//缓存线程安全的factory,context类
public class CityGmlUtils {
	private static CityGMLContext ctx;

	private static CityGMLFactory citygml;
	private static GMLFactory gml;
	private static GMLGeometryFactory geom;
	private static JAXBBuilder builder;
	private static CityGMLOutputFactory out;

	private CityGmlUtils() {
	}

	public static CityGMLFactory getCityGMLFactory() {
		return citygml;
	}

	public static GMLGeometryFactory getGMLGeometryFactory() {
		return geom;
	}

	public static GMLFactory getGMLFactory() {
		return gml;
	}

	public static JAXBBuilder getJAXBBuilder() {
		return builder;
	}
	
	public static CityGMLOutputFactory getCityGMLOutputFactory() {
		return out;
	}

	static {
		System.out.println("正在初始化ctx...");
		ctx = new CityGMLContext();
		System.out.println("正在初始化citygml...");
		citygml = new CityGMLFactory();
		try {
			System.out.println("正在初始化builder...");
			builder = ctx.createJAXBBuilder();
			System.out.println("正在初始化out...");
			out = builder.createCityGMLOutputFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
		gml = new GMLFactory();
		citygml = new CityGMLFactory();
		geom = new GMLGeometryFactory();
	}
}

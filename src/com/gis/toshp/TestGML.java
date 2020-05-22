package com.gis.toshp;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//import org.locationtech.jts.geom.*;


public class TestGML {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 生成uuid
//		String uuid = UUID.randomUUID().toString();
//		System.out.println(uuid);
//		// 获取当前运行目录
//		System.out.println(System.getProperty("user.dir"));// user.dir指定了当前的路径
//		// 生成制定长度的随机字符
//		String filename = getRandomStr(5);
//		System.out.println(filename);
//
//		// 遍历指定目录下的obj文件
//		File fileFolder = new File("E:\\CODE\\objgml\\javagmltest\\src");
//		List<String> listObjFiles = new ArrayList<>();
//		funcGetObjFiles(fileFolder, listObjFiles);
//		System.out.println(listObjFiles);

		// // 遍历shp创建新shp
		// String filepath = "E:\\CODE\\objgml\\LoD2_NJ24.shp";
		// // 读取shp模板的属性字段
		// Map<String, Object> shpAttrs = new
		// ReadShp().getShpAttributes(filepath);
		// // 创建shp——字段名太长字段丢失了，建议采用拷贝shp的形式
		// String outpath = "E:\\CODE";
		// ShapefileDataStore ndatastore = new WriteShp().createShpfile(outpath,
		// filename + ".shp", shpAttrs);

		// 向空白shp中添加记录
		String typeName;
		try {
			// 向空白shp中插入记录
			File file = new File("D:\\数据部小程序\\高\\gmltest\\shp模板\\template.shp");
			FileDataStore dataStore = null;
			SimpleFeatureIterator iter = null;
			FileDataStore filedataStore = FileDataStoreFinder
					.getDataStore(file);

			typeName = filedataStore.getTypeNames()[0];
			SimpleFeatureSource featureSource = filedataStore
					.getFeatureSource(typeName);
			SimpleFeatureType shapeType = featureSource.getSchema();

			List<SimpleFeature> features = new ArrayList<>();
			SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(
					shapeType);

			GeometryFactory geometryFactory = JTSFactoryFinder
					.getGeometryFactory(null);
			WKTReader reader = new WKTReader(geometryFactory);
			// 拼装wkt后插入记录
			Geometry poly = reader
					.read("POLYGON ((407.5078 294.6238 24.1097,401.7158 294.4789 44.1097,402.0869 283.6161 44.1097,407.8789 283.761 24.1097,407.5078 294.6238 24.1097))");
			Object[] obj = { poly };
			SimpleFeature feature = featureBuilder.buildFeature(null, obj);
			features.add(feature);
			Geometry poly2 = reader
					.read("POLYGON ((401.7701 294.436 20.2249,402.1382 283.6616 20.2249,402.1382 283.6616 24.0101,401.7701 294.436 24.0101,401.7701 294.436 20.2249))");
			Object[] obj2 = { poly2 };
			SimpleFeature feature2 = featureBuilder.buildFeature(null, obj2);
			features.add(feature2);
			boolean isSuc = new WriteShp().addFeature(
					(ShapefileDataStore) filedataStore, features);
			System.out.println(isSuc);

		} catch (IOException | ParseException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
	}

	// 生成指定长度的字符串
	public static String getRandomStr(int num) {
		String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random1 = new Random();
		// 指定字符串长度，拼接字符并toString
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < num; i++) {
			// 获取指定长度的字符串中任意一个字符的索引值
			int number = random1.nextInt(str.length());
			// 根据索引值获取对应的字符
			char charAt = str.charAt(number);
			sb.append(charAt);
		}
		return sb.toString();
	}

	// 遍历目录文件夹
	private static void funcGetObjFiles(File file, List<String> listObjFiles) {
		File[] fs = file.listFiles();
		for (File f : fs) {
			if (f.isDirectory()) // 若是目录，则递归打印该目录下的文件
				funcGetObjFiles(f, listObjFiles);
			if (f.isFile()) // 若是文件，添加记录
				listObjFiles.add(f.getPath());
		}
	}

}

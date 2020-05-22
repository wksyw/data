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
		// ����uuid
//		String uuid = UUID.randomUUID().toString();
//		System.out.println(uuid);
//		// ��ȡ��ǰ����Ŀ¼
//		System.out.println(System.getProperty("user.dir"));// user.dirָ���˵�ǰ��·��
//		// �����ƶ����ȵ�����ַ�
//		String filename = getRandomStr(5);
//		System.out.println(filename);
//
//		// ����ָ��Ŀ¼�µ�obj�ļ�
//		File fileFolder = new File("E:\\CODE\\objgml\\javagmltest\\src");
//		List<String> listObjFiles = new ArrayList<>();
//		funcGetObjFiles(fileFolder, listObjFiles);
//		System.out.println(listObjFiles);

		// // ����shp������shp
		// String filepath = "E:\\CODE\\objgml\\LoD2_NJ24.shp";
		// // ��ȡshpģ��������ֶ�
		// Map<String, Object> shpAttrs = new
		// ReadShp().getShpAttributes(filepath);
		// // ����shp�����ֶ���̫���ֶζ�ʧ�ˣ�������ÿ���shp����ʽ
		// String outpath = "E:\\CODE";
		// ShapefileDataStore ndatastore = new WriteShp().createShpfile(outpath,
		// filename + ".shp", shpAttrs);

		// ��հ�shp����Ӽ�¼
		String typeName;
		try {
			// ��հ�shp�в����¼
			File file = new File("D:\\���ݲ�С����\\��\\gmltest\\shpģ��\\template.shp");
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
			// ƴװwkt������¼
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
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		}
	}

	// ����ָ�����ȵ��ַ���
	public static String getRandomStr(int num) {
		String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random1 = new Random();
		// ָ���ַ������ȣ�ƴ���ַ���toString
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < num; i++) {
			// ��ȡָ�����ȵ��ַ���������һ���ַ�������ֵ
			int number = random1.nextInt(str.length());
			// ��������ֵ��ȡ��Ӧ���ַ�
			char charAt = str.charAt(number);
			sb.append(charAt);
		}
		return sb.toString();
	}

	// ����Ŀ¼�ļ���
	private static void funcGetObjFiles(File file, List<String> listObjFiles) {
		File[] fs = file.listFiles();
		for (File f : fs) {
			if (f.isDirectory()) // ����Ŀ¼����ݹ��ӡ��Ŀ¼�µ��ļ�
				funcGetObjFiles(f, listObjFiles);
			if (f.isFile()) // �����ļ�����Ӽ�¼
				listObjFiles.add(f.getPath());
		}
	}

}

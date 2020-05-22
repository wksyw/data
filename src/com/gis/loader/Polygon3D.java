package com.gis.loader;

import com.gis.gui.panel.TransformPanel;
import com.gis.gui.util.FileUtil;
import com.gis.util.FileUtils;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;


import java.io.*;
import java.util.*;
import com.gis.gui.util.Info;
import com.gis.toshp.WriteShp;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
/**
 * The Polygon3D class represents a polygon as a series of vertices.
 */
public class Polygon3D {

	private Vector3D[] v;
	private int numVertices;

	private String uuid;

	/**
	 * Creates an empty polygon that can be used as a "scratch" polygon for
	 * transforms, projections, etc.
	 */
	public Polygon3D() {
		numVertices = 0;
		v = new Vector3D[0];
		uuid = "ID_" + UUID.randomUUID().toString() + "poly";
	}

	/**
	 * Creates a new Polygon3D with the specified vertices.
	 */
	public Polygon3D(Vector3D v0, Vector3D v1, Vector3D v2) {
		this(new Vector3D[] { v0, v1, v2 });
	}

	/**
	 * Creates a new Polygon3D with the specified vertices. All the vertices are
	 * assumed to be in the same plane.
	 */
	public Polygon3D(Vector3D v0, Vector3D v1, Vector3D v2, Vector3D v3) {
		this(new Vector3D[] { v0, v1, v2, v3 });
	}

	/**
	 * Creates a new Polygon3D with the specified vertices. All the vertices are
	 * assumed to be in the same plane.
	 */
	public Polygon3D(Vector3D[] vertices) {
		this.v = vertices;
		numVertices = vertices.length;
		uuid = "ID_" + UUID.randomUUID().toString() + "poly";
	}

	/**
	 * Sets this polygon to the same vertices as the specified polygon.
	 */
	public void setTo(Polygon3D polygon) {
		numVertices = polygon.numVertices;

		ensureCapacity(numVertices);
		for (int i = 0; i < numVertices; i++) {
			v[i].setTo(polygon.v[i]);
		}
	}

	/**
	 * Ensures this polygon has enough capacity to hold the specified number of
	 * vertices.
	 */
	protected void ensureCapacity(int length) {
		if (v.length < length) {
			Vector3D[] newV = new Vector3D[length];
			System.arraycopy(v, 0, newV, 0, v.length);
			for (int i = v.length; i < newV.length; i++) {
				newV[i] = new Vector3D();
			}
			v = newV;
		}
	}

	/**
	 * Gets the number of vertices this polygon has.
	 */
	public int getNumVertices() {
		return numVertices;
	}

	/**
	 * Gets the vertex at the specified index.
	 */
	public Vector3D getVertex(int index) {
		return v[index];
	}

	/**
	 * Projects this polygon onto the view window.
	 * @param
	 */
	// public void project(ViewWindow view) {
	// for (int i = 0; i < numVertices; i++) {
	// view.project(v[i]);
	// }
	// }

	public static Document root;
	public static List<String> Features2s = new ArrayList<>();
	public static void print1(List ALL,String lon,String lat,String lowerCorner,String upperCorner,String namecopy,String fileNameNow) throws DocumentException, IOException {
		String Features1="";
		String Features2="";
		 String necessary="";
		final SAXReader sax1 = new SAXReader();// 创建一个SAXReader对象
		File file = new File("");
		File xmlFile1 = new File(new File(file.getCanonicalPath()),"template.gml");
		//final File xmlFile1 = new File("D:/数据部小程序/template.gml");// 根据指定的路径创建file对象

		final Document document1 = sax1.read(xmlFile1);// 获取document对象,如果文档无节点，则会抛出Exception提前结束
		if(root==null){
			root = sax1.read(xmlFile1);
			Element rootElement = root.getRootElement();
			Element cityObjectMember = rootElement.elements("cityObjectMember").get(0);
			rootElement.remove(cityObjectMember);
		}
		final Element root1 = document1.getRootElement();// 获取根节点
//			Element cityObjectMember = root1.elements("cityObjectMember").get(m-1);// 获取根节点
		Element cityObjectMember = root1.elements("cityObjectMember").get(0);// 获取根节点

//写入中心点
		Element Building = cityObjectMember.elements("Building").get(0);
		Element boundedBy = Building.elements("boundedBy").get(0);
		Element Envelope = boundedBy.elements("Envelope").get(0);
		//System.out.println("lowerCorner------------------------------------------1" + lowerCorner);
		//System.out.println("upperCorner------------------------------------------1" + upperCorner);
		Envelope.addElement("gml:lowerCorner").setText(lowerCorner);
		Envelope.addElement("gml:upperCorner").setText(upperCorner);
		//写入objname名字
		Element stringAttribute = Building.elements("stringAttribute").get(0);
		stringAttribute.addElement("gen:value").setText(namecopy);

//加入偏移值
if(lon=="")
{
	lon="0";
	lat="0";
}
		System.out.println("加入x坐标偏移值"+lon+"加入y坐标偏移值"+lat);
		for (int j = 0; j <  ALL.size(); j++) {
			Element boundedBy1 = cityObjectMember.addElement("bldg:boundedBy");
			Element WallSurface = boundedBy1.addElement("bldg:WallSurface");
			//WallSurface.addAttribute("gml:id", uuid);
			WallSurface.addAttribute("gml:id", "0_31425669-79b4-4105-af89-5e13a2aec202");

			Element lod2MultiSurface = WallSurface.addElement("bldg:lod2MultiSurface");
			Element MultiSurface = lod2MultiSurface.addElement("gml:MultiSurface");
			Element surfaceMember = MultiSurface.addElement("gml:surfaceMember");
			Element Polygon = surfaceMember.addElement("gml:Polygon");
			//System.out.println("往building里加节点模板====================================3");
			//Polygon.setText("gml:id=\"b38fa4ac-277f-46f6-b93Building4-cc3653c3d9b7_poly\"");
			//Polygon.addAttribute("gml:id", uuid + "_poly");
			Polygon.addAttribute("gml:id", "0_31425669-79b4-4105-af89-5e13a2aec202" + "_poly");
			Element exterior = Polygon.addElement("gml:exterior");
			Element LinearRing = exterior.addElement("gml:LinearRing");
			Element posList = LinearRing.addElement("gml:posList");
			posList.addAttribute("srsDimension", "3");
			// System.out.println("Features================3"+Features);


			Features1 = ALL.get(j).toString();
			String point[] = Features1.split("\\)/\\(");
			if (point.length == 3) {
				point[0] =  point[0].replaceAll("\\(", "");
				point[0] = point[0].replaceAll("\\)", "");
				String point0[] = point[0].split(",");
				Double lon0 = Double.parseDouble(point0[0].replaceAll("/"," "))+Double.parseDouble(lon);
				Double lat0 = Double.parseDouble(point0[1].replaceAll("/"," "))+Double.parseDouble(lat);
				Double len = Double.parseDouble(point0[2].replaceAll("/"," "));
				point[0]=lon0+" "+lat0+" "+len;
				point[1] =  point[1].replaceAll("\\(", "");
				point[1] = point[1].replaceAll("\\)", "");
				String point1[] = point[1].split(",");
//				System.out.println(" point0[0].toString();"+ point0[0].toString());
//				System.out.println(" point0[1].toString();"+ point0[1].toString());
				Double lon1 = Double.parseDouble(point1[0].replaceAll("/"," "))+Double.parseDouble(lon);
				Double lat1 = Double.parseDouble(point1[1].replaceAll("/"," "))+Double.parseDouble(lat);
				Double len1 = Double.parseDouble(point1[2].replaceAll("/"," "));
				point[1]=lon1+" "+lat1+" "+len1;
				point[2] =  point[2].replaceAll("\\(", "");
				point[2] = point[2].replaceAll("\\)", "");
				//point[2] = point[2].replaceAll(",", "");
				String point2[] = point[2].split(",");
				Double lon2 = Double.parseDouble(point2[0].replaceAll("/"," "))+Double.parseDouble(lon);
				Double lat2 = Double.parseDouble(point2[1].replaceAll("/"," "))+Double.parseDouble(lat);
				Double len2 = Double.parseDouble(point2[2].replaceAll("/"," "));
				point[2]=lon2+" "+lat2+" "+len2;
				Features1=point[0] + " " + point[1 ] + " " + point[2 ] + " " + point[0];
				//System.out.println("循环转换Feature-----" + Features1);
				Features2 = "POLYGON(("+point[0] + "," + point[1 ] + "," + point[2 ] + "," + point[0]+"))" ;
			}
			if (point.length == 4) {
				point[0] =  point[0].replaceAll("\\(", "");
				point[0] = point[0].replaceAll("\\)", "");
				//point[0] = point[0].replaceAll(",", "");
				//point[0] = point[0].replaceAll("  ", " ");
				//System.out.println("point[0]"+point[0]);
				String point0[] = point[0].split(",");
//				System.out.println(" point0[0].toString();"+ point0[1].toString());
//				System.out.println(" point0[1].toString();"+ point0[2].toString());
//				System.out.println(" point0[1].toString();"+ point0[4].toString());
				Double lon0 = Double.parseDouble(point0[0].replaceAll("/"," "))+Double.parseDouble(lon);
				Double lat0 = Double.parseDouble(point0[1].replaceAll("/"," "))+Double.parseDouble(lat);
				Double len = Double.parseDouble(point0[2].replaceAll("/"," "));
				point[0]=lon0+" "+lat0+" "+len;
				point[1] =  point[1].replaceAll("\\(", "");
				point[1] = point[1].replaceAll("\\)", "");
				//point[1] = point[1].replaceAll(",", "");
				//System.out.println("point[0]"+point[1]);
				String point1[] = point[1].split(",");
//				System.out.println(" point0[0].toString();"+ point0[0].toString());
//				System.out.println(" point0[1].toString();"+ point0[1].toString());
				Double lon1 = Double.parseDouble(point1[0].replaceAll("/"," "))+Double.parseDouble(lon);
				Double lat1 = Double.parseDouble(point1[1].replaceAll("/"," "))+Double.parseDouble(lat);
				Double len1 = Double.parseDouble(point1[2].replaceAll("/"," "));
				point[1]=lon1+" "+lat1+" "+len1;
				point[2] =  point[2].replaceAll("\\(", "");
				point[2] = point[2].replaceAll("\\)", "");
				//point[2] = point[2].replaceAll(",", "");
				String point2[] = point[2].split(",");
				Double lon2 = Double.parseDouble(point2[0].replaceAll("/"," "))+Double.parseDouble(lon);
				Double lat2 = Double.parseDouble(point2[1].replaceAll("/"," "))+Double.parseDouble(lat);
				Double len2 = Double.parseDouble(point2[2].replaceAll("/"," "));
				point[2]=lon2+" "+lat2+" "+len2;
				point[3] =  point[3].replaceAll("\\(", "");
				point[3] = point[3].replaceAll("\\)", "");
				//point[3] = point[3].replaceAll(",", "");
				String point3[] = point[3].split(",");
				Double lon3 = Double.parseDouble(point3[0].replaceAll("/"," "))+Double.parseDouble(lon);
				Double lat3 = Double.parseDouble(point3[1].replaceAll("/"," "))+Double.parseDouble(lat);
				Double len3 = Double.parseDouble(point3[2].replaceAll("/"," "));
				point[3]=lon3+" "+lat3+" "+len3;
				Features1=point[0] + " " + point[1 ] + " " + point[2 ] + " " + point[3 ] +" "+ point[0];
				//System.out.println("循环转换Feature-----" + Features1);
				Features2 = "POLYGON(("+point[0] + "," + point[1 ] + "," + point[2 ] + ","  + point[3 ]+"," + point[0] +"))";
//
			}

			posList.setText(Features1);
			Features2s.add(Features2);

		}
		root.getRootElement().add((Element)cityObjectMember.clone());
		//System.out.println("数据循环转换中..." + Features2s);
		//writeXml(document1, "D:/数据部小程序/"+fileNameNow+".gml");


//写polygon到shp
		/*String typeName;
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


			//System.out.println(Features1+"//////");
			// 拼装wkt后插入记录

			//"POLYGON ((407.5078 294.6238 24.1097,401.7158 294.4789 44.1097,402.0869 283.6161 44.1097,407.8789 283.761 24.1097,407.5078 294.6238 24.1097))"

			for (int j = 0; j <  Features2s.size(); j++) {
				Features2=	Features2s.get(j);
				Geometry poly = reader
						.read(Features2);
				Object[] obj = { poly };
				SimpleFeature feature = featureBuilder.buildFeature(null, obj);
				features.add(feature);
			}

			boolean isSuc = new WriteShp().addFeature(
					(ShapefileDataStore) filedataStore, features);
			System.out.println(fileNameNow+".obj"+"数据转换结果："+isSuc);

		} catch (IOException | ParseException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}*/




	}

	public static void main(String[] args) throws IOException {
		File path = new File("");
		System.out.println(path.getAbsolutePath());
		System.out.println(path.getCanonicalPath());
		File file = new File(new File(path.getCanonicalPath()),/*File.separator +*/"src"/*+File.separator*/);
		System.out.println(file.getCanonicalPath());
		System.out.println(file.getAbsolutePath());
	}

	public static void toShp(String fileNameNow){
		String typeName;
		try {
			File path = new File("");
			File filePath = new File(new File(path.getCanonicalPath()),"shp");
			FileUtils.dirCopy(filePath.getCanonicalPath(), TransformPanel.outputPath+File.separator+"shp");
			// 向空白shp中插入记录
			//File file = new File("D:\\数据部小程序\\高\\gmltest\\shp模板\\template.shp");
			File file = new File(TransformPanel.outputPath+File.separator+"shp"+File.separator+"template.shp");
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


			//System.out.println(Features1+"//////");
			// 拼装wkt后插入记录

			//"POLYGON ((407.5078 294.6238 24.1097,401.7158 294.4789 44.1097,402.0869 283.6161 44.1097,407.8789 283.761 24.1097,407.5078 294.6238 24.1097))"

			for (int j = 0; j <  Features2s.size(); j++) {
				String Features2=	Features2s.get(j);
				Geometry poly = reader
						.read(Features2);
				Object[] obj = { poly };
				SimpleFeature feature = featureBuilder.buildFeature(null, obj);
				features.add(feature);
			}

			boolean isSuc = new WriteShp().addFeature(
					(ShapefileDataStore) filedataStore, features);
			System.out.println(fileNameNow+".obj"+"数据转换结果："+isSuc);

		} catch (IOException | ParseException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
	}
		//判断Features1坐标值是否有负数

//		for (int j = 0; j <  ALL.size(); j++) {
//			Features1 = ALL.get(j).toString();
//			if (Features1.contains("-")) {
//			 necessary="no";
//			}
//		}




//		//System.out.println("请输入坐标偏移值");
//		if(necessary!="no")
//		{
//
//		for (int j = 0; j <  ALL.size(); j++) {
//		Element boundedBy1 = cityObjectMember.addElement("bldg:boundedBy");
//		Element WallSurface = boundedBy1.addElement("bldg:WallSurface");
//		//WallSurface.addAttribute("gml:id", uuid);
//		WallSurface.addAttribute("gml:id", "0_31425669-79b4-4105-af89-5e13a2aec202");
//
//		Element lod2MultiSurface = WallSurface.addElement("bldg:lod2MultiSurface");
//		Element MultiSurface = lod2MultiSurface.addElement("bldg:MultiSurface");
//		Element surfaceMember = MultiSurface.addElement("bldg:surfaceMember");
//		Element Polygon = surfaceMember.addElement("bldg:Polygon");
//		//System.out.println("往building里加节点模板====================================3");
//		//Polygon.setText("gml:id=\"b38fa4ac-277f-46f6-b934-cc3653c3d9b7_poly\"");
//		//Polygon.addAttribute("gml:id", uuid + "_poly");
//		Polygon.addAttribute("gml:id", "0_31425669-79b4-4105-af89-5e13a2aec202" + "_poly");
//			Element exterior = Polygon.addElement("bldg:exterior");
//		Element LinearRing = exterior.addElement("bldg:LinearRing");
//		Element posList = LinearRing.addElement("bldg:posList");
//		// System.out.println("Features================3"+Features);
//
//			Features1 = ALL.get(j).toString();
//
//		String point[] = Features1.split("\\)\\(");
//
//
//		if(point.length==3)
//		{
//			point[0] =  point[0].replaceAll("\\(", " ");
//			point[0] = point[0].replaceAll("\\)", " ");
//			point[0] = point[0].replaceAll(",", " ");
//			point[1] =  point[1].replaceAll("\\(", " ");
//			point[1] = point[1].replaceAll("\\)", " ");
//			point[1] = point[1].replaceAll(",", " ");
//			point[2] =  point[2].replaceAll("\\(", " ");
//			point[2] = point[2].replaceAll("\\)", " ");
//			point[2] = point[2].replaceAll(",", " ");
//
//			Features1=point[0] + " " + point[1 ] + " " + point[2 ] + " " + point[0];
//			Features2 = "POLYGON(("+point[0] + "," + point[1 ] + "," + point[2 ] + "," + point[0]+"))" ;
//		}
//
//
//		if(point.length==4)
//		{
//
//
//			point[0] =  point[0].replaceAll("\\(", " ");
//			point[0] = point[0].replaceAll("\\)", " ");
//			point[0] = point[0].replaceAll(",", " ");
//			point[1] =  point[1].replaceAll("\\(", " ");
//			point[1] = point[1].replaceAll("\\)", " ");
//			point[1] = point[1].replaceAll(",", " ");
//			point[2] =  point[2].replaceAll("\\(", " ");
//			point[2] = point[2].replaceAll("\\)", " ");
//			point[2] = point[2].replaceAll(",", " ");
//			point[3] =  point[3].replaceAll("\\(", " ");
//			point[3] = point[3].replaceAll("\\)", " ");
//			point[3] = point[3].replaceAll(",", " ");
//			Features1=point[0] + " " + point[1 ] + " " + point[2 ] + " "  + point[3 ]+" " + point[0];
//			Features2 = "POLYGON(("+point[0] + "," + point[1 ] + "," + point[2 ] + ","  + point[3 ]+"," + point[0] +"))";
//
//		}
//
//		posList.setText(Features1);
//		Features2s.add(Features2);
//		}
//		}
//		writeXml(document1, "D:/数据部小程序/output.gml");
//
//
////写polygon到shp
//		String typeName;
//		try {
//			// 向空白shp中插入记录
//			File file = new File("D:\\数据部小程序\\高\\gmltest\\shp模板\\template.shp");
//			FileDataStore dataStore = null;
//			SimpleFeatureIterator iter = null;
//			FileDataStore filedataStore = FileDataStoreFinder
//					.getDataStore(file);
//
//			typeName = filedataStore.getTypeNames()[0];
//			SimpleFeatureSource featureSource = filedataStore
//					.getFeatureSource(typeName);
//			SimpleFeatureType shapeType = featureSource.getSchema();
//
//			List<SimpleFeature> features = new ArrayList<>();
//			SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(
//					shapeType);
//
//			GeometryFactory geometryFactory = JTSFactoryFinder
//					.getGeometryFactory(null);
//			WKTReader reader = new WKTReader(geometryFactory);
//
//
//			//System.out.println(Features1+"//////");
//			// 拼装wkt后插入记录
//
//			//"POLYGON ((407.5078 294.6238 24.1097,401.7158 294.4789 44.1097,402.0869 283.6161 44.1097,407.8789 283.761 24.1097,407.5078 294.6238 24.1097))"
//
//			for (int j = 0; j <  Features2s.size(); j++) {
//				Features2=	Features2s.get(j);
//				Geometry poly = reader
//						.read(Features2);
//				Object[] obj = { poly };
//				SimpleFeature feature = featureBuilder.buildFeature(null, obj);
//				features.add(feature);
//			}
//			boolean isSuc = new WriteShp().addFeature(
//					(ShapefileDataStore) filedataStore, features);
//			System.out.println(isSuc);
//
//		} catch (IOException | ParseException e1) {
//			// TODO 自动生成的 catch 块
//			e1.printStackTrace();
//		}
//
//
//
//
//	}
	public String print(int m) throws DocumentException, IOException {
		String Features="";
		for (Vector3D vertor : v) {
			//System.out.print(vertor.toString()+"$$$");

			Features+=vertor.toString()+"/";

		}
		//Features=Features.replace(" ","/");
		return Features;
		//System.out.println();
//		if(m!=0) {
//
//			final SAXReader sax1 = new SAXReader();// 创建一个SAXReader对象
//			final File xmlFile1 = new File("D:/数据部小程序/template.gml");// 根据指定的路径创建file对象
//			final Document document1 = sax1.read(xmlFile1);// 获取document对象,如果文档无节点，则会抛出Exception提前结束
//			final Element root1 = document1.getRootElement();// 获取根节点
////			Element cityObjectMember = root1.elements("cityObjectMember").get(m-1);// 获取根节点
//			Element cityObjectMember = root1.elements("cityObjectMember").get(0);// 获取根节点
//
//			Element boundedBy1 = cityObjectMember.addElement("bldg:boundedBy");
//			Element WallSurface = boundedBy1.addElement("bldg:WallSurface");
//			WallSurface.addAttribute("gml:id", uuid);
//			Element lod2MultiSurface = WallSurface.addElement("bldg:lod2MultiSurface");
//			Element MultiSurface = lod2MultiSurface.addElement("bldg:MultiSurface");
//			Element surfaceMember = MultiSurface.addElement("bldg:surfaceMember");
//			Element Polygon = surfaceMember.addElement("bldg:Polygon");
//			//System.out.println("往building里加节点模板====================================3");
//			//Polygon.setText("gml:id=\"b38fa4ac-277f-46f6-b934-cc3653c3d9b7_poly\"");
//			Polygon.addAttribute("gml:id", uuid + "_poly");
//			Element exterior = Polygon.addElement("bldg:exterior");
//			Element LinearRing = exterior.addElement("bldg:LinearRing");
//			Element posList = LinearRing.addElement("bldg:posList");
//		   // System.out.println("Features================3"+Features);
//		String point[] = Features.split("\\)\\(");
//
//
//			if(point.length==3)
//			{
//				Features = point[0] + " " + point[1 ] + " " + point[2 ] + " " + point[0] ;
//				Features = Features.replaceAll("\\(", " ");
//				Features = Features.replaceAll("\\)", " ");
//				Features = Features.replaceAll(",", " ");
//			}
//
//
//			if(point.length==4)
//			{
//				Features = point[0] + " " + point[1 ] + " " + point[2 ] + " " + point[3] + " " + point[0] ;
//				Features = Features.replaceAll("\\(", " ");
//				Features = Features.replaceAll("\\)", " ");
//				Features = Features.replaceAll(",", " ");
//			}
//
//			posList.setText(Features);
//			writeXml(document1, "D:/数据部小程序/template.gml");
//		}
	}
	/**
	 * 输出xml文件
	 *
	 * @param document
	 * @param filePath
	 * @throws IOException
	 */

	public static void writeXml(Document document, String filePath) throws IOException {

		File xmlFile = new File(filePath);

		XMLWriter writer = null;
		//System.out.println("XMLWriter");
		try {

			if (xmlFile.exists())

				xmlFile.delete();

			writer = new XMLWriter(new FileOutputStream(xmlFile), OutputFormat.createPrettyPrint());
			writer.write(document);

			writer.close();

		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();

		} catch (FileNotFoundException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			if (writer != null)

				writer.close();

		}

	}
	public List<Double> toList() {
		List<Double> list = new LinkedList<Double>();
		for (Vector3D vertor : v) {
			list.add(new Double(vertor.x));
			list.add(new Double(vertor.y));
			list.add(new Double(vertor.z));
		}
		return list;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}

package com.gis.toshp;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import org.geotools.data.DataUtilities;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.Transaction;
import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.feature.SchemaException;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.referencing.CRS;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.locationtech.jts.geom.MultiPolygon;

public class WriteShp {

	String srid = "32651"; // shp坐标系

	public ShapefileDataStore createShpfile(String path, String file,
                                            Map<String, Object> shpAttrs) {
		File out = null;
		try {
			if (path.endsWith("/")) {
				out = new File(path + file);
			} else {
				out = new File(path + "/" + file);
			}
			ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();
			Map<String, Serializable> params = new HashMap<>();
			params.put("url", out.toURI().toURL());
			params.put("create spatial index", Boolean.TRUE);

			ShapefileDataStore newDataStore = (ShapefileDataStore) dataStoreFactory
					.createNewDataStore(params);
			// newDataStore.createSchema(type);
			// 定义属性字段
			SimpleFeatureType TYPE = null;
			String attrs = "the_geom:Polygon";
			for (Map.Entry<String, Object> entry : shpAttrs.entrySet()) {
				System.out.println(entry.getKey() + ":" + entry.getValue());
				attrs += "," + entry.getKey() + ":" + entry.getValue();
			}
			TYPE = DataUtilities.createType("Location", attrs);
			newDataStore.createSchema(TYPE);
			newDataStore.setCharset(Charset.forName("GBK"));
			CoordinateReferenceSystem dataCrs = CRS.decode("EPSG:" + srid);
			newDataStore.forceSchemaCRS(dataCrs);
			return newDataStore;
		} catch (SchemaException | IOException | FactoryException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return null;
		}
	}

	public Boolean addFeature(ShapefileDataStore newDataStore,
                              List<SimpleFeature> features) {
		Transaction transaction = new DefaultTransaction("create");
		String typeName;
		try {
			typeName = newDataStore.getTypeNames()[0];

			SimpleFeatureSource featureSource = newDataStore
					.getFeatureSource(typeName);
			SimpleFeatureType shapeType = featureSource.getSchema(); // 插入记录
			if (featureSource instanceof SimpleFeatureStore) {
				SimpleFeatureStore featureStore = (SimpleFeatureStore) featureSource;
				SimpleFeatureCollection collection = new ListFeatureCollection(
						shapeType, features);
				featureStore.setTransaction(transaction);
				try {
					featureStore.addFeatures(collection);
					transaction.commit();
				} catch (Exception e) {
					e.printStackTrace();
					transaction.rollback();
				} finally {
					transaction.close();
				}

			} else {
				System.out.println(typeName
						+ " does not support read/write access.");
			}
		} catch (IOException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
			return false;
		}
		return true;
	}

	public static com.vividsolutions.jts.geom.Polygon createPolygon(List points) {
		GeometryFactory geometryFactory = JTSFactoryFinder
				.getGeometryFactory(null);
		Coordinate[] coords = (Coordinate[]) points
				.toArray(new Coordinate[points.size()]);
		com.vividsolutions.jts.geom.Polygon polygon = geometryFactory
				.createPolygon(coords);
		return polygon;

	}

}

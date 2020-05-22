package com.gis.toshp;

import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadShp {

	/**
	 * 
	 * @param file
	 *            shapefile�ļ�
	 * @throws IOException
	 *             �����ļ��쳣
	 */
	void resolveShp(String filepath) throws IOException {
		File file = new File(filepath);
		FileDataStore dataStore = null;
		SimpleFeatureIterator iter = null;
		try {
			dataStore = FileDataStoreFinder.getDataStore(file);
			// ע������ʽ,����Ϊ������ʽ����UTF-8
			((ShapefileDataStore) dataStore).setCharset(Charset.forName("GBK"));
			SimpleFeatureSource featureSource = dataStore.getFeatureSource();
			System.out.println(featureSource.getFeatures());
			// List<AttributeDescriptor> attrList = featureSource.getSchema()
			// .getAttributeDescriptors();
			// Map<String, Object> mapAttr = new HashMap<String, Object>();
			// for (AttributeDescriptor attr : attrList) {
			// System.out.println(attr.getLocalName() + ":����:"
			// + attr.getType().getBinding());
			// mapAttr.put(attr.getLocalName(), attr.getType().getBinding());
			// }
			SimpleFeatureCollection featureCollection = featureSource
					.getFeatures();
			// ÿ��featureΪһ����¼
			iter = featureCollection.features();
			while (iter.hasNext()) {
				SimpleFeature sf = iter.next();
				SimpleFeatureType sftype = sf.getFeatureType();
				int count = sftype.getAttributeCount();
				for (int i = 0; i < count; i++) {
					Object o = sf.getAttribute(i);
					if (o != null) {
						System.out.println(o.toString());
					}
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (iter != null) {
				iter.close();
			}
			if (dataStore != null) {
				dataStore.dispose();
			}
		}
	}

	public Map<String, Object> getShpAttributes(String filepath) {
		File file = new File(filepath);
		FileDataStore dataStore = null;
		SimpleFeatureIterator iter = null;
		Map<String, Object> mapAttr = new HashMap<String, Object>();
		try {
			dataStore = FileDataStoreFinder.getDataStore(file);
			// ע������ʽ,����Ϊ������ʽ����UTF-8
			((ShapefileDataStore) dataStore).setCharset(Charset.forName("GBK"));
			SimpleFeatureSource featureSource = dataStore.getFeatureSource();
			System.out.println(featureSource.getFeatures());
			List<AttributeDescriptor> attrList = featureSource.getSchema()
					.getAttributeDescriptors();
			for (AttributeDescriptor attr : attrList) {
				mapAttr.put(attr.getLocalName(), attr.getType().getBinding()
						.toString().replace("class ", ""));
			}
			System.out.println(mapAttr);
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} finally {
			dataStore.dispose();
		}
		return mapAttr;
	}
}

package com.gis;

import java.io.File;
import org.citygml4j.model.citygml.core.CityModel;

import com.gis.gui.MainGui;
import com.gis.gui.util.IContains;
import com.gis.loader.ObjectLoader;
import com.gis.loader.PolygonGroup;
//import com.gis.transform.CitygmlTransform;
import com.gis.util.CityGmlUtils;

public class Main {
	public static void main(String[] args) throws Exception {
		CityGmlUtils.getCityGMLFactory();
		MainGui gui = new MainGui();
	}

	public void test() throws Exception {
		// load
		ObjectLoader loader = new ObjectLoader();
		PolygonGroup group = loader.loadObject("D:/数据部小程序/智慧姐/jyc2174a.obj");

		// test
		group.print();

		// transform
//		CitygmlTransform transform = new CitygmlTransform(new File("test.xml"),
//				IContains.buildlingPlan[0]);
//		CityModel model = transform.getCityModelFormPolygonGroup(group);
//
//		// write
//		transform.wirteCityGml(model);
//		System.out.println("end write");
	}

}

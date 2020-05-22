/*
 * This file is part of citygml4j.
 * Copyright (c) 2007 - 2010
 * Institute for Geodesy and Geoinformation Science
 * Technische Universitaet Berlin, Germany
 * http://www.igg.tu-berlin.de/
 *
 * The citygml4j library is free software:
 * you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library. If not, see 
 * <http://www.gnu.org/licenses/>.
 */
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.citygml4j.CityGMLContext;
import org.citygml4j.builder.jaxb.JAXBBuilder;
import org.citygml4j.model.citygml.CityGML;
import org.citygml4j.model.citygml.CityGMLClass;
import org.citygml4j.model.citygml.building.AbstractOpening;
import org.citygml4j.model.gml.feature.AbstractFeature;
import org.citygml4j.model.module.citygml.CityGMLVersion;
import org.citygml4j.model.module.citygml.CoreModule;
import org.citygml4j.xml.io.CityGMLInputFactory;
import org.citygml4j.xml.io.CityGMLOutputFactory;
import org.citygml4j.xml.io.reader.CityGMLReader;
import org.citygml4j.xml.io.reader.FeatureReadMode;
import org.citygml4j.xml.io.reader.ParentInfo;
import org.citygml4j.xml.io.writer.CityModelInfo;
import org.citygml4j.xml.io.writer.CityModelWriter;
import org.citygml4j.xml.io.writer.FeatureWriteMode;


public class WritingCityModel {

	public static void main(String[] args) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("[HH:mm:ss] "); 

		System.out.println(df.format(new Date()) + "setting up citygml4j context and JAXB builder");
		CityGMLContext ctx = new CityGMLContext();
		JAXBBuilder builder = ctx.createJAXBBuilder();

		System.out.println(df.format(new Date()) + "reading CityGML file LOD3_Ettenheim_v100.xml chunk-wise");
		CityGMLInputFactory in = builder.createCityGMLInputFactory();
		in.setProperty(CityGMLInputFactory.FEATURE_READ_MODE, FeatureReadMode.SPLIT_PER_COLLECTION_MEMBER);
		in.setProperty(CityGMLInputFactory.KEEP_INLINE_APPEARANCE, Boolean.TRUE);

		CityGMLReader reader = in.createCityGMLReader(new File("../../datasets/LOD3_Ettenheim_v100.xml"));

		System.out.println(df.format(new Date()) + "creating CityGML 0.4.0 model writer");
		CityGMLOutputFactory out = builder.createCityGMLOutputFactory();

		// please note that in CityGML 0.4.0 it is not allowed to reference
		// BuildingPart,  BuildingInstallation, IntBuildingInstallation, Room, or
		// BuildingFurniture instances using an xlink:href. Both the CityModelWriter 
		// and the CityGMLWriter automatically take care of this fact when splitting features. 
		// Try and change the CityGMLVersion to v1_0_0 to see the difference!
		CityGMLVersion version = CityGMLVersion.v0_4_0;

		System.out.println(df.format(new Date()) + "splitting citygml4j object by feature members whilst writing to file");
		FeatureWriteMode writeMode = FeatureWriteMode.SPLIT_PER_COLLECTION_MEMBER;
		Class<?>[] excludeList = new Class<?>[]{AbstractOpening.class};
		boolean splitOnCopy = false;

		out.setCityGMLVersion(version);

		out.setProperty(CityGMLOutputFactory.FEATURE_WRITE_MODE, writeMode);
		out.setProperty(CityGMLOutputFactory.EXCLUDE_FROM_SPLITTING, excludeList);
		out.setProperty(CityGMLOutputFactory.SPLIT_COPY, splitOnCopy);

		CityModelWriter writer = out.createCityModelWriter(new File("LOD3_Ettenheim_split.xml"));
		writer.setPrefixes(version);
		writer.setDefaultNamespace(CoreModule.v0_4_0);
		writer.setSchemaLocations(version);
		writer.setIndentString("  ");
		writer.setHeaderComment("written by citygml4j", 
				"using a CityModelWriter instance", 
				"Split mode: " + writeMode, 
				"Split on copy: " + splitOnCopy);

		boolean isInited = false;

		while (reader.hasNextFeature()) {
			CityGML feature = reader.nextFeature();

			if (!isInited) {
				ParentInfo parentInfo = reader.getParentInfo();
				if (parentInfo != null && parentInfo.getCityGMLClass() == CityGMLClass.CITY_MODEL) {
					System.out.println(df.format(new Date()) + "setting original CityModel attributes for new CityModel");
					CityModelInfo cityModelInfo = new CityModelInfo(parentInfo);

					writer.setCityModelInfo(cityModelInfo);
					writer.writeStartDocument();

					isInited = true;
				}
			}

			if (feature instanceof AbstractFeature)
				writer.writeFeatureMember((AbstractFeature)feature);
		}

		writer.writeEndDocument();

		reader.close();
		writer.close();
		
		System.out.println(df.format(new Date()) + "CityGML file LOD3_Ettenheim_split.xml written");
		System.out.println(df.format(new Date()) + "sample citygml4j application successfully finished");
	}

}

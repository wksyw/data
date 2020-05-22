//package com.gis.transform;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//import org.citygml4j.factory.CityGMLFactory;
//import org.citygml4j.factory.GMLFactory;
//import org.citygml4j.factory.geometry.DimensionMismatchException;
//import org.citygml4j.factory.geometry.GMLGeometryFactory;
//import org.citygml4j.geometry.BoundingBox;
//import org.citygml4j.impl.gml.CodeImpl;
//import org.citygml4j.impl.gml.StringOrRefImpl;
//import org.citygml4j.model.citygml.appearance.Appearance;
//import org.citygml4j.model.citygml.appearance.AppearanceMember;
//import org.citygml4j.model.citygml.appearance.Color;
//import org.citygml4j.model.citygml.appearance.ParameterizedTexture;
//import org.citygml4j.model.citygml.appearance.TexCoordList;
//import org.citygml4j.model.citygml.appearance.TextureAssociation;
//import org.citygml4j.model.citygml.appearance.TextureCoordinates;
//import org.citygml4j.model.citygml.appearance.TextureType;
//import org.citygml4j.model.citygml.appearance.WrapMode;
//import org.citygml4j.model.citygml.appearance.X3DMaterial;
//import org.citygml4j.model.citygml.building.AbstractBuilding;
//import org.citygml4j.model.citygml.building.Building;
//import org.citygml4j.model.citygml.building.BuildingPart;
//import org.citygml4j.model.citygml.core.CityModel;
//import org.citygml4j.model.citygml.core.CityObjectMember;
//import org.citygml4j.model.gml.AbstractSurface;
//import org.citygml4j.model.gml.BoundingShape;
//import org.citygml4j.model.gml.Code;
//import org.citygml4j.model.gml.MultiSurface;
//import org.citygml4j.model.gml.Polygon;
//import org.citygml4j.model.gml.StringOrRef;
//import org.citygml4j.model.module.citygml.CityGMLVersion;
//import org.citygml4j.model.module.citygml.CoreModule;
//import org.citygml4j.xml.io.CityGMLOutputFactory;
//import org.citygml4j.xml.io.writer.CityGMLWriter;
//
//import com.gis.gui.util.FileUtil;
//import com.gis.gui.util.IContains;
//import com.gis.gui.util.Info;
//import com.gis.loader.Material;
//import com.gis.loader.Polygon3D;
//import com.gis.loader.PolygonGroup;
//import com.gis.loader.ShadedTexture;
//import com.gis.loader.Vector3D;
//import com.gis.util.CityGmlUtils;
//
//public class CitygmlTransform {
//	private CityGMLFactory cityGml;
//	private GMLFactory gml;
//	private GMLGeometryFactory geom;
//	private CityGMLOutputFactory out;
//	private File cityGmlFile;
//	private String imagePath;
//	private String buildlingPlan;
//	private Info info;
//
//	public CitygmlTransform(File file, String buildlingPlan) {
//		cityGmlFile = file;
//		imagePath = cityGmlFile.getParent() + File.separator + "appearance";
//		cityGml = CityGmlUtils.getCityGMLFactory();
//		gml = CityGmlUtils.getGMLFactory();
//		geom = CityGmlUtils.getGMLGeometryFactory();
//		out = CityGmlUtils.getCityGMLOutputFactory();
//		this.buildlingPlan = buildlingPlan;
//		info = Info.getInstance();
//	}
//
//	/** 将 PolygonGroup转换为 CityModel */
//	public CityModel getCityModelFormPolygonGroup(PolygonGroup group) {
//		Map<String, Material> apps = group.getMaterials();
//
//		for (Object obj : group.getObjects()) {
//			if (obj instanceof PolygonGroup) {
//				group = (PolygonGroup) obj;
//			}
//		}
//
//		// 创建cityModel
//		CityModel cityModel = cityGml.createCityModel();
//
//		// 增加BuildingPart
//		if (buildlingPlan.equals(IContains.buildlingPlan[0])) {
//			// 增加cityObjectMember
//			CityObjectMember cityObjectMember = cityGml
//					.createCityObjectMember();
//			cityModel.addCityObjectMember(cityObjectMember);
//
//			// 增加building
//			Building building = cityGml.createBuilding();
//			Code name = new CodeImpl();
//			name.setValue(group.getName());
//			building.addName(name);
//			cityObjectMember.setCityObject(building);
//			addBuildingPart(building, group, "");
//		} else if (buildlingPlan.equals(IContains.buildlingPlan[3])) {
//			findBuildling(group, cityModel);
//		}
//
//		// 增加appearance
//		if (apps != null) {
//			addApperance(cityModel, apps);
//		}
//		return cityModel;
//	}
//
//	private void findBuildling(PolygonGroup group, CityModel cityModel) {
//		PolygonGroup polygonGroup;
//		for (Object obj : group.getObjects()) {
//			if (obj instanceof PolygonGroup) {
//				polygonGroup = (PolygonGroup) obj;
//				if (polygonGroup.getBuildlingPlan().equals("Y")) {
//					// 增加cityObjectMember
//					CityObjectMember cityObjectMember = cityGml
//							.createCityObjectMember();
//					cityModel.addCityObjectMember(cityObjectMember);
//
//					// 增加building
//					Building building = cityGml.createBuilding();
//					Code name = new CodeImpl();
//					name.setValue(group.getName());
//					building.addName(name);
//					cityObjectMember.setCityObject(building);
//					addBuildingPart(building, group, "");
//				} else
//					findBuildling(polygonGroup, cityModel);
//			}
//		}
//
//	}
//
//	/** 递归增加BuildingPart */
//	private void addBuildingPart(AbstractBuilding parentPart,
//			PolygonGroup group, String buildingName) {
//		List<AbstractSurface> shell = new ArrayList<AbstractSurface>();
//		BoundingShape boundingShape;
//		String curBuildingName;
//		BoundingBox box;
//		Vector3D vector3D;
//		PolygonGroup polygonGroup;
//		Polygon3D polygon3D;
//		String uuid;
//		for (Object obj : group.getObjects()) {
//			// 如果是PolygonGroup则增加ConsistsOfBuildingPart(
//			if (obj instanceof PolygonGroup) {
//				polygonGroup = (PolygonGroup) obj;
//				if (!buildingName.equals(""))
//					curBuildingName = buildingName + "-"
//							+ polygonGroup.getName();
//				else
//					curBuildingName = polygonGroup.getName();
//				addBuildingPart(parentPart, polygonGroup, curBuildingName);
//			}
//			// 如果是Polygon3D则将Polygon增加到shell数组中
//			else {
//				polygon3D = (Polygon3D) obj;
//				try {
//					Polygon polygon = geom.createLinearPolygon(polygon3D
//							.toList(), 3);
//					polygon.setId(polygon3D.getUuid());
//					polygon.getExterior().getRing().setId(
//							polygon3D.getUuid().replace("poly", "ring"));
//					shell.add(polygon);
//					// if(polygon.gete){}
//				} catch (DimensionMismatchException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		// 增加CompositeSurface
//		if (shell.size() > 0) {
//			BuildingPart buildingPart = cityGml.createBuildingPart();
//			Code name = new CodeImpl();
//			name.setValue(buildingName);
//			buildingPart.addName(name);
//			uuid = "ID_" + UUID.randomUUID().toString().replace("-", "");
//			buildingPart.setId(uuid);
//			if (!group.getUsage().equals("")) {
//				buildingPart.addUsage(group.getUsage());
//			}
//			if (!group.getDescription().equals("")) {
//				StringOrRef descritption = new StringOrRefImpl();
//				descritption.setValue(group.getDescription());
//				buildingPart.setDescription(descritption);
//			}
//
//			// 设置边界
//			if (group.getBoundedBy().exist()) {
//				box = new BoundingBox();
//				vector3D = group.getBoundedBy().getLowerCorner();
//				box.setLowerCorner(new Double(vector3D.x), new Double(
//						vector3D.y), new Double(vector3D.z));
//				vector3D = group.getBoundedBy().getUpperCorner();
//				box.setUpperCorner(new Double(vector3D.x), new Double(
//						vector3D.y), new Double(vector3D.z));
//				boundingShape = gml.createBoundingShape(box);
//				buildingPart.setBoundedBy(boundingShape);
//			}
//
//			parentPart.addConsistsOfBuildingPart(cityGml
//					.createBuildingPartProperty(buildingPart));
//			MultiSurface multiSurface = gml.createMultiSurface(shell);
//			buildingPart.setLod1MultiSurface(gml
//					.createMultiSurfaceProperty(multiSurface));
//		}
//	}
//
//	private void addApperance(CityModel cityModel,
//			Map<String, Material> Materials) {
//		// 增加AppearanceMember
//		AppearanceMember appearanceMember = cityGml.createAppearanceMember();
//		cityModel.addAppearanceMember(appearanceMember);
//		Appearance appearance = cityGml.createAppearance();
//		appearanceMember.setAppearance(appearance);
//
//		ParameterizedTexture parameterizedTexture;
//		TextureCoordinates textureCoordinates;
//		TextureAssociation textureAssociation;
//		File objImageFile;
//		File cityImageFile;
//		TexCoordList texCoordList;
//		X3DMaterial x3Dmaterial;
//		Material objMaterial;
//		Color color;
//
//		File dir = new File(imagePath);
//		if (!dir.exists()) {
//			info.info("正在创建贴图目录appearance");
//			dir.mkdirs();
//		}
//
//		// 遍历材质，增加parameterizedTexture,x3Dmaterial
//		for (java.util.Map.Entry<String, Material> entry : Materials.entrySet()) {
//			objMaterial = entry.getValue();
//
//			// 设置贴图
//			if (objMaterial.getUri() != null) {
//				// 拷贝文件
//				objImageFile = new File(objMaterial.getUri());
//				info.info("正在复制贴图文件" + objImageFile.getName());
//				cityImageFile = new File(imagePath, objImageFile.getName());
//				if (cityImageFile.exists())
//					cityImageFile = new File(imagePath, UUID.randomUUID() + "."
//							+ FileUtil.getExt(objImageFile.getName()));
//				FileUtil.copyFiles(objImageFile, cityImageFile);
//
//				parameterizedTexture = cityGml.createParameterizedTexture();
//				parameterizedTexture.setImageURI("appearance" + "/"
//						+ cityImageFile.getName());
//				parameterizedTexture.setTextureType(TextureType.UNKNOWN);
//				parameterizedTexture.setWrapMode(WrapMode.WRAP);
//				appearance.addSurfaceDataMember(cityGml
//						.createSurfaceDataProperty(parameterizedTexture));
//
//				for (java.util.Map.Entry<String, ShadedTexture> targets : entry
//						.getValue().getTargets().entrySet()) {
//					textureCoordinates = cityGml.createTextureCoordinates();
//					textureCoordinates.setValue(targets.getValue().toList());
//					textureCoordinates.setRing(targets.getKey().replace("poly",
//							"ring"));
//					texCoordList = cityGml.createTexCoordList();
//					texCoordList.addTextureCoordinates(textureCoordinates);
//					textureAssociation = cityGml.createTextureAssociation();
//					textureAssociation.setUri(targets.getKey());
//					textureAssociation.setTextureParameterization(texCoordList);
//					parameterizedTexture.addTarget(textureAssociation);
//				}
//			}
//
//			x3Dmaterial = cityGml.createX3DMaterial();
//			// 设置环境强度
//			if (objMaterial.getAmbientIntensity() != null)
//				x3Dmaterial.setAmbientIntensity(0.01);
//			// 设置散射颜色
//			if (objMaterial.getDiffuseColor() != null) {
//				color = cityGml.createColor();
//				color.setBlue(objMaterial.getDiffuseColor().getBlue());
//				color.setRed(objMaterial.getDiffuseColor().getRed());
//				color.setGreen(objMaterial.getDiffuseColor().getGreen());
//				x3Dmaterial.setDiffuseColor(color);
//			}
//			// 设置镜面颜色
//			if (objMaterial.getSpecularColor() != null) {
//				color = cityGml.createColor();
//				color.setBlue(objMaterial.getSpecularColor().getBlue());
//				color.setRed(objMaterial.getSpecularColor().getRed());
//				color.setGreen(objMaterial.getSpecularColor().getGreen());
//				x3Dmaterial.setSpecularColor(color);
//			}
//			// 设置放射颜色
//			if (objMaterial.getEmissiveColor() != null) {
//				color = cityGml.createColor();
//				color.setBlue(objMaterial.getEmissiveColor().getBlue());
//				color.setRed(objMaterial.getEmissiveColor().getRed());
//				color.setGreen(objMaterial.getEmissiveColor().getGreen());
//				x3Dmaterial.setEmissiveColor(color);
//			}
//
//			x3Dmaterial.setTarget(new ArrayList<String>(objMaterial
//					.getTargets().keySet()));
//			appearance.addSurfaceDataMember(cityGml
//					.createSurfaceDataProperty(x3Dmaterial));
//		}
//
//	}
//
//	/**
//	 * 将citygml输出成文件
//	 *
//	 * @throws Exception
//	 **/
//	public void wirteCityGml(CityModel cityModel) throws Exception {
//		out.setCityGMLVersion(CityGMLVersion.v1_0_0);
//		CityGMLWriter writer = out.createCityGMLWriter(cityGmlFile, "utf-8");
//		writer.setPrefixes(CityGMLVersion.v1_0_0);
//		writer.setDefaultNamespace(CoreModule.v1_0_0);
//		writer.setSchemaLocations(CityGMLVersion.v1_0_0);
//		writer.setIndentString("  ");
//		writer.write(cityModel);
//		writer.close();
//	}
//
//}

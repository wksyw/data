package com.gis.loader;

import java.io.File;
import java.io.*;
import java.util.*;
import com.gis.gui.util.Info;

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
public class BoundedBy {
	private Vector3D lowerCorner;

	private Vector3D upperCorner;

	public Vector3D getLowerCorner() {
		return lowerCorner;
	}

	public void setLowerCorner(Vector3D lowerCorner) {
		this.lowerCorner = lowerCorner;
	}

	public Vector3D getUpperCorner() {
		return upperCorner;
	}

	public void setUpperCorner(Vector3D upperCorner) {
		this.upperCorner = upperCorner;
	}

	public boolean exist() {
		return lowerCorner != null;
	}

//	public void print() throws DocumentException, IOException {
//		final SAXReader sax = new SAXReader();// ����һ��SAXReader����
//		final File xmlFile = new File("D:/���ݲ�С����/template.gml");// ����ָ����·������file����
//		final Document document = sax.read(xmlFile);// ��ȡdocument����,����ĵ��޽ڵ㣬����׳�Exception��ǰ����
//		final Element root = document.getRootElement();// ��ȡ���ڵ�
//		Element cityObjectMember=root.addElement("cityObjectMember");
//		Element Building = cityObjectMember.addElement("Building");
//		Element boundedBy = Building.addElement("boundedBy");
//		Element Envelope = boundedBy.addElement("Envelope");
//		//��Building���½�objname
//		Element stringAttribute =Building.addElement("stringAttribute");
//		stringAttribute.addAttribute("name", "objname");
//		//��Building���½�lod2Solid
//		Element lod2Solid=Building.addElement("lod2Solid");
//		Element Solid= lod2Solid.addElement("Solid");
//		Element exterior= Solid.addElement("exterior");
//		Element CompositeSurface= exterior.addElement("CompositeSurface");
//
//
//
//
//		Element surfaceMember1 = CompositeSurface.addElement("bldg:surfaceMember");
//		String uuid = UUID.randomUUID().toString();
//		surfaceMember1.addAttribute("xlink:href", "#" + uuid + "_poly");
//		//System.out.println("lowerCorner:" + lowerCorner.toString());
//		//System.out.println("upperCorner:" + upperCorner.toString());
//		writeXml(document, "D:/���ݲ�С����/"+fileNameNow+".gml");
//	}
	public String print1() {
		return lowerCorner.toString()+upperCorner.toString();
	}
	/**
	 * ���xml�ļ�
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
	// ���±߽�
	public void update(Polygon3D polygon3D) {
		float minX, maxX;
		float minY, maxY;
		float minZ, maxZ;
		if (exist()) {
			minX = lowerCorner.x;
			minY = lowerCorner.y;
			minZ = lowerCorner.z;
			maxX = upperCorner.x;
			maxY = upperCorner.y;
			maxZ = upperCorner.z;
		} else {
			lowerCorner = new Vector3D();
			upperCorner = new Vector3D();
			minX = maxX = polygon3D.getVertex(0).x;
			minY = maxY = polygon3D.getVertex(0).y;
			minZ = maxZ = polygon3D.getVertex(0).z;
		}

		// ѡ��polygon�ı߽�
		for (int i = 0; i < polygon3D.getNumVertices(); i++) {
			if (polygon3D.getVertex(i).x < minX)
				minX = polygon3D.getVertex(i).x;
			if (polygon3D.getVertex(i).y < minY)
				minY = polygon3D.getVertex(i).y;
			if (polygon3D.getVertex(i).z < minZ)
				minZ = polygon3D.getVertex(i).z;

			if (polygon3D.getVertex(i).x > maxX)
				maxX = polygon3D.getVertex(i).x;
			if (polygon3D.getVertex(i).y > maxY)
				maxY = polygon3D.getVertex(i).y;
			if (polygon3D.getVertex(i).z > maxZ)
				maxZ = polygon3D.getVertex(i).z;
		}

		// ���±߽�
		lowerCorner.x = minX;
		lowerCorner.y = minY;
		lowerCorner.z = minZ;
		upperCorner.x = maxX;
		upperCorner.y = maxY;
		upperCorner.z = maxZ;
	}

	// ͨ��group���±߽�
	public void update(BoundedBy boundedBy) {
		if (!boundedBy.exist())
			return;
		if (!this.exist()) {
			lowerCorner = new Vector3D(boundedBy.getLowerCorner().x, boundedBy
					.getLowerCorner().y, boundedBy.getLowerCorner().z);
			upperCorner = new Vector3D(boundedBy.getUpperCorner().x, boundedBy
					.getUpperCorner().y, boundedBy.getUpperCorner().z);
		} else {
			if (lowerCorner.x > boundedBy.getLowerCorner().x)
				lowerCorner.x = boundedBy.getLowerCorner().x;
			if (lowerCorner.y > boundedBy.getLowerCorner().y)
				lowerCorner.y = boundedBy.getLowerCorner().y;
			if (lowerCorner.z > boundedBy.getLowerCorner().z)
				lowerCorner.z = boundedBy.getLowerCorner().z;
			if (upperCorner.x < boundedBy.getUpperCorner().x)
				upperCorner.x = boundedBy.getUpperCorner().x;
			if (upperCorner.y < boundedBy.getUpperCorner().y)
				upperCorner.y = boundedBy.getUpperCorner().y;
			if (upperCorner.z < boundedBy.getUpperCorner().z)
				upperCorner.z = boundedBy.getUpperCorner().z;
		}
	}
}

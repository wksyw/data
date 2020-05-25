package com.gis.loader;

import java.io.*;
import java.util.*;

import com.gis.gui.util.Info;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * The ObjectLoader class loads a subset of the Alias|Wavefront OBJ file
 * specification.
 */
public class ObjectLoader {

	/**
	 * The Material class wraps a ShadedTexture.
	 */
	// public static class Material {
	// public File sourceFile;
	// // public ShadedTexture texture;
	// }

	/**
	 * A LineParser is an interface to parse a line in a text file. Separate
	 * LineParsers are used for OBJ and MTL files.
	 */
	protected interface LineParser {
		public void parseLine(String line) throws IOException,
				NumberFormatException, NoSuchElementException;
	}

	protected File path;
	protected static List<Vector3D> vertices = new ArrayList<Vector3D>();
	protected static List<Vector2D> coordinates = new ArrayList<Vector2D>();
	protected static List<Vector3D> normals = new ArrayList<Vector3D>();
	protected static Material currentMaterial;
	public static HashMap<String, Material> materials = new HashMap<String, Material>();
	protected float ambientLightIntensity;
	protected static HashMap<String, LineParser> parsers;
	private PolygonGroup object;
	public PolygonGroup currentGroup;
	private Info info;
	public ObjectLoader() {

	}

	public static void clear(){
		vertices.clear();
		coordinates.clear();
		normals.clear();
		materials.clear();
		Polygon3D.root=null;
		Polygon3D.Features2s.clear();
	}

	/**
	 * Creates a new ObjectLoader.
	 */
	public ObjectLoader(String fileName) {
		/*vertices = new ArrayList<Vector3D>();
		coordinates = new ArrayList<Vector2D>();
		normals = new ArrayList<Vector3D>();*/
		parsers = new HashMap<String, LineParser>();
		parsers.put("obj", new ObjLineParser());
		parsers.put("mtl", new MtlLineParser());
		currentMaterial = null;
		info = Info.getInstance();
		File file = new File(fileName);
		object = new PolygonGroup("root");
		object.setFilename(file.getName());
		path = file.getParentFile();

		//vertices.clear();
		currentGroup = object;
		//parseFile(file.getName());

		// ���ò���
		if (materials != null) {
			object.setMaterials(materials);
		}
	}

	/**
	 * Loads an OBJ file as a PolygonGroup.
	 */
	public PolygonGroup loadObject(String fileName) throws IOException {
		File file = new File(fileName);
		object = new PolygonGroup("root");
		object.setFilename(file.getName());
		path = file.getParentFile();

		vertices.clear();
		currentGroup = object;
		//parseFile(file.getName());

		// ���ò���
		if (materials != null) {
			object.setMaterials(materials);
		}

		// Material objMaterial;
		// for (java.util.Map.Entry<String, Material> entry :
		// materials.entrySet()) {
		// objMaterial = entry.getValue();
		// System.out.println("**************");
		// System.out.println(objMaterial.getAmbientIntensity());
		// System.out.println(objMaterial.getDiffuseColor());
		// System.out.println(objMaterial.getEmissiveColor());
		// System.out.println(objMaterial.getSpecularColor());
		// System.out.println("**************");
		// }

		return object;
	}

	/**
	 * Gets a Vector3D from the list of vectors in the file. Negative indices
	 * count from the end of the list, positive indices count from the
	 * beginning. 1 is the first index, -1 is the last. 0 is invalid and throws
	 * an exception.
	 */
	protected Vector3D getVector(String indexStr) {
		int index = Integer.parseInt(indexStr);
		if (index < 0) {
			index = vertices.size() + index + 1;
		}
		return (Vector3D) vertices.get(index - 1);
	}

	/** �����ͼ���� */
	protected Vector2D getCoordinate(String indexStr) {
		int index = Integer.parseInt(indexStr);
		if (index < 0) {
			index = coordinates.size() + index + 1;
		}
		return coordinates.get(index - 1);
	}

	/** ��÷������� */
	protected Vector3D getNormal(String indexStr) {
		int index = Integer.parseInt(indexStr);
		if (index < 0) {
			index = coordinates.size() + index + 1;
		}
		return normals.get(index - 1);
	}

	/**
	 * Parses an OBJ (ends with ".obj") or MTL file (ends with ".mtl").
	 */
	public static void parseFile(File path,String filename) throws IOException {
		// get the file relative to the source path
		File file = new File(path, filename);
		BufferedReader reader = new BufferedReader(new FileReader(file));

		// get the parser based on the file extension
		LineParser parser = null;
		int extIndex = filename.lastIndexOf('.');
		if (extIndex != -1) {
			String ext = filename.substring(extIndex + 1);
			parser = (LineParser) parsers.get(ext.toLowerCase());
		}
		if (parser == null) {
			parser = (LineParser) parsers.get("obj");
		}

		// parse every line in the file
		while (true) {
			String line = reader.readLine();
			// no more lines to read
			if (line == null) {
				reader.close();
				return;
			}

			line = line.trim();

			// ignore blank lines and comments
			if (line.length() > 0 && !line.startsWith("#")) {
				// interpret the line
				try {
					parser.parseLine(line);
				} catch (NumberFormatException ex) {
					throw new IOException(ex.getMessage());
				} catch (NoSuchElementException ex) {
					throw new IOException(ex.getMessage());
				}
			}

		}
	}

	public void parse(String line) throws IOException {
		parsers.get("obj").parseLine(line);
	}

	protected class ObjLineParser implements LineParser {

		/** ����obj��ʽ�ļ� */
		public void parseLine(String line) throws IOException,
				NumberFormatException, NoSuchElementException {
			StringTokenizer tokenizer = new StringTokenizer(line);
			String command = tokenizer.nextToken();
			if (command.equals("v")) {
				// ����һ������
				vertices.add(new Vector3D(Float.parseFloat(tokenizer
						.nextToken()), Float.parseFloat(tokenizer.nextToken()),
						Float.parseFloat(tokenizer.nextToken())));
			} else if (command.equals("vt")) {
				// ����һ������
				coordinates
						.add(new Vector2D(Float.parseFloat(tokenizer
								.nextToken()), Float.parseFloat(tokenizer
								.nextToken())));
			} else if (command.equals("vn")) {
				// ����һ������
				normals.add(new Vector3D(Float
						.parseFloat(tokenizer.nextToken()), Float
						.parseFloat(tokenizer.nextToken()), Float
						.parseFloat(tokenizer.nextToken())));
			} else if (command.equals("f")) {
				// ����һ���� (flat, convex polygon)
				List<Vector3D> currVertices = new ArrayList<Vector3D>();
				List<Vector2D> curCoordinates = new ArrayList<Vector2D>();
				Vector3D curNormal = null;
				while (tokenizer.hasMoreTokens()) {
					String lineStr = tokenizer.nextToken();
					String indexStr = lineStr;
					// ���Ӷ�������
					int endIndex = lineStr.indexOf('/');
					if (endIndex != -1) {
						indexStr = lineStr.substring(0, endIndex);
						lineStr = lineStr.substring(endIndex + 1);
					}
					currVertices.add(getVector(indexStr));
					// ������ͼ����
					if (endIndex == -1)
						continue;
					endIndex = lineStr.indexOf('/');
					if (endIndex != -1) {
						indexStr = lineStr.substring(0, endIndex);
						lineStr = lineStr.substring(endIndex + 1);
					}
					curCoordinates.add(getCoordinate(indexStr));
					if (endIndex == -1)
						continue;
					curNormal = getNormal(lineStr);
				}
				// ����һ���в��ʵĶ����
				Vector3D[] array = new Vector3D[currVertices.size()];
				currVertices.toArray(array);
				TexturedPolygon3D poly = new TexturedPolygon3D(array);
				if (curNormal != null)
					poly.setNormal(curNormal);
				ShadedTexture shadedTexture = null;
				if (currentMaterial != null && currentMaterial.getUri() != null) {
					shadedTexture = new ShadedTexture();
					shadedTexture.setCoordinates(curCoordinates);
				}
				poly.setMaterial(currentMaterial);
				currentMaterial.getTargets().put("#"+poly.getUuid(), shadedTexture);
				currentGroup.addPolygon(poly);
			} else if (command.equals("g")) {
				// ������
				List<String> nameList = new ArrayList<String>();
				PolygonGroup groupParent = object;
				while (tokenizer.hasMoreTokens()) {
					nameList.add(tokenizer.nextToken());
				}
				Collections.reverse(nameList);
				for (String str : nameList) {
					if (groupParent.getGroup(str) != null) {
						currentGroup = groupParent = groupParent.getGroup(str);
					} else {
						currentGroup = new PolygonGroup(str);
						groupParent.addPolygonGroup(currentGroup);
						groupParent = currentGroup;
					}
				}
				info.info("���ڴ���group" + currentGroup.getName());
			} /*else if (command.equals("mtllib")) {
				// ���ļ��м���materials
				String name = tokenizer.nextToken();
				parseFile(name);
			} */else if (command.equals("usemtl")) {
				// �������ڵ�material
				String name = tokenizer.nextToken();
				currentMaterial = (Material) materials.get(name);
				if (currentMaterial == null) {
					System.out.println("no material: " + name);
				}
			}
		}
	}

	/** ���ز����ļ� */
	protected class MtlLineParser implements LineParser {
		public void parseLine(String line) throws IOException,
				NumberFormatException, NoSuchElementException {
			StringTokenizer tokenizer = new StringTokenizer(line);
			String command = tokenizer.nextToken();
			//ShadedTexture texture;
			String str;
			Material material = null;
			// ����һ���²���
			if (command.equals("newmtl")) {
				str = tokenizer.nextToken();
				material = new Material();
				currentMaterial = material;
				materials.put(str, material);
				//info.info("���ڴ�������" + str);
			} else if (command.toLowerCase().equals("ka")) {
				// ���û���ǿ��
				str = tokenizer.nextToken();
				if (currentMaterial != null && str.matches("^[0-9\\.]+$")) {
					currentMaterial
							.setAmbientIntensity(Double.parseDouble(str));
				}
			} else if (command.toLowerCase().equals("kd")) {
				// ����ɢ��ǿ��
				str = tokenizer.nextToken();
				if (currentMaterial != null && str.matches("^[0-9\\.]+$")) {
					currentMaterial.setDiffuseColor(new Color(str, tokenizer
							.nextToken(), tokenizer.nextToken()));
				}
			} else if (command.toLowerCase().equals("ks")) {
				// ���þ�����ɫ
				str = tokenizer.nextToken();
				if (currentMaterial != null && str.matches("^[0-9\\.]+$")) {
					currentMaterial.setSpecularColor(new Color(str, tokenizer
							.nextToken(), tokenizer.nextToken()));
				}
			} else if (command.toLowerCase().equals("ke")) {
				// ���÷�����ɫ
				str = tokenizer.nextToken();
				if (currentMaterial != null && str.matches("^[0-9\\.]+$")) {
					currentMaterial.setEmissiveColor(new Color(str, tokenizer
							.nextToken(), tokenizer.nextToken()));
				}
			} else if (command.contains("map")) {
				// ������ͼuri
				if (currentMaterial != null) {
					currentMaterial.setUri(tokenizer.nextToken());
					File file=new File(currentMaterial.getUri());
					if(!file.isAbsolute()){
						currentMaterial.setUri(path+"\\"+currentMaterial.getUri());
					}
					currentMaterial.setImageType(command.substring(command
							.indexOf("_")));
				}
			}
		}
	}

	public static void main(String[] args) throws IOException, DocumentException {
		ObjectLoader loader = new ObjectLoader();
		PolygonGroup group = loader.loadObject("D:/���ݲ�С����/�ǻ۽�/jyc2174a.obj");
		//group.print0();
//		System.out.println("group.print(); " + group.print());
//		List ALL=group.print();
//		Polygon3D.print1(ALL);
	}
}

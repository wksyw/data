package com.gis.loader;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.util.HashMap;

/**
 * The PolygonGroup is a group of polygons with a MovingTransform3D.
 * PolygonGroups can also contain other PolygonGroups.
 */
public class PolygonGroup {

	public String name;

	private List<Object> objects;

	private BoundedBy boundedBy;

	private String description;

	private String LOD;

	private String usage;

	private String buildlingPlan = "";

	private Map<String, Material> materials;

	public List<Object> getObjects() {
		return objects;
	}

	public void setObjects(List<Object> objects) {
		this.objects = objects;
	}

	// private MovingTransform3D transform;
	private String filename;
	private int iteratorIndex;
	public int n;
	public int m;
//	public String prename="";
	public String allfeatures="";
    HashMap<String,Integer> map =new HashMap<>();
	List<String> fileList = new ArrayList<>();
	public String boundedBySTR;
	String namecopy;
	/**
	 * Creates a new, empty PolygonGroup.
	 */
	public PolygonGroup() {
		this("unnamed");
	}

	/**
	 * Creates a new, empty PolygonGroup with the specified name.
	 */
	public PolygonGroup(String name) {
		setName(name);
		description = "";
		LOD = "";
		usage = "";
		objects = new ArrayList<Object>();
		boundedBy = new BoundedBy();
		// transform = new MovingTransform3D();
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * Gets the MovingTransform3D for this PolygonGroup.
	 */
	// public MovingTransform3D getTransform() {
	// return transform;
	// }

	public Map<String, Material> getMaterials() {
		return materials;
	}

	public void setMaterials(Map<String, Material> materials) {
		this.materials = materials;
	}

	/**
	 * Gets the name of this PolygonGroup.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of this PolygonGroup.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Adds a polygon to this group.
	 */
	public void addPolygon(Polygon3D o) {
		objects.add(o);
		updateBoundeBy(o);
	}

	/**
	 * Adds a PolygonGroup to this group.
	 */
	public void addPolygonGroup(PolygonGroup p) {
		objects.add(p);
	}

	public String getBuildlingPlan() {
		return buildlingPlan;
	}

	public void setBuildlingPlan(String buildlingPlan) {
		this.buildlingPlan = buildlingPlan;
	}

	/**
	 * Clones this polygon group. Polygon3Ds are shared between this group and
	 * the cloned group; Transform3Ds are copied.
	 */
	public Object clone() {
		PolygonGroup group = new PolygonGroup(name);
		group.setFilename(filename);
		for (int i = 0; i < objects.size(); i++) {
			Object obj = objects.get(i);
			if (obj instanceof Polygon3D) {
				group.addPolygon((Polygon3D) obj);
			} else {
				PolygonGroup grp = (PolygonGroup) obj;
				group.addPolygonGroup((PolygonGroup) grp.clone());
			}
		}
		// group.transform = (MovingTransform3D) transform.clone();
		return group;
	}

	// 计算边界
	public BoundedBy calculateBoundedBy() {
		if (boundedBy.exist())
			return boundedBy;
		for (int i = 0; i < objects.size(); i++) {
			Object obj = objects.get(i);
			if (obj instanceof PolygonGroup) {
				updateBoundeBy(((PolygonGroup) obj).calculateBoundedBy());
			}
		}
		return boundedBy;
	}
	public void print0(String lon,String lat,String high,String lowerCorner,String upperCorner,String fileNameNow) throws IOException, DocumentException {


		List ALL=print();
		 namecopy =printname();
	    Polygon3D.print1(ALL,lon,lat,high,lowerCorner,upperCorner,namecopy,fileNameNow);
	};
	public List<String> print() throws IOException, DocumentException {
		for (int i = 0; i < objects.size(); i++) {
			if (boundedBy.exist()) {
			}
			Object obj = objects.get(i);
			if (obj instanceof Polygon3D) {
				allfeatures=((Polygon3D) obj).print(m);
				fileList.add(allfeatures);
			} else {
				//递归循环遍历
				fileList=	((PolygonGroup) obj).print();
			}
		}
		return fileList;
	}
	public String printname() throws IOException, DocumentException {
		for (int i = 0; i < objects.size(); i++) {
			if (boundedBy.exist()) {
			}
			Object obj = objects.get(i);
			 namecopy=name;
			if (obj instanceof Polygon3D) {
				allfeatures=((Polygon3D) obj).print(m);
				fileList.add(allfeatures);
			} else {
				//递归循环遍历
				namecopy=	((PolygonGroup) obj).printname();
			}
		}
		return namecopy;
	}
	public void addnum()
	{
		n++;
	}
	public void addmnum()
	{

		m++;
	}

	public String print1() {

		//if(boundedBySTR!=null)  return boundedBySTR;
		if (boundedBy.exist()) {
			boundedBySTR = boundedBy.print1();
			return boundedBySTR;
		} else if (boundedBySTR == null) {
			for (int i = 0; i < objects.size(); i++) {
				Object obj = objects.get(i);
				//System.out.println("groupName:" + name);
				if (obj instanceof Polygon3D) {
					//System.out.println("print1:");
					// ((Polygon3D) obj).print1();
				} else {
					boundedBySTR = ((PolygonGroup) obj).print1();

				}
			}
			return boundedBySTR;
		} else {
			return boundedBySTR;
		}
	}
//	public void writename(String name,int n) throws DocumentException, IOException {
//		if (name != null ) {
//			final SAXReader sax = new SAXReader();// 创建一个SAXReader对象
//			final File xmlFile = new File("D:/数据部小程序/template.gml");// 根据指定的路径创建file对象
//			final Document document = sax.read(xmlFile);// 获取document对象,如果文档无节点，则会抛出Exception提前结束
//			final Element root = document.getRootElement();// 获取根节点
//			Element cityObjectMember = root.elements("cityObjectMember").get(n-1);
//			Element Building = cityObjectMember.elements("Building").get(0);
//
//			Element stringAttribute = Building.elements("stringAttribute").get(0);
//
//			Element value = stringAttribute.addElement("value");
//			value.setText(name);
//			writeXml(document, "D:/数据部小程序/"+fileNameNow+".gml");
//		}
//	}
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
	/**
	 * Gets the PolygonGroup in this group with the specified name, or null if
	 * none is found.
	 */
	public PolygonGroup getGroup(String name) {
		// check for this group
		if (this.name != null && this.name.equals(name)) {
			return this;
		}
		for (int i = 0; i < objects.size(); i++) {
			Object obj = objects.get(i);
			if (obj instanceof PolygonGroup) {
				PolygonGroup subgroup = ((PolygonGroup) obj).getGroup(name);
				if (subgroup != null) {
					return subgroup;
				}
			}
		}

		// group not found
		return null;
	}

	/**
	 * Updates the MovingTransform3Ds of this group and any subgroups.
	 */
	// public void update(long elapsedTime) {
	// transform.update(elapsedTime);
	// for (int i = 0; i < objects.size(); i++) {
	// Object obj = objects.get(i);
	// if (obj instanceof PolygonGroup) {
	// PolygonGroup group = (PolygonGroup) obj;
	// group.update(elapsedTime);
	// }
	// }
	// }

	@Override
	public String toString() {
		return name;
	}

	/**
	 * Resets the polygon iterator for this group.
	 */
	public void resetIterator() {
		iteratorIndex = 0;
		for (int i = 0; i < objects.size(); i++) {
			Object obj = objects.get(i);
			if (obj instanceof PolygonGroup) {
				((PolygonGroup) obj).resetIterator();
			}
		}
	}

	/**
	 * Checks if there is another polygon in the current iteration.
	 */
	public boolean hasNext() {
		return (iteratorIndex < objects.size());
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLOD() {
		return LOD;
	}

	public void setLOD(String lOD) {
		LOD = lOD;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public BoundedBy getBoundedBy() {
		return boundedBy;
	}

	public void setBoundedBy(BoundedBy boundedBy) {
		this.boundedBy = boundedBy;
	}

	/**
	 * Gets the next polygon in the current iteration, applying the
	 * MovingTransform3Ds to it, and storing it in 'cache'.
	 */
	// public void nextPolygonTransformed(Polygon3D cache) {
	// Object obj = objects.get(iteratorIndex);
	//
	// if (obj instanceof PolygonGroup) {
	// PolygonGroup group = (PolygonGroup) obj;
	// group.nextPolygonTransformed(cache);
	// if (!group.hasNext()) {
	// iteratorIndex++;
	// }
	// } else {
	// iteratorIndex++;
	// cache.setTo((Polygon3D) obj);
	// }
	//
	// cache.add(transform);
	// }

	// public boolean draw(Graphics2D g, PolygonGroup group) {
	// boolean visible = false;
	// group.resetIterator();
	// while (group.hasNext()) {
	// group.nextPolygonTransformed(temp);
	// visible |= draw(g, temp);
	// }
	// return visible;
	// }

	/** 更新边界 */
	public void updateBoundeBy(Polygon3D polygon3D) {
		boundedBy.update(polygon3D);
	}

	/** 更新边界 */
	public void updateBoundeBy(BoundedBy boundedBy) {
		this.boundedBy.update(boundedBy);
	}
}

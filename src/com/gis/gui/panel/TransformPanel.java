package com.gis.gui.panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.*;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;

import com.gis.loader.Polygon3D;
import com.gis.toshp.WriteShp;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import org.citygml4j.model.citygml.core.CityModel;

import com.gis.data.PublicData;
import com.gis.gui.MainGui;
import com.gis.gui.tree.GroupTree;
import com.gis.gui.util.GuiUtil;
import com.gis.gui.util.IContains;
import com.gis.gui.util.Info;
import com.gis.loader.ObjectLoader;
import com.gis.loader.PolygonGroup;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
//import com.gis.transform.CitygmlTransform;

public class TransformPanel extends JPanel {


	public static String Polygon3D2;
	public static String Polygon3D3;
	//公用uuid
	String uuid1 = UUID.randomUUID().toString();
	public static String  lowerCorner1;
	public static String  upperCorner1;
	public List  Polygon3D1;
	public  static int n;

	public static String outputPath;



	private static final long serialVersionUID = 490014062968293473L;

	private JList fileList;
	private DefaultListModel fileListModel;
	private JButton browseButton;
	private JButton removeButton;
	private JTextField exportText;

	private JTextField exportText1;
	private JTextField exportText2;
	private JTextField exportText3;

	private JButton exportBrowse;
	private JButton exportButton;
	private JButton loadButton;
	private JPanel filterPanel;
	private JComboBox buildingCombo;
	private JComboBox lodCombo;
	private GroupTree treePanel;
	private DefaultMutableTreeNode root;
	private MainGui mainGui;
	private Info info = Info.getInstance();

	public static ArrayList lowerCorner11;
	public static ArrayList upperCorner11;

	public static ArrayList lowerCorner33;
	public static ArrayList upperCorner33;

	public int m;
	//每个building包含的points组个数
	public static ArrayList pointsgroups;

	public static String lowerCorner;
	public static String upperCorner;
	public TransformPanel(MainGui mainGui) {
		this.mainGui = mainGui;
		initGui();
		addListener();
	}

	public void initGui() {
		fileList = new JList();
		browseButton = new JButton("browse");
		removeButton = new JButton("remove");
		exportText = new JTextField("");
		exportText.setEditable(false);

		//加入输入经纬度偏移的框
		exportText1 = new JTextField("");
		exportText2 = new JTextField("");
		exportText3 = new JTextField("");
		exportBrowse = new JButton("browse");
		exportButton = new JButton("export");
		loadButton = new JButton("load");
		//exportButton.setEnabled(false);

		filterPanel = new JPanel();
		// bulidling生成方案
		buildingCombo = new JComboBox(IContains.buildlingPlan);
		root = new DefaultMutableTreeNode("root");
		treePanel = new GroupTree(root);

		fileListModel = new DefaultListModel();
		fileList.setModel(fileListModel);
		fileList
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		// layout
		setLayout(new GridBagLayout());
		{
			JPanel row1 = new JPanel();
			JPanel buttons = new JPanel();
			add(row1, GuiUtil.setConstraints(0, 0, 1.0, .3,
					GridBagConstraints.BOTH, 10, 5, 5, 5));
			row1.setLayout(new GridBagLayout());
			{
				row1.setBorder(BorderFactory.createTitledBorder("导入"));
				row1.add(new JScrollPane(fileList), GuiUtil.setConstraints(0,
						0, 1.0, 1.0, GridBagConstraints.BOTH, 5, 5, 5, 5));
				row1.add(buttons, GuiUtil.setConstraints(1, 0, 0.0, 0.0,
						GridBagConstraints.BOTH, 5, 5, 5, 5));
				buttons.setLayout(new GridBagLayout());
				{
					buttons.add(browseButton, GuiUtil.setConstraints(0, 0, 0.0,
							0.0, GridBagConstraints.HORIZONTAL, 0, 0, 0, 0));
					GridBagConstraints c = GuiUtil.setConstraints(0, 1, 0.0,
							1.0, GridBagConstraints.HORIZONTAL, 5, 0, 5, 0);
					GridBagConstraints c1 = GuiUtil.setConstraints(0, 2, 0.0,
							1.0, GridBagConstraints.HORIZONTAL, 10, 0, 10, 0);
					c.anchor = GridBagConstraints.NORTH;
					c1.anchor = GridBagConstraints.NORTH;
					buttons.add(removeButton, c);
					buttons.add(exportButton, c1);
				}
				JPanel panel = new JPanel();
				panel.add(loadButton);
				row1.add(panel, GuiUtil.setConstraints(0, 1, 0.0, 0.0,
						GridBagConstraints.BOTH, 0, 0, 0, 3));
			}
		}
		{
			filterPanel.setBorder(BorderFactory.createTitledBorder("输入坐标偏移值"));
			filterPanel.setLayout(new GridBagLayout());
			add(filterPanel, GuiUtil.setConstraints(0, 1, 1.0, 0.0,
					GridBagConstraints.HORIZONTAL, 10, 5, 5, 5));
			{
				JLabel label = new JLabel("偏移经度:");
				filterPanel.add(label, GuiUtil.setConstraints(0, 0, 0.0, 0.0,
						GridBagConstraints.BOTH, 0, 5, 5, 5));
				GridBagConstraints c = GuiUtil.setConstraints(1, 0, 0.0, 0.0,
						GridBagConstraints.NONE, 0, 5, 5, 5);
				c.anchor = GridBagConstraints.WEST;
				exportText1=new JTextField("0",30);
				filterPanel.add(exportText1, c);

				label = new JLabel("偏移纬度:");
				filterPanel.add(label, GuiUtil.setConstraints(0, 1, 0.0, 0.0,
						GridBagConstraints.BOTH, 0, 5, 5, 5));
				c = GuiUtil.setConstraints(1, 1, 0.0, 0.0,
						GridBagConstraints.NONE, 0, 5, 5, 5);
				c.anchor = GridBagConstraints.WEST;
				exportText2=new JTextField("0",30);
				filterPanel.add(exportText2, c);

				label = new JLabel("偏移高度:");
				filterPanel.add(label, GuiUtil.setConstraints(0, 2, 0.0, 0.0,
						GridBagConstraints.BOTH, 0, 5, 5, 5));
				c = GuiUtil.setConstraints(1, 2, 0.0, 0.0,
						GridBagConstraints.NONE, 0, 5, 5, 5);
				c.anchor = GridBagConstraints.WEST;
				exportText3=new JTextField("0",30);
				filterPanel.add(exportText3, c);

				label = new JLabel("");
				c = GuiUtil.setConstraints(0, 3, 1.0, 0.0,
						GridBagConstraints.NONE, 0, 0, 0, 0);
				c.gridwidth = 2;
				filterPanel.add(label, c);
			}
		}
//		{
//			JPanel panel = new JPanel();
//			panel.setBorder(BorderFactory.createTitledBorder("数据"));
//			add(panel, GuiUtil.setConstraints(0, 2, 1.0, 1.0,
//					GridBagConstraints.BOTH, 0, 5, 5, 0));
//			panel.setLayout(new GridBagLayout());
//			{
//				JScrollPane scroll = new JScrollPane(treePanel);
//				scroll.setBorder(BorderFactory.createEtchedBorder());
//				panel.add(scroll, GuiUtil.setConstraints(0, 0, 1.0, 1.0,
//						GridBagConstraints.BOTH, 0, 0, 0, 5));
//			}
//		}
//		{
//			JPanel row3 = new JPanel();
//			row3.setBorder(BorderFactory.createTitledBorder("导出"));
//			add(row3, GuiUtil.setConstraints(0, 3, 1.0, 0.0,
//					GridBagConstraints.BOTH, 10, 5, 5, 5));
//			row3.setLayout(new GridBagLayout());
//			{
//				row3.add(exportText, GuiUtil.setConstraints(0, 0, 1.0, 1.0,
//						GridBagConstraints.BOTH, 5, 5, 5, 5));
//				row3.add(exportBrowse, GuiUtil.setConstraints(1, 0, 0.0, 0.0,
//						GridBagConstraints.NONE, 5, 5, 5, 5));
//				row3.add(exportButton, GuiUtil.setConstraints(0, 2, 0.0, 0.0,
//						GridBagConstraints.NONE, 5, 5, 5, 5));
//			}
//		}
	}

	public void addListener() {
		browseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadFile();
			}
		});

		Action remove = new RemoveAction();
		fileList.getInputMap().put(
				KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "remove");
		fileList.getInputMap().put(
				KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), "remove");
		fileList.getActionMap().put("remove", remove);
		removeButton.addActionListener(remove);

		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fileListModel.isEmpty()) {
					info.err("没有文件被选中");
					return;
				}
				new Thread() {
					public void run() {
						// 现在只做一个一个的导入
						//String filePath = (String) fileListModel.get(0);
						String filePath;
						//遍历文件
						for (int j = 0; j < fileListModel.size(); j++) {
							ObjectLoader.clear();
							filePath = (String) fileListModel.get(j);



						//获取文件名
						String fileNameNow = filePath.substring(filePath.lastIndexOf("\\") + 1);
						fileNameNow = fileNameNow.substring(0, fileNameNow.indexOf("."));
						List<ObjectLoader> loaders = new ArrayList<>();
						//System.out.print(fileNameNow);
							/*File file = new File(fileNameNow);
							File file = new File(file.getParentFile(), filename);*/
							try {
								BufferedReader reader = new BufferedReader(new FileReader(filePath));
								ObjectLoader loader = new ObjectLoader(filePath);
								ObjectLoader.parseFile(new File(filePath).getParentFile(),fileNameNow+".mtl");
								while (true){
									String line = reader.readLine();
									// no more lines to read
									if (line == null) {
										reader.close();
										break;
									}

									line = line.trim();

									// ignore blank lines and comments
									if (line.length() > 0/* && !line.startsWith("#")*/) {
										/*if(line.contains("mtllib")){
											StringTokenizer tokenizer = new StringTokenizer(line);
											String command = tokenizer.nextToken();
											String name = tokenizer.nextToken();
											ObjectLoader.parseFile(new File(filePath).getParentFile(),name);
										}*/
										if(line.contains("object")){
											loader = new ObjectLoader(filePath);
											loaders.add(loader);
										}
										loader.parse(line);

										// interpret the line
										/*try {
											//parser.parseLine(line);
										} catch (NumberFormatException ex) {
											throw new IOException(ex.getMessage());
										} catch (NoSuchElementException ex) {
											throw new IOException(ex.getMessage());
										}*/
									}
								}
							} catch (FileNotFoundException ex) {
								ex.printStackTrace();
							} catch (IOException ex) {
								ex.printStackTrace();
							}
						try {
							loadButton.setEnabled(false);
							info.info("开始加载数据...");


							String lon = "0";
							String lat = "0";
							String high = "0";
							lon = exportText1.getText();
							lat = exportText2.getText();
							high = exportText3.getText();
							if (exportText1.getText().trim().equals("")) {
//空的情况执行
								// info.info("偏移不能为空，若不偏移请保留0");
								lon = "0";
							}
							if (exportText2.getText().trim().equals("")) {
//空的情况执行
								// info.info("偏移不能为空，若不偏移请保留0");
								lat = "0";
							}
							if (exportText3.getText().trim().equals("")) {
//空的情况执行
								// info.info("偏移不能为空，若不偏移请保留0");
								high = "0";
							}
//							if (lon == null || lon == "");
//							{
//								lon="0";
//							}
//							if (lat == null || lat == "");
//							{
//								lat="0";
//							}
							for (ObjectLoader loader:loaders) {
								PolygonGroup group = loader.currentGroup;
								String CornerString = group.print1();
								lowerCorner = CornerString.substring(1, CornerString.indexOf(")("));
								lowerCorner = lowerCorner.replaceAll(",", " ");
								upperCorner = CornerString.substring(CornerString.indexOf(")(") + 2, CornerString.length() - 1);
								upperCorner = upperCorner.replaceAll(",", " ");

								String lowerCornerxyz[] = lowerCorner.split(" ");
								String upperCornerxyz[] = upperCorner.split(" ");
								lowerCornerxyz[0] = String.valueOf(Double.parseDouble(lowerCornerxyz[0]) + Double.parseDouble(lon));
								lowerCornerxyz[1] = String.valueOf(Double.parseDouble(lowerCornerxyz[1]) + Double.parseDouble(lat));
								lowerCornerxyz[3] = String.valueOf(Double.parseDouble(lowerCornerxyz[3]) + Double.parseDouble(high));
								lowerCorner = lowerCornerxyz[0] + " " + lowerCornerxyz[1] + " " + lowerCornerxyz[3];
								upperCornerxyz[0] = String.valueOf(Double.parseDouble(upperCornerxyz[0]) + Double.parseDouble(lon));
								upperCornerxyz[1] = String.valueOf(Double.parseDouble(upperCornerxyz[1]) + Double.parseDouble(lat));
								upperCornerxyz[3] = String.valueOf(Double.parseDouble(upperCornerxyz[3]) + Double.parseDouble(high));
								upperCorner = upperCornerxyz[0] + " " + upperCornerxyz[1] + " " + upperCornerxyz[3];
								group.print0(lon, lat,high, lowerCorner, upperCorner, fileNameNow);
							}
							Polygon3D.writeXml(Polygon3D.root, outputPath+File.separator+fileNameNow+".gml");
							Polygon3D.toShp(fileNameNow);
							System.out.println("完成");
						} catch (IOException | DocumentException e1) {
							info.err(e1.getMessage());
							e1.printStackTrace();
						} finally {
							loadButton.setEnabled(true);
						}
					}
					}
				}.start();
			}
		});


		treePanel.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				// System.out.println("tree监听事件");
				if (treePanel.getLastSelectedPathComponent() == null)
					return;
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) treePanel
						.getLastSelectedPathComponent();
				Object selectObject = node.getUserObject();
				if (selectObject instanceof PolygonGroup) {
					mainGui.getTable().update((PolygonGroup) selectObject);
				}
			}
		});

		exportBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveFile();
			}
		});

		exportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int result = chooser.showSaveDialog(getTopLevelAncestor());
				if (result == JFileChooser.CANCEL_OPTION)
					return;
				outputPath=chooser.getSelectedFile().getPath();
				//System.out.println(outputPath);
			}
		});
	}

	private void saveFile() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"CityGML Files (*.gml, *.xml)", "gml", "xml");
		chooser.addChoosableFileFilter(filter);
		chooser.addChoosableFileFilter(chooser.getAcceptAllFileFilter());
		chooser.setFileFilter(filter);
		int result = chooser.showSaveDialog(getTopLevelAncestor());
		if (result == JFileChooser.CANCEL_OPTION)
			return;
		try {
			String exportString = chooser.getSelectedFile().toString();
			if ((!chooser.getSelectedFile().getName().contains("."))
					&& (!exportString.equals("")))
				exportString += ".gml";
			exportText.setText(exportString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadFile() {
		JFileChooser chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(true);
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"OBJ Files (*.obj)", "obj");
		chooser.addChoosableFileFilter(filter);
		chooser.addChoosableFileFilter(chooser.getAcceptAllFileFilter());
		chooser.setFileFilter(filter);

		int result = chooser.showOpenDialog(getTopLevelAncestor());
		if (result == JFileChooser.CANCEL_OPTION)
			return;

		fileListModel.clear();
		for (File file : chooser.getSelectedFiles())
			fileListModel.addElement(file.toString());

	}

	@SuppressWarnings("serial")
	private final class RemoveAction extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			if (fileList.getSelectedIndices().length > 0) {
				int[] selectedIndices = fileList.getSelectedIndices();
				int firstSelected = selectedIndices[0];

				for (int i = selectedIndices.length - 1; i >= 0; --i)
					fileListModel.removeElementAt(selectedIndices[i]);

				if (firstSelected > fileListModel.size() - 1)
					firstSelected = fileListModel.size() - 1;

				fileList.setSelectedIndex(firstSelected);
			}
		}
	}

	public GroupTree getTreePanel() {
		return treePanel;
	}

	public void setTreePanel(GroupTree treePanel) {
		this.treePanel = treePanel;
	}

	public void setFileListModel(DefaultListModel fileListModel) {
		this.fileListModel = fileListModel;
	}
}

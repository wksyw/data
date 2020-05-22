package com.gis.gui.tree;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import com.gis.loader.PolygonGroup;

public class GroupTree extends JTree {
	private static final long serialVersionUID = -7680030601122937426L;
	private DefaultMutableTreeNode root;

	public GroupTree(DefaultMutableTreeNode root) {
		super(root);
		this.root = root;
		initialize();
	}

	public void updateValue(Integer row, String value) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) this
				.getLastSelectedPathComponent();
		Object selectObject = node.getUserObject();
		if (selectObject instanceof PolygonGroup) {
			PolygonGroup group = (PolygonGroup) selectObject;
			switch (row) {
			case 0:
				group.setLOD(value);
				break;
			case 1:
				group.setName(value);
				this.updateUI();
				break;
			case 2:
				group.setDescription(value);
				break;
			case 3:
				group.setUsage(value);
				break;
			case 4:
				if (value.equals("Y"))
					group.setBuildlingPlan("Y");
				else
					group.setBuildlingPlan("");
				break;
			}
		}
	}

	public void updateTree(PolygonGroup groups) {
		root.removeAllChildren();
		updateNode(root, groups);
		this.updateUI();
	}

	public void updateNode(DefaultMutableTreeNode parent, PolygonGroup groups) {
		DefaultMutableTreeNode node;
		for (Object obj : groups.getObjects()) {
			if (obj instanceof PolygonGroup) {
				node = new DefaultMutableTreeNode(obj);
				parent.add(node);
				updateNode(node, (PolygonGroup) obj);
			}
		}
	}

	private void initialize() {
		this.setRootVisible(false);
	}

}

package com.gis.gui.table;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;

import com.gis.gui.tree.GroupTree;
import com.gis.loader.PolygonGroup;

public class GroupTable extends JTable {
	private GroupTree tree;

	private ModelListener listener;

	public GroupTable(Object[][] tableData, Object[] columnTitle, GroupTree tree) {
		super(tableData, columnTitle);
		this.tree = tree;
		listener = new ModelListener(tree, this.getModel());
	}

	public void setTree(GroupTree tree) {
		this.tree = tree;
	}

	public void update(PolygonGroup group) {
		TableModel model = this.getModel();
		model.removeTableModelListener(listener);
		model.setValueAt(group.getLOD(), 0, 1);
		model.setValueAt(group.getName(), 1, 1);
		model.setValueAt(group.getDescription(), 2, 1);
		model.setValueAt(group.getUsage(), 3, 1);
		model.setValueAt(group.getBuildlingPlan(), 3, 1);
		model.addTableModelListener(listener);
	}

	@Override
	public TableCellEditor getCellEditor(int row, int column) {
		if (row == 0 && column == 1)
			return new DefaultCellEditor(new JComboBox(new String[] { "LOD1",
					"LOD2", "LOD3" }));
		if (row == 4 && column == 1)
			return new DefaultCellEditor(new JComboBox(
					new String[] { "Y", "N" }));
		return super.getCellEditor(row, column);
	}

	class ModelListener implements TableModelListener {
		private GroupTree tree;
		private TableModel model;

		private Object[][] tableData = { new Object[] { "LOD", "" },
				{ "name", "" }, { "description", "" }, { "usage ", "" },
				{ "buildling ", "" } };

		public ModelListener(GroupTree tree, TableModel model) {
			this.tree = tree;
			this.model = model;
		}

		public void tableChanged(TableModelEvent e) {
			// System.out.println("table¼àÌýÊÂ¼þ");
			int column = e.getColumn();
			int row = e.getLastRow();
			if (column == 0) {
				model.removeTableModelListener(this);
				model.setValueAt(tableData[row][0], row, 0);
				model.addTableModelListener(this);
			} else {
				tree.updateValue(row, model.getValueAt(row, 1).toString());
			}
		}
	}
}

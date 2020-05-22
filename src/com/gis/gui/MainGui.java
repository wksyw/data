package com.gis.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

import com.gis.gui.panel.TransformPanel;
import com.gis.gui.table.GroupTable;
import com.gis.gui.util.GuiUtil;

public class MainGui extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextArea consoleText;
	private JLabel statusText;
	private JTabbedPane menu;
	private JSplitPane splitPane;
	private TransformPanel transformPanel;
	private JPanel console;
	private JLabel consoleLabel;
	private GroupTable table;
	private Object[][] tableData;
	private PrintStream out;
	private PrintStream err;

	// Look & Feel
	{
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager
					.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MainGui() {
		initGui();
		initConsole();
		addListener();
		addText();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	public void initGui() {
		menu = new JTabbedPane();
		transformPanel = new TransformPanel(this);

		menu.add(transformPanel, "");

		// 定义二维数组作为表格数据
		tableData = new Object[][] { new Object[] { "LOD", "" },
				{ "name", "" }, { "description", "" }, { "usage ", "" },
				{ "buildling ", "" } };
		// 定义一维数据作为列标题
		Object[] columnTitle = { "属性", "值" };
		table = new GroupTable(tableData, columnTitle, transformPanel
				.getTreePanel());

		consoleText = new JTextArea();
		consoleLabel = new JLabel();
		consoleText.setAutoscrolls(true);
		statusText = new JLabel();

		// consoleText.setFont(new Font("宋体", 0, 11));
		consoleText.setEditable(false);

		Border border = BorderFactory.createEtchedBorder();
		Border margin = BorderFactory.createEmptyBorder(0, 2, 0, 2);
		statusText.setBorder(new CompoundBorder(border, margin));
		statusText.setOpaque(true);
		statusText.setBackground(new Color(255, 255, 255));

		// layout
		this.setTitle("");
		setLayout(new GridBagLayout());

		JPanel leftTop = new JPanel();
		leftTop.setBorder(BorderFactory.createEmptyBorder());
		leftTop.setBackground(this.getBackground());
		leftTop.setLayout(new GridBagLayout());
		{
			leftTop.add(menu, GuiUtil.setConstraints(0, 0, 1.0, 1.0,
					GridBagConstraints.BOTH, 5, 5, 5, 5));
			JPanel status = new JPanel();
			status.setBorder(BorderFactory.createEmptyBorder());
			status.setBackground(this.getBackground());
			leftTop.add(status, GuiUtil.setConstraints(0, 1, 1.0, 0.0,
					GridBagConstraints.HORIZONTAL, 5, 5, 0, 5));
			status.setLayout(new GridBagLayout());
			{
				status.add(statusText, GuiUtil.setConstraints(0, 0, 1.0, 0.0,
						GridBagConstraints.HORIZONTAL, 0, 0, 0, 2));
			}
		}

		console = new JPanel();
		console.setBorder(BorderFactory.createEmptyBorder());
		console.setBackground(this.getBackground());
		console.setLayout(new GridBagLayout());
		{
			consoleLabel = new JLabel();
			console.add(consoleLabel, GuiUtil.setConstraints(0, 0, 0.0, 0.0,
					GridBagConstraints.BOTH, 5, 5, 0, 5));
			JScrollPane scroll = new JScrollPane(consoleText);
			scroll.setBorder(BorderFactory.createEtchedBorder());
			console.add(scroll, GuiUtil.setConstraints(0, 1, 1.0, 2.0,
					GridBagConstraints.BOTH, 0, 5, 5, 5));

//			JLabel label = new JLabel("Properties");
//			console.add(label, GuiUtil.setConstraints(0, 2, 0.0, 0.0,
//					GridBagConstraints.BOTH, 20, 5, 0, 5));

//			scroll = new JScrollPane(table);
//			scroll.setBorder(BorderFactory.createEtchedBorder());
//			scroll.setPreferredSize(console.getSize());
//			console.add(scroll, GuiUtil.setConstraints(0, 3, 1.0, 1.0,
//					GridBagConstraints.BOTH, 0, 5, 5, 5));
		}

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setContinuousLayout(true);
		splitPane.setBorder(BorderFactory.createEmptyBorder());
		splitPane.setOpaque(false);
		splitPane.setUI(new BasicSplitPaneUI() {
			@SuppressWarnings("serial")
			public BasicSplitPaneDivider createDefaultDivider() {
				return new BasicSplitPaneDivider(this) {
					public void setBorder(Border b) {
					}
				};
			}
		});

		splitPane.setLeftComponent(leftTop);
		splitPane.setRightComponent(console);

		this.add(splitPane, GuiUtil.setConstraints(0, 0, 1.0, 1.0,
				GridBagConstraints.BOTH, 0, 0, 0, 0));

		// finally adapt GUI elements according to config data
		// set window size
		Toolkit t = Toolkit.getDefaultToolkit();
		Dimension sz = t.getScreenSize();

		Integer x, y, width, height;
		width = sz.width - 200;
		height = sz.height - 200;
		if (height < 700)
			height = 700;
		x = (sz.width - width) / 2;
		y = (sz.height - height) / 2;

		setLocation(x, y);
		setSize(width, height);
		leftTop.setPreferredSize(new Dimension((int) (width * .5), 1));

	}

	public void initConsole() {
		JTextAreaOutputStream jTextwriter = new JTextAreaOutputStream(
				consoleText, new ByteArrayOutputStream());
		PrintStream writer;

		try {
			writer = new PrintStream(jTextwriter, true, "gbk");
		} catch (UnsupportedEncodingException e) {
			writer = new PrintStream(jTextwriter);
		}

		out = System.out;
		err = System.err;

		System.setOut(writer);
		System.setErr(writer);
	}

	public void addText() {
		setTitle("objloader");
		consoleLabel.setText("console");
		statusText.setText("Ready");
		menu.setTitleAt(0, "转换");
	}

	public void addListener() {
	}

	public static void main(String[] args) {
		MainGui gui = new MainGui();
	}

	private class JTextAreaOutputStream extends FilterOutputStream {
		private int MAX_DOC_LENGTH = 10000;
		private JTextArea ta;

		public JTextAreaOutputStream(JTextArea ta, OutputStream stream) {
			super(stream);
			this.ta = ta;
		}

		@Override
		public void write(final byte[] b) {
			try {
				ta.append(new String(b));
			} catch (Error e) {
				//
			}

			flush();
		}

		@Override
		public void write(final byte b[], final int off, final int len) {
			try {
				ta.append(new String(b, off, len));
			} catch (Error e) {
				//
			}

			flush();
		}

		public void flush() {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					ta.setCaretPosition(ta.getDocument().getLength());
					if (ta.getLineCount() > MAX_DOC_LENGTH)
						ta.setText("...truncating console output after "
								+ MAX_DOC_LENGTH + " log messages...");
				}
			});
		}
	}

	public GroupTable getTable() {
		return table;
	}

	public void setTable(GroupTable table) {
		this.table = table;
	}

}

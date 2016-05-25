import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.xmlbeans.impl.xb.xsdschema.impl.ImportDocumentImpl.ImportImpl;
import org.jfree.chart.ChartPanel;

import javax.swing.JComboBox;

public class Stat extends JPanel implements ActionListener, TableModelListener {

	/**
	 * Create the panel.
	 */

	private JTable table;
	public static DefaultTableModel defaultModel = null;
	private JButton importbutton;
	private JButton addbutton;
	private JButton subbutton;
	private JButton clearbutton;
	private String[] name = { "日期", "操作", "金额", "项目编号", "赎回操作" };
	private String dir = System.getProperty("user.dir");
	private JButton createbutton;
	private JButton exportbutton;
	private ChartPanel chartpanel;
	private List<String[]> projectlist = new ArrayList<String[]>();
	private ReadAndWrite raw;
	private JComboBox comboBox;
	private String linetype = "投资曲线";
	private boolean flag = true;
	private String oldvalue;
	public static boolean change=false;

	public Stat() {
		setLayout(null);
		importbutton = new JButton("导入");
		importbutton.setBounds(34, 30, 123, 29);
		importbutton.addActionListener(this);
		add(importbutton);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(172, 30, 1062, 365);
		add(scrollPane);
		raw = new ReadAndWrite();
		defaultModel = new DefaultTableModel(raw.readexcel(System
				.getProperty("user.dir") + "\\project.xls"), name);

		defaultModel.addTableModelListener(this);
		table = new JTable(defaultModel) {
			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 1)
					return false;
				return true;
			}
		};

		table.getColumnModel().getColumn(4)
				.setCellRenderer(new MyButtonRender());
		table.getColumnModel().getColumn(4)
				.setCellEditor(new MyButtonEditor(table));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				oldvalue = (String) table.getModel().getValueAt(
						table.getSelectedRow(), table.getSelectedColumn());
			}
		});

		scrollPane.setViewportView(table);

		addbutton = new JButton("增加");
		addbutton.setBounds(1259, 133, 123, 29);
		addbutton.addActionListener(this);
		add(addbutton);

		subbutton = new JButton("删除");
		subbutton.setBounds(1259, 241, 123, 29);
		subbutton.addActionListener(this);
		add(subbutton);

		clearbutton = new JButton("清空");
		clearbutton.setBounds(1259, 366, 123, 29);
		clearbutton.addActionListener(this);
		add(clearbutton);

		createbutton = new JButton("生成");
		createbutton.setBounds(1249, 802, 123, 29);
		createbutton.addActionListener(this);
		add(createbutton);

		exportbutton = new JButton("导出");
		exportbutton.setBounds(1259, 30, 123, 29);
		exportbutton.addActionListener(this);
		add(exportbutton);

		JLabel lblNewLabel = new JLabel("曲线类型");
		lblNewLabel.setBounds(1249, 722, 81, 21);
		add(lblNewLabel);

		comboBox = new JComboBox();
		comboBox.setBounds(1345, 719, 106, 27);
		comboBox.addItem("投资曲线");
		comboBox.addItem("收益曲线");
		comboBox.addItem("收益率曲线");
		comboBox.addActionListener(this);
		add(comboBox);
		

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addbutton) {
			AddColDialog adddialog = new AddColDialog(
					(MyFrame) getTopLevelAncestor(), true,
					defaultModel.getDataVector());
			adddialog.show();
			if (adddialog.issucess())
			{
				defaultModel.addRow(new Object[] {
						adddialog.getTimemessage().replace("-", ""), "购买",
						adddialog.getMoneymessage(), adddialog.getNamemesage(),
						new JButton("赎回") });
				change=true;
			}
		} else if (e.getSource() == subbutton) {
			int rowcount = defaultModel.getRowCount() - 1;// getRowCount返回行数，rowcount<0代表已经没有任何行了。
			if (rowcount >= 0) {
				int id = table.getSelectedRow();
				if (id != -1) {
					defaultModel.removeRow(id);
					defaultModel.setRowCount(rowcount);
					change=true;
				}

			}
		} else if (e.getSource() == importbutton) {
			JFileImportChooser jfc;
			jfc = new JFileImportChooser();
			jfc.setCurrentDirectory(new File(dir));
			jfc.setSelectedFile(new File("project.xls"));
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int result = jfc.showDialog(new JLabel(), "选择");
			if (result == 0) {
				File file = jfc.getSelectedFile();
				if (file.isFile()) {
					ReadAndWrite raw = new ReadAndWrite();
					System.out.println(dir);
					Object[][] Importdata=raw.readexcel(file.getAbsolutePath());
					Object[][] data = checkImportData(defaultModel.getDataVector(),Importdata);
					if (flag == false) {
						JOptionPane
								.showMessageDialog(null, "导入的文件数据有误或者重复或者为空或者投资超过了剩余投资");
						flag = true;
					}
					else{
						caculatecurrentmoney(Importdata);
					}
					defaultModel = new DefaultTableModel(data, name);
					defaultModel.addTableModelListener(this);
					table.setModel(defaultModel);
					table.getColumnModel().getColumn(4)
							.setCellRenderer(new MyButtonRender());
					table.getColumnModel().getColumn(4)
							.setCellEditor(new MyButtonEditor(table));
					change=true;
				}
			}
		} else if (e.getSource() == exportbutton) {
			Vector<Vector> data = defaultModel.getDataVector();
			SimpleDateFormat dateformat = new SimpleDateFormat(
					"yyyy-MM-dd_HHmmss");
			String name = dateformat.format(new Date());
			name = name + ".xls";
			FileSystemView fsv = FileSystemView.getFileSystemView();
			File path = fsv.getHomeDirectory();

			JFileExportChooser chooser = new JFileExportChooser();
			chooser.setCurrentDirectory(path);
			chooser.setSelectedFile(new File(name));
			chooser.setDialogTitle("保存文件");
			int result = chooser.showDialog(new JLabel(), "保存文件");

			if (result == 0) {
				raw.exportexcel(chooser.getSelectedFile().getAbsolutePath(),
						data);
			}

		} else if (e.getSource() == clearbutton) {
			defaultModel = new DefaultTableModel(null, name);
			defaultModel.addTableModelListener(this);
			table.setModel(defaultModel);
			table.getColumnModel().getColumn(4)
					.setCellRenderer(new MyButtonRender());
			table.getColumnModel().getColumn(4)
					.setCellEditor(new MyButtonEditor(table));
			change=true;
			if (chartpanel != null) {
				this.setVisible(false);
				this.remove(chartpanel);
				this.setVisible(true);
			}
		} else if (e.getSource() == createbutton) {
			Vector<Vector> data = defaultModel.getDataVector();
			int a = data.size();
			int b = data.get(0).size() - 1;
			for (int i = 0; i < a; i++) {
				for (int j = 0; j < b; j++)
					System.out.print(data.get(i).get(j) + " ");
				System.out.println();
			}
			switch (linetype) {
			case "投资曲线":
				try {
					InvestCurve invest = new InvestCurve();
					invest.init(data);
					invest.drawpanel();
					this.setVisible(false);
					if (chartpanel != null)
						this.remove(chartpanel);
					chartpanel = invest.getChartPanel();
					chartpanel.setBounds(172, 420, 1062, 400);
					this.add(chartpanel);
					this.setVisible(true);

				} catch (ParseException e1) {

					e1.printStackTrace();
				}

				break;
			case "收益曲线":
				try {
					IncomeCurve income = new IncomeCurve();
					income.init(data);
					income.drawpanel();
					this.setVisible(false);
					if (chartpanel != null)
						this.remove(chartpanel);
					chartpanel = income.getChartPanel();
					chartpanel.setBounds(172, 420, 1062, 400);
					this.add(chartpanel);
					this.setVisible(true);

				} catch (ParseException e1) {

					e1.printStackTrace();
				}
				break;
			case "收益率曲线":
				try {
					RateCurve ratecurve = new RateCurve();
					ratecurve.init(data);
					ratecurve.drawpanel();
					this.setVisible(false);
					if (chartpanel != null)
						this.remove(chartpanel);
					chartpanel = ratecurve.getChartPanel();
					chartpanel.setBounds(172, 420, 1062, 400);
					this.add(chartpanel);
					this.setVisible(true);

				} catch (ParseException e1) {

					e1.printStackTrace();
				}
				break;

			default:
				break;
			}

		} else if (e.getSource() == comboBox) {
			String type = comboBox.getSelectedItem().toString();
			switch (type) {
			case "投资曲线":
				linetype = "投资曲线";
				break;
			case "收益曲线":
				linetype = "收益曲线";
				break;
			case "收益率曲线":
				linetype = "收益率曲线";
				break;
			default:
				break;
			}
			System.out.println(linetype);
		}

	}

	public Object[][] checkImportData(Vector<Vector> befordata, Object[][] newdata) {
		Object[][] data;
		Object[][] beforedata1;
		int a = befordata.size();
		int b = 5;
		if (a == 0) {
			beforedata1 = null;

		} else {
			beforedata1 = new Object[a][b];
			for (int i = 0; i < a; i++)
				for (int j = 0; j < b; j++)
					beforedata1[i][j] = befordata.get(i).get(j);
		}

		if (newdata == null) {
			flag = false;
			return beforedata1;
		}
		int c = newdata.length;
		data = new Object[a + c][b];
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date initdate = null;
		Date currentdate = new Date();
		try {
			initdate = format.parse(Filter.message.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < c; i++) {
			try {
				Date date = format.parse((String) newdata[i][0]);
				if (!(initdate.before(date) && currentdate.after(date))) {
					flag = false;
					return beforedata1;
				}
			} catch (ParseException e) {
				flag = false;
				e.printStackTrace();
				return beforedata1;
			}
		}
		for (int i = 0; i < c; i++) {
			try {
				Double k = Double.parseDouble((String) newdata[i][2]);
			} catch (NumberFormatException e) {
				flag = false;
				return beforedata1;
			}

			if (newdata[i][1].equals("赎回")) {
				int k = 0;
				for (k = 0; k < c && k != i; k++)
					if (newdata[k][3].equals(newdata[i][3])
							&& newdata[k][1].equals("购买"))
						break;
				if (k == c) {
					flag = false;
					return beforedata1;
				}

			} else if (newdata[i][1].equals("购买")) {
				if (beforedata1 != null)
					for (int j = 0; j < a; j++)
						if (beforedata1[j][3].equals(newdata[i][3])
								&& beforedata1[j][1].equals(newdata[i][1])) {
							flag = false;
							return beforedata1;
						}
				for (int j = 0; j < c && j != i; j++)
					if (newdata[j][3].equals(newdata[i][3])) {
						flag = false;
						return beforedata1;
					}

			} else {
				flag = false;
				return beforedata1;
			}
		}
		if (beforedata1 != null)
			for (int i = 0; i < a; i++)
				for (int j = 0; j < b; j++)
					data[i][j] = beforedata1[i][j];
		Double sum=0.0;
		for (int i = a; i < a + c; i++)
		{
			sum+=Double.parseDouble((String)newdata[i-a][2]);
			for (int j = 0; j < b; j++)
				data[i][j] = newdata[i - a][j];
		}
		BigDecimal k1=new BigDecimal(sum);
		BigDecimal k2=new BigDecimal(Filter.message.getcurrentmoney());
		if(k1.compareTo(k2)==1){
			flag = false;
			return beforedata1;
		}
		return data;
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
		int col = e.getColumn();
		String message = (String) defaultModel.getValueAt(row, col);
		
		if (!message.equals(oldvalue)) {
			boolean flag = true;
			if (col != -1 && row != -1)
				flag = check(message, row, col);
			if (!flag)
			{
				defaultModel.setValueAt(oldvalue, row, col);
			}
			change=true;
		}
		
	}

	private boolean check(String currentdata, int row, int col) {
		switch (col) {
		case 0:
			Pattern pattern = Pattern
					.compile("[0-9][0-9][0-9][0-9]((0[0-9])|(1[0-2]))(([0-2][0-9])|(3[0-1]))");
			Matcher isdate = pattern.matcher(currentdata);
			if (!isdate.matches()) {
				JOptionPane.showMessageDialog(null, "请输入符合格式的时间");
				return false;
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			try {
				Date initdate = format.parse(Filter.message.getTime().replace(
						"-", ""));
				Date currentdate = new Date();
				Date date = format.parse(currentdata);
				if (!(initdate.before(date) && currentdate.after(date))) {
					JOptionPane.showMessageDialog(null,
							"该输入时间超过当前时间或者在初始设置时间之前");
					return false;
				}
			} catch (ParseException e) {
				JOptionPane.showMessageDialog(null, "请输入符合格式的时间");
				e.printStackTrace();
				return false;
			}

			break;
		case 2:
			System.out.println(currentdata);
			Double k;
			try {
				k = Double.parseDouble(currentdata);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "请输入数字！");
				return false;
			}
			BigDecimal k1 = new BigDecimal(k);
			Vector<Vector> olddata = defaultModel.getDataVector();
			int a = olddata.size();
			for (int i = 0; i < a; i++) {
				if (i == row)
					continue;
				if (olddata.get(i).get(3).equals(olddata.get(row).get(3))) {
					System.out.println("get");
					BigDecimal k2 = new BigDecimal(
							Double.parseDouble((String) olddata.get(i).get(2)));
					if (k1.compareTo(k2) == -1) {
						if (olddata.get(row).get(1).equals("赎回")) {
							JOptionPane.showMessageDialog(null,
									"赎回项目的金额要比购买的金额高");
							return false;
						}
					} else if (k1.compareTo(k2) == 1) {
						if (olddata.get(row).get(1).equals("购买")) {
							JOptionPane.showMessageDialog(null,
									"赎回项目的金额要比购买的金额高");
							return false;
						}
					}
					break;
				}
			}

		case 3:
			if(currentdata.trim().equals("")){
				JOptionPane.showMessageDialog(null, "项目名称不能为空");
				return false;
			}
			Vector<Vector> data = defaultModel.getDataVector();
			int num = data.size();
			for (int i = 0; i < num; i++)
				if (data.get(i).get(3).equals(currentdata)) {
					JOptionPane.showMessageDialog(null, "不能有重复的项目");
					return false;
				}
			break;

		default:
			break;
		}
		return true;

	}
	public void caculatecurrentmoney(Object[][] Importdata){
		int a=Importdata.length;
		Double sum=0.0;
		for(int i=0;i<a;i++){
			if(Importdata[i][1].equals("购买"))
				sum+=Double.parseDouble((String)Importdata[i][2]);
			else
				sum-=Double.parseDouble((String)Importdata[i][2]);
		}
		Filter.message.setcurrentmoney(Filter.message.getcurrentmoney()-sum);
		Filter.Displaymoney.setText("可投资金额："+Filter.message.getcurrentmoney()+"元");
	}

}

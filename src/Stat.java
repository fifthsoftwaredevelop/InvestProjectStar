import java.awt.Graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

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
	private String[] name = { "����", "����", "���", "��Ŀ���", "��ز���" };
	private String dir = null;
	private JButton createbutton;
	private JButton exportbutton;
	private ChartPanel chartpanel;
	private List<String[]> projectlist = new ArrayList<String[]>();
	private ReadAndWrite raw;
	private JComboBox comboBox;
	private String linetype = "Ͷ������";

	public Stat() {
		setLayout(null);
		importbutton = new JButton("����");
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
		table = new JTable(defaultModel);
		table.getColumnModel().getColumn(4)
				.setCellRenderer(new MyButtonRender());
		table.getColumnModel().getColumn(4)
				.setCellEditor(new MyButtonEditor(table));
		scrollPane.setViewportView(table);

		addbutton = new JButton("����");
		addbutton.setBounds(1259, 133, 123, 29);
		addbutton.addActionListener(this);
		add(addbutton);

		subbutton = new JButton("ɾ��");
		subbutton.setBounds(1259, 241, 123, 29);
		subbutton.addActionListener(this);
		add(subbutton);

		clearbutton = new JButton("���");
		clearbutton.setBounds(1259, 366, 123, 29);
		clearbutton.addActionListener(this);
		add(clearbutton);

		createbutton = new JButton("����");
		createbutton.setBounds(1249, 802, 123, 29);
		createbutton.addActionListener(this);
		add(createbutton);

		exportbutton = new JButton("����");
		exportbutton.setBounds(1259, 30, 123, 29);
		exportbutton.addActionListener(this);
		add(exportbutton);

		JLabel lblNewLabel = new JLabel("��������");
		lblNewLabel.setBounds(1249, 722, 81, 21);
		add(lblNewLabel);

		comboBox = new JComboBox();
		comboBox.setBounds(1345, 719, 106, 27);
		comboBox.addItem("Ͷ������");
		comboBox.addItem("��������");
		comboBox.addItem("����������");
		comboBox.addActionListener(this);
		add(comboBox);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addbutton) {

			defaultModel.addRow(new Object[] { null, null, null, null,
					new JButton("���") });
			System.out.println("kk");
		} else if (e.getSource() == subbutton) {
			System.out.println("nn");
			int rowcount = defaultModel.getRowCount() - 1;// getRowCount����������rowcount<0�����Ѿ�û���κ����ˡ�
			if (rowcount >= 0) {
				int id = table.getSelectedRow();
				if (id != -1) {
					defaultModel.removeRow(id);
					defaultModel.setRowCount(rowcount);
				}

			}
		} else if (e.getSource() == importbutton) {
			// List<String[]>list=new ArrayList<String[]>();
			JFileChooser jfc;
			if (dir != null)
				jfc = new JFileChooser(dir);
			else
				jfc = new JFileChooser();
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jfc.showDialog(new JLabel(), "ѡ��");
			File file = jfc.getSelectedFile();
			if (file.isFile()) {
				ReadAndWrite raw = new ReadAndWrite();
				dir = file.getAbsolutePath();
				defaultModel = new DefaultTableModel(raw.readexcel(file
						.getAbsolutePath()), name);
				table.setModel(defaultModel);
				table.getColumnModel().getColumn(4)
						.setCellRenderer(new MyButtonRender());
				table.getColumnModel().getColumn(4)
						.setCellEditor(new MyButtonEditor(table));

			}
		} else if (e.getSource() == exportbutton) {
			Vector<Vector> data = defaultModel.getDataVector();
			SimpleDateFormat dateformat = new SimpleDateFormat(
					"yyyy-MM-dd_HHmmss");
			String name = dateformat.format(new Date());
			name = name + ".xls";
			FileSystemView fsv = FileSystemView.getFileSystemView();
			File path = fsv.getHomeDirectory();

			JFileChooserDemo chooser = new JFileChooserDemo();
			chooser.setCurrentDirectory(path);
			chooser.setSelectedFile(new File(name));
			chooser.setDialogTitle("�����ļ�");
			int result = chooser.showDialog(new JLabel(), "�����ļ�");

			if (result == 0) {
				raw.exportexcel(chooser.getSelectedFile().getAbsolutePath(),
						data);
			}

		} else if (e.getSource() == clearbutton) {
			defaultModel = new DefaultTableModel(null, name);
			table.setModel(defaultModel);
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
			case "Ͷ������":
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
			case "��������":
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
			case "����������":
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
			case "Ͷ������":
				linetype = "Ͷ������";
				break;
			case "��������":
				linetype = "��������";
				break;
			case "����������":
				linetype = "����������";
				break;
			default:
				break;
			}
			System.out.println(linetype);
		}

	}

	@Override
	public void tableChanged(TableModelEvent e) {

	}
}

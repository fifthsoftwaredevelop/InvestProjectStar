import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayer;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;
import javax.swing.JList;

public class Filter extends JPanel implements ActionListener {

	/**
	 * Create the panel.
	 */
	private JPanel panel_1 = new JPanel();
	private int currentpage = 1;
	private int totalpage = 1;
	private ArrayList<Project> list = new ArrayList<Project>();
	private JButton fetchbutton;
	private JTextField time_min;
	private JTextField time_max;
	private JTextField money_min;
	private JTextField money_max;
	private JTextField rate_min;
	private JTextField rate_max;
	private ButtonGroup timegroup;
	private ButtonGroup moneygroup;
	private ButtonGroup rategroup;
	private ButtonGroup sortgroup;
	private JRadioButton time_no_limit;
	private JRadioButton time_six;
	private JRadioButton time_six_twelve;
	private JRadioButton time_twelve;
	private JRadioButton money_no_limit;
	private JRadioButton money_one;
	private JRadioButton money_one_five;
	private JRadioButton money_five_ten;
	private JRadioButton money_ten;
	private JRadioButton rate_no_limit;
	private JRadioButton rate_five;
	private JRadioButton rate_five_ten;
	private JRadioButton rate_ten;
	private JRadioButton sort_no_limit;
	private JRadioButton sort_money;
	private JRadioButton sort_time;
	private JRadioButton sort_rate;
	private JLabel project_one = new JLabel();
	private JLabel project_one_rate = new JLabel();
	private JLabel project_one_time = new JLabel();
	private JLabel project_one_money = new JLabel();
	private JButton project_one_button = new JButton();
	private JLabel project_two = new JLabel();
	private JLabel project_two_rate = new JLabel();
	private JLabel project_two_time = new JLabel();
	private JLabel project_two_money = new JLabel();
	private JButton project_two_button = new JButton();
	private JLabel project_three = new JLabel();
	private JLabel project_three_rate = new JLabel();
	private JLabel project_three_time = new JLabel();
	private JLabel project_three_money = new JLabel();
	private JButton project_three_button = new JButton();
	private JLabel project_four = new JLabel();
	private JLabel project_four_rate = new JLabel();
	private JLabel project_four_time = new JLabel();
	private JLabel project_four_money = new JLabel();
	private JButton project_four_button = new JButton();
	private JLabel project_five = new JLabel();
	private JLabel project_five_rate = new JLabel();
	private JLabel project_five_time = new JLabel();
	private JLabel project_five_money = new JLabel();
	private JButton project_five_button = new JButton();
	private JLabel project_six = new JLabel();
	private JLabel project_six_rate = new JLabel();
	private JLabel project_six_time = new JLabel();
	private JLabel project_six_money = new JLabel();
	private JButton project_six_button = new JButton();
	private JLabel project_seven = new JLabel();
	private JLabel project_seven_rate = new JLabel();
	private JLabel project_seven_time = new JLabel();
	private JLabel project_seven_money = new JLabel();
	private JButton project_seven_button = new JButton();
	private JLabel project_eight = new JLabel();
	private JLabel project_eight_rate = new JLabel();
	private JLabel project_eight_time = new JLabel();
	private JLabel project_eight_money = new JLabel();
	private JButton project_eight_button = new JButton();
	private JLabel project_nine = new JLabel();
	private JLabel project_nine_rate = new JLabel();
	private JLabel project_nine_time = new JLabel();
	private JLabel project_nine_money = new JLabel();
	private JButton project_nine_button = new JButton();
	private JLabel project_ten = new JLabel();
	private JLabel project_ten_rate = new JLabel();
	private JLabel project_ten_time = new JLabel();
	private JLabel project_ten_money = new JLabel();
	private JButton project_ten_button = new JButton();
	private JButton first;
	private JButton pre;
	private JButton next;
	private JButton last;
	private JLabel page;
	private Capture cap;
	private JLabel Displaymoney;
	private JButton changemoney;
	public  static Double investmoney;
	private ReadAndWrite raw;

	private JLabel projects[] = { project_one, project_two, project_three,
			project_four, project_five, project_six, project_seven,
			project_eight, project_nine, project_ten };
	private JLabel projects_rate[] = { project_one_rate, project_two_rate,
			project_three_rate, project_four_rate, project_five_rate,
			project_six_rate, project_seven_rate, project_eight_rate,
			project_nine_rate, project_ten_rate };
	private JLabel projects_time[] = { project_one_time, project_two_time,
			project_three_time, project_four_time, project_five_time,
			project_six_time, project_seven_time, project_eight_time,
			project_nine_time, project_ten_time };
	private JLabel projects_money[] = { project_one_money, project_two_money,
			project_three_money, project_four_money, project_five_money,
			project_six_money, project_seven_money, project_eight_money,
			project_nine_money, project_ten_money };
	private JButton projects_button[] = { project_one_button,
			project_two_button, project_three_button, project_four_button,
			project_five_button, project_six_button, project_seven_button,
			project_eight_button, project_nine_button, project_ten_button };
	private JTextField choosepage;
	private JLabel lblNewLabel;
	private String mintime = null;
	private String maxtime = null;
	private String minrate = null;
	private String maxrate = null;
	private String minmoney = null;
	private String maxmoney = null;
	private String order = null;

	public Filter() {

		panel_1.setBounds(0, 0, 876, 244);
		add(panel_1);
		panel_1.setLayout(null);
		setOpaque(false);
		setLayout(null);
		setTime();
		setmoney();
		setrate();
		setsort();
		fetchbutton = new JButton("筛选");
		fetchbutton.setBounds(905, 178, 123, 29);
		fetchbutton.addActionListener(this);
		add(fetchbutton);

		JLabel projectname = new JLabel();
		projectname.setBounds(10, 259, 109, 35);
		projectname.setFont(new Font("宋体", 1, 16));
		projectname.setText("投资项目");
		projectname.setForeground(Color.blue);
		add(projectname);

		JLabel label = new JLabel();
		label.setText("投资利率");
		label.setForeground(Color.BLUE);
		label.setFont(new Font("宋体", Font.BOLD, 16));
		label.setBounds(290, 259, 109, 35);
		add(label);

		JLabel label_1 = new JLabel();
		label_1.setText("投资期限");
		label_1.setForeground(Color.BLUE);
		label_1.setFont(new Font("宋体", Font.BOLD, 16));
		label_1.setBounds(573, 259, 109, 35);
		add(label_1);

		JLabel label_2 = new JLabel();
		label_2.setText("投资金额");
		label_2.setForeground(Color.BLUE);
		label_2.setFont(new Font("宋体", Font.BOLD, 16));
		label_2.setBounds(884, 259, 109, 35);
		add(label_2);

		for (int i = 0; i < 10; i++) {
			projects[i].setForeground(Color.DARK_GRAY);
			projects[i].setFont(new Font("宋体", Font.BOLD, 16));
			projects[i].setBounds(15, 320 + 50 * i, 228, 21);
			add(projects[i]);
			projects_rate[i].setForeground(Color.DARK_GRAY);
			projects_rate[i].setFont(new Font("宋体", Font.BOLD, 16));
			projects_rate[i].setBounds(288, 320 + 50 * i, 81, 21);
			add(projects_rate[i]);
			projects_time[i].setForeground(Color.DARK_GRAY);
			projects_time[i].setFont(new Font("宋体", Font.BOLD, 16));
			projects_time[i].setBounds(573, 320 + 50 * i, 81, 21);
			add(projects_time[i]);
			projects_money[i].setForeground(Color.DARK_GRAY);
			projects_money[i].setFont(new Font("宋体", Font.BOLD, 16));
			projects_money[i].setBounds(882, 320 + 50 * i, 171, 21);
			add(projects_money[i]);
			projects_button[i].setText("购买");
			projects_button[i].setVisible(true);
			projects_button[i].setBounds(1115, 320 + 50 * i, 123, 29);
			projects_button[i].addActionListener(this);
			add(projects_button[i]);
		}

		first = new JButton("首页");
		first.setVisible(false);
		first.setBounds(110, 831, 123, 29);
		first.addActionListener(this);
		add(first);

		pre = new JButton("上一页");
		pre.setVisible(false);
		pre.setBounds(327, 831, 123, 29);
		pre.addActionListener(this);
		add(pre);

		next = new JButton("下一页");
		next.setVisible(false);
		next.setBounds(559, 831, 123, 29);
		next.addActionListener(this);
		add(next);

		last = new JButton("尾页");
		last.setVisible(false);
		last.setBounds(778, 831, 123, 29);
		last.addActionListener(this);
		add(last);

		page = new JLabel("当前第1页");
		page.setBounds(916, 835, 156, 21);
		add(page);

		choosepage = new JTextField();
		choosepage.setColumns(10);
		choosepage.setBounds(1068, 832, 87, 27);
		choosepage.addActionListener(this);
		add(choosepage);

		lblNewLabel = new JLabel("页");
		lblNewLabel.setBounds(1157, 835, 81, 21);
		add(lblNewLabel);
		cap = new Capture();
		list = cap.get(mintime, maxtime, minrate, maxrate, minmoney, maxmoney,
				currentpage, order);
		setcurrentpage();
		page.setText("当前第" + currentpage + "页");
		String path=System.getProperty("user.dir")+"\\money.txt";
		raw=new ReadAndWrite();
		investmoney=raw.readtxt(path);
		System.out.println(investmoney);
		Displaymoney = new JLabel("可投资金额："+investmoney+"元");
		Displaymoney.setBounds(891, 15, 234, 29);
		Displaymoney.setFont(new Font("宋体", Font.BOLD, 16));
		add(Displaymoney);
		
		changemoney = new JButton("修改可投资");
		changemoney.setBounds(1130, 14, 123, 29);
		changemoney.addActionListener(this);
		add(changemoney);
	}

	private void setTime() {
		timegroup = new ButtonGroup();
		time_no_limit = new JRadioButton("不限");
		time_no_limit.setBounds(78, 11, 94, 29);
		time_no_limit.setSelected(true);
		time_no_limit.addActionListener(this);
		panel_1.add(time_no_limit);

		time_six = new JRadioButton("6个月以下");
		time_six.setBounds(184, 11, 94, 29);
		time_six.addActionListener(this);
		panel_1.add(time_six);

		time_six_twelve = new JRadioButton("6-12个月");
		time_six_twelve.setBounds(285, 10, 94, 29);
		time_six_twelve.addActionListener(this);
		panel_1.add(time_six_twelve);

		time_twelve = new JRadioButton("12个月以上");
		time_twelve.setBounds(375, 11, 94, 29);
		time_twelve.addActionListener(this);
		panel_1.add(time_twelve);

		timegroup.add(time_no_limit);
		timegroup.add(time_six);
		timegroup.add(time_six_twelve);
		timegroup.add(time_twelve);

		time_min = new JTextField();
		time_min.addActionListener(this);
		time_min.setBounds(476, 12, 87, 27);

		panel_1.add(time_min);
		time_min.setColumns(10);

		time_max = new JTextField();
		time_max.addActionListener(this);
		time_max.setColumns(10);
		time_max.setBounds(621, 12, 87, 27);
		panel_1.add(time_max);

		JLabel label = new JLabel("投资期限：");
		label.setBounds(15, 15, 81, 21);
		panel_1.add(label);

		JLabel label_1 = new JLabel("个月-");
		label_1.setBounds(570, 15, 50, 21);
		panel_1.add(label_1);

		JLabel label_2 = new JLabel("个月");
		label_2.setBounds(723, 15, 81, 21);
		panel_1.add(label_2);

	}

	private void setmoney() {

		JLabel label_3 = new JLabel("投资金额");
		label_3.setBounds(15, 70, 81, 21);
		panel_1.add(label_3);

		moneygroup = new ButtonGroup();
		money_no_limit = new JRadioButton("不限");
		money_no_limit.setBounds(78, 66, 94, 29);
		money_no_limit.setSelected(true);
		money_no_limit.addActionListener(this);
		panel_1.add(money_no_limit);

		money_one = new JRadioButton("一万元以下");
		money_one.setBounds(184, 66, 94, 29);
		money_one.addActionListener(this);
		panel_1.add(money_one);

		money_one_five = new JRadioButton("1-5万元");
		money_one_five.setBounds(285, 66, 94, 29);
		money_one_five.addActionListener(this);
		panel_1.add(money_one_five);

		money_five_ten = new JRadioButton("5-10万元");
		money_five_ten.setBounds(375, 66, 94, 29);
		money_five_ten.addActionListener(this);
		panel_1.add(money_five_ten);

		money_ten = new JRadioButton("10万元以上");
		money_ten.setBounds(476, 66, 94, 29);
		money_ten.addActionListener(this);
		panel_1.add(money_ten);
		moneygroup.add(money_no_limit);
		moneygroup.add(money_one);
		moneygroup.add(money_one_five);
		moneygroup.add(money_five_ten);
		moneygroup.add(money_ten);

		money_min = new JTextField();
		money_min.setColumns(10);
		money_min.setBounds(570, 67, 87, 27);
		money_min.addActionListener(this);
		panel_1.add(money_min);

		JLabel label_4 = new JLabel("万元-");
		label_4.setBounds(672, 70, 50, 21);
		panel_1.add(label_4);

		money_max = new JTextField();
		money_max.setColumns(10);
		money_max.setBounds(728, 67, 87, 27);
		money_max.addActionListener(this);
		panel_1.add(money_max);

		JLabel label_5 = new JLabel("万元");
		label_5.setBounds(830, 70, 81, 21);
		panel_1.add(label_5);

	}

	private void setrate() {
		JLabel label_6 = new JLabel("收益率");
		label_6.setBounds(15, 126, 81, 21);
		panel_1.add(label_6);

		rategroup = new ButtonGroup();
		rate_no_limit = new JRadioButton("不限");
		rate_no_limit.setBounds(78, 122, 94, 29);
		rate_no_limit.setSelected(true);
		rate_no_limit.addActionListener(this);
		panel_1.add(rate_no_limit);

		rate_five = new JRadioButton("5%以下");
		rate_five.setBounds(184, 122, 94, 29);
		rate_five.addActionListener(this);
		panel_1.add(rate_five);

		rate_five_ten = new JRadioButton("5%-10%");
		rate_five_ten.setBounds(285, 122, 94, 29);
		rate_five_ten.addActionListener(this);
		panel_1.add(rate_five_ten);

		rate_ten = new JRadioButton("10%以上");
		rate_ten.setBounds(375, 122, 94, 29);
		rate_ten.addActionListener(this);
		panel_1.add(rate_ten);

		rategroup.add(rate_no_limit);
		rategroup.add(rate_five);
		rategroup.add(rate_five_ten);
		rategroup.add(rate_ten);

		rate_min = new JTextField();
		rate_min.setColumns(10);
		rate_min.setBounds(476, 123, 87, 27);
		rate_min.addActionListener(this);

		panel_1.add(rate_min);

		JLabel label_7 = new JLabel("%-");
		label_7.setBounds(570, 126, 50, 21);
		panel_1.add(label_7);

		rate_max = new JTextField();
		rate_max.setColumns(10);
		rate_max.setBounds(591, 123, 87, 27);
		rate_max.addActionListener(this);
		panel_1.add(rate_max);

		JLabel label_8 = new JLabel("%");
		label_8.setBounds(682, 126, 81, 21);
		panel_1.add(label_8);
	}

	private void setsort() {
		JLabel label_9 = new JLabel("排序");
		label_9.setBounds(15, 174, 81, 21);
		panel_1.add(label_9);
		sortgroup = new ButtonGroup();
		sort_no_limit = new JRadioButton("默认");
		sort_no_limit.setBounds(78, 170, 94, 29);
		sort_no_limit.setSelected(true);
		sort_no_limit.addActionListener(this);
		panel_1.add(sort_no_limit);

		sort_money = new JRadioButton("投资金额");
		sort_money.setBounds(184, 170, 94, 29);
		sort_money.addActionListener(this);
		panel_1.add(sort_money);

		sort_time = new JRadioButton("投资期限");
		sort_time.setBounds(285, 170, 94, 29);
		sort_time.addActionListener(this);
		panel_1.add(sort_time);

		sort_rate = new JRadioButton("投资利率");
		sort_rate.setBounds(375, 170, 94, 29);
		sort_rate.addActionListener(this);
		panel_1.add(sort_rate);
		sortgroup.add(sort_no_limit);
		sortgroup.add(sort_money);
		sortgroup.add(sort_time);
		sortgroup.add(sort_rate);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == fetchbutton) {

			currentpage = 1;
			list = cap.get(mintime, maxtime, minrate, maxrate, minmoney,
					maxmoney, currentpage, order);

			setcurrentpage();
			page.setText("当前第" + currentpage + "页");

		}

		else if (e.getSource() == pre) {

			if (currentpage > 1)
				currentpage--;
			list = cap.get(mintime, maxtime, minrate, maxrate, minmoney,
					maxmoney, currentpage, order);
			setcurrentpage();
			page.setText("当前第" + currentpage + "页");

		} else if (e.getSource() == next) {

			currentpage++;
			list = cap.get(mintime, maxtime, minrate, maxrate, minmoney,
					maxmoney, currentpage, order);
			setcurrentpage();
			page.setText("当前第" + currentpage + "页");

		} else if (e.getSource() == last) {
			/*
			 * currentpage = totalpage; setcurrentpage(); page.setText("当前第" +
			 * currentpage + "页/共" + totalpage + "页");
			 */
		} else if (e.getSource() == first) {
			currentpage = 1;
			list = cap.get(mintime, maxtime, minrate, maxrate, minmoney,
					maxmoney, currentpage, order);
			setcurrentpage();
			page.setText("当前第" + currentpage + "页");
		} else if (e.getSource() == choosepage) {
			if (isNumeric(choosepage.getText().trim())) {
				int size = Integer.parseInt(choosepage.getText().trim());
				if (size != 0) {
					currentpage = size;
					System.out.println("size:" + size);
					list = cap.get(mintime, maxtime, minrate, maxrate,
							minmoney, maxmoney, currentpage, order);
					setcurrentpage();
					page.setText("当前第" + currentpage + "页");
					choosepage.setText("");
				}
			}

		} else if (e.getSource() == time_min) {
			if (time_min.getText().trim() == null)
				mintime = null;
			else
				mintime = String.valueOf((int) (Double.parseDouble(time_min
						.getText().trim()) * 30));

			timegroup.clearSelection();

		} else if (e.getSource() == time_max) {
			if (time_max.getText().trim() == null)
				maxtime = null;
			else
				maxtime = String.valueOf((int) (Double.parseDouble(time_max
						.getText().trim()) * 30));
			timegroup.clearSelection();

		} else if (e.getSource() == rate_min) {
			minrate = String.valueOf((Double.parseDouble(rate_min.getText()
					.trim()) / 100));
			rategroup.clearSelection();

		} else if (e.getSource() == rate_max) {
			maxrate = String.valueOf((Double.parseDouble(rate_max.getText()
					.trim()) / 100));
			rategroup.clearSelection();

		} else if (e.getSource() == money_min) {
			minmoney = String.valueOf((int) (Double.parseDouble(money_min
					.getText().trim()) * 10000));
			moneygroup.clearSelection();

		} else if (e.getSource() == money_max) {
			maxmoney = String.valueOf((int) (Double.parseDouble(money_max
					.getText().trim()) * 10000));
			moneygroup.clearSelection();

		} else if (e.getSource() == sort_no_limit) {
			order = null;

		} else if (e.getSource() == sort_money) {
			order = "TRANSFER_PRICE_ASC";

		} else if (e.getSource() == sort_rate) {
			order = "INVEST_RATE_ASC";

		} else if (e.getSource() == sort_time) {
			order = "INVEST_PERIOD_ASC";

		} else if (e.getSource() == time_no_limit) {
			mintime = maxtime = null;
			time_min.setText("");
			time_max.setText("");
		} else if (e.getSource() == time_six) {
			mintime = null;
			maxtime = String.valueOf(6 * 30);
			time_min.setText("");
			time_max.setText("");
		} else if (e.getSource() == time_six_twelve) {
			mintime = String.valueOf(6 * 30);
			maxtime = String.valueOf(12 * 30);
			time_min.setText("");
			time_max.setText("");
		} else if (e.getSource() == time_twelve) {
			mintime = String.valueOf(12 * 30);
			maxtime = null;
			time_min.setText("");
			time_max.setText("");
		} else if (e.getSource() == rate_no_limit) {
			minrate = maxrate = null;
			rate_min.setText("");
			rate_max.setText("");
		} else if (e.getSource() == rate_five) {
			minrate = null;
			maxrate = String.valueOf(5.0 / 100);
			rate_min.setText("");
			rate_max.setText("");
		} else if (e.getSource() == rate_five_ten) {
			minrate = String.valueOf(5.0 / 100);
			maxrate = String.valueOf(10.0 / 100);
			rate_min.setText("");
			rate_max.setText("");
		} else if (e.getSource() == rate_ten) {
			minrate = String.valueOf(10.0 / 100);
			maxrate = null;
			rate_min.setText("");
			rate_max.setText("");
		} else if (e.getSource() == money_no_limit) {
			minmoney = maxmoney = null;
			money_min.setText("");
			money_max.setText("");
		} else if (e.getSource() == money_one) {
			minmoney = null;
			maxmoney = String.valueOf(10000);
			money_min.setText("");
			money_max.setText("");
		} else if (e.getSource() == money_one_five) {
			minmoney = String.valueOf(10000);
			maxmoney = String.valueOf(50000);
			money_min.setText("");
			money_max.setText("");
		} else if (e.getSource() == money_five_ten) {
			minmoney = String.valueOf(50000);
			maxmoney = String.valueOf(100000);
			money_min.setText("");
			money_max.setText("");
		} else if (e.getSource() == money_ten) {
			minmoney = String.valueOf(100000);
			maxmoney = null;
			money_min.setText("");
			money_max.setText("");
		}
		else if(e.getSource()==changemoney){
	        MyDialog dlg=new MyDialog((MyFrame)(getTopLevelAncestor()),true);
	        dlg.show();
	        if(dlg.issucess())
	        {
	        investmoney=Double.parseDouble(dlg.getMoneymessage());
			Displaymoney.setText("可投资金额："+investmoney+"元");
	        }
			
		}
		else{
		
		for(int i=0;i<10;i++)
			if(projects_button[i]==e.getSource()){
				Double k=list.get(i).getMoney();
				if(k.compareTo(investmoney)==1){
					break;
				}
				investmoney-=k;
				String[] data=new String[4];
				Date date=new Date();
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
				data[0]=format.format(date);
				data[1]="购买";
				data[2]=String.valueOf(list.get(i).getMoney());
				data[3]=list.get(i).getName();
				System.out.println(data[0]);
				System.out.println(data[1]);
				System.out.println(data[2]);
				System.out.println(data[3]);
				try {
					raw.writeexcel(System.getProperty("user.dir")+"\\project.xls", data);
				} catch (IOException e1) {
				
					e1.printStackTrace();
					break;
				}
				
				cap.StartBrower(list.get(i).getUrl());
				Displaymoney.setText("可投资"+investmoney+"元");
				break;
			}
		
		
		}

	}

	public void setprojectvisible(int i, int len, boolean flag) {
		for (; i < len; i++) {
			projects[i].setVisible(flag);
			projects_rate[i].setVisible(flag);
			projects_time[i].setVisible(flag);
			projects_money[i].setVisible(flag);
			projects_button[i].setVisible(flag);
		}

	}

	public void setfenyebuttonvisivble(boolean flag) {
		first.setVisible(flag);
		pre.setVisible(flag);
		next.setVisible(flag);
		last.setVisible(flag);
	}

	public void setcurrentpage() {
		if (list != null) {
			if (list.size() != 0) {
				int len = list.size();
				setprojectvisible(0, len, true);
				setprojectvisible(len, 10, false);
				setfenyebuttonvisivble(true);
				for (int i = 0; i < len; i++) {
					projects[i].setText(list.get(i).getName());
					projects_rate[i].setText(list.get(i).getRate() + "%");
					projects_time[i].setText(list.get(i).getTime());
					projects_money[i].setText(list.get(i).getMoney() + "元");
				}
			} else {

				if (currentpage == 1) {
					setprojectvisible(0, 10, false);
					JOptionPane.showMessageDialog(null, "陆金所网站上暂无符合条件的项目！",
							"筛选结果", JOptionPane.ERROR_MESSAGE);
				} else
					setprojectvisible(0, 10, false);
			}
		} else {
			setprojectvisible(0, 10, false);
			JOptionPane.showMessageDialog(null, "无网络连接，请检查网路！！",
					"异常", JOptionPane.ERROR_MESSAGE);
		}
	}

	public boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
}

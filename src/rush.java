import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class rush extends JPanel implements ActionListener {

	/**
	 * Create the panel.
	 */
	private JPanel panel_1 = new JPanel();
	private ArrayList<Project> list = new ArrayList<Project>();
	private JButton rushbutton;
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
	private Timer  time = null;
	private String mintime = null;
	private String maxtime = null;
	private String minrate = null;
	private String maxrate = null;
	private String minmoney = null;
	private String maxmoney = null;
	private String order = null;

	public rush() {
		panel_1.setBounds(0, 0, 890, 248);
		add(panel_1);
		panel_1.setLayout(null);
		setOpaque(false);
		setLayout(null);
		setTime();
		setmoney();
		setrate();
		setsort();
		rushbutton = new JButton("开始抢购");
		rushbutton.setBounds(924, 174, 123, 29);
		rushbutton.addActionListener(this);
		add(rushbutton);

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
		time_min.setBounds(476, 12, 87, 27);
		time_min.addActionListener(this);

		panel_1.add(time_min);
		time_min.setColumns(10);

		time_max = new JTextField();
		time_max.setColumns(10);
		time_max.setBounds(621, 12, 87, 27);
		time_max.addActionListener(this);
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
		if (rushbutton == e.getSource()) {
			boolean flag = false;
			if (rushbutton.getText() == "开始抢购") {
				rushbutton.setText("抢购中");
				time = new Timer();
				time.schedule(new TimerTask() {
					@Override
					public void run() {
						System.out.println("mintime"+mintime);
						System.out.println("maxtime"+maxtime);
						AnotherCapture k = new AnotherCapture(mintime, maxtime,
								minrate, maxrate, minmoney, maxmoney, order);
						k.getrushproject();
						if (k.flag&&k.rushproject!=null) {
							System.out.println("name:"+k.rushproject.getName());
							System.out.println("rate:"+k.rushproject.getRate());
							System.out.println("time:"+k.rushproject.getTime());
							System.out.println("timeday:"+k.rushproject.getTimeday());
							System.out.println("money:"+k.rushproject.getMoney());
							System.out.println();
							int result = JOptionPane.showConfirmDialog(null,
									"已找到合适的转让项目", "抢购",
									JOptionPane.YES_NO_OPTION);
							if (0 == result) {
								time.cancel();
								rushbutton.setText("开始抢购");
								setbuttonflag(true);
								k.g=0;
								k.StartBrower(k.rushproject.getUrl());
							}
						}
						else if(k.flag==false){
							JOptionPane.showMessageDialog(null, "无网络连接，请检查网路！！",
									"异常", JOptionPane.ERROR_MESSAGE);
							time.cancel();
							rushbutton.setText("开始抢购");
							setbuttonflag(true);
						}
					}
				}, 0, 10 * 1000);
			} else if (rushbutton.getText() == "抢购中") {
				rushbutton.setText("开始抢购");
				flag = true;
				if (time != null) {
					time.cancel();
					System.out.println("cancel");
				}
			}
			setbuttonflag(flag);
		}
		else if(e.getSource()==time_no_limit){
			mintime=maxtime=null;
			time_min.setText("");
			time_max.setText("");
		}
		else if(e.getSource()==time_six){
			mintime=null;
			maxtime=String.valueOf(6*30);
			time_min.setText("");
			time_max.setText("");
		}
		else if(e.getSource()==time_six_twelve){
			mintime=String.valueOf(6*30);
			maxtime=String.valueOf(12*30);
			time_min.setText("");
			time_max.setText("");
		}
		else if(e.getSource()==time_twelve){
			mintime=String.valueOf(12*30);
			maxtime=null;
			time_min.setText("");
			time_max.setText("");
		}
		else if(e.getSource()==rate_no_limit){
			minrate=maxrate=null;
			rate_min.setText("");
			rate_max.setText("");
		}
		else if(e.getSource()==rate_five){
			minrate=null;
			maxrate=String.valueOf(0.05);
			rate_min.setText("");
			rate_max.setText("");
		}
		else if(e.getSource()==rate_five_ten){
			minrate=String.valueOf(0.05);
			maxrate=String.valueOf(0.10);
			rate_min.setText("");
			rate_max.setText("");
		}
		else if(e.getSource()==rate_ten){
			minrate=String.valueOf(0.10);
			maxrate=null;
			rate_min.setText("");
			rate_max.setText("");
		}
		else if(e.getSource()==money_no_limit){
			minmoney=maxmoney=null;
			money_min.setText("");
			money_max.setText("");
		}
		else if(e.getSource()==money_one){
			minmoney=null;
			maxmoney=String.valueOf(10000);
			money_min.setText("");
			money_max.setText("");
		}
		else if(e.getSource()==money_one_five){
			minmoney=String.valueOf(10000);
			maxmoney=String.valueOf(50000);
			money_min.setText("");
			money_max.setText("");
		}
		else if(e.getSource()==money_five_ten){
			minmoney=String.valueOf(50000);
			maxmoney=String.valueOf(100000);
			money_min.setText("");
			money_max.setText("");
		}
		else if(e.getSource()==money_ten){
			minmoney=String.valueOf(100000);
			maxmoney=null;
			money_min.setText("");
			money_max.setText("");
		}
		else if(e.getSource()==sort_no_limit){
			order=null;
		}
		else if(e.getSource()==sort_money){
			order="TRANSFER_PRICE_ASC";
		}
		else if(e.getSource()==sort_rate){
			order="INVEST_RATE_ASC";
		}
		else if(e.getSource()==sort_time){
			order="INVEST_PERIOD_ASC";
		}
		else if (e.getSource() == time_min) {
			if(time_min.getText().trim()==null)
				mintime=null;
			else
			mintime=String.valueOf((Integer.parseInt(time_min.getText().trim())*30));
			System.out.println(mintime);
			timegroup.clearSelection();

		} else if (e.getSource() == time_max) {
			if(time_max.getText().trim()==null)
			maxtime=null;
			else
			maxtime=String.valueOf((Integer.parseInt(time_max.getText().trim())*30));
			System.out.println(maxtime);
			timegroup.clearSelection();
		} 
		else if (e.getSource() == rate_min) {
			minrate=String.valueOf((Double.parseDouble(rate_min.getText().trim())/100));
		    rategroup.clearSelection();
		    
		} else if (e.getSource() == rate_max) {
			maxrate=String.valueOf((Double.parseDouble(rate_max.getText().trim())/100));
			rategroup.clearSelection();

		} else if (e.getSource() == money_min) {
			minmoney=String.valueOf((int)(Double.parseDouble(money_min.getText().trim())*10000));
			moneygroup.clearSelection();

		} else if (e.getSource() == money_max) {
			maxmoney=String.valueOf((int)(Double.parseDouble(money_max.getText().trim())*10000));
			moneygroup.clearSelection();

		}
		
	}

	public void setbuttonflag(boolean flag) {
		time_max.setEnabled(flag);
		time_min.setEnabled(flag);
		rate_min.setEnabled(flag);
		rate_max.setEnabled(flag);
		money_min.setEnabled(flag);
		money_max.setEnabled(flag);
		time_no_limit.setEnabled(flag);
		time_six.setEnabled(flag);
		time_six_twelve.setEnabled(flag);
		time_twelve.setEnabled(flag);
		rate_no_limit.setEnabled(flag);
		rate_five.setEnabled(flag);
		rate_five_ten.setEnabled(flag);
		rate_ten.setEnabled(flag);
		money_no_limit.setEnabled(flag);
		money_one.setEnabled(flag);
		money_one_five.setEnabled(flag);
		money_five_ten.setEnabled(flag);
		money_ten.setEnabled(flag);
		sort_no_limit.setEnabled(flag);
		sort_time.setEnabled(flag);
		sort_money.setEnabled(flag);
		sort_rate.setEnabled(flag);
	}

}

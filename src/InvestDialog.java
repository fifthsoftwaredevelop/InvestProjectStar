import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.StyledEditorKit.ForegroundAction;

public class InvestDialog extends Dialog implements ActionListener {
	JLabel label1 = new JLabel("������Ͷ��ʱ��:");
	JLabel label2 = new JLabel("������Ͷ�ʽ��:");
	JLabel label3 = new JLabel("ʱ���ʽ��2015-09-06");
	JTextField time = new JTextField(50);
	JTextField money = new JTextField(50);
	JButton ok = new JButton("ȷ��");
	JButton cancel = new JButton("ȡ��");
	private String timemessage;
	private String moneymessage;
	private Double investmoney;
	private boolean sucess = false;

	public boolean issucess() {
		return sucess;
	}

	public String getTimemessage() {
		return timemessage;
	}

	public String getMoneymessage() {
		return moneymessage;
	}

	InvestDialog(MyFrame parent, boolean modal,Double investmoney) {
		super(parent, modal);
		this.investmoney=investmoney;
		setTitle("�Զ���Ի���");
		setBounds(500, 300, 410, 150);
		setResizable(false);
		setLayout(null);
		add(label1);
		add(label2);
		add(label3);
		label1.setBounds(20, 40, 100, 20);
		label2.setBounds(20, 70, 100, 20);
		label3.setBounds(250, 40, 150, 20);
		add(time);
		add(money);

		time.setBounds(130, 40, 90, 20);
		money.setBounds(130, 70, 90, 20);
		add(ok);
		add(cancel);
		ok.setBounds(100, 100, 60, 25);
		cancel.setBounds(230, 100, 60, 25);
		ok.addActionListener(this);
		cancel.addActionListener(this);
		addWindowListener(new WindowListener() {

			@Override
			public void windowClosing(WindowEvent e) {
				sucess = false;
				dispose();

			}

			@Override
			public void windowOpened(WindowEvent e) {

			}

			@Override
			public void windowClosed(WindowEvent e) {

			}

			@Override
			public void windowIconified(WindowEvent e) {

			}

			@Override
			public void windowDeiconified(WindowEvent e) {

			}

			@Override
			public void windowActivated(WindowEvent e) {

			}

			@Override
			public void windowDeactivated(WindowEvent e) {

			}

		});

	}

	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ok) {

			timemessage = time.getText();
			moneymessage = money.getText();
			Pattern pattern = Pattern
					.compile("[0-9][0-9][0-9][0-9]-((0[0-9])|(1[0-2]))-(([0-2][0-9])|(3[0-1]))");
			Matcher isdate = pattern.matcher(timemessage);
			if (!isdate.matches()) {
				JOptionPane.showMessageDialog(null, "��������ϸ�ʽ��ʱ��");
				return;
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date initdate=format.parse(Filter.message.getTime());
				Date firstdate = format.parse(timemessage);
				Date currentdate = new Date();
				if(currentdate.before(firstdate))
				{
					JOptionPane.showMessageDialog(null, "������ʱ�䳬���˵�ǰʱ��");
					return ;
				}
				if(initdate.after(firstdate)){
					JOptionPane.showMessageDialog(null, "������ʱ���ڳ�ʼͶ��ʱ��֮ǰ");
					return ;
				}
			} catch (ParseException e1) {
			
				e1.printStackTrace();
			}
		
           Double k;
			try {
				k=Double.parseDouble(moneymessage);
			} catch (NumberFormatException e2) {
				JOptionPane.showMessageDialog(null, "����������");
				return;
			}
			BigDecimal k1=new BigDecimal(k);
			BigDecimal k2=new BigDecimal(investmoney);
			BigDecimal k3=new BigDecimal(Filter.message.getcurrentmoney());
			if(!(k1.compareTo(k2)==1&&k1.compareTo(k3)==-1)){
			   JOptionPane.showMessageDialog(null, "����Ľ��ܳ������е�Ͷ�ʲ���Ҫ������ĿҪ���Ͷ��");
				sucess=false;
				return ;
			}
			sucess = true;
		} else
			sucess = false;
		dispose();
	}

}

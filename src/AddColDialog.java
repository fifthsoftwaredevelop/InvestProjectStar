import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;
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

public class AddColDialog extends Dialog implements ActionListener {
	JLabel label1 = new JLabel("������Ͷ��ʱ��:");
	JLabel label2 = new JLabel("������Ͷ�ʽ��:");
	JLabel label4 = new JLabel("������Ͷ����Ŀ����:");
	JLabel label3 = new JLabel("ʱ���ʽ��2015-09-06");
	JTextField time = new JTextField(50);
	JTextField money = new JTextField(50);
	JTextField name = new JTextField(50);
	JButton ok = new JButton("ȷ��");
	JButton cancel = new JButton("ȡ��");
	private String timemessage;
	private String moneymessage;
	private String namemesage;
	private Vector<Vector> data;
	private boolean sucess = false;

	public boolean issucess() {
		return sucess;
	}

	public String getNamemesage() {
		return namemesage;
	}

	public String getTimemessage() {
		return timemessage;
	}

	public String getMoneymessage() {
		return moneymessage;
	}

	AddColDialog(MyFrame parent, boolean modal,Vector<Vector> data) {
		super(parent, modal);
		this.data=data;
		setTitle("�Զ���Ի���");
		setBounds(500, 300, 455, 219);
		setResizable(false);
		setLayout(null);
		add(label1);
		add(label2);
		add(label3);
		add(label4);
		label1.setBounds(20, 40, 140, 20);
		label2.setBounds(20, 70, 120, 20);
		label3.setBounds(250, 40, 140, 20);
		label4.setBounds(20, 100, 140, 20);
		add(time);
		add(money);
		add(name);

		time.setBounds(160, 40, 90, 20);
		money.setBounds(160, 70, 90, 20);
		name.setBounds(160, 100, 90, 20);
		add(ok);
		add(cancel);
		ok.setBounds(120, 130, 60, 25);
		cancel.setBounds(250, 130, 60, 25);
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
			namemesage=name.getText().trim();
			
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
		

			try {
				Double.parseDouble(moneymessage);
			} catch (NumberFormatException e2) {
				JOptionPane.showMessageDialog(null, "����Ͷ�ʽ�������Ϸ�����");
				return;
			}
			
			if(namemesage.equals("")){
				JOptionPane.showMessageDialog(null, "Ͷ����Ŀ���Ʋ���Ϊ�գ�");
				return;
			}
			
		
			
			int a=data.size();
			if(a!=0){
				for(int i=0;i<a;i++)
					if(data.get(i).get(3).equals(namemesage)){
						JOptionPane.showMessageDialog(null, "�������Ŀ�ظ�");
						return;
					}
			}
			sucess = true;
		} else
			sucess = false;
		dispose();
	}

}

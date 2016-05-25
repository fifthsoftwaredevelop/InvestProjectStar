import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;

public class MyFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyFrame frame = new MyFrame("投资星");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MyFrame(String title) {
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 1000);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		getContentPane().setBackground(Color.blue);
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		Filter f = new Filter();
		JScrollPane scrollPane = new JScrollPane(f);
		scrollPane.setBounds(0, 0, 1000, 700);
		f.setPreferredSize(new Dimension(scrollPane.getWidth() + 300,
				scrollPane.getHeight() + 200));
		tabbedPane.addTab("筛选项目", null, scrollPane, null);
		rush r = new rush();
		JScrollPane scrollPane_1 = new JScrollPane(r);
		scrollPane_1.setBounds(0, 0, 1000, 700);
		r.setPreferredSize(new Dimension(scrollPane.getWidth() + 300,
				scrollPane.getHeight() + 200));
		tabbedPane.addTab("设定抢购", null, scrollPane_1, null);
		Stat s = new Stat();
		JScrollPane scrollPane_2 = new JScrollPane(s);
		scrollPane_2.setBounds(0, 0, 1000, 700);
		s.setPreferredSize(new Dimension(scrollPane.getWidth() + 500,
				scrollPane.getHeight() + 200));
		tabbedPane.addTab("收益统计", null, scrollPane_2, null);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				System.out.println(Stat.change);
				ReadAndWrite raw = new ReadAndWrite();
				raw.writetxt(System.getProperty("user.dir") + "\\money.txt",
						String.valueOf(Filter.message.getInitmoney()),
						String.valueOf(Filter.message.getcurrentmoney()),
						Filter.message.getTime());
				if (Stat.change) {
					int result = JOptionPane.showConfirmDialog(null,
							"数据发生了改动，是否保存到本地数据库", "保存",
							JOptionPane.YES_NO_OPTION);
					if (result == 0) {

						String path = System.getProperty("user.dir")
								+ "\\project.xls";
						try {
							raw.Keepdata(path,
									Stat.defaultModel.getDataVector());
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}

				}

			}
		});
	}
}

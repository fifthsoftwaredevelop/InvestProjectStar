import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;



public class MyButtonEditor extends DefaultCellEditor{
	private JButton button;
	private JTable table;

	public MyButtonEditor(JTable table) {
		super(new JTextField());
		this.table = table;
		// ���õ�����μ���༭��
		this.setClickCountToStart(1);

		this.initButton();

	}

	private void initButton() {
		this.button = new JButton();

		// ���ð�ť�Ĵ�С��λ�á�
		this.button.setBounds(0, 0, 50, 15);

		// Ϊ��ť����¼�������ֻ�����ActionListner�¼���Mouse�¼���Ч��
		this.button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ����ȡ���༭���¼����������tableModel��setValue������
				String time=(String) Stat.defaultModel.getValueAt(table.getSelectedRow(), 0);
				String money=(String) Stat.defaultModel.getValueAt(table.getSelectedRow(), 2);
				MyButtonEditor.this.fireEditingCanceled();
				MyButtonDialog mydialog=new MyButtonDialog((MyFrame)(getComponent().getParent()), true,time,money);
				mydialog.show();
				int result=mydialog.issucess()?0:1;
				if (result == 0&&((JButton)e.getSource()).getText()!="") {
					String s=(String) Stat.defaultModel.getValueAt(table.getSelectedRow(), 3);
					Object data[]={mydialog.getTimemessage().replace("-", ""),"���",mydialog.getMoneymessage(),s,null};
					Stat.defaultModel.addRow(data);
					Stat.defaultModel.setValueAt(null, table.getSelectedRow(), table.getSelectedColumn());
				    Filter.message.addmoney(Double.parseDouble(mydialog.getMoneymessage()));
				    Filter.Displaymoney.setText("��Ͷ��"+Filter.message.getcurrentmoney()+"Ԫ");
				}
		
			}
		});

	}

	/**
	 * ������д����ı༭����������һ��JPanel���󼴿ɣ�Ҳ����ֱ�ӷ���һ��Button���󣬵��������������������Ԫ��
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		// ֻΪ��ť��ֵ���ɡ�Ҳ����������������
		// this.button.setText(value == null ? "" : String.valueOf(value));
		this.button.setText(value == null ? "" : ((JButton) value).getText());
		/*if(value==null)
			button.setEnabled(false);*/

		 if(value==null)
				return null;
	        else
	        return this.button;

	}

}

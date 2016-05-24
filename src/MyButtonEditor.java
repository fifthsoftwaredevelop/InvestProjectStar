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
				MyButtonEditor.this.fireEditingCanceled();
				int result = JOptionPane.showConfirmDialog(
						null,
						"��" + table.getSelectedRow() + "��" + "��"
								+ table.getSelectedColumn() + "��"
								+ "   JButton   Clicked", "Test",
						JOptionPane.OK_CANCEL_OPTION);
				if (result == 0&&((JButton)e.getSource()).getText()!="") {
					String s=(String) Stat.defaultModel.getValueAt(table.getSelectedRow(), 3);
					Object data[]={null,"���",null,s,null};
					//Stat.defaultModel.addRow(new Vector());
					Stat.defaultModel.addRow(data);
					//JButton b=new JButton("�����");
					Stat.defaultModel.setValueAt(null, table.getSelectedRow(), table.getSelectedColumn());
					//System.out.println();
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

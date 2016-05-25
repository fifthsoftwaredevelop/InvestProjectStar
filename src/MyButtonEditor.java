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
		// 设置点击几次激活编辑。
		this.setClickCountToStart(1);

		this.initButton();

	}

	private void initButton() {
		this.button = new JButton();

		// 设置按钮的大小及位置。
		this.button.setBounds(0, 0, 50, 15);

		// 为按钮添加事件。这里只能添加ActionListner事件，Mouse事件无效。
		this.button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 触发取消编辑的事件，不会调用tableModel的setValue方法。
				String time=(String) Stat.defaultModel.getValueAt(table.getSelectedRow(), 0);
				String money=(String) Stat.defaultModel.getValueAt(table.getSelectedRow(), 2);
				MyButtonEditor.this.fireEditingCanceled();
				MyButtonDialog mydialog=new MyButtonDialog((MyFrame)(getComponent().getParent()), true,time,money);
				mydialog.show();
				int result=mydialog.issucess()?0:1;
				if (result == 0&&((JButton)e.getSource()).getText()!="") {
					String s=(String) Stat.defaultModel.getValueAt(table.getSelectedRow(), 3);
					Object data[]={mydialog.getTimemessage().replace("-", ""),"赎回",mydialog.getMoneymessage(),s,null};
					Stat.defaultModel.addRow(data);
					Stat.defaultModel.setValueAt(null, table.getSelectedRow(), table.getSelectedColumn());
				    Filter.message.addmoney(Double.parseDouble(mydialog.getMoneymessage()));
				    Filter.Displaymoney.setText("可投资"+Filter.message.getcurrentmoney()+"元");
				}
		
			}
		});

	}

	/**
	 * 这里重写父类的编辑方法，返回一个JPanel对象即可（也可以直接返回一个Button对象，但是那样会填充满整个单元格）
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		// 只为按钮赋值即可。也可以作其它操作。
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

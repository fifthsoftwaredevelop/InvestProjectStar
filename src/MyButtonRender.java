import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;


public class MyButtonRender implements TableCellRenderer{
	 private JButton button;
	    public MyButtonRender()
	    {
	        this.initButton();
	    }

	    private void initButton()
	    {
	        this.button = new JButton();

	   
	        this.button.setBounds(0, 0, 50, 15);

	    
	    }
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,
	            int column)
	    {
	      
	        this.button.setText(value == null ? "" : ((JButton)value).getText());
	        if(value==null)
				return null;
	        else
	        return this.button;
	    }

}

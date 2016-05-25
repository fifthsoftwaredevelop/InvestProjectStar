import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class JFileImportChooser extends JFileChooser{
	@Override
	public void approveSelection() {
		File file = getSelectedFile();

		 if (file.getName().indexOf(".") != -1) {
			String k = file.getName().substring(file.getName().indexOf("."));
			if (k.equals(".xls")|k.equals(".xlsx"))
				super.approveSelection();
			else
				JOptionPane.showMessageDialog(getParent(), "�ⲻ��һ��excel�ļ�");
		} else {
			super.approveSelection();
		}
	}

}

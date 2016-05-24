import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class JFileChooserDemo extends JFileChooser{
	@Override
	public void approveSelection() {
		File file = getSelectedFile();

		// ��֤�ļ����Ƿ�Ϸ�
		if (!validateFileName(file.getName())) {
			JOptionPane.showMessageDialog(getParent(),
					"�ļ������ܰ��������κ��ַ�֮һ��: * ? \" < > |");
			return;
		} else if (file.exists()) {
			int result = JOptionPane.showConfirmDialog(null, "���ļ��Ѵ��ڣ��Ƿ񸲸�",
					"�ļ�����", JOptionPane.YES_NO_OPTION);
			if (result == 0)
				super.approveSelection();
		} else if (file.getName().indexOf(".") == -1) {
			JOptionPane.showMessageDialog(getParent(), "�ļ���������");
		} else if (file.getName().indexOf(".") != -1) {
			String k = file.getName().substring(file.getName().indexOf("."));
			if (k.equals(".xls"))
				super.approveSelection();
			else
				JOptionPane.showMessageDialog(getParent(), "�ⲻ��һ��excel�ļ�");
		} else {
			super.approveSelection();
		}
	}
	public static boolean validateFileName(String name) {
		if ( name.indexOf(':') != -1 || name.indexOf('"') != -1
				|| name.indexOf('<') != -1 || name.indexOf('>') != -1
				|| name.indexOf('|') != -1) {

			return false;
		} else {
			return true;
		}
	}

}

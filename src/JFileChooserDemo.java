import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class JFileChooserDemo extends JFileChooser{
	@Override
	public void approveSelection() {
		File file = getSelectedFile();

		// 验证文件名是否合法
		if (!validateFileName(file.getName())) {
			JOptionPane.showMessageDialog(getParent(),
					"文件名不能包含下列任何字符之一：: * ? \" < > |");
			return;
		} else if (file.exists()) {
			int result = JOptionPane.showConfirmDialog(null, "该文件已存在，是否覆盖",
					"文件覆盖", JOptionPane.YES_NO_OPTION);
			if (result == 0)
				super.approveSelection();
		} else if (file.getName().indexOf(".") == -1) {
			JOptionPane.showMessageDialog(getParent(), "文件名有问题");
		} else if (file.getName().indexOf(".") != -1) {
			String k = file.getName().substring(file.getName().indexOf("."));
			if (k.equals(".xls"))
				super.approveSelection();
			else
				JOptionPane.showMessageDialog(getParent(), "这不是一个excel文件");
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

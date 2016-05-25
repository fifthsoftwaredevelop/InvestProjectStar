import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ReadAndWrite {
	
	public void exportexcel(String dirname,Vector<Vector> data){
		HSSFWorkbook wb = new HSSFWorkbook();
		// 声明一个单子并命名
		HSSFSheet sheet = wb.createSheet("学生表");
		// 给单子名称一个长度
		sheet.setDefaultColumnWidth((short) 15);
		// 生成一个样式
		HSSFCellStyle style = wb.createCellStyle();
		// 创建第一行（也可以称为表头）
		HSSFRow row = sheet.createRow(0);
		// 样式字体居中
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 给表头第一行一次创建单元格
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("日期");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("操作");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("金额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("项目编号");
		cell.setCellStyle(style);
		int a = data.size();
		int b = data.get(0).size() - 1;
		for (int i = 0; i < a; i++) {
			row = sheet.createRow(i + 1);
			
			for (int j = 0; j < b; j++)
			{
				System.out.println(data.get(i).get(j));
				row.createCell(j).setCellValue(String.valueOf(data.get(i).get(j)));
			
			}
			System.out.println();
			
		}
		 try { 
		  FileOutputStream out = new FileOutputStream(dirname); 
	      wb.write(out); 
	      out.close();
		  JOptionPane.showMessageDialog(null, "导出成功!"); } catch
		  (FileNotFoundException e1) {
		  JOptionPane.showMessageDialog(null, "导出失败!");
		  e1.printStackTrace(); } catch (IOException e1) {
		  JOptionPane.showMessageDialog(null, "导出失败!");
		  e1.printStackTrace(); 
		 }
      
	}
	public void writeexcel(String dirname, String[] data) throws IOException {
		dirname=dirname.replace("\\","/");
		FileInputStream fs = new FileInputStream(dirname); // 获取d://test.xls
		POIFSFileSystem ps = new POIFSFileSystem(fs); // 使用POI提供的方法得到excel的信息
		HSSFWorkbook wb = new HSSFWorkbook(ps);
		// 创建表格
		Sheet sheet = wb.getSheetAt(0);
		// 创建行
		Row row=null;
		Cell cell = null;
		if(sheet.getRow(0)==null)
		{
			row = sheet.createRow(0);
			int b = data.length;
		    cell = row.createCell(0);
		    cell.setCellValue("日期");
		    cell = row.createCell(1);
		    cell.setCellValue("操作");
		    cell = row.createCell(2);
		    cell.setCellValue("金额");
		    cell = row.createCell(3);
		    cell.setCellValue("项目编号");
		}
		row = sheet.createRow(sheet.getLastRowNum()+1);
		int b = data.length;
		for (int j = 0; j < b; j++) {
			cell = row.createCell(j);
			cell.setCellValue(data[j]);
		  }
		FileOutputStream fos;

		fos = new FileOutputStream(dirname);
		wb.write(fos);
		if (null != fos) {
			fos.close();
		}

	}

	public Object[][] readexcel(String dirname) {
		dirname=dirname.replace("\\","/");
		Object[][] data=null;
		List<String[]> list = new ArrayList<String[]>();
		Workbook wb = null;
		InputStream inp;
		try {
			inp = new FileInputStream(dirname);
			wb = WorkbookFactory.create(inp);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (InvalidFormatException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		if (wb == null)
			return null;
		Sheet sheet = wb.getSheetAt(0);
		int col = 0;
		if (sheet.getRow(0) != null) {
			col = sheet.getRow(0).getLastCellNum()
					- sheet.getRow(0).getFirstCellNum();
			if (col <= 0)
				return null;

			for (Row row : sheet) {
				String[] singlerow = new String[col];
				int n = 0;
				for (int i = 0; i < col; i++) {
					Cell cell = row.getCell(i, Row.CREATE_NULL_AS_BLANK);
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_BLANK:
						singlerow[n] = "";
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						singlerow[n] = Boolean.toString(cell
								.getBooleanCellValue());
						break;
					// 数值
					case Cell.CELL_TYPE_NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
							singlerow[n] = String.valueOf(cell
									.getDateCellValue());
						} else {
							cell.setCellType(Cell.CELL_TYPE_STRING);
							String temp = cell.getStringCellValue();
							// 判断是否包含小数点，如果不含小数点，则以字符串读取，如果含小数点，则转换为Double类型的字符串
							if (temp.indexOf(".") > -1) {
								singlerow[n] = String.valueOf(new Double(temp))
										.trim();
							} else {
								singlerow[n] = temp.trim();
							}
						}
						break;
					case Cell.CELL_TYPE_STRING:
						singlerow[n] = cell.getStringCellValue().trim();
						break;
					case Cell.CELL_TYPE_ERROR:
						singlerow[n] = "";
						break;
					case Cell.CELL_TYPE_FORMULA:
						cell.setCellType(Cell.CELL_TYPE_STRING);
						singlerow[n] = cell.getStringCellValue();
						if (singlerow[n] != null) {
							singlerow[n] = singlerow[n].replaceAll("#N/A", "")
									.trim();
						}
						break;
					default:
						singlerow[n] = "";
						break;
					}
					n++;
				}
				if ("".equals(singlerow[0])) {
					continue;
				}// 如果第一行为空，跳过
				list.add(singlerow);
			}
			int a=list.size();
	        int b=list.get(0).length;
	        System.out.println(b);
	        data=new Object[a-1][b+1];
	        for(int i=0;i<a-1;i++)
	        {
	        	for(int j=0;j<b;j++){
	        		data[i][j]=list.get(i+1)[j];
	        	}
	        	System.out.println(list.get(i+1)[1]+" ");
	        	if(list.get(i+1)[1].equals("购买"))
	        	{
	        		
	        		data[i][b]=new JButton("赎回");
	        	}
	        	else
	        		data[i][b]=null;
	        }
	        for(int i=0;i<a-1;i++)
	        	if(data[i][b]!=null)
	        		for(int j=0;j<a-1;j++){
	        			if(data[j][1].equals("赎回")&&data[i][b-1].equals(data[j][b-1]))
	        				data[i][b]=null;
	        		}
		}
		
		
		return data;
	}
	public InitInvestMessage readtxt(String dirname) {
		InitInvestMessage init=new InitInvestMessage(0.0, "未设置时间");
		Double money=0.0;
		try {
			String encoding = "GBK";
			File file = new File(dirname);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String s=null;
				if((s=bufferedReader.readLine())!=null)
				money = Double.parseDouble(s);
			    init.setMoney(money);
			    if((s=bufferedReader.readLine())!=null)
			    init.setTime(s);
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return init;
	}

	public void writetxt(String dirname, String money,String time) {
		try {
			String encoding = "GBK";
			File file = new File(dirname);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				OutputStreamWriter read = new OutputStreamWriter(
						new FileOutputStream(file), encoding);// 考虑到编码格式
				BufferedWriter bufferedwrite = new BufferedWriter(read);
				String lineTxt = null;
				bufferedwrite.write(money + "\r\n");
				bufferedwrite.write(time+"\r\n");
				bufferedwrite.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
	}
       
	


}

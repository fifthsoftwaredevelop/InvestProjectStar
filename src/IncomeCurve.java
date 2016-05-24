import java.awt.Font;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;


public class IncomeCurve {
	private static ArrayList<Record> list;
	private ChartPanel frame1;

	public IncomeCurve() {
		list = new ArrayList<Record>();
	}

	public void init(Vector<Vector> vector) {

		list.clear();
		int a = vector.size();
		if (a == 0)
			return;
		int b = vector.get(0).size()-1;
		for (int i = 0; i < a; i++) {
			String s = (String) vector.get(i).get(1);

			if (s.equals("购买")) {
				Record r = new Record();
				r.setName((String) vector.get(i).get(3));
				r.setBudget(Double.parseDouble((String) vector.get(i).get(2)));
				r.setIsredeem(false);
				r.setBuytime((String) vector.get(i).get(0));
				list.add(r);
			} else if (s.equals("赎回")) {
				int c = list.size();
				String k = (String) vector.get(i).get(3);
				for (int j = 0; j < c; j++) {
					if (list.get(j).getName().equals(k)) {
						list.get(j).setRedeemprice(
								Double.parseDouble((String) vector.get(i)
										.get(2)));
						list.get(j).setIsredeem(true);
						list.get(j)
								.setRedeemtime((String) vector.get(i).get(0));
						break;
					}
				}
			}
		}
	}

	public void drawpanel() throws ParseException {
		XYDataset xydataset = createDataset();
		JFreeChart jfreechart = ChartFactory.createTimeSeriesChart("收益曲线",
				"日期", "收益", xydataset, true, true, true);
		XYPlot xyplot = (XYPlot) jfreechart.getPlot();
		DateAxis dateaxis = (DateAxis) xyplot.getDomainAxis();
		dateaxis.setDateFormatOverride(new SimpleDateFormat("dd-MMM-yyyy"));
		frame1 = new ChartPanel(jfreechart, true);
		dateaxis.setLabelFont(new Font("黑体", Font.BOLD, 14)); // 水平底部标题
		dateaxis.setTickLabelFont(new Font("宋体", Font.BOLD, 12)); // 垂直标题
		ValueAxis rangeAxis = xyplot.getRangeAxis();// 获取柱状
		rangeAxis.setLabelFont(new Font("黑体", Font.BOLD, 15));
		jfreechart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));
		jfreechart.getTitle().setFont(new Font("宋体", Font.BOLD, 20));// 设置标题字体
	}

	private static XYDataset createDataset() throws ParseException { // 这个数据集有点多，但都不难理解
		TimeSeries timeseries = new TimeSeries("收益曲线",
				org.jfree.data.time.Day.class);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		Date firstdate = format.parse("20140225");
		Date currentdate = new Date();
		int len = list.size();
		long days = (currentdate.getTime() - firstdate.getTime())
				/ (24 * 60 * 60 * 1000);
		System.out.println("days:"+days);
		for (int i = 0; i <= days; i++) {
			c.setTime(firstdate);
			c.add(Calendar.DAY_OF_YEAR, i);
			Double sum = 0.0;
			currentdate = c.getTime();
			for (int j = 0; j < len; j++)
			{
			    Date date = format.parse(list.get(j).getBuytime());
			    
			    if (!date.after(currentdate)) 
			    {
			    	long k=(currentdate.getTime()-date.getTime())/(24 * 60 * 60 * 1000);
				if (!list.get(j).isIsredeem()) {
					sum+=list.get(j).getBudget()*0.084*k/365;
				}
				else
				{
					Date d=format.parse(list.get(j).getRedeemtime());
					if(d.after(currentdate))
						sum+=list.get(j).getBudget()*0.084*k/365;
					else
						sum+=(list.get(j).getRedeemprice()-list.get(j).getBudget());
				}
			    }
			    
			}
			timeseries.add(
					new Day(currentdate.getDate(), currentdate.getMonth() + 1,
							currentdate.getYear() + 1900), sum);
		}
		TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
		timeseriescollection.addSeries(timeseries);

		return timeseriescollection;
	}

	public ChartPanel getChartPanel() {
		return frame1;

	}

}

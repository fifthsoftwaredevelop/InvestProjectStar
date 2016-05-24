import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Capture {
	public ArrayList<Project> get(String mintime, String maxtime,
			String minrate, String maxrate, String minmoney, String maxmoney,
			Integer currentpage, String ordercondition) {
		ArrayList<Project> list = new ArrayList<Project>();
		int len;
		if(minmoney==null)
			minmoney="";
			
		if(maxmoney==null)
			maxmoney="";
		    
		if(minrate==null)
			minrate="";
		    
		if(maxrate==null)
			maxrate="";
		    
		if(mintime==null)
			mintime="";
		    
		if(maxtime==null)
			maxtime="";
		
		if(currentpage==null)
			currentpage=1;
		
		if(ordercondition==null)
			ordercondition="";
		
	
		String url = "https://list.lu.com/list/transfer-p2p?minMoney="
				+ minmoney + "&maxMoney=" + maxmoney + "&minDays=" + mintime
				+ "&maxDays=" + maxtime + "&minRate=" + minrate + "&maxRate="
				+ maxrate + "&mode=&tradingMode=&isCx=&currentPage="
				+ currentpage + "&orderCondition=" + ordercondition
				+ "&isShared=&canRealized=&productCategoryEnum=";

		
			Document doc = null;
			try {
				doc = Jsoup.connect(url).timeout(5000).get();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			list.clear();
			Elements name = doc.getElementsByClass("product-name");
			len = name.size();
			if (len == 0)
				return list;
			Elements rate = doc.getElementsByClass("interest-rate");
			Elements time = doc.getElementsByClass("invest-period");
			Elements money = doc.getElementsByClass("product-amount");
			for (int j = 0; j < len; j++) {
				Project p = new Project();
				String s = name.get(j).text().replace(" ×ª", "");
				s = s.replace("¾ºÅÄ", "");
				p.setName(s);
				p.setTime(time.get(j).child(1).text());
				p.setTimeday(p.getTime());
				p.setRate(Double.parseDouble(rate.get(j)
						.getElementsByClass("num-style").text()
						.replace("%", "")));
				p.setMoney(Double.parseDouble(money.get(j)
						.getElementsByClass("num-style").text()
						.replace(",", "")));
				String projecturl="https://list.lu.com"+name.get(j).getElementsByTag("a").attr("href");
				p.setUrl(projecturl);
				list.add(p);
			}
		return list;
	}
	public void StartBrower(String url){
		
			try {
				 java.net.URI uri = java.net.URI.create(url); 
		         
		         java.awt.Desktop dp = java.awt.Desktop.getDesktop();

		         if(dp.isSupported(java.awt.Desktop.Action.BROWSE))
				  dp.browse(uri);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

}

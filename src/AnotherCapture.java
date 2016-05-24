import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class AnotherCapture {
	private String minmoney;
	private String maxmoney;
	private String mindays;
	private String maxdays;
	private String minrate;
	private String maxrate;
	private String ordercondition;
	public boolean flag;
	public Project rushproject;
	public static int g=0;

	public AnotherCapture(String mintime, String maxtime, String minrate,
			String maxrate, String minmoney, String maxmoney,
			 String ordercondition) {
		if(minmoney!=null)
		this.minmoney = minmoney;
		else 
			this.minmoney="";
		
	    if(maxmoney!=null)
		this.maxmoney = maxmoney;
	    else 
	    	this.maxmoney="";
	    
	    if(minrate!=null)
		this.minrate = minrate;
	    else 
	    	this.minrate="";
	    
	    if(maxrate!=null)
		this.maxrate = maxrate;
	    else
	    	this.maxrate="";
	    
	    if(mintime!=null)
		this.mindays = mintime;
	    else
	    	this.mindays="";
	    
	    if(maxtime!=null)
		this.maxdays = maxtime;
	    else
	    	this.maxdays="";
	   

	    if(ordercondition!=null)
		this.ordercondition = ordercondition;
	    else
	    	this.ordercondition="";
	    
	}

	public void getrushproject() {
		String url = "https://list.lu.com/list/transfer-p2p?minMoney="
				+ minmoney + "&maxMoney=" + maxmoney + "&minDays=" + mindays
				+ "&maxDays=" + maxdays + "&minRate=" + minrate + "&maxRate="
				+ maxrate + "&mode=&tradingMode=&isCx=&currentPage="
				+ "&orderCondition=" + ordercondition
				+ "&isShared=&canRealized=&productCategoryEnum=";
		System.out.println(url);
		rushproject=null;
		Document doc = null;
		try {
			doc = Jsoup
					.connect(url)
					.timeout(5000)
					.get();
		} catch (IOException e) {
		    flag=false;
			e.printStackTrace();
			return ;
		}
		
		flag=true;
		Elements name=doc.getElementsByClass("product-name");
		int len=name.size();
		if(name.size()==0)
			return ;
		
		rushproject=new Project();
			Elements rate=doc.getElementsByClass("interest-rate");
			Elements time=doc.getElementsByClass("invest-period");
			Elements money=doc.getElementsByClass("product-amount");
			rushproject.setName(name.get(g).text().replace(" ת", ""));
			rushproject.setTime(time.get(g).child(1).text());
			rushproject.setTimeday(rushproject.getTime());
			rushproject.setRate(Double.parseDouble(rate.get(g).getElementsByClass("num-style").text().replace("%", "")));
			rushproject.setMoney(Double.parseDouble(money.get(g).getElementsByClass("num-style").text().replace(",", "")));
			String s="https://list.lu.com"+name.get(g).getElementsByTag("a").attr("href");
			rushproject.setUrl(s);
			g=(g+1)%10;	
			System.out.println("g:"+g);
			
	}
	public void StartBrower(String url){
		
		try {
			 java.net.URI uri = java.net.URI.create(url); 
	         java.awt.Desktop dp = java.awt.Desktop.getDesktop();
	         if(dp.isSupported(java.awt.Desktop.Action.BROWSE))
			  dp.browse(uri);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	
}

}


/**
 * @author lenovo
 *
 */
public class Project {
	private String name;
	private String time;
	private Double timeday;
	private Double rate;
	private Double money;
	private String url;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Double getTimeday() {
		return timeday;
	}
	public void setTimeday(String time) {
		if(time.indexOf("个月")!=-1){
			this.timeday=Double.parseDouble(time.replace("个月", ""))*30;
		}
		else if(time.indexOf("天")!=-1){
			this.timeday=Double.parseDouble(time.replace("天",""));
		}
		else if(time.indexOf("日")!=-1){
			this.timeday=Double.parseDouble(time.replace("日",""));
		}
	}
	
}

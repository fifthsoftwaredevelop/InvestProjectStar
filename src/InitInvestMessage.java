
public class InitInvestMessage {
	private Double money;
	private String time;
	public InitInvestMessage(Double money,String time) {
		this.money=money;
		this.time=time;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public void submoney(Double k){
		money-=k;
	}
	

}

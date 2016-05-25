
public class InitInvestMessage {
	private Double currentmoney;
	private Double initmoney;
	private String time;
	public InitInvestMessage(Double money,String time) {
		this.currentmoney=money;
		this.time=time;
	}
	
	public Double getInitmoney() {
		return initmoney;
	}

	public void setInitmoney(Double initmoney) {
		this.initmoney = initmoney;
	}

	public Double getcurrentmoney() {
		return currentmoney;
	}
	public void setcurrentmoney(Double money) {
		this.currentmoney = money;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public void submoney(Double k){
		currentmoney-=k;
	}
	public void addmoney(Double k){
		currentmoney+=k;
	}
	

}

package common;

public class ApartmentInfoAvg {

	// ����⵵
	private int builtYear;
	
	// ��
	private int year;
	
	// ��
	private int month;
	
	// ��
	private int day;
	
	// �����ڵ�
	private int jCode;
	
	// ����
	private String jibeon;
	
	// ������
	private String legalDong;
	
	// �Ÿ� ���
	private int tradePriceAvg;
	
	// �Ÿ� �Ѿ�
	private int tradePriceTotal = 0;
	
	// �����ݾ� ���
	private int rentPriceAvg;

	// �����ݾ� �Ѿ�
	private int rentPriceTotal = 0;
	
	private int tradeCount = 0;
	
	private int rentCount = 0;
	
	// ����Ʈ
	private String apartmentName;
	
	public void setBuiltYear(int builtYear) {
		this.builtYear = builtYear;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public void setMonth(int month) {
		this.month = month;
	}
	
	public void setDay(int day) {
		this.day = day;
	}
	
	public void setJCode(int jCode) {
		this.jCode = jCode;
	}
	
	public void setJibeon(String jibeon) {
		this.jibeon = jibeon;
	}
	
	public void setLegalDong(String legalDong) {
		this.legalDong = legalDong;
	}
	
	public void setTradePriceAvg(int tradePriceAvg) {
		this.tradePriceAvg = tradePriceAvg;
	}
	
	public void setRentPriceAvg(int rentPriceAvg) {
		this.rentPriceAvg = rentPriceAvg;
	}
	
	public void addTradePrice(int tradePrice) {
		
		this.tradePriceTotal += tradePrice;
		this.tradeCount++;
		this.tradePriceAvg = this.tradePriceTotal / tradeCount;
	}
	
	public void addRentPrice(int rentPrice) {
		
		this.rentPriceTotal += rentPrice;
		this.rentCount++;
		this.rentPriceAvg = this.rentPriceTotal / rentCount;
	}
	
	public void setApartmentName(String apartmentName) {
		this.apartmentName = apartmentName;
	}
	
	public int getBuiltYear() {
		return this.builtYear;
	}
	
	public int getYear() {
		return this.year;
	}
	
	public int getMonth() {
		return this.month;
	}
	
	public int getDay() {
		return this.day;
	}
	
	public int getJCode() {
		return this.jCode;
	}
	
	public String getJibeon() {
		return this.jibeon;
	}
	
	public String getLegalDong() {
		return this.legalDong;
	}
	
	public int getTradePriceAvg() {
		return this.tradePriceAvg;
	}
	
	public int getRentPriceAvg() {
		return this.rentPriceAvg;
	}
	
	public String getApartmentName() {
		return this.apartmentName;
	}
	
	public int getTradeCount() {
		return this.tradeCount;
	}
	
	public int getRentCount() {
		return this.rentCount;
	}
}

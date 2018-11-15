package common;

public class ApartmentInfoAvg {

	// 건축년도
	private int builtYear;
	
	// 년
	private int year;
	
	// 월
	private int month;
	
	// 일
	private int day;
	
	// 지역코드
	private int jCode;
	
	// 지번
	private String jibeon;
	
	// 법정동
	private String legalDong;
	
	// 매매 평균
	private int tradePriceAvg;
	
	// 매매 총액
	private int tradePriceTotal = 0;
	
	// 보증금액 평균
	private int rentPriceAvg;

	// 보증금액 총액
	private int rentPriceTotal = 0;
	
	private int tradeCount = 0;
	
	private int rentCount = 0;
	
	// 아파트
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

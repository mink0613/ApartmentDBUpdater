package common;

public class ApartmentInfo implements Comparable<ApartmentInfo> {

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
	
	// 매매
	private int tradePrice;
	
	// 보증금액
	private int rentPrice;
	
	// 웰세
	private int rentPriceMonthly;
	
	// 아파트
	private String apartmentName;
	
	// 전용면적
	private double apartmentSize;
	
	// 층
	private int floor;
	
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
	
	public void setTradePrice(int tradePrice) {
		this.tradePrice = tradePrice;
	}
	
	public void setRentPrice(int rentPrice) {
		this.rentPrice = rentPrice;
	}
	
	public void setRentPriceMonthly(int rentPriceMonthly) {
		this.rentPriceMonthly = rentPriceMonthly;
	}
	
	public void setApartmentName(String apartmentName) {
		this.apartmentName = apartmentName;
	}
	
	public void setApartmentSize(double apartmentSize) {
		this.apartmentSize = apartmentSize;
	}
	
	public void setFloor(int floor) {
		this.floor = floor;
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
	
	public int getTradePrice() {
		return this.tradePrice;
	}
	
	public int getRentPrice() {
		return this.rentPrice;
	}
	
	public int getRentPriceMonthly() {
		return this.rentPriceMonthly;
	}
	
	public String getApartmentName() {
		return this.apartmentName;
	}
	
	public double getApartmentSize() {
		return this.apartmentSize;
	}
	
	public int getFloor() {
		return this.floor;
	}
	
	@Override
	public int compareTo(ApartmentInfo info) {
		return apartmentName.compareTo(info.getApartmentName());
	}
}

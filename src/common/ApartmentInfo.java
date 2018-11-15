package common;

public class ApartmentInfo implements Comparable<ApartmentInfo> {

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
	
	// �Ÿ�
	private int tradePrice;
	
	// �����ݾ�
	private int rentPrice;
	
	// ����
	private int rentPriceMonthly;
	
	// ����Ʈ
	private String apartmentName;
	
	// �������
	private double apartmentSize;
	
	// ��
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

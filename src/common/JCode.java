package common;

public class JCode {

	private int code;
	
	private String name1;
	
	private String name2;
	
	private String name3;
	
	public JCode(int code, String name1, String name2, String name3) {
		this.code = code;
		this.name1 = name1;
		this.name2 = name2;
		this.name3 = name3;
	}
	
	public int getCode() {
		return code;
	}
	
	public String getName1() {
		return name1;
	}
	
	public String getName2() {
		return name2;
	}
	
	public String getName3() {
		return name3;
	}
	
	public String toString() {
		return "Code: " + code + ", Name1: " + name1 + ", Name2: " + name2 + ", Name3: " + name3;
	}
}

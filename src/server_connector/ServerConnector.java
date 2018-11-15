package server_connector;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class ServerConnector {

	private final String key = "GB%2FK4sPdWPbbdPoKng%2FRdzhdnvMMI%2FwddBxVuarEmr2Vc%2B8A7Ek2XN7CqMeT73cGnsOo%2BoII3Zcnnp%2BZrc753g%3D%3D";
	
	private final String rent_String = "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptRent?&LAWD_CD=%s&DEAL_YMD=%s&serviceKey=%s";
	
	private final String trade_String = "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTrade?&LAWD_CD=%s&DEAL_YMD=%s&serviceKey=%s";
	
	public String GetApartmentRentList(int code, String yyyymm) {
		
		String url = String.format(rent_String, String.valueOf(code), yyyymm, key);
		return getFromServer(url);
	}
	
	public String GetApartmentTradeList(int code, String yyyymm) {
		
		String url = String.format(trade_String, String.valueOf(code), yyyymm, key);
		return getFromServer(url);
	}
	
	public String AddJCode(int code, String name1, String name2, String name3) {
		
		String url = "http://modoocoupon.woobi.co.kr/gnuboard4/apartmentfinder/php/AddJCode.php";
		url += "?code=" + String.valueOf(code) + "&name1=" + name1 + "&name2=" + name2 + "&name3=" + name3;
		return getFromServer(url);
	}
	
	public String AddApartmentInfo(String apt_name, int built_year, int year, int month, int day, 
			String legal_dong, String jibeon, double apt_size, int j_code, int floor, int trade_price,
			int rent_price, int rent_month_price) {
		
		String url = "http://modoocoupon.woobi.co.kr/gnuboard4/apartmentfinder/php/AddApartmentInfo.php";
		url += "?apt_name=" + apt_name + "&built_year=" + String.valueOf(built_year) +
				"&year=" + String.valueOf(year) + "&month=" + String.valueOf(month) + "&day=" + String.valueOf(day) +
				"&legal_dong=" + legal_dong + "&jibeon=" + jibeon + 
				"&apt_size=" + String.valueOf(apt_size) + "&j_code=" + String.valueOf(j_code) +
				"&floor=" + String.valueOf(floor) + "&trade_price=" + String.valueOf(trade_price) +
				"&rent_price=" + String.valueOf(rent_price) + "&rent_month_price=" + String.valueOf(rent_month_price);
		return getFromServer(url);
	}
	
	public String AddApartmentInfoAvg(String apt_name, int built_year, int year, int month,  
			String legal_dong, String jibeon, int j_code, int trade_price_avg, int rent_price_avg, int trade_count, int rent_count) {
		
		String url = "http://modoocoupon.woobi.co.kr/gnuboard4/apartmentfinder/php/AddApartmentInfoAvg.php";
		url += "?apt_name=" + apt_name + "&built_year=" + String.valueOf(built_year) +
				"&year=" + String.valueOf(year) + "&month=" + String.valueOf(month) + 
				"&legal_dong=" + legal_dong + "&jibeon=" + jibeon + "&j_code=" + String.valueOf(j_code) +
				"&trade_price_avg=" + String.valueOf(trade_price_avg) + "&rent_price_avg=" + String.valueOf(rent_price_avg) +
				"&trade_count=" + String.valueOf(trade_count) + "&rent_count=" + String.valueOf(rent_count);
		return getFromServer(url);
	}
	
	public String GetJCodeAll() {
		
		String url = "http://modoocoupon.woobi.co.kr/gnuboard4/apartmentfinder/php/GetJCodeAll.php";
		return getFromServer(url);
	}
	
	private String getFromServer(String url) {
		
		String result = "";
		String test = "";
		
		try {

			URL phpUrl = new URL(url); 
		    URLConnection urlCon = phpUrl.openConnection(); 
		    BufferedReader br = new BufferedReader(new InputStreamReader(urlCon.getInputStream(), "utf-8")); 
		    String line; 
	
		    result =  br.lines().collect(Collectors.joining());
		    int size = result.length();
		    
		    /*while ((line = br.readLine()) != null) {
		    	result += line;
		    }*/
		    
		    br.close();
	     } catch(Exception e) {

	    	result = e.toString();
	     }
		
		return result;
	}
}

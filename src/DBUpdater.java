import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class DBUpdater {

	private final String key = "U8H1ShPPOGDRdX89Vnsq1XGSCWl4BZN%2F6VNReWUMYrDnhMzCpSAz2F2N%2F%2FIvIQ%2BvishMy2NhpGTboFEsVGdk1A%3D%3D";
	
	public String AccessApartmentDataTrade(int jCode, int yyyymm) {
		
		String result = "";
		
		try {
			
			URL url = new
	          URL("http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTrade?&LAWD_CD="
	        		  + Integer.toString(jCode) + "&DEAL_YMD=" + Integer.toString(yyyymm) + 
	        		  "&serviceKey=" + key);
			
	        HttpsURLConnection urlConn = (HttpsURLConnection) url.openConnection();

	        System.out.println("sending request...");
	        urlConn.setRequestMethod("GET");
	        urlConn.setAllowUserInteraction(false); // no user interaction
	        urlConn.setDoOutput(true); // want to send
	        urlConn.setRequestProperty( "Content-type", "text/xml" );
	        urlConn.setRequestProperty( "accept", "text/xml" );
	        Map headerFields = urlConn.getHeaderFields();
	        System.out.println("header fields are: " + headerFields);

	        int rspCode = urlConn.getResponseCode();
	        
	        if (rspCode == 200) {
	            InputStream ist = urlConn.getInputStream();
	            InputStreamReader isr = new InputStreamReader(ist);
	            BufferedReader br = new BufferedReader(isr);
	        
	            String nextLine = br.readLine();
	            while (nextLine != null) {

	            	result += nextLine;
	                nextLine = br.readLine();
	            }
	        }
		}catch (Exception e) {
			
			System.out.println("Error occurred: " + e.toString());
		}
        
		return result;
	}
	
	public String AccessApartmentDataRent(int jCode, int yyyymm) {
		
		String result = "";
		
		try {
			
			URL url = new
	          URL("http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptRent?&LAWD_CD="
	        		  + Integer.toString(jCode) + "&DEAL_YMD=" + Integer.toString(yyyymm) + 
	        		  "&serviceKey=" + key);
			
	        HttpsURLConnection urlConn = (HttpsURLConnection) url.openConnection();

	        System.out.println("sending request...");
	        urlConn.setRequestMethod("GET");
	        urlConn.setAllowUserInteraction(false); // no user interaction
	        urlConn.setDoOutput(true); // want to send
	        urlConn.setRequestProperty( "Content-type", "text/xml" );
	        urlConn.setRequestProperty( "accept", "text/xml" );
	        Map headerFields = urlConn.getHeaderFields();
	        System.out.println("header fields are: " + headerFields);

	        int rspCode = urlConn.getResponseCode();
	        
	        if (rspCode == 200) {
	            InputStream ist = urlConn.getInputStream();
	            InputStreamReader isr = new InputStreamReader(ist);
	            BufferedReader br = new BufferedReader(isr);
	        
	            String nextLine = br.readLine();
	            while (nextLine != null) {

	            	result += nextLine;
	                nextLine = br.readLine();
	            }
	        }
		}catch (Exception e) {
			
			System.out.println("Error occurred: " + e.toString());
		}
        
		return result;
	}
	
	
}

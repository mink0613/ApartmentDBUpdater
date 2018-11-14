import java.io.StringReader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import common.ApartmentInfo;
import common.JCode;
import server_connector.ServerConnector;

public class ApartmentFinderDBUpdater {
	
	private static ServerConnector serverConnector;
	
	private static ExcelReader excelReader;
	
	private static ArrayList<JCode> jCodeList;
	
	// HashMap<code, ArrayList<ApartmentInfo>>
	private static HashMap<String, ArrayList<ApartmentInfo>> apartmentInfoList = new HashMap<>();
	
	public static void main(String[] args) {
		
		/*ArrayList<ApartmentInfo> list = new ArrayList();
		ApartmentInfo info = new ApartmentInfo();
		info.setAreaCode(1111);
		list.add(info);
		test(list);*/
		
		serverConnector = new ServerConnector();
		excelReader = new ExcelReader();
		
		readJCodeFile();
		//updateJCode();
		updateRentList("201810");
		updateTradeList("201810");
		
		sendDataToDB();
		
	}
	
	private static void test(ArrayList<ApartmentInfo> list) {
		
		ApartmentInfo info = list.get(0);
		info.setApartmentName("ddddd");
		list.add(new ApartmentInfo());
	}
	
	private static void readJCodeFile() {
		
		jCodeList = excelReader.ReadJCodeFile();
	}
	
	private static void updateJCode() {
		
		int totalCount = jCodeList.size();
		int actualCount = 0;
		for (JCode jCode : jCodeList) {
			
			String result = serverConnector.AddJCode(jCode.getCode(), jCode.getName1(), jCode.getName2(), jCode.getName3());
			if (result.equals("1")) {
				actualCount++;
			}
		}
		
		System.out.println("JCodeUpdate Total: " + String.valueOf(totalCount) + " Actual: " + String.valueOf(actualCount));
	}
	
	private static Document convertStringToDocument(String xmlStr) {
		
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        DocumentBuilder builder;  
        try  
        {  
            builder = factory.newDocumentBuilder();  
            Document doc = builder.parse( new InputSource( new StringReader( xmlStr ) ) ); 
            return doc;
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
        return null;
    }
	
	private static void updateRentList(String yyyyMM) {
		
		System.out.println("Updating rent list... Total: " + jCodeList.size());
		
		for (JCode jCode : jCodeList) {
			
			int code = jCode.getCode();
			
			String rentSearched = serverConnector.GetApartmentRentList(code, yyyyMM);
			Document document = convertStringToDocument(rentSearched);
			if (document == null) {
				continue;
			}
			
			System.out.println("Updating rent list... current j code: " + jCode.toString());
			
			NodeList items = document.getElementsByTagName("item");
			ArrayList<ApartmentInfo> infoList;
			
			if (apartmentInfoList.containsKey(String.valueOf(code))) {
				infoList = apartmentInfoList.get(String.valueOf(code));
			} else {
				infoList = new ArrayList<>();
			}
			
			for (int j = 0; j < items.getLength(); j++) {
				Node item = items.item(j);
				if (item == null) {
					continue;
				}
				
				NodeList children = item.getChildNodes();
				ApartmentInfo info = new ApartmentInfo();
				
				for (int k = 0; k < children.getLength(); k++) {
					
					Node child = children.item(k);
					if (child == null) {
						continue;
					}
					
					try {
						String childName = child.getNodeName();
						String value = child.getTextContent();
						if (childName.equals("건축년도")) {
							
							int builtYear = Integer.parseInt(value);
							info.setBuiltYear(builtYear);
						} else if (childName.equals("년")) {
							
							int year = Integer.parseInt(value);
							info.setYear(year);
						} else if (childName.equals("월")) {
							
							int month = Integer.parseInt(value);
							info.setMonth(month);
						} else if (childName.equals("일")) {
							
							int day = Integer.parseInt(value.split("~")[0]);
							info.setDay(day);
						} else if (childName.equals("보증금액")) {
							
							String trimmed = value.trim().replace(",", "");
							int rentPrice = Integer.parseInt(trimmed);
							info.setRentPrice(rentPrice);
						} else if (childName.equals("월세금액")) {
							
							String trimmed = value.trim().replace(",", "");
							int rentPriceMonthly = Integer.parseInt(trimmed);
							info.setRentPriceMonthly(rentPriceMonthly);
						} else if (childName.equals("전용면적")) {
							
							double apartmentSize = Double.parseDouble(value);
							info.setApartmentSize(apartmentSize);
						} else if (childName.equals("지번")) {
							
							String jibeon = value;
							info.setJibeon(jibeon);
						} else if (childName.equals("법정동")) {
							
							String legalDong = value.trim();
							info.setLegalDong(legalDong);
						} else if (childName.equals("아파트")) {
							
							String apartmentName = value.replaceAll("\\p{Z}", "");
							info.setApartmentName(apartmentName);
						} else if (childName.equals("층")) {
							
							int floor = Integer.parseInt(value);
							info.setFloor(floor);
						}
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (DOMException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				info.setJCode(code);
				infoList.add(info);
			}

			if (infoList.size() > 0) {
				
				apartmentInfoList.put(String.valueOf(code), infoList);
			}
		}
	}
	
	private static void updateTradeList(String yyyyMM) {
		
		System.out.println("Updating trade list... Total: " + jCodeList.size());
		
		for (JCode jCode : jCodeList) {
			
			int code = jCode.getCode();

			String rentSearched = serverConnector.GetApartmentTradeList(code, yyyyMM);
			Document document = convertStringToDocument(rentSearched);
			if (document == null) {
				continue;
			}
			
			System.out.println("Updating trade list... current j code: " + jCode.toString());
			
			NodeList items = document.getElementsByTagName("item");
			ArrayList<ApartmentInfo> infoList;
			
			if (apartmentInfoList.containsKey(String.valueOf(code))) {
				infoList = apartmentInfoList.get(String.valueOf(code));
			} else {
				infoList = new ArrayList<>();
			}
			
			for (int j = 0; j < items.getLength(); j++) {
				Node item = items.item(j);
				if (item == null) {
					continue;
				}
				
				NodeList children = item.getChildNodes();
				ApartmentInfo info = new ApartmentInfo();
				
				for (int k = 0; k < children.getLength(); k++) {
					
					Node child = children.item(k);
					if (child == null) {
						continue;
					}
					
					try {
						String childName = child.getNodeName();
						String value = child.getTextContent();
						if (childName.equals("건축년도")) {
							
							int builtYear = Integer.parseInt(value);
							info.setBuiltYear(builtYear);
						} else if (childName.equals("년")) {
							
							int year = Integer.parseInt(value);
							info.setYear(year);
						} else if (childName.equals("월")) {
							
							int month = Integer.parseInt(value);
							info.setMonth(month);
						} else if (childName.equals("일")) {
							
							int day = Integer.parseInt(value.split("~")[0]);
							info.setDay(day);
						} else if (childName.equals("거래금액")) {
							
							String trimmed = value.trim().replace(",", "");
							int tradePrice = Integer.parseInt(trimmed);
							info.setTradePrice(tradePrice);
						} else if (childName.equals("전용면적")) {
							
							double apartmentSize = Double.parseDouble(value);
							info.setApartmentSize(apartmentSize);
						} else if (childName.equals("지번")) {
							
							String jibeon = value;
							info.setJibeon(jibeon);
						} else if (childName.equals("법정동")) {
							
							String legalDong = value.trim();
							info.setLegalDong(legalDong);
						} else if (childName.equals("아파트")) {
							
							String apartmentName = value.replaceAll("\\p{Z}", "");
							info.setApartmentName(apartmentName);
						} else if (childName.equals("층")) {
							
							int floor = Integer.parseInt(value);
							info.setFloor(floor);
						}
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (DOMException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				info.setJCode(code);
				updateList(infoList, info);
			}

			if (apartmentInfoList.containsKey(String.valueOf(code)) == false) {
				
				if (infoList.size() > 0) {
					
					apartmentInfoList.put(String.valueOf(code), infoList);
				}
			}
		}
	}
	
	private static void updateList(ArrayList<ApartmentInfo> list, ApartmentInfo info) {
		
		for (ApartmentInfo item : list) {
			
			if (item.getApartmentName().equals(info.getApartmentName()) &&
				item.getApartmentSize() == info.getApartmentSize() &&
				item.getJCode() == info.getJCode() &&
				item.getLegalDong().equals(info.getLegalDong()) &&
				item.getDay() == info.getDay() &&
				item.getFloor() == info.getFloor() &&
				item.getJibeon() == info.getJibeon()) {
				
				item.setTradePrice(info.getTradePrice());
			}
		}
		
		list.add(info);
	}
	
	private static void sendDataToDB() {
		
		System.out.println("Sending data to DB...");
		
		for (JCode jCode : jCodeList) {
			
			ArrayList<ApartmentInfo> infoList = apartmentInfoList.get(String.valueOf(jCode.getCode()));
			if (infoList == null) {
				continue;
			}
			
			System.out.println("Sending apartment info... current j code: " + jCode.toString());
			
			for (ApartmentInfo info : infoList) {
				
				serverConnector.AddApartmentInfo(info.getApartmentName(), info.getBuiltYear(),
						info.getYear(), info.getMonth(), info.getDay(), info.getLegalDong(), info.getJibeon(), 
						info.getApartmentSize(), info.getJCode(), info.getFloor(), 
						info.getTradePrice(), info.getRentPrice(), info.getRentPriceMonthly());
			}
		}
	}
}

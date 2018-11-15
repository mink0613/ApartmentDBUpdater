import java.io.StringReader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import common.ApartmentInfoAvg;
import common.JCode;
import server_connector.ServerConnector;

public class ApartmentFinderDBUpdater {
	
	private static ServerConnector serverConnector;
	
	private static ExcelReader excelReader;
	
	private static ArrayList<JCode> jCodeList;
	
	// HashMap<code, ArrayList<ApartmentInfo>>
	private static HashMap<String, ArrayList<ApartmentInfo>> apartmentInfoList = new HashMap<>();
	
	private static HashMap<String, ArrayList<ApartmentInfoAvg>> apartmentInfoAvgList = new HashMap<>();
	
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
		for (int year = 2018; year < 2019; year++) {
			
			for (int month = 8; month < 11; month++) {
				
				String yyyymm = String.valueOf(year) + String.format("%02d", month);
				updateRentList(yyyymm);
				updateTradeList(yyyymm);
			}
		}
		//updateRentList("201810");
		//updateTradeList("201810");
		
		updateAvgList();
		
		sendDataToDB();
		sendAvgDataToDB();
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
        try  {
        	
            builder = factory.newDocumentBuilder();  
            Document doc = builder.parse( new InputSource( new StringReader( xmlStr ) ) ); 
            return doc;
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
        return null;
    }
	
	private static void updateRentList(String yyyyMM) {
		
		System.out.println(yyyyMM + " Updating rent list... Total: " + jCodeList.size());
		
		for (JCode jCode : jCodeList) {
			
			int code = jCode.getCode();
			
			String rentSearched = serverConnector.GetApartmentRentList(code, yyyyMM);
			Document document = convertStringToDocument(rentSearched);
			if (document == null) {
				continue;
			}
			
			System.out.println(yyyyMM + " Updating rent list... current j code: " + jCode.toString());
			
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
							
							String trimmed = value.replaceAll("\\p{Z}", "").replaceAll(",", "");
							int rentPrice = Integer.parseInt(trimmed);
							info.setRentPrice(rentPrice);
						} else if (childName.equals("월세금액")) {
							
							String trimmed = value.replaceAll("\\p{Z}", "").replaceAll(",", "");
							int rentPriceMonthly = Integer.parseInt(trimmed);
							info.setRentPriceMonthly(rentPriceMonthly);
						} else if (childName.equals("전용면적")) {
							
							double apartmentSize = Double.parseDouble(value);
							info.setApartmentSize(apartmentSize);
						} else if (childName.equals("지번")) {
							
							String jibeon = value.replaceAll("\\p{Z}", "");
							info.setJibeon(jibeon);
						} else if (childName.equals("법정동")) {
							
							String legalDong = value.replaceAll("\\p{Z}", "");
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
		
		System.out.println(yyyyMM + " Updating trade list... Total: " + jCodeList.size());
		
		for (JCode jCode : jCodeList) {
			
			int code = jCode.getCode();

			String rentSearched = serverConnector.GetApartmentTradeList(code, yyyyMM);
			Document document = convertStringToDocument(rentSearched);
			if (document == null) {
				continue;
			}
			
			System.out.println(yyyyMM + " Updating trade list... current j code: " + jCode.toString());
			
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
							
							String trimmed = value.replaceAll("\\p{Z}", "").replaceAll(",", "");
							int tradePrice = Integer.parseInt(trimmed);
							info.setTradePrice(tradePrice);
						} else if (childName.equals("전용면적")) {
							
							double apartmentSize = Double.parseDouble(value);
							info.setApartmentSize(apartmentSize);
						} else if (childName.equals("지번")) {
							
							String jibeon = value.replaceAll("\\p{Z}", "");
							info.setJibeon(jibeon);
						} else if (childName.equals("법정동")) {
							
							String legalDong = value.replaceAll("\\p{Z}", "");
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
				//updateList(infoList, info);
			}

			if (apartmentInfoList.containsKey(String.valueOf(code)) == false) {
				
				if (infoList.size() > 0) {
					
					Collections.sort(infoList);
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
				item.getBuiltYear() == info.getBuiltYear() &&
				item.getYear() == info.getYear() &&
				item.getMonth() == info.getMonth() &&
				item.getDay() == info.getDay() &&
				item.getFloor() == info.getFloor() &&
				item.getJibeon().equals(info.getJibeon())) {
				
				item.setTradePrice(info.getTradePrice());
			}
		}
		
		list.add(info);
	}

	private static void updateAvgList() {
		
		for (JCode jCode : jCodeList) {
			
			ArrayList<ApartmentInfo> infoList = apartmentInfoList.get(String.valueOf(jCode.getCode()));
			if (infoList == null) {
				continue;
			}
			
			System.out.println("Updating apartment info average... current j code: " + jCode.toString());
			
			ArrayList<ApartmentInfoAvg> infoAvgList = new ArrayList();
			
			for (ApartmentInfo info : infoList) {
				
				updateInfoAvg(infoAvgList, info);
			}
			
			apartmentInfoAvgList.put(String.valueOf(jCode.getCode()), infoAvgList);
		}
	}
	
	private static ApartmentInfoAvg hasInfoAvg(ArrayList<ApartmentInfoAvg> apartmentInfoAvgList, ApartmentInfo info) {
		
		if (apartmentInfoAvgList.size() == 0) {
			return null;
		}
		
		for (ApartmentInfoAvg infoAvg : apartmentInfoAvgList) {
			
			if (infoAvg.getApartmentName().equals(info.getApartmentName()) &&
				infoAvg.getJCode() == info.getJCode() &&
				infoAvg.getLegalDong().equals(info.getLegalDong()) &&
				infoAvg.getBuiltYear() == info.getBuiltYear() &&
				infoAvg.getYear() == info.getYear() &&
				infoAvg.getMonth() == info.getMonth() &&
				infoAvg.getJibeon().equals(info.getJibeon()) &&
				infoAvg.getJCode() == info.getJCode()) {
				
				return infoAvg;
			}
		}
		
		return null;
	}
	
	private static void updateInfoAvg(ArrayList<ApartmentInfoAvg> apartmentInfoAvgList, ApartmentInfo info) {
		
		ApartmentInfoAvg infoAvg = hasInfoAvg(apartmentInfoAvgList, info);
		if (infoAvg == null) {
			
			ApartmentInfoAvg newInfoAvg = new ApartmentInfoAvg();
			newInfoAvg.setApartmentName(info.getApartmentName());
			newInfoAvg.setBuiltYear(info.getBuiltYear());
			newInfoAvg.setYear(info.getYear());
			newInfoAvg.setMonth(info.getMonth());
			newInfoAvg.setDay(info.getDay());
			newInfoAvg.setLegalDong(info.getLegalDong());
			newInfoAvg.setJCode(info.getJCode());
			newInfoAvg.setJibeon(info.getJibeon());
			
			if (info.getTradePrice() > 0) {
				newInfoAvg.addTradePrice(info.getTradePrice());
			}
			
			// 반전세는 계산에서 제외
			if (info.getRentPrice() > 0 && info.getRentPriceMonthly() == 0) {
				newInfoAvg.addRentPrice(info.getRentPrice());
			}
			
			apartmentInfoAvgList.add(newInfoAvg);
		} else {
			
			if (info.getTradePrice() > 0) {
				infoAvg.addTradePrice(info.getTradePrice());
			}
			
			// 반전세는 계산에서 제외
			if (info.getRentPrice() > 0 && info.getRentPriceMonthly() == 0) {
				infoAvg.addRentPrice(info.getRentPrice());
			}
		}
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
	
	private static void sendAvgDataToDB() {
		
		System.out.println("Sending average data to DB...");
		
		for (JCode jCode : jCodeList) {
			
			ArrayList<ApartmentInfoAvg> infoList = apartmentInfoAvgList.get(String.valueOf(jCode.getCode()));
			if (infoList == null) {
				continue;
			}
			
			System.out.println("Sending apartment info average... current j code: " + jCode.toString());
			
			for (ApartmentInfoAvg info : infoList) {
				
				serverConnector.AddApartmentInfoAvg(info.getApartmentName(), info.getBuiltYear(),
						info.getYear(), info.getMonth(), info.getLegalDong(), info.getJibeon(), 
						info.getJCode(), info.getTradePriceAvg(), info.getRentPriceAvg(), info.getTradeCount(), info.getRentCount());
			}
		}
	}
}

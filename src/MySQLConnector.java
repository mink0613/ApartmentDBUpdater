import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLConnector {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://modoocoupon.woobi.co.kr/modoocoupon"; // 182.162.94.60:35523

	static final String USERNAME = "modoocoupon";
	static final String PASSWORD = "fjtnldywjd1";

	public String executeQuery(String query) {
		
		Connection conn = null;
		Statement stmt = null;
		String result = "";
		
		try{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			System.out.println("\n- MySQL Connection");
			stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(query);

			while(rs.next()){
				
				result += rs.toString();
			}
			rs.close();
			stmt.close();
			conn.close();
		}catch(SQLException se1){
			se1.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException se2){
			}
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
		
		System.out.println("\n\n- MySQL Connection Close");
		return result;
	}
	
	public void updateJCodeDB() {
		
		
	}
}

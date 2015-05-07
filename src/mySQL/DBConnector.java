package mySQL;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector {

	private static final String USERNAME = "dbuser";
	private static final String PASSWORD = "dbpassword";
	private static final String CONN_URL_STRING = 
			"jdbc:mysql://localhost/mytest";
	
	public static void main(String[] args) {
		try (
			Connection conn = DriverManager.getConnection(
				CONN_URL_STRING, USERNAME, PASSWORD
			);
			Statement stmt = conn.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE, 
				ResultSet.CONCUR_READ_ONLY
			);
			ResultSet rs = stmt.executeQuery(
				"select order_id, order_name, order_Qyt from order_details"
			);
		) {
			rs.last();
			System.out.println("Number of rows: "+ rs.getRow());
			rs.beforeFirst();
			while (rs.next()) {
				int id = rs.getObject("order_id", Integer.class);
				String name = rs.getObject("order_name", String.class);
				int qyt = rs.getObject("order_Qyt", Integer.class);
				System.out.println("["+id+"] "+name+" ("+qyt+")");
			}
		} 
		catch (SQLException e) {
			DBUtil.processException(e);
		} 
	}

}

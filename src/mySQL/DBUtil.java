package mySQL;

import java.sql.SQLException;

public class DBUtil {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static void processException(SQLException e) {
		System.err.println("Error Message: "+e.getMessage());
		System.err.println("Error Code: "+e.getErrorCode());
		System.err.println("SQL state: "+e.getSQLState());
	}
}

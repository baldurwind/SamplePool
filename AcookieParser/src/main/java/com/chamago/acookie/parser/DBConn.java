package com.chamago.acookie.parser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConn {
	
	public static Connection acookiedb = null;
	public static Connection opendb = null;
	private static Properties props = new Properties();
	
	static {
		try {
			// Class.forName("com.mysql.jdbc.Driver");
			com.mysql.jdbc.Driver driver = new com.mysql.jdbc.Driver();
			opendb = DriverManager.getConnection(C.OPEN_DB_URL, C.OPEN_DB_USER,
					C.OPEN_DB_PASSWORD);
			acookiedb = DriverManager.getConnection(C.ACOOKIE_DB_URL,
					C.ACOOKIE_DB_USER, C.ACOOKIE_DB_PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String ags[]) throws SQLException {
		System.out.println(DBConn.opendb);
	}

}

package com.chamago.acookie.parser;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBConnTest {

	@Test
	public void test() throws SQLException {
		  DBConn  dbconn= new DBConn();
		Connection conn=dbconn.open;
		Statement st=conn.createStatement();
		ResultSet rs=st.executeQuery("SELECT 1");
		Assert.assertTrue(rs.getRow()==0);
	}
	

}

package com.chamago.acookie.parser;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class LkpService {

	public List<String[]> channel = new ArrayList<String[]>();
	public List<String[]> channelPattern = new ArrayList<String[]>();
	public List<String[]> city = new ArrayList<String[]>();
	public List<String[]> province = new ArrayList<String[]>();
	public List<String[]> browser = new ArrayList<String[]>();
	public Map<String, String> sellerMapping = new HashMap<String, String>();

	public Hashtable<String, String> sellerBuyerMapping = new Hashtable<String, String>();

	private Connection open;
	private Connection acookie;

	private final static String SQL_QUERY_ALL_PROVINCE = "SELECT id,name FROM lkp_province";
	private final static String SQL_QUERY_ALL_CITY = "SELECT id,name FROM lkp_city";
	private final static String SQL_QUERY_ALL_CHANNEL = "SELECT  channel_id,url_pattern_regex,url_pattern FROM lkp_channel_pattern WHERE status=1";
	private final static String SQL_QUERY_ALL_BROWSER = "SELECT id, name FROM lkp_browser";
	private final static String SQL_QUERY_ALL_SELLER_MAPPING = "SELECT nick,user_id FROM taobao_subscriber WHERE app_key="
			+ C.APPKEY;
	private final static String SQL_QUERY_ALL_SELLER_MASK_MAPPING = "SELECT id,seller_id,mask FROM  seller_buyer_mapping ";// where
																															// seller_id

	public LkpService() {
		open = new DBConn().opendb;
		acookie = new DBConn().acookiedb;
		System.out.println("Province Size:" + open);
		try {
			initProvince();
			initCity();
			initBrowser();
			initChannel();
			initSellerMapping();
			initSellerBuyerMapping();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("Province Size:" + province.size());
		System.out.println("City Size:" + city.size());
		System.out.println("Channel Pattern Size:" + channelPattern.size());
	}

	private void initSellerMapping() throws SQLException {
		Statement stat = open.createStatement();
		ResultSet rs = stat.executeQuery(SQL_QUERY_ALL_SELLER_MAPPING);

		while (rs.next()) {
			System.out.println(rs.getString(1));
			sellerMapping.put(rs.getString(1), rs.getString(2));
		}

		rs.close();
		stat.close();
	}

	private void initSellerBuyerMapping() throws SQLException {
		Statement stat = acookie.createStatement();
		ResultSet rs = stat.executeQuery(SQL_QUERY_ALL_SELLER_MASK_MAPPING);

		while (rs.next())
			sellerBuyerMapping.put(rs.getString(2) + "_" + rs.getString(3),
					rs.getString(1));

		rs.close();
		stat.close();
	}

	private void initProvince() throws SQLException {
		Statement stat = acookie.createStatement();
		ResultSet rs = stat.executeQuery(SQL_QUERY_ALL_PROVINCE);
		while (rs.next())
			province.add(new String[] { rs.getString(1), rs.getString(2) });
		rs.close();
		stat.close();
	}

	private void initCity() throws SQLException {
		Statement stat = acookie.createStatement();
		ResultSet rs = stat.executeQuery(SQL_QUERY_ALL_CITY);
		while (rs.next())
			city.add(new String[] { rs.getString(1), rs.getString(2) });
		rs.close();
		stat.close();
	}

	private void initChannel() throws SQLException {
		Statement stat = acookie.createStatement();
		ResultSet rs = stat.executeQuery(SQL_QUERY_ALL_CHANNEL);
		while (rs.next())
			channelPattern.add(new String[] { rs.getString(1), rs.getString(2),
					rs.getString(3) });
		rs.close();
		stat.close();
	}

	private void initBrowser() throws SQLException {
		Statement stat = acookie.createStatement();
		ResultSet rs = stat.executeQuery(SQL_QUERY_ALL_BROWSER);
		while (rs.next())
			browser.add(new String[] { rs.getString(1), rs.getString(2) });
		rs.close();
		stat.close();
	}

	public static void main(String ags[]) {
		new LkpService();
	}

}
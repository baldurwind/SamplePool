package com.chamago.acookie.parser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class C {
	private static Properties props = new Properties();

	public static String OPEN_DB_DRIVER;
	public static String OPEN_DB_URL;
	public static String OPEN_DB_USER;
	public static String OPEN_DB_PASSWORD;

	public static String ACOOKIE_DB_DRIVER;
	public static String ACOOKIE_DB_URL;
	public static String ACOOKIE_DB_USER;
	public static String ACOOKIE_DB_PASSWORD;

	public static String IP_FILE_PATH;
	public static String APPKEY;

	static {
		try {
			InputStream in = C.class.getClassLoader().getResourceAsStream(
					"configuration.properties");
			props.load(in);
			OPEN_DB_DRIVER = props.getProperty("open.db.driver");
			OPEN_DB_URL = props.getProperty("open.db.url");
			OPEN_DB_USER = props.getProperty("open.db.user");
			OPEN_DB_PASSWORD = props.getProperty("open.db.password");

			ACOOKIE_DB_DRIVER = props.getProperty("acookie.db.driver");
			ACOOKIE_DB_URL = props.getProperty("acookie.db.url");
			ACOOKIE_DB_USER = props.getProperty("acookie.db.user");
			ACOOKIE_DB_PASSWORD = props.getProperty("acookie.db.password");

			IP_FILE_PATH = props.getProperty("ip.file.path");
			APPKEY = props.getProperty("appkey");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
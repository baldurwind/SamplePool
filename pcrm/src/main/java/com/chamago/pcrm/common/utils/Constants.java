package com.chamago.pcrm.common.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Constants {
	
	public static String FILE_PATH;
	
	public static String REMINDER_TIMEOUTMINUTES;
	
	public static String REMINDER_INTERVALMINUTES;
	
	static {
		try {
			 InputStream is = Constants.class.getResourceAsStream ("/init.properties");
			 Properties props=new Properties();
			 props.load(is);		 
			 FILE_PATH = props.getProperty("file.path");
			 REMINDER_TIMEOUTMINUTES = props.getProperty("reminder.timeoutMinutes");
			 REMINDER_INTERVALMINUTES = props.getProperty("reminder.intervalMinutes");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String ags[]){
		System.out.println(Constants.FILE_PATH);
	}
}

package com.chamago.pcrm.common.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

	/**
	* 发送短信基础类
	* @author administration
	*/
	public class SmsUtils {

	public  static String SendSms(String mobile,String content) throws UnsupportedEncodingException{
	
	Integer x_ac=10;//发送信息
	HttpURLConnection httpconn = null;
		String result="-20";
		String memo = content.length()<70?content.trim():content.trim().substring(0, 70);
	StringBuilder sb = new StringBuilder();
		sb.append("http://service.winic.org/sys_port/gateway/?");
		sb.append("id=").append(C.SMS_USERNAME);
		sb.append("&pwd=").append(C.SMS_PASSWORD);
		sb.append("&to=").append(mobile);
		sb.append("&content=").append(URLEncoder.encode(content, "gb2312"));  //注意乱码的话换成gb2312编码
	try {
		URL url = new URL(sb.toString());
		httpconn = (HttpURLConnection) url.openConnection();
		BufferedReader rd = new BufferedReader(new InputStreamReader(httpconn.getInputStream()));
		result = rd.readLine();
		rd.close();
	}
	catch (MalformedURLException e) {
		e.printStackTrace();
	} 
	catch (IOException e) {
		e.printStackTrace();
	} 
	finally{
		if(httpconn!=null){
			httpconn.disconnect();
			httpconn=null;
		}
	}
	return result;
	}
	
}

package com.chamago.pcrm.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Utils {
	private final static String userdir=System.getProperty("user.dir");
	public final static String  fileSeparator =System.getProperty("file.separator");
	public final static String lineSeparator =System.getProperty("line.separator");
	
	public final static ApplicationContext getFileSystemXMlApplication(){
		  return new FileSystemXmlApplicationContext(userdir+fileSeparator+"WebRoot/WEB-INF/config/applicationContext.xml");
	}
	public final static ApplicationContext getClassPathXMlApplication(){
		return  new ClassPathXmlApplicationContext("ac.xml");
	}
	
	public final static Date formatDate(String str){
		 try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			 return sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String DateToString(Date date,String pattern){
		
		if(null==date){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern(pattern);
		return sdf.format(date);
		
	}
	public static void delDirectory(File directory){  
        File[] children = directory.listFiles();  
        if(children.length==0){  
            directory.delete();  
        }  
        else{  
            for(int i=0;i<children.length;i++){  
                if(children[i].isFile()){  
                    children[i].delete();  
                }  
                else if(children[i].isDirectory()){  
                	delDirectory(children[i]);  
                    System.out.println("\t"+children[i].getAbsolutePath());  
                }  
            }  
            directory.delete();  
            System.out.println("\t删除目录"+directory.getName());  
        }  
    }   
	public static Date StringToDate(String date,String pattern) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern(pattern);
		return sdf.parse(date);
	}
	
	public final static String parseDate(Date str){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			 return sdf.format(str);
	}
	public static Date nextDate(int minitues){
		 Calendar cal=Calendar.getInstance();
		 cal.add(Calendar.MINUTE, minitues);
		 return  cal.getTime();
	}
	// 获取当月第一天
	public static String getFirstDayOfMonth() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1 号
		str = sdf.format(lastDate.getTime());
		return str+" 00:00:00";
	}
	
	public static String getCurrentDate() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar lastDate = Calendar.getInstance();
		str = sdf.format(lastDate.getTime());
		return str+" 00:00:00";
	}
	public static String getCurrentDateTime() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		str = sdf.format(new Date());
		return str;
	}
	public static String BUILD_SQL_IN_FIELD(List<String> list){
		StringBuilder sb=new StringBuilder();
		for(String tmp:list){
			sb.append(tmp).append(",");
		}
		return sb.substring(0, sb.toString().length()-1);
	}
	
	public static Object diffDateTimeTip(Object obj){
		Timestamp time= Timestamp.valueOf((String)obj) ;
		Date now=new Date();
		
		return obj;
	}
	
	public static String[] readAllLines(String path)
	{
		FileReader reader=null;
		BufferedReader br=null;
		ArrayList lines=new ArrayList();
		try
		{
			reader = new FileReader(path);
			br = new BufferedReader(reader);		
			String nextLine;
			
			while ((nextLine = br.readLine()) != null) 
			{
				lines.add(nextLine);
			}	
			if(br!=null)
				br.close();
			if(reader!=null)
				reader.close();
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		 
	
		return (String[]) lines.toArray(new String[lines.size()]);
	}
	public static void writeAllText(String path, String contents) throws IOException
	{
		FileOutputStream os=null;
		PrintWriter out=null;
		try
		{
			os=new FileOutputStream(path);
			out =new PrintWriter(os);
			out.println(contents);
		}finally	
		{
	
			if(out!=null)
				out.close();
			if(os!=null)
				os.close();
		}
	}
	
	public  static PagedListHolder<?>  pagination(Integer pageSize,PagedListHolder<?> obj,HttpServletRequest req){
		obj.setPageSize(pageSize);
		String value=req.getParameter("page");
		
		 if(null==value)
			 return obj;
		else if (C.PAGINATION_TAG_NEXT.equals(value)){
			obj.nextPage();
		}
			
		else if (C.PAGINATION_TAG_PREVIOUS.equals(value)) 
			obj.previousPage();
		else if(C.PAGINATION_TAG_FIRST.equals(value))
			obj.setPage(0);
		else if(C.PAGINATION_TAG_LAST.equals(value))
			obj.setPage(obj.getPageCount());
		
		return obj;
	}
	
	/**
	 *  html、script、style 代码得到纯字符串 
	 */
	public static String getNoHTMLString(String content) {
		if (null == content && content.trim().length() <=0 ) {
			return "";
		}
		Pattern p_script;
		Matcher m_script;
		Pattern p_style;
		Matcher m_style;
		Pattern p_html;
		Matcher m_html;
		
		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
			// 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script> }
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
			// 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style> }
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(content);
			content = m_script.replaceAll(""); // 过滤script标签
			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(content);
			content = m_style.replaceAll(""); // 过滤style标签

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(content);

			content = m_html.replaceAll(""); // 过滤html标签
		} catch (Exception e) {
			return "";
		}
		
		content = validHtml(content);
		return content;
	}
	
	/**
	 * 转译特殊字符
	 */
	public static String validHtml(String original) {
        StringBuilder sb = new StringBuilder(original.length());
        for (char next : original.toCharArray()) {
            switch (next) {
                case '>':
                    sb.append("&gt;");
                    break;
                case '<':
                    sb.append("&lt;");
                    break;
                case '&':
                    sb.append("&amp;");
                    break;
                case '"':
                    sb.append("&quot;");
                    break;
                case '\'':
                    sb.append("&#39;");
                    break;
                default:
                    sb.append(next);
                    break;
            }
        }
        return sb.toString().replaceAll("\r\n", "<br />");
    }
	
	public static void checkFileExist(String filepath) throws Exception {
		File file = new File(filepath);
		if (!file.exists()) {
			file.mkdirs();
		}
	}
	
	
	public static String decodeUTF8(String value){
//		if(value==null){
//			return null;
//		} 
//		try {
//			String os = System.getProperty("os.name");
//			if(os!=null){
//				os = os.toUpperCase();
//				if(os.contains("WIN")){
//					value = java.net.URLDecoder.decode(value, "GBK");
//					value = new String(value.getBytes("ISO-8859-1"),"UTF-8");
//				}else {
//					value = java.net.URLDecoder.decode(value, "UTF-8");
//				}
//			}
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
		
		return value;
	}
	
	/*
	 * JSON 输出
	 */
	public static void outJsonString(Map<String, ? extends Object> map, HttpServletResponse response) {
		JSONObject obj = JSONObject.fromObject(map);
		// AJAX输出，返回null
		try {
			response.setContentType("text/html;charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().write(obj.toString());
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				response.getWriter().close();
			} catch(IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public static void main(String ags[]){
		
	}
}

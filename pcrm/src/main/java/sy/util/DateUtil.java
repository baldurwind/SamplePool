package sy.util;

import java.text.ParseException;
import java.util.Date;

/**
 * 日期工具类
 * 
 * @author ben.xu
 * 
 */
public class DateUtil {
	/**
	 * 将Date类型转换为字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		return format(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 将Date类型转换为字符串
	 * 
	 * @param date
	 * @param pattern
	 *            字符串格式
	 * @return
	 */
	public static String format(Date date, String pattern) {
		if (date == null) {
			return "null";
		}
		if (pattern == null || pattern.equals("") || pattern.equals("null")) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		return new java.text.SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 将字符串转换为Date类型
	 * 
	 * @param date
	 * @return
	 */
	public static Date format(String date) {
		return format(date, null);
	}

	/**
	 * 将字符串转换为Date类型
	 * 
	 * @param date
	 * @param pattern
	 *            格式
	 * @return
	 */
	public static Date format(String date, String pattern) {
		if (pattern == null || pattern.equals("") || pattern.equals("null")) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		if (date == null || date.equals("") || date.equals("null")) {
			return new Date();
		}
		Date d = null;
		try {
			d = new java.text.SimpleDateFormat(pattern).parse(date);
		} catch (ParseException pe) {
		}
		return d;
	}

}

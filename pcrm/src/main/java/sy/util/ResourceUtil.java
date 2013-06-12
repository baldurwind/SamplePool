package sy.util;

import java.util.ResourceBundle;

/**
 * 项目参数工具类
 * 
 * @author ben.xu
 * 
 */
public class ResourceUtil {

//	private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("config/syConfig");
	private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("jdbc");
	/**
	 * 获得sessionInfo名字
	 * 
	 * @return
	 */
	public static final String getSessionInfoName() {
		return bundle.getString("sypro.sessionInfoName");
	}

}

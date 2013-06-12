/**
 * 
 */
package com.chamago.pcrm.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author gavin.peng
 *
 */
public class PCRMEncrypt {

	
	 public static String e(String inputText)
    {
      return md5(inputText);
    }
	 
	public static String md5(String inputText)
    {
	    return encrypt(inputText, "md5");
	}

	private static String encrypt(String inputText, String algorithmName)
	{
	    if ((inputText == null) || ("".equals(inputText.trim()))) {
	      throw new IllegalArgumentException("请输入要加密的内容");
	    }
	    if ((algorithmName == null) || ("".equals(algorithmName.trim()))) {
	      algorithmName = "md5";
	    }
	    String encryptText = null;
	    try {
	      MessageDigest m = MessageDigest.getInstance(algorithmName);
	      m.update(inputText.getBytes("UTF8"));
	      byte[] s = m.digest();

	      return hex(s);
	    } catch (NoSuchAlgorithmException e) {
	      e.printStackTrace();
	    } catch (UnsupportedEncodingException e) {
	      e.printStackTrace();
	    }
	    return encryptText;
	}
	 
	 
	 private static String hex(byte[] arr)
	  {
	    StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < arr.length; i++) {
	      sb.append(Integer.toHexString(arr[i] & 0xFF | 0x100).substring(1, 3));
	    }
	    return sb.toString();
	  }
}

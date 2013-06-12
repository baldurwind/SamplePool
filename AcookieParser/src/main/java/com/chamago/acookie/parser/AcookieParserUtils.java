package com.chamago.acookie.parser;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.chamago.utils.ObjectID;

public class AcookieParserUtils {
	
	private static LkpService lkp=new LkpService();
	private static URLDecoder decoder=new URLDecoder();
	private static IPSeeker seek=new  IPSeeker();
	
	public final static String INSERT_TAG="i";
	private static Hashtable<String,String> sessionTable=new Hashtable();
	private String ANONYMOUS="anonymous";
	
	public  String parseKeyWords(String url) {
		String tag="s.taobao.com/search?q=";
		try {
			if(url.contains("&"))
				url=url.substring(0,url.indexOf("&"));
			
			if(url.contains(tag)){
				url=url.replace(tag,"");
				return decoder.decode(url,"GBK").replace("http://", "");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	return  null;
} 
	public String parseBrowser(String value){
		for(String[] str:lkp.browser){
			if(value.toLowerCase().contains(str[1]))
				return str[0];
		}
		return "0";
	}
	public  String parseNumiid(String url) {
		if(url==null)
			return null;
	    Pattern pt=Pattern.compile("[?&]id=\\d*",Pattern.CASE_INSENSITIVE);
	    Matcher m=pt.matcher(url);
	    if(m.find())
	       return m.group().substring(4);
	    else
	    	return null;
	}	
	
	public String parseChannel(String fromUrl){
		for(String [] str:lkp.channelPattern){
			   if(fromUrl.toLowerCase().contains(str[2].toLowerCase()))
				   return str[0];
		}
		return null;
	/*	 Pattern pt=null;
		Matcher m=null;
		for(String[] strPattern:lkp.channelPattern){
			  pt=Pattern.compile(strPattern[1],Pattern.CASE_INSENSITIVE);
			  m=pt.matcher(fromUrl);
			  if(m.find())
			      return strPattern[0];
		}
		return null; */
	}
	
	public String parseCity(String ip){
		String value=seek.getAddress(ip);
		List<String[]> list=lkp.city;
		for(String[] arr:list){
			if(value.contains(arr[1]))
				return arr[0];
		}
		return "0";
	}
	
	public String parseSellerId(String seller){
		return lkp.sellerMapping.get(seller);
	}
	public void complete(){
	/*	String str="";
		Map map=lkp.sellerBuyerMapping;
		Iterator it=map.keySet().iterator();
		while(it.hasNext()){
			str+=String.valueOf(it.next()+"/n");
		}*/
	/*	try {
			Utils.writeAllText("c:/11.txt", str);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}
	public synchronized String parseSessionID(String session){
		session=session.split("_")[0];
		String id=sessionTable.get(session);
		if(null==id){
			id=ObjectID.id();
			sessionTable.put(session, id);
			id= INSERT_TAG+id;
		}
		return id;
		
	}
	public String parseSession(String session){
		return session.split("_")[0];
	}
	
	public synchronized String parseSBID(String seller_id,String mask){
		if(null==mask||"".equals(mask.trim()))
			mask=this.ANONYMOUS;
		
		String id=lkp.sellerBuyerMapping.get(seller_id+"_"+mask);
		if(id==null){
			  id=ObjectID.id();
			lkp.sellerBuyerMapping.put(seller_id+"_"+mask, id);
			id= INSERT_TAG+id;
		} 
		return id; 
	}
	public String newID(String seller_id,String mask){
		String id=ObjectID.id();
		lkp.sellerBuyerMapping.put(seller_id+"_"+mask, id);
		return id;
	}
	
	public String parseCategoryId(String currentUrl){
		Pattern pt=Pattern.compile("[?&]cid=\\d*",Pattern.CASE_INSENSITIVE);
	    Matcher m=pt.matcher(currentUrl);
	    if(m.find())
	       return m.group().substring(5);
	    else
	    	return null;
	}
	
	public static void main(String[] agrsg){
		AcookieParserUtils utils =new AcookieParserUtils();
		 //System.out.println(utils.parseCity("116.209.26.*"));
		 System.out.println(utils.parseSellerId("西品集旗舰店"));
    	//	 String str=utils.parseNumiid("http://item.tmall.com/item.htm?id=12703333640");
		//String str=utils.parseBrowser("FIREfox");
		//String str=utils.parseKeyWords("http://s.taobao.com/search?q=%D7%D4%C8%BB%D6%AE%B1%A6+%D7%F3%D0%FD%C8%E2&sort=sale-des");
		//String str=utils.parseChannel("http://naturesbounty.tmall.com/shop/view_shop.htm");;
    		//   str=utils.parseSessionID("4f5ad81840ca3b9aaeda5ad5_1320062586_2");
    		//  str= utils.parseChannel("http://item.tmall.com/item.htm?id=13167240849");
    		//  str= utils.parseCategoryId("http://item.tmall.com/item.htm?cid=13167240849&sdsd=sd");
    		// System.out.println(str);
	//	 System.out.println(id);
	/*	Pattern	  pt=Pattern.compile("(trade\\.)(taobao)(\\.com) (?!\\.)",Pattern.CASE_INSENSITIVE);
		Matcher  m=pt.matcher("http://trade.taobao.com/item.htm?id=12703333640");
		  if(m.find())
		    System.out.println(1);
		String value=utils.parseChannel("");
		
		System.out.println(value);*/
	}
	/*	if(id==null){
	id=objectId.id();
				try {
		PreparedStatement	stat = conn.prepareStatement("INSERT INTO seller_buyer_mapping values(?,?,?,?)");
		stat.setString(1, id);
		stat.setString(2, seller_id);
		stat.setString(3, mask);
		stat.setObject(4, new Date());
		stat.execute();
		lkp.sellerBuyerMapping.put(seller_id+"_"+mask, id);
	} catch (SQLException e) {
		e.printStackTrace();
	}

}*/
}

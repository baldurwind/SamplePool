package com.chamago.pcrm.common.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class C {

		public   static String  TOP_SERVER_URL="http://gw.api.taobao.com/router/rest";
	    //public final static String PCRM_APPKEY="12535507";//"12532585";//"12285265";// 12541172 //12291050
	    public   static String  PCRM_APPKEY;//12532584
	    public   static String  KETTLE_HOST;
	    public   static int     KETTLE_PORT;
	    public   static String  KETTLE_USERNAME;
	    public   static String  KETTLE_PASSWORD;
	    public   static String  KETTLE_DB;
	    
	    public static String SMS_USERNAME="";
	    public static String SMS_PASSWORD="";
	
    public final static int PAGINATION_SIZE=3;
    public final static String PAGINATION_TAG_NAME="page";
    public final static String PAGINATION_TAG_NEXT="next";
    public final static String PAGINATION_TAG_PREVIOUS="previous";
    public final static String PAGINATION_TAG_FIRST="first";
    public final static String PAGINATION_TAG_LAST="last";
    public final static String PAGINATION_TAG_="last";
    
    
    public final static String LOG_LAYER_CONTROLLER="c";
    public final static String LOG_LAYER_DAO="d";
    
    public final static String LOG_MODULE_PCRM="pcrm";
    public final static String LOG_MODULE_TRADE="trade";
    public final static String LOG_MODULE_ITEM="item";
    public final static String LOG_MODULE_MARKETING="marketing";
    public final static String LOG_MODULE_BEHAVIOR="behavior";
    public final static String LOG_MODULE_WORKTABLE="worktable";
    public final static String LOG_MODULE_CUSTOMERSERVICE = "customerService";
    public final static String LOG_MODULE_LEADSMEMO = "leadsMemo";
    public final static String LOG_MODULE_REMINER = "reminder";
    public final static String LOG_MODULE_NOTICE = "notice";
    public final static String LOG_MODULE_KNOWLEDGE = "knowledge";
	
	public final static String LUCENE_DIRECTORY_KNOWLEDGE_BASE="knowledge_base";
	public final static String LUCENE_DIRECTORY_ITEM="item";
	public final static String LUCENE_DIRECTORY_SKU="sku";
	
	public final static String LUCENE_FIELD_ITEM_SELLER="seller";
	public final static String LUCENE_FIELD_ITEM_NUMIID="numiid";
	public final static String LUCENE_FIELD_ITEM_TITLE="title";
	public final static String LUCENE_FIELD_ITEM_PICURL="picurl";
	public final static String LUCENE_FIELD_ITEM_NUM="num";	
	public final static String LUCENE_FIELD_ITEM_PRICE="price";
	public final static String LUCENE_FIELD_ITEM_PROPS="props";
	public final static String LUCENE_FIELD_ITEM_CREATED="created";
	public final static String LUCENE_FIELD_ITEM_HAS_DISCOUNT="discounted";
	
	
	public final static String LUCENE_FIELD_SKU_SELLER="seller";
	public final static String LUCENE_FIELD_SKU_NUMIID="numiid";
	public final static String LUCENE_FIELD_SKU_ID="skuid";
	public final static String LUCENE_FIELD_SKU_PROPS="props";
	public final static String LUCENE_FIELD_SKU_NUM="num";		
	public final static String LUCENE_FIELD_SKU_PRICE="price";		
			
	public final static String  LUCENE_FIELD_KNOWLEDGE_ID="id";
	public final static String  LUCENE_FIELD_KNOWLEDGE_CONTENT="content";
	public final static String  LUCENE_FIELD_KNOWLEDGE_TITLE="title";
	public final static String  LUCENE_FIELD_KNOWLEDGE_ITEMID="numiid";
	public final static String  LUCENE_FIELD_KNOWLEDGE_SUBJECT_ID="subjectid";
	public final static String  LUCENE_FIELD_KNOWLEDGE_SUBJECT_NAME="subjectname";
	public final static String  LUCENE_FIELD_KNOWLEDGE_SEND_NUMS="sendnums";
	public final static String  LUCENE_FIELD_KNOWLEDGE_SELLER="seller";		
	public final static String  LUCENE_FIELD_KNOWLEDGE_CREATOR="creator";
	
	
    /*Item Status*/
    public final static String ITEM_STAUTS_WANT_BUY="WANT_BUY"; //想买
    public final static String ITEM_STAUTS_TRADE_NO_CREATE_PAY="TRADE_NO_CREATE_PAY";//(没有创建支付宝交�?
    public final static String ITEM_STAUTS_WAIT_BUYER_PAY="WAIT_BUYER_PAY";//等待买家付款
    public final static String ITEM_STAUTS_WAIT_SELLER_SEND_GOODS="WAIT_SELLER_SEND_GOODS";//等待卖家发货,�?买家已付�?
    public final static String ITEM_STAUTS_WAIT_BUYER_CONFIRM_GOODS="WAIT_BUYER_CONFIRM_GOODS";//等待买家确认收货,�?卖家已发�?
    public final static String ITEM_STAUTS_TRADE_BUYER_SIGNED="TRADE_BUYER_SIGNED";//买家已签�?货到付款专用
    public final static String ITEM_STAUTS_TRADE_FINISHED="TRADE_FINISHED";//交易成功
    public final static String ITEM_STAUTS_TRADE_CLOSED="TRADE_CLOSED";//付款以后用户�?��成功，交易自动关�?
    public final static String ITEM_STAUTS_TRADE_CLOSED_BY_TAOBAO="TRADE_CLOSED_BY_TAOBAO";//淘宝关闭交易
    
    public static void main(String ags[]){
    //	System.out.println(C.PCRM_APPKEY);
    }
    
   
    
    //public static final String INIT_SUBSCRIBER_SCRIPT_PATH=(InitSubscriberScript.class.getClassLoader().getResource("")+"/init_subscriber_script.txt").replace("file:/", "");
    public static final String INIT_SUBSCRIBER_SCRIPT_FILENAME="init_subscriber_script.txt";
    public static final String CMD_REPLACE_VARIABLE_APPKEY="<!--appkey->";
    public static final String CMD_REPLACE_VARIABLE_SELLERNICK="<!--sellernick->";
    public static final String CMD_REPLACE_VARIABLE_ACOOKIE_DAY="<!--acookie_day->";
    public static final String CMD_REPLACE_VARIABLE_SDATE="<!--sdate->";
    public static final String CMD_REPLACE_VARIABLE_EDATE="<!--edate->";
    public static final String CMD_REPLACE_VARIABLE_DB="<!--db->";
    
    
    
   /* //meal has problem
    public static final String CMD_INIT_ITEM="/home/chamago/kettle-4.2/kitchen.sh -rep=repo-taobao -job=item/taobao.items.get -level=Error -log=/home/chamago/kettle-logs/taobao.items.get.`date +\"%Y%m%d%H%M%S\"`.log -param:app_key=<!--appkey-> -param:db=pcrm2 -param:taobao_api_id=px2 -param:nick=<!--sellernick->";
    public static final String CMD_INIT_COUPON="/home/chamago/kettle-4.2/kitchen.sh -rep=repo-taobao -job=marketing/taobao.promotion.coupons.get -level=Error -log=/home/chamago/kettle-logs/taobao.promotion.coupons.get.`date +\"%Y%m%d%H%M%S\"`.log -param:app_key=<!--appkey-> -param:db=pcrm2 -param:taobao_api_id=px2 -param:nick=<!--sellernick->";
    public static final String CMD_INIT_TRADE="/home/chamago/kettle-4.2/kitchen.sh -rep=repo-taobao -job=trade/taobao.trades.sold.get -level=Error -log=/home/chamago/kettle-logs/taobao.trades.sold.get.`date +\"%Y%m%d%H%M%S\"`.log -param:app_key=<!--appkey->  -param:db=pcrm2 -param:taobao_api_id=px2 -param:nick=<!--sellernick->";
    public static final String CMD_INIT_MEAL="/home/chamago/kettle-4.2/kitchen.sh -rep=repo-taobao -job=marketing/taobao.promotion.meal.get -level=Error -log=/home/chamago/kettle-logs/taobao.promotion.meal.get.`date +\"%Y%m%d%H%M%S\"`.log -param:app_key=<!--appkey-> -param:db=pcrm2 -param:taobao_api_id=pj2 -param:nick=<!--sellernick->";
    public static final String CMD_INIT_ACTIVITY="/home/chamago/kettle-4.2/kitchen.sh -rep=repo-taobao -job=marketing/taobao.promotion.activity.get -level=Error -log=/home/chamago/kettle-logs/taobao.promotion.activity.get.`date +\"%Y%m%d%H%M%S\"`.log -param:app_key=<!--appkey-> -param:db=pcrm2 -param:taobao_api_id=px2 -param:nick=<!--sellernick->";
    public static final String CMD_INIT_LIMITDICOUNT="/home/chamago/kettle-4.2/kitchen.sh -rep=repo-taobao -job=marketing/taobao.promotion.limitdiscount.get -level=Error -log=/home/chamago/kettle-logs/taobao.promotion.limitdiscount.get.`date +\"%Y%m%d%H%M%S\"`.log -param:app_key=<!--appkey-> -param:db=pcrm2 -param:taobao_api_id=px2 -param:nick=<!--sellernick->";
    */
    
    public static List<String> init_subscriber_script;
	public final static HashMap<String, String> tradeMap =new HashMap<String, String>();
	public final static HashMap<String,String> initScriptMap=new  HashMap<String,String>();
	
	static{
		
		 Properties props = new Properties();
	        try {
	         InputStream in = new BufferedInputStream (new FileInputStream(C.class.getResource("/").getPath()+"pcrm.properties"));
	         props.load(in);
	         
	         TOP_SERVER_URL=(String)props.get("TOP.SERVER.URL");
	         PCRM_APPKEY=(String)props.get("PCRM.APPKEY");
	         KETTLE_HOST=(String)props.get("KETTLE.HOST");
	         KETTLE_PORT=Integer.valueOf((String)props.get("KETTLE.PORT"));
	         KETTLE_USERNAME=(String)props.get("KETTLE.USERNAME");
	         KETTLE_PASSWORD=(String)props.get("KETTLE.PASSWORD");
	         KETTLE_DB=(String)props.get("KETTLE.DB");
	         
	         
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
		
		tradeMap.put("TRADE_NO_CREATE_PAY", "没有创建支付宝交易");
		tradeMap.put("WAIT_BUYER_PAY", "等待买家付款");
		tradeMap.put("WAIT_SELLER_SEND_GOODS", "等待卖家发货,即:买家已付款");
		tradeMap.put("WAIT_BUYER_CONFIRM_GOODS", "等待买家确认收货,即:卖家已发货");
		tradeMap.put("TRADE_BUYER_SIGNED", "买家已签收,货到付款专用");
		tradeMap.put("TRADE_FINISHED", "交易成功");
		tradeMap.put("TRADE_CLOSED", "交易关闭");
		tradeMap.put("TRADE_CLOSED_BY_TAOBAO", "交易被淘宝关闭");
		tradeMap.put("ALL_WAIT_PAY", "没有创建支付宝交易 & 等待买家付款");
		tradeMap.put("ALL_CLOSED", "交易关闭 & 交易被淘宝关闭");

		initScriptMap.put("taobao.security","/home/chamago/kettle/kitchen.sh -rep=repo-taobao -job=sellercenter/taobao.sellercenter.init.security -level=Detailed -log=/home/chamago/kettle-logs/taobao.sellercenter.init.security.`date +\"%Y%m%d\"`.log -param:app_key=<!--appkey-> -param:db=<!--db-> -param:taobao_api_id=px2 -param:nick=<!--sellernick->");
		initScriptMap.put("cmg.security","/home/chamago/kettle/kitchen.sh -rep=repo-taobao -job=sellercenter/cmg.sellercenter.init.security -level=Detailed -log=/home/chamago/kettle-logs/cmg.sellercenter.init.security.`date +\"%Y%m%d\"`.log -param:app_key=<!--appkey-> -param:db=<!--db-> -param:taobao_api_id=px2 -param:nick=<!--sellernick->");
		initScriptMap.put("item", "/home/chamago/kettle/kitchen.sh -rep=repo-taobao -job=item/taobao.items.get -level=Detailed -log=/home/chamago/kettle-logs/taobao.items.get.`date +\"%Y%m%d\"`.log -param:app_key=<!--appkey-> -param:db=<!--db-> -param:taobao_api_id=px2 -param:nick=<!--sellernick->");
	 	initScriptMap.put("meal", "/home/chamago/kettle/kitchen.sh -rep=repo-taobao -job=marketing/taobao.promotion.meal.get -level=Basic -log=/home/chamago/kettle-logs/taobao.promotion.meal.get.`date +\"%Y%m%d\"`.log -param:app_key=<!--appkey-> -param:db=<!--db-> -param:taobao_api_id=pj2 -param:nick=<!--sellernick->");
		initScriptMap.put("coupon", "/home/chamago/kettle/kitchen.sh -rep=repo-taobao -job=marketing/taobao.promotion.coupons.get -level=Basic -log=/home/chamago/kettle-logs/taobao.promotion.coupons.get.`date +\"%Y%m%d\"`.log -param:app_key=<!--appkey-> -param:db=<!--db-> -param:taobao_api_id=px2 -param:nick=<!--sellernick->");
	 	initScriptMap.put("limitdiscount", "/home/chamago/kettle/kitchen.sh -rep=repo-taobao -job=marketing/taobao.promotion.limitdiscount.get -level=Basic -log=/home/chamago/kettle-logs/taobao.promotion.limitdiscount.get.`date +\"%Y%m%d\"`.log -param:app_key=<!--appkey-> -param:db=<!--db-> -param:taobao_api_id=px2 -param:nick=<!--sellernick->");
	 	initScriptMap.put("activity", "/home/chamago/kettle/kitchen.sh -rep=repo-taobao -job=marketing/taobao.promotion.activity.get -level=Basic -log=/home/chamago/kettle-logs/taobao.promotion.activity.get.`date +\"%Y%m%d\"`.log -param:app_key=<!--appkey-> -param:db=<!--db-> -param:taobao_api_id=px2 -param:nick=<!--sellernick->");
	 	initScriptMap.put("trade", "/home/chamago/kettle/kitchen.sh -rep=repo-taobao -job=trade/taobao.trades.sold.get -level=Basic -log=/home/chamago/kettle-logs/taobao.trades.sold.get.`date +\"%Y%m%d\"`.log -param:app_key=<!--appkey-> -param:db=<!--db-> -param:taobao_api_id=px2 -param:nick=<!--sellernick-> -param:start_created=\"<!--sdate->\" -param:end_created=\"<!--edate->\"");
		
 	}
//coupon item
	public static Properties getInitScriptProperties(){
		 Properties props = new Properties();
	        try {
	         InputStream in = new BufferedInputStream (new FileInputStream(C.getINIT_SUBSCRIBER_SCRIPT_PATH()));
	         props.load(in);
	        } catch (Exception e) {
	         e.printStackTrace();
	         	return null;
	        }
	        return props;
	}
    public static String getINIT_SUBSCRIBER_SCRIPT_PATH(){
    	String class_path =Thread.currentThread().getContextClassLoader().getResource("").getPath();
    	String path=class_path+INIT_SUBSCRIBER_SCRIPT_FILENAME;
    	String os =  System.getProperties().getProperty("os.name");
    	if(os.toLowerCase().contains("windows"))
    		path=path.substring(1);
    	return path;
    }
    // trade 
    public final static String TRADE_SUCCESS_TIMES = "tradeSuccessTimes";
    public final static String TRADE_CLOSE_TIMES = "tradecloseTimes";
    public final static String TRADE_TIMES = "tradeTimes";
    public final static String TRADE_TOTAL = "tradeTotal";
    public final static String TRADE_SUCCESS_RATE = "tradeSuccessRate";
    public final static String TRADE_FAIL_RATE = "tradeFailRate";
    public final static String TRADE_ORDERS = "tradeOrders";
    public final static String TRADE_TRADE = "tradeTrade";
    public final static String INIT_SCHEDULE_STATUS_RUNNING="running";
    public final static String INIT_SCHEDULE_STATUS_SUCCESSED="successed";
    public final static String INIT_SCHEDULE_STATUS_INIT="init";

}



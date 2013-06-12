package com.chamago.pcrm.common.service.impl;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sy.hbm.Syresource;

import com.chamago.lucene.api.LuceneUtils;
import com.chamago.pcrm.common.mapper.AppMapper;
import com.chamago.pcrm.common.mapper.LkpMapper;
import com.chamago.pcrm.common.pojo.App;
import com.chamago.pcrm.common.pojo.InitShell;
import com.chamago.pcrm.common.pojo.LkpCity;
import com.chamago.pcrm.common.pojo.LkpErrorCode;
import com.chamago.pcrm.common.pojo.LkpProvince;
import com.chamago.pcrm.common.pojo.PromotionLkp;
import com.chamago.pcrm.common.pojo.Subscriber;
import com.chamago.pcrm.common.service.InitSellerService;
import com.chamago.pcrm.common.service.LkpService;
import com.chamago.pcrm.common.utils.C;


//@Scope(value=WebApplicationContext.SCOPE_SESSION,proxyMode=ScopedProxyMode.TARGET_CLASS)

@Service
public class LkpServiceImpl  implements LkpService{

	@Autowired
	private LkpMapper mapper;
	 
	@Autowired
	private InitSellerService initSellerService;
	@Autowired()
	private LuceneUtils luceneUtils;
	
	@Autowired
	private AppMapper appMapper;
	private Map<Integer,String> gender=new Hashtable<Integer,String>();
	private Map<Integer,String> province=new Hashtable<Integer,String>();
	private Map<Integer,String> city=new Hashtable<Integer,String>();
	private Map<String,String> itemstatus=new Hashtable<String,String>();
	private Map<String,Subscriber> subscribers=new Hashtable<String,Subscriber>();
	private List<Subscriber> subscribersList;
	private Map<String,App> apps=new Hashtable<String,App>();
	private Map<String,String> promotions=new Hashtable<String,String>();
	private Hashtable<String,Syresource> syResources=new Hashtable<String,Syresource>();

	private Map<String,String> errorcodes=new Hashtable<String,String>();
	private Map<Integer,InitShell> initshells=new Hashtable<Integer,InitShell>();
	private Map<Integer,String> closingTradeReason=new Hashtable<Integer,String>();
	
	
       @PostConstruct
	public void init(){
			initSyresources();
			 initGender();
			initProvince();
			initCity();
			initItemStatus();
			initSubscribers();
			initPromotions();
			initApps();
			initInitShell();
			initClosingTradeReason();
			initErrorCode();  
			initLucene();
			System.out.println("init the LKP completed");
	}
	public void  initSyresources(){
		List<Syresource> list=mapper.findAllSyresources();
		for(Syresource resource:list){
			syResources.put(resource.getUrl().replace("/",""), resource);
		}
		list.clear();
	}
	
	
	public void initGender(){
		gender.put(new Integer(0), "");
		gender.put(new Integer(1),"男");
		gender.put(new Integer(2),"女");
	}
	public void initErrorCode(){
		List<LkpErrorCode> list=mapper.findErrorCodes();
		for(LkpErrorCode pojo:list)
			errorcodes.put(String.valueOf(pojo.getId()),pojo.getDesc());
		list.clear();
	}
	public void initProvince(){
		 List<LkpProvince> list=mapper.findProvinces();
		 for(LkpProvince pojo:list)
			 province.put(pojo.getId(), pojo.getName());
		 list.clear();
	}
	public void initCity(){
		List<LkpCity> list=mapper.findCitys();
		 for(LkpCity pojo:list)
			 city.put(pojo.getId(), pojo.getName());
		 list.clear();
	}
	public void   initItemStatus(){
		itemstatus.put(C.ITEM_STAUTS_WANT_BUY, "想买");
		itemstatus.put( C.ITEM_STAUTS_TRADE_NO_CREATE_PAY,"没有创建支付宝交易");
		itemstatus.put( C.ITEM_STAUTS_WAIT_BUYER_PAY,"等待买家付款");
		itemstatus.put( C.ITEM_STAUTS_WAIT_SELLER_SEND_GOODS,"买家已付款");
		itemstatus.put( C.ITEM_STAUTS_WAIT_BUYER_CONFIRM_GOODS,"卖家已发货");
		itemstatus.put( C.ITEM_STAUTS_TRADE_BUYER_SIGNED,"买家已签收,货到付款");
		itemstatus.put( C.ITEM_STAUTS_TRADE_FINISHED,"交易成功");
		itemstatus.put( C.ITEM_STAUTS_TRADE_CLOSED,"交易自动关闭");
		itemstatus.put( C.ITEM_STAUTS_TRADE_CLOSED_BY_TAOBAO,"淘宝关闭交易");
	}
	public void initPromotions(){
		List<PromotionLkp> list=	mapper.findPromotions();
		for(PromotionLkp pojo:list)
			promotions.put(pojo.getId(),pojo.getName());
			
		list.clear();
	}
	public Subscriber findSubscriberByUserId(Long userid){
		for(Subscriber sub:subscribersList){
			if(userid.equals(sub.getUserid()))
				return sub;
		}
		return null;
	}
	public void initClosingTradeReason(){
		closingTradeReason.put(1, "我不想买了");
		closingTradeReason.put(2, "信息填写错误 重新拍");
		closingTradeReason.put(3, "卖家缺货");
		closingTradeReason.put(4, "同城见面交易");
		closingTradeReason.put(5, "其他原因");
	}
	
	public void initSubscribers(){
		subscribersList=	appMapper.findSubscriber(null,C.PCRM_APPKEY);
		for(Subscriber pojo:subscribersList){
			System.out.println(pojo.getAppKey()+"_"+pojo.getNick());
			subscribers.put(pojo.getAppKey()+"_"+pojo.getNick(), pojo);
		}
	}
	
	public void initLucene(){
		luceneUtils.initDirectory(C.LUCENE_DIRECTORY_KNOWLEDGE_BASE);
		luceneUtils.initDirectory(C.LUCENE_DIRECTORY_ITEM);
		luceneUtils.initDirectory(C.LUCENE_DIRECTORY_SKU);
	}
	
	public void initInitShell(){
		/*List<InitShell> list=	appMapper.findAllInitShell();
		for(InitShell pojo:list)
			initshells.put(pojo.getId(), pojo);
		list.clear();*/
	}
	public Boolean saveOrUpdateSession(Long userid,String nick,String appkey,String session){
		try {
			Subscriber obj=subscribers.get(appkey+"_"+nick);
			if(obj==null){
				obj=new Subscriber();
				obj.setAppKey(appkey);
				obj.setNick(nick);
				obj.setSessionKey(session);
				subscribers.put(obj.getAppKey()+"_"+obj.getNick(), obj);
				System.out.println("pcrm insert session:"+obj.getAppKey()+"_"+obj.getNick()+":"+obj.getSessionKey());
				
				Subscriber sub=new Subscriber();
					sub.setAppKey(appkey);
					sub.setCreated(new Date());
					sub.setNick(nick);
					sub.setUserid(userid);
					sub.setSessionKey(session);
				appMapper.insertSubscriber(sub);
				System.out.println("init previleges :"+nick);
				 initSellerService.initHistoryData(nick);
				System.out.println("init admin account:"+nick);
			}
			else{
				appMapper.updateSession(session, nick, appkey);
				obj.setSessionKey(session);
				System.out.println("pcrm update session:"+obj.getAppKey()+"_"+obj.getNick()+":"+obj.getSessionKey());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	
	public void initApps(){
		List<App> list=	appMapper.findAllApp();
	
		for(App pojo:list)
			apps.put(pojo.getAppKey(),pojo);
		
		list.clear();
	}
	public void updateInitSubscriber(String appkey,String sellernick,Integer isInit){
		appMapper.updateInitSubscriber(appkey, sellernick,isInit);
	}

	public LkpMapper getMapper() {
		return mapper;
	}

	public void setMapper(LkpMapper mapper) {
		this.mapper = mapper;
	}

	public Map<Integer, String> getGender() {
		return gender;
	}

	public void setGender(Map<Integer, String> gender) {
		this.gender = gender;
	}

	public Map<Integer, String> getProvince() {
		return province;
	}

	public void setProvince(Map<Integer, String> province) {
		this.province = province;
	}

	public Map<Integer, String> getCity() {
		return city;
	}

	public void setCity(Map<Integer, String> city) {
		this.city = city;
	}

	public Map<String, String> getItemstatus() {
		return itemstatus;
	}

	public void setItemstatus(Map<String, String> itemstatus) {
		this.itemstatus = itemstatus;
	}

	public Map<String, Subscriber> getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(Map<String, Subscriber> subscribers) {
		this.subscribers = subscribers;
	}

	public Map<String, App> getApps() {
		return apps;
	}

	public void setApps(Map<String, App> apps) {
		this.apps = apps;
	}

	public Map<String, String> getPromotions() {
		return promotions;
	}

	public void setPromotions(Map<String, String> promotions) {
		this.promotions = promotions;
	}

	public Map<String, String> getErrorcodes() {
		return errorcodes;
	}

	public Map<Integer, InitShell> getInitshells() {
		return initshells;
	}

	public void setInitshells(Map<Integer, InitShell> initshells) {
		this.initshells = initshells;
	}

	public Syresource findSyresource(String fullPath){
	/*	String[] cascades=fullPath.split("/");
		Syresource res=null;
		for(String tmp:cascades){
		res=this.syResources.get(tmp);
	
		return syResources.get(fullPath);*/
		return null;
	}

	
}

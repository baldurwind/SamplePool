	package com.chamago.pcrm.common.web;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chamago.pcrm.common.pojo.Subscriber;
import com.chamago.pcrm.common.service.InitSellerService;
import com.chamago.pcrm.common.service.LkpService;
import com.chamago.pcrm.common.utils.AopLogModule;
import com.chamago.pcrm.common.utils.C;
import com.chamago.pcrm.item.service.ItemLuceneService;
import com.chamago.pcrm.taobao.top.service.TopService;
import com.danga.MemCached.MemCachedClient;
import com.taobao.api.ApiException;
import com.taobao.api.response.UserGetResponse;


@Controller
@RequestMapping("/app")
public class AppController {

	@Autowired
	private LkpService lkp;
	@Autowired
	private MemCachedClient mc;
	@Autowired
	private ItemLuceneService itemLuceneService;
	@Autowired
	private TopService topService;
	
	@RequestMapping("/test")
	@AopLogModule(name=C.LOG_MODULE_PCRM,layer=C.LOG_LAYER_CONTROLLER)
	public String test(){
		return "/test";
	}
	//http://pcrm.chamago.com:8091/app/update/?appkey=12532585&seller=cntaobaotp_%e8%8c%b6%e9%a9%ac%e5%8f%a4%e9%81%93:mkt&session=6100e1570ff413cec582969162c0f2e837e70762de4cfb6488781025
	//http://127.0.0.1:8080/pcrm/app/update/?appkey=12532585&seller=cntaobaotp_%e8%8c%b6%e9%a9%ac%e5%8f%a4%e9%81%93:mkt&session=6100e1570ff413cec582969162c0f2e837e70762de4cfb6488781025
//http://127.0.0.1:8080/pcrm/app/update/?appkey=12285265&seller=cntaobaotp_stevemadden旗舰店:mkt&session=61018234e879f17e1f99552303a99aa14a3574f1ce3db92723741315
	
	@RequestMapping(value="/update")
	@AopLogModule(name=C.LOG_MODULE_PCRM,layer=C.LOG_LAYER_CONTROLLER)
	public @ResponseBody String saveOrUpdateSession( HttpServletRequest request, String appkey,  String seller,   String session){
		String result="false";
		System.out.println(appkey+":"+seller+session);
		seller=seller.replace("cntaobao", "").split(":")[0];
		 try {
			UserGetResponse resp=topService.getUserInfo(seller);
			if(resp.isSuccess()){
				Long userid=	resp.getUser().getUserId();
				result=String.valueOf(lkp.saveOrUpdateSession(userid,seller, appkey, session));
			}else
				System.out.println("error:"+resp.getSubMsg());
		} catch (ApiException e) {
			e.printStackTrace();
		}
		//request.setAttribute("result", result);
		 return  "result:"+result; 
	}
	
	
	@RequestMapping("/find")
	@AopLogModule(name=C.LOG_MODULE_PCRM,layer=C.LOG_LAYER_CONTROLLER)
	public @ResponseBody String findSubscribe(@RequestParam String appkey,@RequestParam String sellernick){
		Subscriber s=lkp.getSubscribers().get(appkey+"_"+sellernick);
		return s.toString();
	}
/*	public static void main(String ags[]){
			try {
			String str="/home/chamago/kettle-4.2/kitchen.sh -rep=repo-taobao -job=item/taobao.items.get -level=Error -log=/home/chamago/kettle-logs/taobao.items.get.`date +\"%Y%m%d%H%M%S\"`.log -param:app_key=12442189 -param:db=pcrm2 -param:taobao_api_id=px2 -param:nick=芳蕾玫瑰粉粉旗舰店; echo $? ";
			boolean res=	SSHClient.executeShell("root", "cmg@nasdaq", "cmg2.chamago.com", 22, str);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}*/
	
	/*//http://127.0.0.1:8080/pcrm/app/init?appkey=&sellernick=
	@RequestMapping("/init")
	public @ResponseBody String   init(HttpServletRequest request, final  @RequestParam   String appkey,@RequestParam final   String sellernick){
		System.out.println("pcrm: init...:"+appkey+":"+sellernick);
		
		List<Integer> shellIds=initShellScheduleMapper.findScriptByKey(appkey, sellernick);
		if(shellIds!=null)
			initShellScheduleMapper.LockInitShellSchedule(appkey, sellernick);
		
		final List<InitShell> initShells=new ArrayList<InitShell>();
		
		for(Integer i:shellIds){
			InitShell pojo= lkp.getInitshells().get(i);
			initShells.add(pojo);
		}
		new Thread(){
			public void run(){
				SimpleDateFormat sd1f=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				boolean init_error=false;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				Calendar current = Calendar.getInstance();
				current.set(Calendar.DATE, -7);// 设为当前月的1 号
				String acookie_day = sdf.format(current.getTime());
				for(InitShell shell:initShells){
					try {
						String script=shell.replaceScript(appkey, sellernick,acookie_day);
						
						boolean result=SSHClient.executeShell(C.KETTLE_USERNAME, C.KETTLE_PASSWORD, C.KETTLE_HOST, C.KETTLE_PORT, script);
						
						System.out.println("Shell ID "+shell.getId()+" result:"+result);
						if(result)//update status: successed;
							initShellScheduleMapper.updateStatusByKey(appkey, sellernick, shell.getId(), C.INIT_SCHEDULE_STATUS_SUCCESSED);
						else{
							init_error=true;
							initShellScheduleMapper.updateStatusByKey(appkey, sellernick, shell.getId(), C.INIT_SCHEDULE_STATUS_INIT);//update status:init
							break;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}	
				}
				if(!init_error)
					lkp.updateInitSubscriber(appkey, sellernick, 1);
				lkp.init();
				System.out.println("init job completed");
			}
		}.start();
		return "init shell is activated";
	}
	*/
	
	@RequestMapping("/flush/mc")
	@AopLogModule(name=C.LOG_MODULE_PCRM,layer=C.LOG_LAYER_CONTROLLER)
	public @ResponseBody boolean flushMc(){
		System.out.println("flush mc");
		return mc.flushAll();
	}
	
	@RequestMapping("/flush/lucene")
	@AopLogModule(name=C.LOG_MODULE_PCRM,layer=C.LOG_LAYER_CONTROLLER)
	public @ResponseBody boolean flushLucene(){
		System.out.println("flush lucene");
		  itemLuceneService.init();
		  return true;
	}
	
	
	 
	
}

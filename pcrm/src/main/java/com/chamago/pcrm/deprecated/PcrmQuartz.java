package com.chamago.pcrm.deprecated;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chamago.pcrm.common.mapper.AppMapper;
import com.chamago.pcrm.common.pojo.InitShell;
import com.chamago.pcrm.common.service.LkpService;
import com.mysql.jdbc.CommunicationsException;

//@Component("pcrmQuartz")
public class PcrmQuartz {
	
	/*	public void initSubscriberJob(){
		System.out.println("quartz:initSubscriberJob has started..."+MemoryDisplay.usedmemory());
		List<BatchInitShellSchedule> list=appMapper.batchFindInitShellSchedule(C.PCRM_APPKEY);
		
		int is_lock=0;
		if(list!=null||list.size()<1)
			is_lock=appMapper.LockInitShellSchedule(C.PCRM_APPKEY);
		System.out.println("Lock the shell:"+is_lock);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar current = Calendar.getInstance();
		current.set(Calendar.DATE, -7);// 设为当前月的1 号
		String acookie_day = sdf.format(current.getTime());
		for(BatchInitShellSchedule  pojo:list){
			 initSubscriberJob_INNER(pojo,acookie_day);
		}
		lkp.init();
		System.out.println("quartz:initSubscriberJob has completed..."+MemoryDisplay.usedmemory());
		System.gc();
	}
	
	private  void initSubscriberJob_INNER(BatchInitShellSchedule pojo,String acookie_day){
	boolean has_error=false;
		for(Integer shellId:pojo.getShellId()){
			try {
				InitShell  shell_script=	lkp.getInitshells().get(shellId);
				 String script=shell_script.replaceScript(pojo.getAppKey(), pojo.getSellernick(),acookie_day);
					initShellScheduleMapper.updateStatusByKey(pojo.getAppKey(), pojo.getSellernick(), shellId, C.INIT_SCHEDULE_STATUS_RUNNING);
				boolean result=SSHClient.executeShell(script);
				if(result)
					initShellScheduleMapper.updateStatusByKey(pojo.getAppKey(), pojo.getSellernick(),shellId, C.INIT_SCHEDULE_STATUS_SUCCESSED);
				else{
					initShellScheduleMapper.updateStatusByKey(pojo.getAppKey(), pojo.getSellernick(),shellId, C.INIT_SCHEDULE_STATUS_INIT);
					has_error=true;
					break;
				}
			System.out.println("Quartz Job InitSubscriber:"+pojo.getSellernick()+"Shell ID:"+shellId+",Result:"+result);
			}
			catch(CommunicationsException e){
				System.out.println("Quartz Job:CommunicationsException");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(has_error)
			lkp.updateInitSubscriber(pojo.getAppKey(), pojo.getSellernick(), 0);
		else
			lkp.updateInitSubscriber(pojo.getAppKey(), pojo.getSellernick(), 1);
	}*/
}

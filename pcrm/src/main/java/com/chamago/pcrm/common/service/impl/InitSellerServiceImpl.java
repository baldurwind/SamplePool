package com.chamago.pcrm.common.service.impl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Service;

import com.chamago.pcrm.common.utils.C;
import com.chamago.pcrm.common.utils.SSHClient;
import com.chamago.pcrm.common.service.InitSellerService;

@Service
public class InitSellerServiceImpl implements InitSellerService {


	
	public boolean syncPrivilege(String seller){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date d=new Date();
		String edate=sdf.format(d)+" 23:59:59";
		
		d=DateUtils.addDays(new Date(), -30);
		String sdate=sdf.format(d)+" 00:00:00";
		
		Iterator<String> it=C.initScriptMap.values().iterator();
		List<String>  single_cmds=new ArrayList<String>();
		while(it.hasNext()){
			String str=it.next();
			str=str.replace(C.CMD_REPLACE_VARIABLE_SELLERNICK, seller);
			str=str.replace(C.CMD_REPLACE_VARIABLE_APPKEY, C.PCRM_APPKEY);
			str=str.replace(C.CMD_REPLACE_VARIABLE_DB, C.KETTLE_DB);
			str=str.replace(C.CMD_REPLACE_VARIABLE_SDATE, sdate);
			str=str.replace(C.CMD_REPLACE_VARIABLE_EDATE, edate);
			if(str.contains("taobao.sellercenter.init.security")||str.contains("cmg.sellercenter.init.security")){
			 	single_cmds.add(str);
			}
		}
		final List<String> single_list=single_cmds;
		try {
			SSHClient.executeShell(C.KETTLE_USERNAME, C.KETTLE_PASSWORD, C.KETTLE_HOST, C.KETTLE_PORT, single_list);
			return true;
		}  catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public int initHistoryData(String seller) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date d=new Date();
		String edate=sdf.format(d)+" 23:59:59";
		
		d=DateUtils.addDays(new Date(), -30);
		String sdate=sdf.format(d)+" 00:00:00";
		
		Iterator<String> it=C.initScriptMap.values().iterator();
		List<String>  single_cmds=new ArrayList<String>();
		List<String>  thread_cmds=new ArrayList<String>();
		while(it.hasNext()){
			String str=it.next();
			str=str.replace(C.CMD_REPLACE_VARIABLE_SELLERNICK, seller);
			str=str.replace(C.CMD_REPLACE_VARIABLE_APPKEY, C.PCRM_APPKEY);
			str=str.replace(C.CMD_REPLACE_VARIABLE_DB, C.KETTLE_DB);
			str=str.replace(C.CMD_REPLACE_VARIABLE_SDATE, sdate);
			str=str.replace(C.CMD_REPLACE_VARIABLE_EDATE, edate);
			if(str.contains("taobao.sellercenter.init.security")||str.contains("cmg.sellercenter.init.security")){
			 	single_cmds.add(str);
			}
			else
				thread_cmds.add(str);
				
		}
		final List<String> single_list=single_cmds;
		final List<String> thread_list=thread_cmds;
		try {
			SSHClient.executeShell(C.KETTLE_USERNAME, C.KETTLE_PASSWORD, C.KETTLE_HOST, C.KETTLE_PORT, single_list);
 	 		new Thread(){public void run(){
				try {
				SSHClient.executeShell(C.KETTLE_USERNAME, C.KETTLE_PASSWORD, C.KETTLE_HOST, C.KETTLE_PORT, thread_list);
				} catch (Exception e) {
					e.printStackTrace();
				}	
	 		}}.start();
		}  catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	public static void main(String agsp[]){
		System.out.println("-------------------------");
		new InitSellerServiceImpl().initHistoryData("良无限清洁收纳");
	}

}

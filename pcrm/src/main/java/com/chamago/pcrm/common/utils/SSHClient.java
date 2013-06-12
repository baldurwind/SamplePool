package com.chamago.pcrm.common.utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import com.sshtools.j2ssh.SshClient;
import com.sshtools.j2ssh.authentication.AuthenticationProtocolState;
import com.sshtools.j2ssh.authentication.PasswordAuthenticationClient;
import com.sshtools.j2ssh.session.SessionChannelClient;
 
 public class SSHClient {
	 public static void main(String ags[]){
		 /*String str="/home/chamago/kettle-4.2/kitchen.sh -rep=repo-taobao -job=item/taobao.items.get -level=Error -log=/home/chamago/kettle-logs/taobao.items.get.`date +\"%Y%m%d%H%M%S\"`.log -param:app_key=12441913 -param:db=pcrm2 -param:taobao_api_id=px2 -param:nick=芳蕾玫瑰粉粉旗舰店; echo $? ";
		 try {
			 SSHClient.executeShell(C.KETTLE_USERNAME, C.KETTLE_PASSWORD, C.KETTLE_HOST, C.KETTLE_PORT, str);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		 
		 System.out.println(System.getProperty("file.encoding"));
	 }
/*	 private void executeCommand(String username,String pwd,String host,int port,String cmd )throws IOException{
		 PasswordAuthenticationClient  authentication = new PasswordAuthenticationClient();
		 	authentication.setUsername(username);
		 	authentication.setPassword(pwd);
		 
		  SshClient ssh=new SshClient();  
		  	ssh.connect(host, port);
		 
		  if (ssh.authenticate(authentication) != AuthenticationProtocolState.COMPLETE){
			  System.out.println("not start");
			   return ;
		  }
		  SessionChannelClient session = ssh.openSessionChannel();
		   System.out.println(session.executeCommand("mkdir /tmp/test"));
		  session.close();
	 }	*/
	 
	 public static boolean executeShell(String cmd)throws Exception{
		 List list=new ArrayList();
		 list.add(cmd);
		 return executeShell(C.KETTLE_USERNAME,C.KETTLE_PASSWORD,C.KETTLE_HOST,C.KETTLE_PORT,list);
	 }
	 public static boolean executeShell(String username,String pwd,String host,int port,String cmd)throws Exception{
		 List list=new ArrayList();
		 list.add(cmd);
		 return executeShell(username,pwd,host,port,list);
	 } 
	 public static boolean  executeShell(String username,String pwd,String host,int port,List<String> cmds)throws Exception{
		 SessionChannelClient session=null;
		try {
			PasswordAuthenticationClient  authentication = new PasswordAuthenticationClient();
			 	authentication.setUsername(username);
			 	authentication.setPassword(pwd);
			 	
			  SshClient ssh=new SshClient();  
			  
			  ssh.connect(host, port);
			  if (ssh.authenticate(authentication) != AuthenticationProtocolState.COMPLETE)
				   return false;
			  
			  session = ssh.openSessionChannel();
			     if (session.startShell()) {
	                    OutputStream writer = session.getOutputStream();
	                    
	                    for(String cmd:cmds){
	                    	System.out.println(cmd);
	                    	writer.write((cmd+" $?\n").getBytes("utf-8"));
	                    	
	                    }
	                    writer.flush();	                    
	                    writer.write("exit\n".getBytes());
	                    writer.flush();
	                    BufferedReader in = new BufferedReader(new InputStreamReader(session.getInputStream(),"UTF-8"));
	                    BufferedReader err = new BufferedReader(new InputStreamReader(session.getStderrInputStream()));
	                     String line;
	                     while ((line = in.readLine()) != null) {
	                         System.out.println(line);
	                     }
	                    System.out.println("------------------------");
	                     while ((line = err.readLine()) != null) {
	                         System.out.println(line);
	                     }
	                     if (session != null) {
	                         session.close();
	                     }
	                 }
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
        finally{
            if (session != null) 
                session.close();
        }
        return true;
	 }
	 /*	 private String cmd_item="/home/chamago/kettle-4.2/kitchen.sh -rep=repo-taobao -job=item/taobao.items.get -level=Error -log=/home/chamago/kettle-logs/taobao.items.get.`date +\"%Y%m%d%H%M%S\"`.log -param:app_key=12291050 -param:db=pcrm2 -param:taobao_api_id=px2 -param:nick=芳蕾玫瑰粉粉旗舰店";
	 	 private String cmd_coupon="/home/chamago/kettle-4.2/kitchen.sh -rep=repo-taobao -job=marketing/taobao.promotion.coupons.get -level=Error -log=/home/chamago/kettle-logs/taobao.promotion.coupons.get.`date +\"%Y%m%d%H%M%S\"`.log -param:app_key=12291050 -param:db=pcrm2 -param:taobao_api_id=px2 -param:nick=芳蕾玫瑰粉粉旗舰店";
	 	 private String cmd_trade="/home/chamago/kettle-4.2/kitchen.sh -rep=repo-taobao -job=trade/taobao.trades.sold.get -level=Error -log=/home/chamago/kettle-logs/taobao.trades.sold.get.`date +\"%Y%m%d%H%M%S\"`.log -param:app_key=12291050 -param:db=pcrm2 -param:taobao_api_id=px2 -param:nick=stevemadden旗舰店";
	 	 private String cmd_meal="/home/chamago/kettle-4.2/kitchen.sh -rep=repo-taobao -job=marketing/taobao.promotion.meal.get -level=Error -log=/home/chamago/kettle-logs/taobao.promotion.meal.get.`date +\"%Y%m%d%H%M%S\"`.log -param:app_key=12291050 -param:db=pcrm2 -param:taobao_api_id=pj2 -param:nick=naturesbounty旗舰店";
	 	 private String cmd_activity="/home/chamago/kitchen.sh -rep=repo-taobao -job=marketing/taobao.promotion.activity.get -level=Error -log=/home/chamago/kettle-logs/taobao.promotion.activity.get.`date +\"%Y%m%d%H%M%S\"`.log -param:app_key=12291050 -param:db=pcrm2 -param:taobao_api_id=px2 -param:nick=naturesbounty旗舰店";
	 	 private String cmd_limitdiscount="/home/chamago/kitchen.sh -rep=repo-taobao -job=marketing/taobao.promotion.limitdiscount.get -level=Error -log=/home/chamago/kettle-logs/taobao.promotion.limitdiscount.get.`date +\"%Y%m%d%H%M%S\"`.log -param:app_key=12291050 -param:db=pcrm2 -param:taobao_api_id=px2 -param:nick=naturesbounty旗舰店";
	 */
  }

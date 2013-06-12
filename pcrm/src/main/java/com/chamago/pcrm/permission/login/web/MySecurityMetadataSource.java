package com.chamago.pcrm.permission.login.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import com.chamago.pcrm.common.utils.Utils;
import com.danga.MemCached.MemCachedClient;

import sy.hbm.Syrole;
import sy.hbm.SyroleSyreource;
import sy.model.Role;
import sy.service.RoleServiceI;

public class MySecurityMetadataSource implements  FilterInvocationSecurityMetadataSource {
	
	// 由spring调用
	public MySecurityMetadataSource(RoleServiceI roleService) {
		this.roleService = roleService;
		loadResourceDefine();
	}
	private RoleServiceI roleService;

	public RoleServiceI getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleServiceI roleService) {
		this.roleService = roleService;
	}

	private static Hashtable<String, Hashtable<String,Collection<ConfigAttribute>>> resourceTable = null;

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	public static Hashtable<String, Hashtable<String, Collection<ConfigAttribute>>> getResourceTable() {
		return resourceTable;
	}

	public static void setResourceTable(
			Hashtable<String, Hashtable<String, Collection<ConfigAttribute>>> resourceTable) {
		MySecurityMetadataSource.resourceTable = resourceTable;
	}

	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return true;
	}
	

//	@Scheduled(fixedDelay=5*60*1000)
	private void loadResourceDefine() {  
			resourceTable = new Hashtable<String, Hashtable<String,Collection<ConfigAttribute>>>();
            List<Syrole> roles = this.roleService.findAll();
            for (Syrole  role : roles)     {
            	 Hashtable<String,Collection<ConfigAttribute>> table=new Hashtable<String,Collection<ConfigAttribute>>();
            	 resourceTable.put(role.getSellernick(), table);
            	addResourceMapByRole(role.getSellernick(),role.getId());
            }
            	
     System.out.println("loadResourceDefine complete");       		
	}

	// 返回所请求资源所需要的权限
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {

		String seller=(String)((FilterInvocation) object).getHttpRequest().getAttribute("seller");
		
		String sellernick=(String)((FilterInvocation) object).getHttpRequest().getParameter("sellernick");
		String requestUrl = ((FilterInvocation) object).getRequestUrl();
		System.out.println("requestUrl is " + requestUrl);
		if (resourceTable == null) 
			loadResourceDefine();
		if(requestUrl.contains("?")&&!requestUrl.contains("Controller")){
			requestUrl=requestUrl.substring(0,requestUrl.indexOf("?"));
     	}else if(requestUrl.contains("Controller")&&requestUrl.contains("&")){
     		requestUrl=requestUrl.substring(0,requestUrl.indexOf("&"));
     	}
		if(seller==null && sellernick==null)
			return null;
		else
			if(resourceTable.get(seller)!=null){
			return resourceTable.get(seller).get(requestUrl);
			}
	    return null;		
	}
	
	private void addResourceMapByRole(String seller,String roleId){
	    ConfigAttribute configAttribute = new SecurityConfig(roleId); 
	    
		 for(SyroleSyreource roleres : this.roleService.findSyroleSyreourceByRoleId(roleId)){
         	String url = roleres.getSyresource().getUrl();  
         //	System.out.println(url);
         //	content+=url+"\n";
         	if(url.contains("?")){
         		url=url.substring(0,url.indexOf("?"));
         	}
         	  if (resourceTable.containsKey(url)) {  
         			Hashtable<String, Collection<ConfigAttribute>> table = new Hashtable<String, Collection<ConfigAttribute>>();
    				Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
    				configAttributes.add(new SecurityConfig(roleId));
    				table.put(url, configAttributes);
    				resourceTable.put(seller, table);
               } 
         	  else if(resourceTable.get(seller).containsKey(url)){
   				resourceTable.get(seller).get(url).add(new SecurityConfig(roleId));
   			}else{
   				Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
   				configAttributes.add(new SecurityConfig(roleId));
   				resourceTable.get(seller).put(url,configAttributes);
   			}
		 }
		/* try {
		 	Utils.writeAllText(path, content);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}

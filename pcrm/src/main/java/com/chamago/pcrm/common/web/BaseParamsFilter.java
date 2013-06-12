package com.chamago.pcrm.common.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class BaseParamsFilter implements Filter {

	private String configFileName = "/init.properties";
	//缺省的静态资源主机
	private String staticResourceHost = "http://static.chamago.cn";
	
	@Override
	public void destroy() {
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chan) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		
	    String path = request.getRequestURI();
	    
		if(path.contains("/resource/")||path.contains("/js-pushlet-net.html")){
			chan.doFilter(req, res);
			return;
		}
		
		req.setAttribute("static_resource_host", staticResourceHost);
		chan.doFilter(req, res);
		
	}
	

	@Override
	public void init(FilterConfig req) throws ServletException {
		InputStream is = BaseParamsFilter.class.getResourceAsStream(configFileName);
		try {
			
			Properties config = new Properties();
			config.load(is);
			String configStaticHost = config.getProperty("static.resource.host");
			staticResourceHost = configStaticHost==null?this.staticResourceHost:configStaticHost;
			
		}catch (IOException ioe) {
			ioe.printStackTrace();
		}finally{
			try {
				if(is != null){
                    is.close();
                    is = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	
	
	
	
}

/**
 * 
 */
package com.chamago.pcrm.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author gavin.peng
 * 获取Spring上下文
 */
@Component
public class PCRMSpringApplicationContext implements ApplicationContextAware {

	public static ApplicationContext act=null;
	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		act = applicationContext;
	}
	
	public static ApplicationContext getApplicationContext(){
		return act;
	}

}

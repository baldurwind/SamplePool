package com.chamago.pcrm.common.utils;

import java.io.IOException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.danga.MemCached.MemCachedClient;



@Component
@Aspect
public class MCAroundAdvice   {
	
	@Autowired
	private MemCachedClient mc;
	@Autowired
	private MemCachedSwitch mcSwitch;
	
	
	@Around(value="@annotation(cache)",argNames="point,cache")
	public Object cache(ProceedingJoinPoint  point,MemCached cache)throws Throwable{
		
		Object result=null;
		
		 if(mc==null){
			System.out.println("mc is null");
			result=point.proceed();
			return result;
		 }
		String key=buildKey(point.getSignature().getName(),point.getArgs());
		if(!mcSwitch.ON){
			mc.delete(key);
			result =point.proceed();
			return result;
		}
			try {
				result = mc.get(key);
			} catch (Exception e) {
				result =point.proceed();
				mc.add(key, result,Utils.nextDate(Integer.parseInt(cache.expiredmins())));
				return result;
			}
		if(result==null){
			result = point.proceed();
		    try {
				mc.add(key, result,Utils.nextDate(Integer.parseInt(cache.expiredmins())));
			} catch (Exception e) {
				System.out.println(key);
			}
		}
		return result;
	}
	
	private    String buildKey(String methodName,Object[] arguments){
		StringBuilder sb = new StringBuilder(methodName);
		for(Object obj:arguments){
			sb.append("_"+obj);
		}
		return sb.toString();
	}
	
	
	
	 /* @Pointcut("execution(* com.chamacrm.mapper.impl.*MapperImpl.find*(..))")
		public void pointCut(){}
		
	 	@Around(value="pointCut()", argNames = "joinPoint")
		public Object round(ProceedingJoinPoint  point) throws Throwable{
	 		Object result=null;
	 		 if(mc==null){
	 			 System.out.println("mc is null");
	 			result=point.proceed();
	 			return result;
	 		 }
	 		String key=buildKey(point.getSignature().getName(),point.getArgs());
	 		if(!mcSwitch.ON){
	 			mc.delete(key);
	 			result =point.proceed();
	 			System.out.println("CLOSE_CACHE");
	 		}
	 		else{
	 			result = mc.get(key);
				if(result==null){
					result = point.proceed();	
					System.out.println("query By DB:"+result);
				}
				else
					System.out.println("query By cache:"+result);
	 		}
	 		return result;
		} 
		*/
		
		/*public Object flush(ProceedingJoinPoint  point,Flush flush)throws Throwable{
			Object result=null;
			 if(mc==null){
				 System.out.println("mc is null");
				result=point.proceed();
				return result;
			 }
			String key=buildKey(point.getSignature().getName(),point.getArgs());
			if(!mcSwitch.ON){
				mc.delete(key);
				result =point.proceed();
				System.out.println("CLOSE_CACHE");
			}
			else{
				result = mc.get(key);
				if(result==null){
					result = point.proceed();	
					System.out.println("query By DB:"+result);
				}
				else
					System.out.println("query By cache:"+result);
			}
			return result;
		}*/
}

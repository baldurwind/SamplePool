package com.chamago.pcrm.common.utils;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAroundAdvice {

	
	 private Logger tradeLogger=Logger.getLogger(C.LOG_MODULE_TRADE);
	 private Logger itemLogger=Logger.getLogger(C.LOG_MODULE_ITEM);
	 private Logger behaviorLogger=Logger.getLogger(C.LOG_MODULE_BEHAVIOR);
	 private Logger marketingLogger=Logger.getLogger(C.LOG_MODULE_MARKETING);
	 private Logger worktableLogger=Logger.getLogger(C.LOG_MODULE_WORKTABLE);
	 private Logger pcrmLogger=Logger.getLogger(C.LOG_MODULE_PCRM);
	 private Logger customerServiceLogger = Logger.getLogger(C.LOG_MODULE_CUSTOMERSERVICE);
	 private Logger leadsMemoLogger = Logger.getLogger(C.LOG_MODULE_LEADSMEMO);
	 private Logger reminderLogger = Logger.getLogger(C.LOG_MODULE_REMINER);
	@Around(value="@annotation(module)",argNames="point,module")
	public Object module(ProceedingJoinPoint  point,AopLogModule module)throws Throwable{
		
		Object result=null;
		
		Long sTime=System.currentTimeMillis();
		result=	point.proceed();
		Long eTime=System.currentTimeMillis();
		
		String content= module.layer()+","+module.name()+","+point.getSignature().getDeclaringType().getSimpleName()+","+ point.getSignature().getName()+","+iteratorParams(point.getArgs())+","+(eTime-sTime);
		//System.out.println(content);
		if(module.name().equals(C.LOG_MODULE_ITEM))
			itemLogger.warn(content);
		else if(module.name().equals(C.LOG_MODULE_TRADE))
			tradeLogger.warn(content);
		else if(module.name().equals(C.LOG_MODULE_BEHAVIOR))
			behaviorLogger.warn(content);
		else if(module.name().equals(C.LOG_MODULE_MARKETING))
			marketingLogger.warn(content);
		else if(module.name().equals(C.LOG_MODULE_WORKTABLE))
			worktableLogger.warn(content);
		else if(module.name().equals(C.LOG_MODULE_PCRM))
			pcrmLogger.warn(content);
		else if (module.name().equals(C.LOG_MODULE_CUSTOMERSERVICE))
			customerServiceLogger.warn(content);
		else if (module.name().equals(C.LOG_MODULE_LEADSMEMO))
			leadsMemoLogger.warn(content);
		else if (module.name().equals(C.LOG_MODULE_REMINER))
			reminderLogger.warn(content);
		return result;
	}
	
	private String iteratorParams(Object[] args){
		String str="";
		for(Object obj:args){
			str+=obj+"-";
		}
		return str;
	}
}

package org.rency.pushlet.aspect;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PushletAspect {

	private static final Logger logger = LoggerFactory.getLogger(PushletAspect.class);
	
	private String clazz;
	
	private String method;
	
	public void beforeAdvice(JoinPoint ctx) throws Exception {
		clazz = ctx.getTarget().getClass().getName();
		method = ctx.getSignature().getName();
		String queryString = "";
		Object param = new Object();
		if(ctx.getArgs().length > 0){
			param = ctx.getArgs()[0];
			queryString = param.getClass().getSimpleName()+"="+param.toString();
		}
		logger.debug("-------------->>>>>  execute "+clazz+"."+method+"("+queryString+") start.");
	}
	
	public void afterAdvice() {
		logger.debug("--------------<<<<<  execute "+clazz+"."+method +"() finish.");
	}
	
	public void afterThrowAdvice(Throwable ex) {
		logger.error("xxxxxxxxxxxxxx  execute "+clazz+"."+method+"() error. exception:"+ex);
	}
	
}
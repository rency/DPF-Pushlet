package org.rency.pushlet.dwr;

import org.directwebremoting.impl.DefaultScriptSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @desc 自定义ScriptSession管理器
 * @author T-rency
 * @date 2014年12月5日 上午11:05:22
 */
public class DwrSessionManager extends DefaultScriptSessionManager{
	
	private static final Logger logger = LoggerFactory.getLogger(DwrSessionManager.class);

	public DwrSessionManager(){
		this.addScriptSessionListener(new DwrSessionListener());
		logger.info("bind DwrSessionListener...");
	}
}

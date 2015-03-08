package org.rency.pushlet.dwr;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.event.ScriptSessionEvent;
import org.directwebremoting.event.ScriptSessionListener;
import org.rency.utils.exceptions.CoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @desc 自定义ScriptSession监听器
 * @author T-rency
 * @date 2014年12月5日 上午11:05:43
 */
public class DwrSessionListener implements ScriptSessionListener{
	
	private static final Logger logger = LoggerFactory.getLogger(DwrSessionListener.class);
	private static final Map<String, ScriptSession> sessionMap = new HashMap<String, ScriptSession>();

	@Override
	public void sessionCreated(ScriptSessionEvent event) {
		HttpSession session = WebContextFactory.get().getSession();
		ScriptSession scriptSession = event.getSession();
		sessionMap.put(session.getId(), scriptSession);
		logger.debug("created session context id:"+session.getId()+"="+scriptSession);		
	}

	@Override
	public void sessionDestroyed(ScriptSessionEvent event) {
		HttpSession session = WebContextFactory.get().getSession();
		ScriptSession scriptSession = sessionMap.remove(session.getId());
		logger.debug("destroyed session context id:"+session.getId()+"="+scriptSession);
	}
	
	/**
	 * @desc 获取所有session
	 * @date 2014年12月5日 上午11:02:55
	 * @return
	 * @throws CoreException
	 */
	public static Map<String, ScriptSession> getScriptSessions() throws CoreException{
		return sessionMap;
	}
	
	/**
	 * @desc 判断当前有无客户端连接
	 * @date 2014年12月9日 下午5:28:51
	 * @return
	 */
	public static boolean isEmpty(){
		return sessionMap.isEmpty();
	}
}

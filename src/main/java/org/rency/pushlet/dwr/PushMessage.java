package org.rency.pushlet.dwr;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.directwebremoting.Browser;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ScriptSessionFilter;
import org.rency.pushlet.beans.MessageQueue;
import org.rency.pushlet.service.MessageQueueService;
import org.rency.utils.exceptions.CoreException;
import org.rency.utils.tools.PropUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @desc 推送消息
 * @author T-rency
 * @date 2014年12月5日 上午11:13:26
 */
public class PushMessage{

	private static final Logger logger = LoggerFactory.getLogger(PushMessage.class);
	
	@Autowired
	@Qualifier("messageQueueService")
	private MessageQueueService messageQueueService;
	
	private volatile boolean isRun = true;

	public void initMethod(){
		logger.info("init dwr push message");
		Thread thread = new Thread(new Runnable(){
			@Override
			public void run() {
				while(isRun){
					try {
						Thread.sleep(PropUtils.getInteger("dwr.push.delay") * 1000);
						push();
					}catch(InterruptedException e) {
					}
				}
			}});
		thread.start();
	}
	
	public void destroyMethod(){
		isRun = false;
	}
	
	public void push(){
		if(DwrSessionListener.isEmpty()){
			return;//没有客户端连接则消息不弹出队列
		}
		/************ 设置需要发送的消息 **************/
		final MessageQueue messageQueue = getMessageQueue();
		
		/************ 执行推送 **************/
		Browser.withAllSessionsFiltered(new ScriptSessionFilter() {			
			@Override
			public boolean match(ScriptSession scriptSession) {
				return true;
			}
		}, new Runnable() {
			private ScriptBuffer scriptBuffer = new ScriptBuffer();
			@Override
			public void run() {
				try {
					String messageContent = formatMessage(messageQueue);
					if(StringUtils.isBlank(messageContent)){
						return;
					}
					scriptBuffer.appendCall("pushMessage", messageContent);
					Map<String, ScriptSession> sessions = DwrSessionListener.getScriptSessions();
					for(String sessionId: sessions.keySet()){
						if("system".equals(messageQueue.getSender().trim()) || sessionId.equals(messageQueue.getReceiver())){
							ScriptSession session = sessions.get(sessionId);
							session.addScript(scriptBuffer);
						}else{
							messageQueueService.deleteById(messageQueue.getId());
							logger.info("unkonwn message receiver."+messageQueue+", and delete.");
						}
					}
				} catch (Exception e) {
					logger.error("push message["+messageQueue+"] error, and rollback messageQueue status.",e);
					e.printStackTrace();
					rollBackStatus(messageQueue);
				}
			}
		});	
	}
	
	private MessageQueue getMessageQueue(){
		try{
			MessageQueue messageQueue = messageQueueService.queryQueueTop();
			if(messageQueue != null){
				if(messageQueueService.updateStatus(messageQueue.getId(), true)){
					return messageQueue;
				}else{
					logger.warn("messageQueue["+messageQueue+"] poll, but status modify failed.");
				}
			}
			logger.debug("push message["+messageQueue+"]");
			return messageQueue;
		}catch(Exception e){
			logger.error("get top message queue error.",e);
			return null;
		}
	}
	
	/**
	 * @desc 格式化推送消息
	 * @date 2014年12月5日 上午11:17:11
	 * @param messageQueue
	 * @return
	 * @throws CoreException
	 */
	private String formatMessage(MessageQueue messageQueue) throws Exception{
		if(messageQueue == null){
			return null;
		}
		StringBuilder message = new StringBuilder();
		message.append("<strong>服务器发来消息啦:</strong></br>[");
		if(StringUtils.isNotBlank(messageQueue.getSender())){
			if("system".equals(messageQueue.getSender().trim())){
				message.append("系统运行时:");
			}else{
				message.append(messageQueue.getSender()+"运行时:");
			}			
		}
		if(StringUtils.isNotBlank(messageQueue.getMessage())){
			message.append(messageQueue.getMessage());
		}
		message.append("]");
		return message.toString();
	}
	
	/**
	 * @desc 恢复消息队列状态
	 * @date 2014年12月5日 下午5:00:59
	 * @param messageQueue
	 */
	private void rollBackStatus(MessageQueue messageQueue){
		try {
			messageQueueService.updateStatus(messageQueue.getId(), false);
		} catch (CoreException e) {
			logger.error("goBackStatus message queue["+messageQueue+"] status error.",e);
			e.printStackTrace();
		}
	}

}
package org.rency.pushlet.service;

import java.util.List;

import org.rency.pushlet.beans.MessageQueue;
import org.rency.utils.exceptions.CoreException;

public interface MessageQueueService {

	public List<MessageQueue> list() throws CoreException;
	
	public MessageQueue queryQueueTop() throws CoreException;
	
	public boolean add(MessageQueue messageQueue);
	
	public boolean updateStatus(Integer id, boolean isSend) throws CoreException;
	
	public boolean deleteById(Integer id) throws CoreException;
	
	public boolean deleteAll() throws CoreException;
	
	/**
	 * @desc 删除超过规定日期的消息
	 * @date 2014年12月11日 下午2:26:48
	 * @param day
	 * @return
	 * @throws CoreException
	 */
	public int deleteExceedMessage(int day)throws CoreException;
	
}
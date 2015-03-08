package org.rency.pushlet.dao;

import java.util.List;

import org.rency.pushlet.beans.MessageQueue;
import org.rency.utils.exceptions.CoreException;

public interface MessageQueueDao {

	public List<MessageQueue> list() throws CoreException;
	
	public MessageQueue getQueueTop() throws CoreException;
	
	public boolean save(MessageQueue messageQueue) throws CoreException;
	
	public boolean updateStatus(Integer id,boolean isSend) throws CoreException;
	
	public boolean deleteAll() throws CoreException;
	
	public boolean deleteById(Integer id) throws CoreException;
	
	/**
	 * @desc 删除超过规定日期的消息
	 * @date 2014年12月11日 下午2:24:53
	 * @return
	 * @throws CoreException
	 */
	public int deleteExceedMessage(int day)throws CoreException;
	
}
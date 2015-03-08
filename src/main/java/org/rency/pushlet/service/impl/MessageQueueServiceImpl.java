package org.rency.pushlet.service.impl;

import java.util.List;

import org.rency.pushlet.beans.MessageQueue;
import org.rency.pushlet.dao.MessageQueueDao;
import org.rency.pushlet.service.MessageQueueService;
import org.rency.utils.exceptions.CoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("messageQueueService")
public class MessageQueueServiceImpl implements MessageQueueService{

	private static final Logger logger = LoggerFactory.getLogger(MessageQueueServiceImpl.class);
	
	@Autowired
	@Qualifier("messageQueueDao")
	private MessageQueueDao messageQueueDao;

	@Override
	public List<MessageQueue> list() throws CoreException {
		return messageQueueDao.list();
	}

	@Override
	public MessageQueue queryQueueTop() throws CoreException {
		return messageQueueDao.getQueueTop();
	}

	@Override
	public boolean add(MessageQueue messageQueue){
		try {
			return messageQueueDao.save(messageQueue);
		} catch (CoreException e) {
			logger.error("messageQueue["+messageQueue+"] add error.",e);
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateStatus(Integer id, boolean isSend) throws CoreException{
		return messageQueueDao.updateStatus(id, isSend);
	}
	
	@Override
	public boolean deleteById(Integer id) throws CoreException {
		return messageQueueDao.deleteById(id);
	}
	
	public boolean deleteAll() throws CoreException{
		return messageQueueDao.deleteAll();
	}

	@Override
	public int deleteExceedMessage(int day) throws CoreException {
		return messageQueueDao.deleteExceedMessage(day);
	}
	
}
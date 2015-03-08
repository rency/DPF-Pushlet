package org.rency.pushlet.dao.impl;

import java.util.List;

import org.rency.pushlet.beans.MessageQueue;
import org.rency.pushlet.dao.MessageQueueDao;
import org.rency.utils.dao.BasicDao;
import org.rency.utils.exceptions.CoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("messageQueueDao")
public class MessageQueueDaoImpl implements MessageQueueDao {

	private static final Logger logger = LoggerFactory.getLogger(MessageQueueDaoImpl.class);
	
	@Autowired
	@Qualifier("hibernateDao")
	private BasicDao basicDao;
	
	private final String ENTITY = MessageQueue.class.getName();
	private final String ID = "id";
	private final String SEND = "send";
	
	@Override
	public List<MessageQueue> list() throws CoreException {
		try{
			return basicDao.loadAll(MessageQueue.class);
		}catch(CoreException e){
			logger.error("load all error.",e);
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public MessageQueue getQueueTop() throws CoreException {
		String queryString = "from "+ENTITY+" as model where model."+SEND+"=? order by model.createTime asc";
		try{
			List<MessageQueue> list = basicDao.getByPage(queryString, 1, 1, false);
			if(list != null && list.size() > 0){
				return list.get(0);
			}else{
				return null;
			}
		}catch(CoreException e){
			logger.error("exec queryString["+queryString+"] error.",e);
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public boolean save(MessageQueue messageQueue) throws CoreException {
		try{
			return basicDao.save(messageQueue);
		}catch(CoreException e){
			logger.error("save error.",e);
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public boolean updateStatus(Integer id, boolean isSend) throws CoreException {
		try{
			return basicDao.updateByProperty(ENTITY, ID, id, SEND, isSend);
		}catch(CoreException e){
			logger.error("exec updateStatus error.",e);
			e.printStackTrace();
			throw e;
		}
	}

	public boolean deleteAll() throws CoreException{
		try{
			return basicDao.deleteAll(MessageQueue.class);
		}catch(CoreException e){
			logger.error("delete all error.",e);
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public boolean deleteById(Integer id) throws CoreException {
		try{
			return basicDao.deleteByProperty(ENTITY, ID, id);
		}catch(CoreException e){
			logger.error("exec deleteById error.",e);
			e.printStackTrace();
			throw e;
		}
	}
	

	@Override
	public int deleteExceedMessage(int day) throws CoreException {
		String deleteHQL = "delete from "+ENTITY+" as model where datediff(now(),model.createTime) >= ?";
		try{
			return basicDao.bulkUpdate(deleteHQL, new Object[]{day});
		}catch(CoreException e){
			logger.error("exec deleteExceedMessage error.",e);
			e.printStackTrace();
			throw e;
		}
	}

}

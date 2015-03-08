package org.rency.pushlet.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.rency.utils.tools.ConvertToCNUtils;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="t_message_queue")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class MessageQueue implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8331937013699793639L;
	private int id;
	private String sender;
	private String receiver;
	private String message;
	private boolean isSend;
	private Date createTime;
	
	public MessageQueue(){}
	
	public MessageQueue(int sender,String receiver,String message){
		this.sender = ConvertToCNUtils.getServiceKind(sender);
		this.receiver = receiver;
		this.message = message;
		this.isSend = false;
		this.createTime = new Date();
	}
	
	@Id
	@GeneratedValue
 	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public boolean isSend() {
		return isSend;
	}
	public void setSend(boolean isSend) {
		this.isSend = isSend;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss:SSS")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String toString(){
		return "{id:"+id+", sender:"+sender+", receiver:"+receiver+", message:"+message+", isSend:"+isSend+", createTime:"+createTime+"}";
	}
}

package com.common.bean;

import java.io.Serializable;

public class TextMessage implements Serializable{

	private String messageType;
	

	private String sender;	
	private String receiver;
	public void setSender(String sender) {
		this.sender = sender;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	private String message;
	
	public String getSender() {
		return sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public String getMessage() {
		return message;
	}
    
	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public TextMessage(String sender, String receiver, String message,String Type) {
		this.sender = sender;
		this.receiver = receiver;
		this.message = message;
		this.messageType=Type;
	} 
	
}

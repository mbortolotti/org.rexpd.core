package org.rexpd.core.observer;

public class Message {

	Object messageSender = null;
	Object messageType = null;
	Object messageContent = null;
	
	public Message(Object sender, Object type, Object content) {
		messageType = type;
		messageContent = content;
	}

	public Object getSender() {
		return messageSender;
	}
	
	public Object getType() {
		return messageType;
	}
	
	public Object getContent() {
		return messageContent;
	}

}

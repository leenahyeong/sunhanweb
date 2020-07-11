package com.chat;

public class ChatDTO {
	int chatID;
	String fromID;
	String toID;
	String chatContent;
	String chatTime;
	String chatprofile;
	String tochatprofile;
	public String getTochatprofile() {
		return tochatprofile;
	}
	public void setTochatprofile(String tochatprofile) {
		this.tochatprofile = tochatprofile;
	}
	public String getChatprofile() {
		return chatprofile;
	}
	public void setChatprofile(String chatprofile) {
		this.chatprofile = chatprofile;
	}
	public int getChatID() {
		return chatID;
	}
	public void setChatID(int chatID) {
		this.chatID = chatID;
	}
	public String getFromID() {
		return fromID;
	}
	public void setFromID(String fromID) {
		this.fromID = fromID;
	}
	public String getToID() {
		return toID;
	}
	public void setToID(String toID) {
		this.toID = toID;
	}
	public String getChatContent() {
		return chatContent;
	}
	public void setChatContent(String chatContent) {
		this.chatContent = chatContent;
	}
	public String getChatTime() {
		return chatTime;
	}
	public void setChatTime(String chatTime) {
		this.chatTime = chatTime;
	}
	@Override
	public String toString() {
		return "ChatDTO [chatID=" + chatID + ", fromID=" + fromID + ", toID=" + toID + ", chatContent=" + chatContent
				+ ", chatTime=" + chatTime + "]";
	}
	
	
}

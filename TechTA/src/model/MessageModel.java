package model;

import javax.management.loading.PrivateClassLoader;

public class MessageModel {
	private String mid;
	private String content;
	private String clid;
	private String account;
	private String time;
	/**
	 * @param mid
	 * @param content
	 * @param clid
	 * @param account
	 * @param time
	 */
	public MessageModel(String mid, String content, String clid,
			String account, String time) {
		super();
		this.mid = mid;
		this.content = content;
		this.clid = clid;
		this.account = account;
		this.time = time;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getClid() {
		return clid;
	}
	public void setClid(String clid) {
		this.clid = clid;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	
	


}

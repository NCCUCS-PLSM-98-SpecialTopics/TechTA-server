package model;

import javax.management.loading.PrivateClassLoader;

public class MessageModel {
	private String mid;
	private String content;
	private String clid;
	private String account;
	private String time;
	private String bonus;
	private String role;
	
	
	
	public MessageModel(String mid, String content, String clid,
			String account, String time, String bonus, String role) {
		super();
		this.mid = mid;
		this.content = content;
		this.clid = clid;
		this.account = account;
		this.time = time;
		this.bonus = bonus;
		this.role = role;
	}
	/*
	public MessageModel(String mid, String content, String clid,
			String account, String time, String bonus) {
		super();
		this.mid = mid;
		this.content = content;
		this.clid = clid;
		this.account = account;
		this.time = time;
		this.bonus = bonus;
	}*/
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
	public String getBonus() {
		return bonus;
	}
	public void setBonus(String bonus) {
		this.bonus = bonus;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
	


}

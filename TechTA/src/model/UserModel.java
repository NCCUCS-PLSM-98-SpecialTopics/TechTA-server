package model;

public  class   UserModel {
	private String account ="";
	private String password ="";
	private String name ="";
	private String email ="";
	private String department ="";
	private String role ="";
	private String chatid ="";
	
	
	public UserModel(String account, String password, String name,
			String email, String department, String role, String chatid) {
		super();
		this.account = account;
		this.password = password;
		this.name = name;
		this.email = email;
		this.department = department;
		this.role = role;
		this.chatid = chatid;
	}
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getChatid() {
		return chatid;
	}
	public void setChatid(String chatid) {
		this.chatid = chatid;
	}
	
	
	
	
	
}

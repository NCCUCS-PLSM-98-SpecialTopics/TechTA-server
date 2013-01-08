package model;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class ClassModel {
	private String clid ="";
	private String name ="";
	private String week ="";
	private String active ="";
	private String roomid = "";
	private String parentCourseId = null;
	private List<QuizModel> quizzes = null;
	private List<MessageModel> messages = null;
	private String file = "";
	

	public ClassModel(String clid, String name, String week, String active,
			String roomid, String parentCourseId, List<QuizModel> quizzes,
			List<MessageModel> messages, String file) {
		super();
		this.clid = clid;
		this.name = name;
		this.week = week;
		this.active = active;
		this.roomid = roomid;
		this.parentCourseId = parentCourseId;
		this.quizzes = quizzes;
		this.messages = messages;
		this.file = file;
	}

	public String getClid() {
		return clid;
	}

	public void setClid(String clid) {
		this.clid = clid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getRoomid() {
		return roomid;
	}

	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}

	public String getParentCourseId() {
		return parentCourseId;
	}

	public void setParentCourseId(String parentCourseId) {
		this.parentCourseId = parentCourseId;
	}

	public List<QuizModel> getQuizzes() {
		return quizzes;
	}

	public void setQuizzes(List<QuizModel> quizzes) {
		this.quizzes = quizzes;
	}

	public List<MessageModel> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageModel> messages) {
		this.messages = messages;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
	 
	
	
	
}

package model;

import org.json.JSONException;
import org.json.JSONObject;

public class ClassModel {
	private String clid ="";
	private String name ="";
	private String week ="";
	private String active ="";
	private CourseModel courseModel = null;
	
	/**
	 * @param clid
	 * @param name
	 * @param week
	 * @param active
	 * @param courseModel
	 */
	public ClassModel(String clid, String name, String week, String active,
			CourseModel courseModel) {
		super();
		this.clid = clid;
		this.name = name;
		this.week = week;
		this.active = active;
		this.courseModel = courseModel;
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
	public CourseModel getCourseModel() {
		return courseModel;
	}
	public void setCourseModel(CourseModel courseModel) {
		this.courseModel = courseModel;
	}
	
	
	
	
}

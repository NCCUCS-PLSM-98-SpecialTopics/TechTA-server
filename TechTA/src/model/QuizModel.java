package model;

import java.util.Map;

public class QuizModel {

	private String  qid;
	private String  question;
	private String  correctAnswer;
	private String  choice;
	private String  active;
	private String  clid;
	private Map<String, String> student; //<account,answer>
	
	public QuizModel(String qid, String question, String correctAnswer,
			String choice, String active, String clid,
			Map<String, String> student) {
		super();
		this.qid = qid;
		this.question = question;
		this.correctAnswer = correctAnswer;
		this.choice = choice;
		this.active = active;
		this.clid = clid;
		this.student = student;
	}

	public String getQid() {
		return qid;
	}

	public void setQid(String qid) {
		this.qid = qid;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public String getChoice() {
		return choice;
	}

	public void setChoice(String choice) {
		this.choice = choice;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getClid() {
		return clid;
	}

	public void setClid(String clid) {
		this.clid = clid;
	}

	public Map<String, String> getStudent() {
		return student;
	}

	public void setStudent(Map<String, String> student) {
		this.student = student;
	}
	
	
	
	
	
}

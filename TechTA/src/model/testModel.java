package model;

public class testModel {

	/**
	 * @param name
	 * @param id
	 */
	public testModel(String name, String id) {
		super();
		this.name = name;
		this.id = id;
	}

	String name = "";
	String id = "";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}

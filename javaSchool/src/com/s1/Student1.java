package com.s1;

public class Student1 {
	private String name;

	public Student1(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String show()
	{
		return"名:"+name;
	}

}

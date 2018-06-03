package com.entity;

import javax.validation.constraints.Pattern;

public class User {
	private Integer id;
	private String name;
	private Integer age;
	private String hobby;
	private String gender;
	
	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Pattern(regexp = "1[3|4|5|7|8][0-9]\\d{8}")
	private String mobile;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", age=" + age + ", hobby=" + hobby + ", gender=" + gender
				+ ", mobile=" + mobile + "]";
	}

}

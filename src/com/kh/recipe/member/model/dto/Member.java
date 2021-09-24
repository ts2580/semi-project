package com.kh.recipe.member.model.dto;

import java.sql.Date;

//DTO(DATA TRANSFER OBJECT)
//데이터전송객체
//데이터베이스로부터 얻어 온 데이터를 service(비지니스로직)으로 보내기 위해 사용하는 객체
//비지니스 로직을 포함하고 있지 않은, 순수하게 데이터 전송만을 위해 사용되는 객체
//getter/setter, equals, hashCode, toString 메서드만을 가진다.

//*** 참고
//DTO와 비슷한 친구들
//DOMAIN OBJECT, VALUE OBJECT(VO), ENTITY, BEAN

//DTO의 조건(JAVA BEAN 규약)
//1. 모든 필드변수는 PRIVATE 처리
//2. 모든 필드변수는 GETTER/SETTER 메서드를 가져야 한다.
//3. 반드시 기본생성자가 존재할 것(매개변수가 있는 생성자가 있더라도, 기본생성자가 있어야한다.)

//오라클 - 자바 타입 매핑
//CHAR, VARCHAR2 -> String
//DATE -> java.util.Date, java.sql.Date
//Number -> int, double
public class Member {
	
	private String Id;
	private String password;
	private String email;
	private String phone;
	private String name;
	private Date birth;
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	@Override
	public String toString() {
		return "Member [Id=" + Id + ", password=" + password + ", email=" + email + ", phone=" + phone + ", name="
				+ name + ", birth=" + birth + "]";
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}









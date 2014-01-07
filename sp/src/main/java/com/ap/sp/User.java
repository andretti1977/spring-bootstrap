package com.ap.sp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="user")
public class User {

	
	@Id
	@GeneratedValue
	private Integer id;

	public String username;

	public String password;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public User() {
		this(null, null);
	}
	
}

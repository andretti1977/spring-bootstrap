package com.ap.sp;

public class SecResponse {

	public String status;
	public Roles role;
	
	public enum Roles {USER, ADMIN};
	
	public SecResponse() {
		this("OK", Roles.USER);
	}
	
	public SecResponse(String status, Roles role) {
		this.status = status;
		this.role = role;
	}
	
}

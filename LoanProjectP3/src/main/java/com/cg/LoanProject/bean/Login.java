package com.cg.LoanProject.bean;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//to take log in data from user
public class Login {
	
	@NotNull(message="Account Number is required..!!")
	@Size(min=11,max=11,message="Acccount Number should be of 11 digits..!!")
	private String accountNum;
	
	@NotNull(message="Password is required..!!")
	@Size(min=6,max=16,message="Password Should Contain 6 to 16 Characters..!!")
	private String password;
	
	//constructors
	public Login() {

	}
	
	public Login(String accountNum, String password) {
		this.accountNum = accountNum;
		this.password = password;
	}

	//getters and setters
	public String getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
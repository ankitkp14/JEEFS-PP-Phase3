package com.cg.LoanProject.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="user_account")
public class Account implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull(message="Account Number is Required..!!")
	@Size(min=11,max=11,message="Acccount Number should be of 11 digits..!!")
	@Pattern(regexp="([0-9]+)",message="Should contain Numbers only..!!")
	private String accountNum;
	
	@NotNull(message="Password is Required..!!")
	@Size(min=6,max=16,message="Password Should Contain 6 to 16 Characters..!!")
	@Pattern(regexp="((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,16})",message="Should contain at least one Letter, Number and Special Character..!!")
	private String password;
	
	@NotNull(message="Name is Required..!!")
	@Pattern(regexp="([A-Za-z_ ]+)",message="Should contain Alphabets only..!!")
	private String name;
	
	@NotNull(message="Gender is Required..!!")
	private String gender;
	
	private double loanAmount;
	private double loanBal;
	private double emi;
	private int duration;
	private String loanType;
	private double rate;
	
	@OneToMany(mappedBy="account",cascade=CascadeType.ALL)
	private List<Transactions> transaction=new ArrayList<>();
	
	//constructors
	public Account() {
		
	}
	
	public Account(String accountNum, String password, String name, String gender) {
		this.accountNum = accountNum;
		this.password = password;
		this.name = name;
		this.gender = gender;
	}
	
	public Account(String accountNum,String password,String name,String gender, double loanAmount, double loanBal, double emi,
			int duration, String loanType, double rate, List<Transactions> transaction) {
		this.accountNum = accountNum;
		this.password = password;
		this.name = name;
		this.gender = gender;
		this.loanAmount = loanAmount;
		this.loanBal = loanBal;
		this.emi = emi;
		this.duration = duration;
		this.loanType = loanType;
		this.rate = rate;
		this.transaction = transaction;
	}

	//getters and setters
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public double getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public double getLoanBal() {
		return loanBal;
	}
	public void setLoanBal(double loanBal) {
		this.loanBal = loanBal;
	}
	public double getEmi() {
		return emi;
	}
	public void setEmi(double emi) {
		this.emi = emi;
	}
	public String getLoanType() {
		return loanType;
	}
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}

	//to add record to transaction table
	@JsonIgnore
	public List<Transactions> getTransaction() {
		return transaction;
	}
	public void setTransaction(List<Transactions> transaction) {
		this.transaction = transaction;
	}
	public void addTransaction(Transactions transaction)
	{
		transaction.setAccount(this);
		this.getTransaction().add(transaction);
	}
}
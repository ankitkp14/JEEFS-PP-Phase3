package com.cg.LoanProject.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="transactions")

public class Transactions implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int transactionId;
	private String statement;
	
	//relation with user table
	@ManyToOne
	@JoinColumn(name="accountNum")
	private Account account;
	
	public Transactions() {
		
	}
	
	public Transactions(int transactionId, String statement, Account account) {
		super();
		this.transactionId = transactionId;
		this.statement = statement;
		this.account = account;
	}

	//getters and setters
	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}
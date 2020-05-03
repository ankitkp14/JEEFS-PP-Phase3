package com.cg.LoanProject.service;

import java.util.List;

import com.cg.LoanProject.bean.Account;
import com.cg.LoanProject.bean.Transactions;

//service layer interface
public interface ILoanService {
	
	boolean createAccount(Account account);
	Account logIn(String accountNum,String password);
	
	Account showBal(String accountNum);
	Account applyLoan(String accountNum,double loanAmount,int time,String loanType);
	Account payEmi(String accountNum);
	Account foreClose(String accountNum);
	List<Transactions> printTransactions(String accountNum);
}
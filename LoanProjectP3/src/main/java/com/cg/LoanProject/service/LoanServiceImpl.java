package com.cg.LoanProject.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.LoanProject.bean.Account;
import com.cg.LoanProject.bean.Transactions;
import com.cg.LoanProject.dao.ILoanDAO;
import com.cg.LoanProject.dao.LoanDAOImpl;
import com.cg.LoanProject.exception.AccountAlreadyExistsException;
import com.cg.LoanProject.exception.BadCredentialsException;
import com.cg.LoanProject.exception.LoanHandlingExceptions;

@Service
@Transactional
public class LoanServiceImpl implements ILoanService {
	
	private Logger logger = Logger.getRootLogger();
	
	//to store account details when fetched
	Account account=new Account();
	
	//dao layer access
	
	@Autowired
	ILoanDAO dao;
	
	public LoanServiceImpl() {
		dao=new LoanDAOImpl();
	}
	
	//creating a new account
	@Override
	public boolean createAccount(Account account) throws AccountAlreadyExistsException{
		boolean result=dao.createAccount(account);
		if(result==false) {
			logger.error("Error..! Account Already Exists..");
			throw new AccountAlreadyExistsException("Account Already Exists..Please Login!!");
		}
		return true;
	}

	// login to existing account
	@Override
	public Account logIn(String accountNum, String password) throws BadCredentialsException{
		account=dao.logIn(accountNum, password);
		if(account==null) {
			logger.error("Invalid Credentials Supplied..");
			throw new BadCredentialsException("Invalid Credentials!!..Try Again..!!");
		}
		return account;
	}	
	
	//apply for loan
	@Override
	public Account applyLoan(String accountNum,double loanAmount,int time,String loanType) throws LoanHandlingExceptions{
		account=dao.applyLoan(accountNum, loanAmount, time, loanType);
		if(account==null) {
			logger.error("Loan Already Applied..Tried Another Loan..!");
			throw new LoanHandlingExceptions("Loan Already Exists..Can't apply for Another Loan!!");
		}
		return account;
	}

	//show user balances
	@Override
	public Account showBal(String accountNum) {
		account=dao.showBal(accountNum);
		if(account==null) {
			logger.error("No balance Available..");
			throw new LoanHandlingExceptions("Sorry..!!No Balance Available..!!");
		}
		return account;
	}

	//pay the loan emi
	@Override
	public Account payEmi(String accountNum) throws LoanHandlingExceptions{
		account=dao.payEmi(accountNum);
		if(account==null) {
			logger.error("No Loan Existing..Emi can't be paid...!!");
			throw new LoanHandlingExceptions("Can't Pay!!..No Loan Exists..!!");
		}
		return account;
	}

	//foreclose of loan
	@Override
	public Account foreClose(String accountNum) throws LoanHandlingExceptions{
		account=dao.foreClose(accountNum);
		if(account==null) {
			logger.error("No Loan Existing..Loan can't be foreclosed...!!");
			throw new LoanHandlingExceptions("Can't Pay!!..No Loan Exists..!!");
		}
		return account;
	}

	//list user statement of transactions
	@Override
	public List<Transactions> printTransactions(String accountNum) {
		List<Transactions> transaction=dao.printTransactions(accountNum);
		if(transaction==null) {
			logger.error("No transactions available to show..");
			throw new LoanHandlingExceptions("Sorry..!! No Transactions Available..!!");
		}
		return transaction;
	}
}
package com.cg.LoanProject.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cg.LoanProject.bean.Account;
import com.cg.LoanProject.bean.Transactions;

@Repository
public class LoanDAOImpl implements ILoanDAO {
	
	//creating entity objects 
	Account account=new Account();
	Transactions transaction=new Transactions();
	
	//for date and time
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
	DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("hh:mm");
	
	//entity manager object to persist the data in database
	@Autowired
	private EntityManager entityManager;
	
	//default constructor
	public LoanDAOImpl() {
		
	}
	
	//creating a new account
	@Override
	public boolean createAccount(Account account){
		if(entityManager.find(Account.class, account.getAccountNum())!=null) {
			return false;
		}
		entityManager.persist(account);
		return true;
	}
	
	// login to existing account
	@Override
	public Account logIn(String accountNum, String password) {
		account=entityManager.find(Account.class, accountNum);
		if(account!=null && account.getAccountNum().equals(accountNum) && account.getPassword().equals(password)){
			return account;
		}
		return null;
	}
	
	// function to apply for loan
	@Override
	public Account applyLoan(String accountNum,double loanAmount,int time,String loanType) {
		
		//finding account to apply the loan
		account=entityManager.find(Account.class, accountNum);
		
		if(account==null) {
			return null;
		}
		
		//if loan already exists
		if(account.getLoanBal()!=0) {
			return null;
		}
		
		//setting rate according to loan type
		double rate=0;
		if(loanType.equals("Home Loan")) {
			rate=10.5;
		}
		else if(loanType.equals("Car Loan")) {
			rate=9.7;
		}
		else if(loanType.equals("Education Loan")) {
			rate=10;
		}
		
		//calculating emi for the loan
		double emi=Math.round((loanAmount * Math.pow((1+rate/100),(double)time/12))/time);
		
		//setting values in account and transaction
		account.setLoanBal(emi*time);
		account.setLoanAmount(loanAmount);
		account.setDuration(time);
		account.setEmi(emi);
		account.setLoanType(loanType);
		account.setRate(rate);
		transaction.setStatement(formatter.format(LocalDate.now())+", "+formatter1.format(LocalTime.now())+" * Loan applied for the amount :"+loanAmount+" for "+time+" months.");
		account.addTransaction(transaction);
		
		//persisting data in table
		entityManager.merge(account);
		return account;
	}

	// show balance of user
	@Override
	public Account showBal(String accountNum) {
		account=entityManager.find(Account.class, accountNum);
		
		if(account==null) {
			return null;
		}
		
		if(account.getLoanAmount()==0 || account.getLoanBal()==0) {
			return null;
		}
		return account;
	}

	@Override
	public Account payEmi(String accountNum) {
		//finding account to pay the emi
		account=entityManager.find(Account.class, accountNum);
		
		if(account==null) {
			return null;
		}
		
		//if no loan exists for the account
		if(account.getLoanAmount()==0 || account.getLoanBal()==0) {
			return null;
		}
		
		//calculating new balance after paying the emi
		double newBal=account.getLoanBal()-account.getEmi();
		
		//updating the new balance
		account.setLoanBal(newBal);
		transaction.setStatement(formatter.format(LocalDate.now())+", "+formatter1.format(LocalTime.now())+" * Paid emi amount :"+account.getEmi()+". Updated loan balance is Rs. "+newBal+".");
		account.addTransaction(transaction);
		
		//persisting in database
		entityManager.merge(account);
		return account;
	}

	//foreclosure of loan
	@Override
	public Account foreClose(String accountNum) {
		//finding account to foreclose the loan
		account=entityManager.find(Account.class, accountNum);
		
		if(account==null) {
			return null;
		}
		
		//if no loan exists for the account
		if(account.getLoanAmount()==0 || account.getLoanBal()==0) {
			return null;
		}
		
		//setting all values to zero after foreclosing the loan
		account.setLoanBal(0);
		account.setEmi(0);
		account.setDuration(0);
		account.setLoanAmount(0);
		account.setLoanType("None");
		account.setRate(0);
		transaction.setStatement(formatter.format(LocalDate.now())+", "+formatter1.format(LocalTime.now())+" * Loan foreclosure initiated..Successfully paid whole amount.");
		account.addTransaction(transaction);
		
		//persisting in database
		entityManager.merge(account);
		return account;
	}

	//printing transactions of user
	@Override
	public List<Transactions> printTransactions(String accountNum) {
		
		if(entityManager.find(Account.class, accountNum)==null) {
			return null;
		}
		
		//creating query to fetch transactions from database
		String qStr = "SELECT transaction FROM Transactions transaction WHERE account_num LIKE :paccount order by transaction.transactionId";
		TypedQuery<Transactions> query = entityManager.createQuery(qStr, Transactions.class);
		
		//setting account number to fetch for the particular account number
		query.setParameter("paccount", "%"+accountNum+"%");
		
		//if no transactions available
		if(query.getResultList().isEmpty()) {
			return null;
		}
		
		return query.getResultList();
	}
}
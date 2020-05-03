package com.cg.LoanProject.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.LoanProject.bean.Account;
import com.cg.LoanProject.bean.Login;
import com.cg.LoanProject.bean.Transactions;
import com.cg.LoanProject.service.ILoanService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value="/loan")
public class Client {
	
	private Logger logger = Logger.getRootLogger();
	
	//object to store values when fetched
	Account account;
	
	//service layer access
	@Autowired
	private ILoanService service;
	
	//create a new account
	@PostMapping("/add")
	public ResponseEntity<Object> createAccount(@Valid @RequestBody Account account){
		service.createAccount(account);
		logger.info("Account Creation Successfull..");
		return new ResponseEntity<>("Account Created Successfully..!!", HttpStatus.OK);
	}
	
	//login to existing account
	@PostMapping("/login")
	public ResponseEntity<Account> logIn(@Valid @RequestBody Login login){
		account=service.logIn(login.getAccountNum(), login.getPassword());
		logger.info("Login Successfull..");
		return new ResponseEntity<Account>(account, HttpStatus.OK);
	}
	
	//getting account to display at the home page
	@GetMapping("/home")
	public ResponseEntity<Account> home(){
		return new ResponseEntity<Account>(account, HttpStatus.OK);
	}
	
	//apply loan
	@PutMapping("/apply")
	public ResponseEntity<Object> applyLoan(@RequestBody Account account1){
		if(account1.getLoanAmount()<20000 || account1.getLoanAmount()>10000000 || account1.getLoanType()==null || account1.getDuration()==0) {
			logger.error("Error while applying for loan..");
			return new ResponseEntity<>("Error Occurred..!!", HttpStatus.BAD_REQUEST);
		}
		account=service.applyLoan(account1.getAccountNum(),account1.getLoanAmount(),account1.getDuration(),account1.getLoanType());
		logger.info("Applied for loan..");
		return new ResponseEntity<>("Loan Successfully Applied..!!", HttpStatus.OK);
	}
	
	//show user details
	@GetMapping("/show/{accountNum}")
	public ResponseEntity<Account> showBal(@PathVariable("accountNum")String accountNum){
		account=service.showBal(accountNum);
		logger.info("Checked account balance..");
		return new ResponseEntity<Account>(account, HttpStatus.OK);
	}
	
	//foreclose the loan
	@GetMapping("/foreclose/{accountNum}")
	public ResponseEntity<Account> foreClose(@PathVariable("accountNum")String accountNum){
		account=service.foreClose(accountNum);
		logger.info("Foreclosed loan..");
		return new ResponseEntity<Account>(account, HttpStatus.OK);
	}
	
	//pay the emi
	@GetMapping("/pay/{accountNum}")
	public ResponseEntity<Account> payEmi(@PathVariable("accountNum")String accountNum){
		account=service.payEmi(accountNum);
		logger.info("Paid emi successfully..");
		return new ResponseEntity<Account>(account, HttpStatus.OK);
	}
	
	//print transactions in the account
	@GetMapping("/print/{accountNum}")
	public ResponseEntity<List<Transactions>> printStatement(@PathVariable("accountNum")String accountNum){
		List<Transactions> transaction=service.printTransactions(accountNum);
		logger.info("Viewed transactions..");
		return new ResponseEntity<List<Transactions>>(transaction, HttpStatus.OK);
	}
}
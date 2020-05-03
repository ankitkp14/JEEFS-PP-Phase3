package com.cg.LoanProject.testService;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.cg.LoanProject.bean.Account;
import com.cg.LoanProject.bean.Transactions;
import com.cg.LoanProject.exception.LoanHandlingExceptions;
import com.cg.LoanProject.service.LoanServiceImpl;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class ApplyLoanTest {

	//implementing logger
	private static Logger logger=Logger.getRootLogger();;
	
	//testing service layer
	@Autowired
	private LoanServiceImpl service;
	
	@BeforeAll
	static void beforeAllMethod() {
		System.out.println("Testing process begins ...");
	}
	
	@BeforeEach
	void beforeEachMethod() {
		logger.info("Test Case Starts..");
		System.out.println("Test Case Starts..");
	}

	@AfterEach
	void afterEachMethod() {
		logger.info("Test Case Ends..");
		System.out.println("Test Case Ends..");
	}
	
	@Test
	@DisplayName("Loan Application Successful")
	public void loanApplicationTest() {
		
		//creating object to test
		Account account=null;
		Transactions transact=new Transactions(0,"Loan Applied",account);
		List<Transactions> transaction=new ArrayList<>();
		transaction.add(transact);
		account=new Account("79391001005","","","",100000,0,0,6,"Car Loan",0,transaction);
		String response = null;
		
		//testing
		try {
			if(service.applyLoan(account.getAccountNum(),account.getLoanAmount(),account.getDuration(),account.getLoanType())!=null){
				response="Success";
			}
		}catch (Exception ex){
			response = ex.getMessage();
		}
		String expectedResponse = "Success";
		assertEquals(response,expectedResponse);
	}
	
	@Test
	@DisplayName("Loan Already Applied..")
	public void loanAlreadyAppliedTest() {
		
		Account account=new Account("79391001004","","","",100000,0,0,6,"Car Loan",0,null);
		//exception
		assertThrows(LoanHandlingExceptions.class, () -> {
			service.applyLoan(account.getAccountNum(),account.getLoanAmount(),account.getDuration(),account.getLoanType());
		});
	}
}
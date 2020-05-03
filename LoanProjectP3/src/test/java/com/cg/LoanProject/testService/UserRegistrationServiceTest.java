package com.cg.LoanProject.testService;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import com.cg.LoanProject.exception.AccountAlreadyExistsException;
import com.cg.LoanProject.service.LoanServiceImpl;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class UserRegistrationServiceTest {
	
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
	@DisplayName("Account Creation Successful")
	public void registrationTest() {
		
		//creating object for testing
		Account account=new Account("79391001020","Kush@12","Kush","Male",0,0,0,0,null,0,null);
		String response = null;
		
		try {
			if(service.createAccount(account)) {
				response="Success";
			}
		}catch (Exception ex){
			response = ex.getMessage();
		}
		String expectedResponse = "Success";
		assertEquals(response,expectedResponse);
	}
	
	@Test
	@DisplayName("Account Already Registered..")
	public void accountAlreadyRegisteredTest() {
		//creating object for testing
		
		Account account=new Account("79391001001","Kush@12","Ankit","Male",0,0,0,0,null,0,null);
		assertThrows(AccountAlreadyExistsException.class, () -> {
			//exception
			service.createAccount(account);
		});
	}
}
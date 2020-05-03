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

import com.cg.LoanProject.bean.Login;
import com.cg.LoanProject.exception.BadCredentialsException;
import com.cg.LoanProject.service.LoanServiceImpl;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class UserLoginTest {
	
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
	@DisplayName("User Login Successful")
	public void loginTest() {
		
		//creating object to test login
		Login login=new Login("79391001001","Kush@12");
		String response=null;
		
		try {
			if(service.logIn(login.getAccountNum(),login.getPassword())!=null) {
				response="Success";
			}
		}catch(Exception ex) {
			response=ex.getMessage();
		}
		
		assertEquals(response,"Success");
	}
	
	@Test
	@DisplayName("User Login Denied")
	public void loginTestInvalidAccount() {
		
		Login login=new Login("","Kush@12");
		
		//exception
		assertThrows(BadCredentialsException.class, () -> {
			service.logIn(login.getAccountNum(),login.getPassword());
		});
	}
	
	@Test
	@DisplayName("User Login Denied")
	public void loginTestInvalidPassword() {
		
		Login login=new Login("79391001001","");
		
		assertThrows(BadCredentialsException.class, () -> {
			service.logIn(login.getAccountNum(),login.getPassword());
		});
	}
	
	@Test
	@DisplayName("User Login Denied")
	public void loginTestInvalidAccountAndPassword() {
		
		Login login=new Login("","");
		
		assertThrows(BadCredentialsException.class, () -> {
			service.logIn(login.getAccountNum(),login.getPassword());
		});
	}
}
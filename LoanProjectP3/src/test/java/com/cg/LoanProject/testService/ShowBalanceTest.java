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

import com.cg.LoanProject.exception.LoanHandlingExceptions;
import com.cg.LoanProject.service.LoanServiceImpl;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class ShowBalanceTest {
	
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
	@DisplayName("Show Balance Successful")
	public void showBalanceTest() {
		String response = null;
		
		try {
			if(service.showBal("79391001004")!=null){
				response="Success";
			}
		}catch (Exception ex){
			response = ex.getMessage();
		}
		String expectedResponse = "Success";
		assertEquals(response,expectedResponse);
	}
	
	@Test
	@DisplayName("No Loan available..")
	public void showBalanceNotAvailableTest() {
		assertThrows(LoanHandlingExceptions.class, () -> {
			service.showBal("79391001005");
		});
	}
}
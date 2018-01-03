/*
 * Copyright (c) 2017 Mindstix Inc.
 */
package com.mindstix.cb.stepdefinition.ecom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mindstix.cb.page.ContactUs;
import com.mindstix.cb.page.HomePage;
import com.mindstix.cb.page.LogIn;
import com.mindstix.cb.page.MyAccount;
import com.mindstix.cb.page.Search;
import com.mindstix.cb.stepdefinition.BaseScenarioContext;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

/**
 * Contains objects of all the pages and some global variables.
 * 
 * @author Mindstix
 */
public class EcomScenarioContext extends BaseScenarioContext {

	private final static Logger LOGGER = LoggerFactory.getLogger(EcomScenarioContext.class);

	public HomePage homePage;
	public ContactUs contactUs;
	public LogIn logIn;
	public MyAccount myAccount;
	public Search search;

	public EcomScenarioContext() {
	}

	/**
	 * Method which will Execute before Each Scenario
	 */
	@Before("@ecom")
	public void startMethod(Scenario scenario) {
		LOGGER.info("Initiating execution of Test Scenario {}", scenario.getName());
		initializationMethod("env.baseURL");
	}

	/**
	 * Method which will Execute after Each Scenario After Execution of Each
	 * Scenario if Scenario is failed then method will take Screenshot and put it
	 * into HTML report
	 * 
	 * @param scenario
	 *            -Cucumber Scenario which is currently executing
	 */
	@After("@ecom")
	public void endMethod(Scenario scenario) {
		teardownMethod(scenario);
	}
}

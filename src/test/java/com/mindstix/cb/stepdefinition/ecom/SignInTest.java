/*
 * Copyright (c) 2017 Mindstix Inc.
 */
package com.mindstix.cb.stepdefinition.ecom;

import org.junit.Assert;

import com.mindstix.cb.utils.Constants;
import com.mindstix.cb.utils.RedisUtility;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author Mindstix
 */
public class SignInTest {

	private EcomScenarioContext context;

	public SignInTest(EcomScenarioContext context) {
		this.context = context;
	}

	@When("^inputs email address \"([^\"]*)\" and password \"([^\"]*)\"$")
	public void inputs_email_address_and_password(String emailAddress, String password) {
		context.logIn.enterUserData(emailAddress, password);
		context.myAccount = context.logIn.clickOnSignInButton();
	}
	
	@When("^inputs email address and password from redis set$")
	public void inputs_email_address_and_password() {
		context.userName = RedisUtility.acquireUser(Constants.USERS_KEY);
		context.logIn.enterUserData(context.userName);
		context.myAccount = context.logIn.clickOnSignInButton();
	}

	@Then("^User either signs in or registers with email address \"([^\"]*)\" and password \"([^\"]*)\"$")
	public void user_either_signs_in_or_registers_with(String emailAddress, String password) {
		context.logIn.enterUserData(emailAddress, password);
		context.myAccount = context.logIn.clickOnSignInButton();
		if (context.logIn.verifyUnsuccessfulLogin()) {
			context.myAccount = context.logIn.registerUser(emailAddress, password);
			boolean isOnPage = context.myAccount.isOnPage("automationpractice.com/index.php?controller=my-account");
			Assert.assertTrue(isOnPage);
		} else {
			Assert.assertTrue(true);
		}
		context.userName = emailAddress;
	}
}

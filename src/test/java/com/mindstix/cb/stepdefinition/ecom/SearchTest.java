/*
 * Copyright (c) 2017 Mindstix Inc.
 */
package com.mindstix.cb.stepdefinition.ecom;

import org.junit.Assert;

import com.mindstix.cb.page.HomePage;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author Mindstix
 */
public class SearchTest {

	private EcomScenarioContext context;

	public SearchTest(EcomScenarioContext context) {
		this.context = context;
	}

	@Given("^User is browsing 'Automation Practice' website$")
	public void user_is_browsing_Automation_Practice_website() {
		context.homePage = new HomePage(context.getWebDriver());
	}

	@When("^User clicks \"([^\"]*)\" on \"([^\"]*)\"$")
	public void user_on_on_on_ecom(String clickableField, String pageName) {
		if (pageName.equals("Homepage")) {
			switch (clickableField) {
			case "Contact us":
				context.contactUs = context.homePage.clickOnContactUsTab();
				break;
			case "Sign In":
				context.logIn = context.homePage.clickOnSignInTab();
				break;
			default:
				throw new RuntimeException("Invalid option " + clickableField);
			}
		}
	}

	@Then("^User lands on \"([^\"]*)\" Page$")
	public void user_lands_on(String arg1) throws Throwable {
		boolean isOnPage = false;
		switch (arg1) {
		case "automationpractice.com/index.php?controller=contact":
			isOnPage = context.contactUs.isOnPage(arg1);
			break;
		case "automationpractice.com/index.php?controller=authentication&back=my-account":
			isOnPage = context.logIn.isOnPage(arg1);
			break;
		case "automationpractice.com/index.php?controller=my-account":
			isOnPage = context.myAccount.isOnPage(arg1);
			break;
		case "automationpractice.com/index.php?controller=search":
			isOnPage = context.search.isOnPage(arg1);
			break;
		default:
			throw new RuntimeException("Invalid option " + arg1);
		}
		Assert.assertTrue("User is not on expected page", isOnPage);
	}

	@When("^User searches for \"([^\"]*)\"$")
	public void user_search_for(String searchField) {
		context.homePage.enterSearchKeyWord(searchField);
		context.search = context.homePage.clickOnSearchButton();
	}

	@Then("^verifies all result product contain the word \"([^\"]*)\"$")
	public void verifies_all_result_product_contain_the_word(String expectedProduct) {
		boolean isExpectedProduct = context.search.verifyProduct(expectedProduct);
		Assert.assertTrue("Expected product not present in search results", isExpectedProduct);
	}
}

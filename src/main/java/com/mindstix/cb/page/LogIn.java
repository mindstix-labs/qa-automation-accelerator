/*
 * Copyright (c) 2017 Mindstix Inc.
 */
package com.mindstix.cb.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Represents Log In Page <li>
 * automationpractice.com/index.php?controller=authentication&back=my-account</li>
 * 
 * @author Mindstix
 */
public class LogIn extends BasePage {

	private WebElement emailAddress;
	private WebElement password;
	private WebElement signInButton;
	private WebElement registerEmail;
	private WebElement createAccountButton;
	private WebElement registerButton;
	private WebElement titleChoice;
	private WebElement firtsName;
	private WebElement lastName;
	private WebElement registerPassword;
	private WebElement addressFirstName;
	private WebElement addressLastName;
	private WebElement address;
	private WebElement city;
	private WebElement state;
	private WebElement mobileNumber;
	private WebElement postalCode;

	public LogIn(WebDriver webDriver) {
		super(webDriver);
	}

	public void enterUserData(String emailAddressValue, String passwordValue) {
		emailAddress = getElementByCSS("emailAddressCSS");
		password = getElementByCSS("passwordCSS");
		sendKeysToElement(emailAddress, emailAddressValue);
		sendKeysToElement(password, passwordValue);
	}
	
	public void enterUserDataUsingRedis(String user){
		emailAddress = getElementByCSS("emailAddressCSS");
		password = getElementByCSS("passwordCSS");
		sendKeysToElement(emailAddress, user);
		sendKeysToElement(password, getTestData("password"));
	}

	public MyAccount clickOnSignInButton() {
		signInButton = getElementByCSS("signInButtonCSS");
		scrollAndClick(signInButton);
		return new MyAccount(webDriver);
	}

	public Boolean verifyUnsuccessfulLogin() {
		return isElementPresentByCSS(("authenticationFailedAlertMessageCSS"));
	}

	public MyAccount registerUser(String emailAddress, String password) {
		registerEmail = getElementByCSS("registerEmailAddressCSS");
		sendKeysToElement(registerEmail, emailAddress);
		createAccountButton = getElementByCSS("createAccountButtonCSS");
		scrollAndClick(createAccountButton);
		titleChoice = getElementByCSS("titleChoiceCSS");
		scrollAndClick(titleChoice);
		firtsName = getElementByCSS("firstnameCSS");
		sendKeysToElement(firtsName, getTestData("firstname"));
		lastName = getElementByCSS("lastnameCSS");
		sendKeysToElement(lastName, getTestData("lastname"));
		registerPassword = getElementByCSS("passwordCSS");
		sendKeysToElement(registerPassword, password);
		addressFirstName = getElementByCSS("addressFirstnameCSS");
		sendKeysToElement(addressFirstName, getTestData("firstname"));
		addressLastName = getElementByCSS("addressLastnameCSS");
		sendKeysToElement(addressLastName, getTestData("lastname"));
		address = getElementByCSS("addressCSS");
		sendKeysToElement(address, getTestData("address"));
		city = getElementByCSS("cityCSS");
		sendKeysToElement(city, getTestData("city"));
		state = getElementByCSS("", "stateSelectCSS");
		selectValueInDropDownByVisibleText(state, getTestData("state"));
		postalCode = getElementByCSS("postalCodeCSS");
		sendKeysToElement(postalCode, getTestData("postalCode"));
		mobileNumber = getElementByCSS("mobileNumberCSS");
		sendKeysToElement(mobileNumber, getTestData("phoneNumber"));
		registerButton = getElementByCSS("registerButtonCSS");
		scrollAndClick(registerButton);
		return new MyAccount(webDriver);
	}
}

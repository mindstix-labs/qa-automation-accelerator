/*
 * Copyright (c) 2017 Mindstix Inc.
 */
package com.mindstix.cb.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Represents Automation Practice Home Page <li>automationpractice.com/index.php
 * </li>
 * 
 * @author Mindstix
 */
public class HomePage extends BasePage {

	private WebElement contactUsTab;
	private WebElement signInTab;
	private WebElement searchField;
	private WebElement searchButton;

	public HomePage(WebDriver webDriver) {
		super(webDriver);
	}

	public ContactUs clickOnContactUsTab() {
		contactUsTab = getElementByCSS("contactUsTabCSS");
		scrollAndClick(contactUsTab);
		return new ContactUs(webDriver);
	}

	public LogIn clickOnSignInTab() {
		signInTab = getElementByCSS("signInTabCSS");
		scrollAndClick(signInTab);
		return new LogIn(webDriver);
	}

	public void enterSearchKeyWord(String searchKeyWord) {
		searchField = getElementByCSS("searchBarCSS");
		sendKeysToElement(searchField, searchKeyWord);
	}

	public Search clickOnSearchButton() {
		searchButton = getElementByCSS("searchButtonCSS");
		scrollAndClick(searchButton);
		return new Search(webDriver);
	}
}

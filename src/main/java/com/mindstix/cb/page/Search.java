/*
 * Copyright (c) 2017 Mindstix Inc.
 */
package com.mindstix.cb.page;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents Search Result Page 
 * <li>automationpractice.com/index.php?controller=search</li>
 * 
 * @author Mindstix
 */
public class Search extends BasePage {

	private final static Logger LOGGER = LoggerFactory.getLogger(Search.class);

	public Search(WebDriver webDriver) {
		super(webDriver);
	}

	public boolean verifyProduct(String expectedProduct) {
		boolean isExpectedProduct = false;
		List<WebElement> productList = getArrayOfElementByCSS("searchResultListCSS");
		LOGGER.info("Expecetd product is {}", expectedProduct);
		for (WebElement product : productList) {
			LOGGER.info("Product title is {}", product.getText());
			if (product.getText().contains(expectedProduct)) {
				isExpectedProduct = true;
			}
		}
		return isExpectedProduct;
	}
}

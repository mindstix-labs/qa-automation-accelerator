/*
 * Copyright (c) 2017 Mindstix Inc.
 */

package com.mindstix.cb.stepdefinition;

import org.apache.commons.lang3.time.StopWatch;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mindstix.cb.utils.DriverUtility;

import cucumber.api.Scenario;

/**
 * @author Mindstix
 */
public abstract class BaseScenarioContext {

	private final static Logger LOGGER = LoggerFactory.getLogger(BaseScenarioContext.class);

	private WebDriver webDriver;
	private StopWatch stopWatch;

	/**
	 * Returns webDriver
	 * 
	 * @return
	 */
	public WebDriver getWebDriver() {
		return webDriver;
	}

	/**
	 * Method which will Execute before Each Scenario
	 * 
	 * @param urlKey
	 */
	protected void initializationMethod(String urlKey) {
		stopWatch = new StopWatch();
		stopWatch.start();
		String browser = System.getProperty("env.browser");
		LOGGER.info("Browser:{}", browser);
		webDriver = DriverUtility.loadDriver(browser);
		webDriver.get(System.getProperty(urlKey));
		LOGGER.info("Browser navigating to URL {}", System.getProperty(urlKey));
	}

	/**
	 * Method which will execute after each scenario after execution of each Takes
	 * screenshot when Scenario fails and saves it into HTML report
	 * 
	 * @param scenario
	 */
	protected void teardownMethod(Scenario scenario) {
		if (scenario.isFailed()) {
			try {
				byte[] screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
				scenario.embed(screenshot, "image/png");
				LOGGER.info("Test Case Failed. Screenshot will be saved in build/reports/cucumberreport/cucumber");
			} catch (Exception ex) {
				LOGGER.error("Exception while getting screenshot", ex);
			}
		}
		if (webDriver != null) {
			stopWatch.stop();
			LOGGER.info("Total time taken to Execute Test Scenario {} Milliseconds", stopWatch.getTime());
			DriverUtility.quitWebDriver(webDriver);
		}
	}
}

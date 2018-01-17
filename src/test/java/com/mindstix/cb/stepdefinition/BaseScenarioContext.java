/*
 * Copyright (c) 2017 Mindstix Inc.
 */

package com.mindstix.cb.stepdefinition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.StopWatch;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import com.mindstix.cb.utils.DriverUtility;
import com.mindstix.cb.utils.PropertiesUtility;

import cucumber.api.Scenario;

/**
 * @author Mindstix
 */
public abstract class BaseScenarioContext {

	private final static Logger LOGGER = LoggerFactory.getLogger(BaseScenarioContext.class);

	private WebDriver webDriver;
	private StopWatch stopWatch;

	public List<String> allProductID;
	private String testResult;

	/**
	 * Returns webDriver
	 * 
	 * @return
	 */
	public WebDriver getWebDriver() {
		allProductID = new ArrayList<>();
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
				testResult = "Failed";
			} catch (Exception ex) {
				LOGGER.error("Exception while getting screenshot", ex);
			}
		} else {
			testResult = "Pass";
		}
		if (webDriver != null) {
			stopWatch.stop();
			LOGGER.info("Total time taken to Execute Test Scenario {} Milliseconds", stopWatch.getTime());
			DriverUtility.quitWebDriver(webDriver);
		}

		if (this.allProductID.size() > 0) {
			addDataInReport(scenario.getName());
		}
	}

	private void addDataInReport(String scenario) {
		LOGGER.info("Adding data in report");
		Map<String, Object> data = new HashMap<String, Object>();
		if (scenario.equals("")) {
			scenario = "NA";
		}
		data.put("scenario", scenario);
		data.put("allProductID", allProductID);
		data.put("testResult", testResult);
		List<Object> listObj = new ArrayList<>();
		listObj.add(data);
		Yaml yaml = new Yaml();
		yaml.dump(listObj, PropertiesUtility.getFileWriter());
		LOGGER.info("Data added in report");
	}
}

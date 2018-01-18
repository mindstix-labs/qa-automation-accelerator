/*
 * Copyright (c) 2017 Mindstix Inc.
 */
package com.mindstix.cb.data;

import java.util.List;

/**
 * Represents the report data
 * 
 * @author Mindstix
 */
public class ReportData {
	private String scenario;
	private List<String> allProductID;
	private String testResult;

	public String getTestResult() {
		return testResult;
	}

	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}

	/**
	 * @return the scenario
	 */
	public String getScenario() {
		return scenario;
	}

	/**
	 * @param scenario
	 *            the scenario to set
	 */
	public void setScenario(String scenario) {
		this.scenario = scenario;
	}

	/**
	 * @return the allProductID
	 */
	public List<String> getAllProductID() {
		return allProductID;
	}

	/**
	 * @param allProductID
	 *            the allProductID to set
	 */
	public void setAllProductID(List<String> allProductID) {
		this.allProductID = allProductID;
	}
}
/*
 * Copyright (c) 2017 Mindstix Inc.
 */
package com.mindstix.cb.stepdefinition.report;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mindstix.cb.utils.GlobalContext;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author Mindstix
 */
public class ReportTest {

	private final static Logger LOGGER = LoggerFactory.getLogger(ReportTest.class);
	private StopWatch stopWatch;

	/**
	 * Method which will Execute before Report Scenario
	 */
	@Before("@report")
	public void startMethod() {
		LOGGER.info("Genearting report and sending Email!");
		stopWatch = new StopWatch();
		stopWatch.start();
	}

	/**
	 * Sends report to given email address
	 * 
	 * @throws Throwable
	 */
	@Given("^Setup report header$")
	public void setup_report_header() {
		GlobalContext.setupReportHeader();
	}

	@When("^Setup report body$")
	public void setup_report_body() {
		GlobalContext.setupReportTable();
	}

	@When("^Setup report footer$")
	public void setup_report_footer() {
		GlobalContext.setupReportFooter();
	}

	@Then("^Email report \"([^\"]*)\"$")
	public void print_all_order_numbers(String to) throws Throwable {
		GlobalContext.emailReportTo(to);
	}

	@Given("^Total time taken$")
	public void print_total_time() {
		GlobalContext.printTime();
	}
	/**
	 * Method which will Execute after Report scenario
	 * 
	 * @param scenario
	 *            -Cucumber Scenario which is currently executing
	 */
	@After("@report")
	public void endMethod() {
		stopWatch.stop();
		LOGGER.info("Total time taken to generate report and send email {} Milliseconds", stopWatch.getTime());
	}
}

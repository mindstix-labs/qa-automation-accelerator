/*
 * Copyright (c) 2017 Mindstix Inc.
 */
package com.mindstix.cb.runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/feature/ecom/search.feature",
	tags = "@cbt", dryRun = false, strict = true, monochrome = true,
	glue = "com.mindstix.cb.stepdefinition",
	plugin = {
		"pretty", "html:target/reports/cucumberreport/search", "json:target/reports/cucumberreport/search.json" })

/**
 * This runner is a fallback for maven based builds
 * Do not use them in gradle builds - use RunnerCourgette instead
 *
 * @author Mindstix
 *
 */
@Deprecated
public class SearchRunner {

}

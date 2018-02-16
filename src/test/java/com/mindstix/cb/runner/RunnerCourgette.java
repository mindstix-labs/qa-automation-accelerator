/*
 * Copyright (c) 2017 Mindstix Inc.
 */
package com.mindstix.cb.runner;

import org.junit.runner.RunWith;

import courgette.api.CourgetteOptions;
import courgette.api.CourgetteRunLevel;
import courgette.api.junit.Courgette;
import cucumber.api.CucumberOptions;

@RunWith(Courgette.class)
@CourgetteOptions(
	    runLevel = CourgetteRunLevel.SCENARIO,
	    showTestOutput = true,
	    rerunFailedScenarios = false,
	    threads = 2,
	    cucumberOptions = @CucumberOptions(
	    		  features = {"src/test/resources/feature/ecom",
											      "src/test/resources/feature/setup",
			                      "src/test/resources/feature/api"},
	    		  tags = "@cbt",
	    		  dryRun = false,
	    		  strict = true,
	    		  monochrome=true,
	    		  glue = {"com.mindstix.cb.stepdefinition.ecom",
			              "com.mindstix.cb.stepdefinition.api"},
	    		  plugin = {"pretty", "html:build/reports/cucumberreport/index", 
	    		                  "json:build/reports/cucumberreport/cucumber.json" }
	    		))

/**
 * This runner is used from gradle build and avoids creating runner classes manually
 * to achieve parallel execution.
 * Caution: The cucumber.json generated from this runner is ONLY useful when the build is complete
 * Any use of this cucumber.json while the build is running OR it is aborted - will lead to wrong reports
 * The report is claimed to be threadsafe but the disk version is unusable until the build is complete
 *
 * Original author - Prashant Ramcharan
 * License - https://github.com/prashant-ramcharan/courgette-jvm/blob/master/LICENSE
 * GitHub - https://github.com/prashant-ramcharan/courgette-jvm/
 * License file added under 3rdparty/courgette-MIT-license.txt
 *
 * @author Mindstix
 *
 */
public class RunnerCourgette {

}
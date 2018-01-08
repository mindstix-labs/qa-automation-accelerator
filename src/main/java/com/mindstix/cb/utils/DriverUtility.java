/*
 * Copyright (c) 2017 Mindstix Inc.
 */

package com.mindstix.cb.utils;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;

/**
 * Initializing and closing Web Driver
 * 
 * @author Mindstix
 */
public final class DriverUtility {

	private final static Logger LOGGER = LoggerFactory.getLogger(DriverUtility.class);

	/**
	 * Constructor
	 */
	private DriverUtility() {

	}

	/**
	 * Method to load properties according to mentioned Browser Name and Initialize
	 * Web Driver
	 * 
	 * @param browserName
	 *            -Name of Browser Mentioned in testng.xml
	 */
	public static WebDriver loadDriver(String browserName) {
		InputStream driverInput = null;
		try {
			WebDriver webDriver = initDriver(browserName);
			webDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			webDriver.manage().timeouts().setScriptTimeout(9000, TimeUnit.MILLISECONDS);
			webDriver.manage().timeouts().pageLoadTimeout(10, TimeUnit.MINUTES);
			return webDriver;
		} catch (Exception ex) {
			LOGGER.error("An Exception occurred!", ex);
			throw new RuntimeException("Failed to load driver!");
		} finally {
			IOUtils.closeQuietly(driverInput);
		}
	}

	public static void quitWebDriver(WebDriver webDriver) {
		webDriver.quit();
	}

	public void closeWebDriver(WebDriver webDriver) {
		webDriver.close();
	}

	/**
	 * To initialize Web Driver according to value of browserName provided in .xml
	 * 
	 * @param browserType Browser Name
	 */
	private static WebDriver initDriver(String browserType) {
		WebDriver webDriver;
		switch (browserType) {
		case "chrome":
			webDriver = initChromeDriver();
			break;
		case "firefox":
			webDriver = initFirefoxDriver();
			break;
		case "IE":
			webDriver = initIEDriver();
			break;
		default:
			LOGGER.info("browser: {} is invalid, Launching Chrome as browser of choice..", browserType);
			webDriver = initChromeDriver();
		}
		return webDriver;
	}

	/**
	 * To initialize Chrome Web Driver
	 * 
	 * @return chrome Web Driver
	 */
	private static WebDriver initChromeDriver() {
		ChromeDriverManager.getInstance().version("2.33").setup();
		String mode = System.getProperty("env.mode");
		ChromeOptions options = null;
		WebDriver webDriver = null;
		if (mode != null) {
			switch (mode) {
			case "normal":
				DesiredCapabilities handlSSLErr = DesiredCapabilities.chrome();
				handlSSLErr.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				webDriver = new ChromeDriver(handlSSLErr);
				break;
			case "headless":
				options = new ChromeOptions();
				options.addArguments("--headless");
				options.addArguments("--start-maximized");
				webDriver = new ChromeDriver(options);
				break;
			case "grid":
				String hubIPAddress = System.getProperty("env.hubIP");
				DesiredCapabilities dc = DesiredCapabilities.chrome();
				try {
					webDriver = new RemoteWebDriver(new URL("http://" + hubIPAddress + "/wd/hub"), dc);
				} catch (MalformedURLException e) {
					throw new RuntimeException(e.getMessage());
				}
				break;
			case "incognito":
				options = new ChromeOptions();
				options.addArguments("--incognito");
				webDriver = new ChromeDriver(options);
				webDriver.manage().window().maximize();
				break;
			default:
				webDriver = new ChromeDriver();
				break;
			}
		}
		return webDriver;
	}

	/**
	 * To initialize Firefox Web Driver
	 * 
	 * @return Firefox Web Driver
	 */
	private static WebDriver initFirefoxDriver() {
		FirefoxDriverManager.getInstance().version("0.18").setup();
		WebDriver webDriver = new FirefoxDriver();
		webDriver.manage().window().maximize();
		return webDriver;
	}

	/**
	 * To initialize IE Web Driver
	 * 
	 * @return IE Web Driver
	 */
	private static WebDriver initIEDriver() {
		InternetExplorerDriverManager.getInstance().setup();
		WebDriver webDriver = new InternetExplorerDriver();
		webDriver.manage().window().maximize();
		return webDriver;
	}
}

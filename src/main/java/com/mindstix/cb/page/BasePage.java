/*
 * Copyright (c) 2017 Mindstix Inc.
 */
package com.mindstix.cb.page;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.time.StopWatch;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mindstix.cb.utils.PropertiesUtility;

/**
 * Contains common methods used in Page classes like
 * <ul>
 * <li>Finding Web Elements on Web Page</li>
 * <li>Checking availability of Web Elements on Web Page</li>
 * <li>Get current page title and URL</li>
 * <li>To close Ads</li>
 * <li>To scroll and click on Web Elements</li>
 * <li>For sending keys to a Web Element</li>
 * </ul>
 * 
 * @author Mindstix
 */
public abstract class BasePage {

	private final static Logger LOGGER = LoggerFactory.getLogger(BasePage.class);

	private Properties selectors;
	private Properties testData;

	private JavascriptExecutor jScriptExecutor;
	private StopWatch stopWatch;

	protected WebDriver webDriver;

	/**
	 * Constructor
	 */
	public BasePage(WebDriver webDriver) {
		this.webDriver = webDriver;
		selectors = PropertiesUtility.getSelectors();
		testData = PropertiesUtility.getTestdata();
	}

	public String getSelectors(String selectorKey) {
		return selectors.getProperty(selectorKey);
	}

	public String getTestData(String dataKey) {
		return testData.getProperty(dataKey);
	}

	public Boolean isOnPage(String requiredURL) {
		String pageURL = getCurrentURL();
		LOGGER.info("Current Page URL:{}", pageURL);
		LOGGER.info("Required Page URL:{}", requiredURL);
		return pageURL.contains(requiredURL);
	}

	/**
	 * To get WebElement by specifying XPath of Element Present on Web Page
	 * 
	 * @param webElementKey
	 *            -The XPath which must Present on Web Page
	 * @return WebElement element -Will Return WebElement which is present on
	 *         Web Page with Mentioned XPath
	 */
	public WebElement getElementByXPath(String webElementKey) {
		stopWatch = new StopWatch();
		stopWatch.start();
		WebDriverWait webDriverWait = new WebDriverWait(webDriver, 30);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(getSelectors(webElementKey))));
		WebElement element = webDriver.findElement(By.xpath(getSelectors(webElementKey)));
		LOGGER.info("Located Web Element");
		jScriptExecutor = (JavascriptExecutor) webDriver;
		jScriptExecutor.executeScript("arguments[0].style.border='3px solid red'", element);
		stopWatch.stop();
		LOGGER.info("Time taken to load element is {} Milliseconds", stopWatch.getTime());
		return element;
	}

	/**
	 * To get WebElement by specifying CSS Selector of Element Present on Web
	 * Page
	 * 
	 * @param webElementKey
	 *            -The CSS Selector which must Present on Web Page
	 * @return WebElement element -Will Return WebElement which is present on
	 *         Web Page with Mentioned CssSelector
	 */
	public WebElement getElementByCSS(String webElementKey) {
		stopWatch = new StopWatch();
		stopWatch.start();
		WebDriverWait webDriverWait = new WebDriverWait(webDriver, 30);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(getSelectors(webElementKey))));
		WebElement element = webDriver.findElement(By.cssSelector(getSelectors(webElementKey)));
		LOGGER.info("Located Web Element");
		jScriptExecutor = (JavascriptExecutor) webDriver;
		jScriptExecutor.executeScript("arguments[0].style.border='3px solid red'", element);
		stopWatch.stop();
		LOGGER.info("Time taken to load element is {} Milliseconds", stopWatch.getTime());
		return element;
	}

	/**
	 * To get WebElement by specifying prefix of selector and key of cssSelctor
	 * of Element Present on Web Page
	 * 
	 * @param webElementKey
	 *            -The key of CSS Selector which must be Present on Web Page
	 * @param prefix
	 *            -The prefix of cssSelector
	 * @return WebElement element -Will Return WebElement which is present on
	 *         Web Page with Mentioned CssSelector
	 */
	public WebElement getElementByCSS(String prefix, String webElementKey) {
		stopWatch = new StopWatch();
		stopWatch.start();
		WebElement element = webDriver.findElement(By.cssSelector(prefix + getSelectors(webElementKey)));
		LOGGER.info("Located Web Element");
		jScriptExecutor = (JavascriptExecutor) webDriver;
		jScriptExecutor.executeScript("arguments[0].style.border='3px solid red'", element);
		stopWatch.stop();
		LOGGER.info("Time taken to load element is {} Milliseconds", stopWatch.getTime());
		return element;
	}

	public WebElement getElementByCSS(String prefix, int index, String suffix) {
		stopWatch = new StopWatch();
		stopWatch.start();
		WebDriverWait webDriverWait = new WebDriverWait(webDriver, 30);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(getSelectors(prefix) + index + getSelectors(suffix))));
		WebElement element = webDriver.findElement(By.cssSelector(getSelectors(prefix) + index + getSelectors(suffix)));
		LOGGER.info("Located Web Element");
		jScriptExecutor = (JavascriptExecutor) webDriver;
		jScriptExecutor.executeScript("arguments[0].style.border='3px solid red'", element);
		stopWatch.stop();
		LOGGER.info("Time taken to load element is {} Milliseconds", stopWatch.getTime());
		return element;
	}

	/**
	 * Method to get Array of WebElement by specifying XPath of Elements Present
	 * on Web Page
	 * 
	 * @param webElementKey
	 *            -XPath of Elements Present on Web Page
	 * @return List<WebElement> -List of Web Elements
	 */
	public List<WebElement> getArrayOfElementByXPath(String webElementKey) {
		List<WebElement> dropDownList = new ArrayList<WebElement>();
		try {
			stopWatch = new StopWatch();
			stopWatch.start();
			WebDriverWait webDriverWait = new WebDriverWait(webDriver, 30);
			webDriverWait.ignoring(NoSuchElementException.class);
			webDriverWait.ignoring(TimeoutException.class);
			webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(getSelectors(webElementKey))));
			dropDownList = webDriver.findElements(By.xpath(getSelectors(webElementKey)));
			stopWatch.stop();
			LOGGER.info("Time taken to load element is {} Milliseconds", stopWatch.getTime());
		} catch (NoSuchElementException exception) {
			LOGGER.error("No Such Element Present on Web Page");
		} catch (Exception exception) {
			LOGGER.error("No Such Element Present on Web Page");
		}
		return dropDownList;
	}

	/**
	 * Method to get Array of WebElement by specifying CSS Selector of Elements
	 * Present on Web Page
	 * 
	 * @param webElementKey
	 *            cssSelectorElement of Elements Present on Web Page
	 * @return List<WebElement> -List of Web Elements
	 */
	public List<WebElement> getArrayOfElementByCSS(String webElementKey) {
		List<WebElement> dropDownList = new ArrayList<WebElement>();
		try {
			LOGGER.info(webElementKey);
			stopWatch = new StopWatch();
			stopWatch.start();
			WebDriverWait webDriverWait = new WebDriverWait(webDriver, 30);
			webDriverWait.ignoring(NoSuchElementException.class);
			webDriverWait.ignoring(TimeoutException.class);
			webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(getSelectors(webElementKey))));
			dropDownList = webDriver.findElements(By.cssSelector(getSelectors(webElementKey)));
			stopWatch.stop();
			LOGGER.info("Time taken to load element is {} Milliseconds", stopWatch.getTime());
		} catch (NoSuchElementException exception) {
			LOGGER.error("No Such Element Present on Web Page");
		} catch (Exception exception) {
			LOGGER.error("Unknown exception", exception);
		}
		return dropDownList;
	}

	/**
	 * Method to get Array of WebElement by specifying CSS Selector of Elements
	 * Present on Web Page
	 * 
	 * @param webElementKey
	 *            cssSelectorElement of Elements Present on Web Page
	 * @param suffix
	 *            suffix for cssSelector
	 * @return List<WebElement> -List of Web Elements
	 */
	public List<WebElement> getArrayOfElementByCSS(String suffix, String webElementKey) {
		List<WebElement> dropDownList = new ArrayList<WebElement>();
		try {
			LOGGER.info(webElementKey);
			stopWatch = new StopWatch();
			stopWatch.start();
			WebDriverWait webDriverWait = new WebDriverWait(webDriver, 30);
			webDriverWait.ignoring(NoSuchElementException.class);
			webDriverWait.ignoring(TimeoutException.class);
			webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(suffix + getSelectors(webElementKey))));
			dropDownList = webDriver.findElements(By.cssSelector(suffix + getSelectors(webElementKey)));
			stopWatch.stop();
			LOGGER.info(suffix + getSelectors(webElementKey));
			LOGGER.info("Time taken to load element is {} Milliseconds", stopWatch.getTime());
		} catch (NoSuchElementException exception) {
			LOGGER.error("No Such Element Present on Web Page");
		} catch (Exception exception) {
			LOGGER.error("Unknown exception", exception);
		}
		return dropDownList;
	}

	/**
	 * Method to get URL of current web page
	 * 
	 * @return String currentURL -Current Page URL
	 */
	public String getCurrentURL() {
		LOGGER.info("Capturing Current URL");
		String currentURL = webDriver.getCurrentUrl();
		return currentURL;
	}

	/**
	 * To get WebElement by specifying Link Text of Element Present on Web Page
	 * 
	 * @param webElementKey
	 *            -The LinkText of element which is Present on Web Page
	 * @return WebElement element -Will Return WebElement which is present on
	 *         Web Page with Mentioned LinkText
	 */
	public WebElement getElementByLink(String webElementKey) {
		stopWatch = new StopWatch();
		stopWatch.start();
		WebDriverWait webDriverWait = new WebDriverWait(webDriver, 30);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(getSelectors(webElementKey))));
		WebElement element = webDriver.findElement(By.linkText(getSelectors(webElementKey)));
		jScriptExecutor = (JavascriptExecutor) webDriver;
		jScriptExecutor.executeScript("arguments[0].style.border='3px solid red'", element);
		stopWatch.stop();
		LOGGER.info("Time taken to load element is {} Milliseconds", stopWatch.getTime());
		return element;
	}

	/**
	 * Method to get Title of current Page
	 * 
	 * @return String currentTitle -Title of current web page
	 */
	public String getCurrentPageTitle() {
		LOGGER.info("Getting Current Title of Page");
		String currentTitle = webDriver.getTitle();
		return currentTitle;
	}

	/**
	 * Method to Scroll to Web Element and perform click Action
	 * 
	 * @param webElement
	 *            -The Web Element which want to click
	 */
	public void scrollAndClick(WebElement webElement) {
		stopWatch = new StopWatch();
		stopWatch.start();
		if (webDriver != null) {
			JavascriptExecutor js = (JavascriptExecutor) webDriver;
			js.executeScript("window.scrollTo(0, " + webElement.getLocation().y + ")");
			if (webElement.isDisplayed()) {
				jScriptExecutor = (JavascriptExecutor) webDriver;
				jScriptExecutor.executeAsyncScript("window.setTimeout(arguments[arguments.length - 1], 800);");
				webElement.click();
				LOGGER.info("Clicked Web Element");
				stopWatch.stop();
				LOGGER.info("Time taken to click element is {} Milliseconds", stopWatch.getTime());
			}
		}
	}

	/**
	 * Method to send value to Web Element
	 * 
	 * @param webElement
	 *            -Web Element where want to send value
	 * @param value
	 *            -value which want to send
	 */
	public void sendKeysToElement(WebElement webElement, String value) {
		stopWatch = new StopWatch();
		stopWatch.start();
		jScriptExecutor = (JavascriptExecutor) webDriver;
		jScriptExecutor.executeAsyncScript("window.setTimeout(arguments[arguments.length - 1], 1000);");
		if (webElement.isEnabled()) {
			LOGGER.info("Selected Value is {}", value);
			webElement.clear();
			webElement.sendKeys(value);
			stopWatch.stop();
			LOGGER.info("Time taken to send values to element is {} Milliseconds", stopWatch.getTime());
		}
	}

	/**
	 * Add Values to List
	 * 
	 * @param expectedProductTitle
	 *            -ArrayList
	 * @param values
	 *            -values
	 */
	public void addItemToList(List<String> expectedProductTitle, String values) {
		expectedProductTitle.add(values);
		LOGGER.info("{} is added to list", values);
	}

	/**
	 * Verifies the existence of specific web element based on the its xpath.
	 * 
	 * @param webElementKey
	 *            - the xpath of the element to be searched.
	 * @return true if element present on site, false if not found.
	 */
	public Boolean isElementPresent(String webElementKey) {
		if (webDriver.findElements(By.xpath(getSelectors(webElementKey))).size() != 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Verifies the existence of specific web element based on its CSS selector.
	 * 
	 * @param webElementKey
	 *            - the CSS selector of the element to be searched.
	 * @return true if element present on site, false if not found.
	 */
	public Boolean isElementPresentByCSS(String webElementKey) {
		if (webDriver.findElements(By.cssSelector(getSelectors(webElementKey))).size() != 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Verifies the existence of specific web element based on its CSS selector.
	 * 
	 * @param webElementKey
	 *            - the CSS selector key of the element to be searched.
	 * @param prefix
	 *            - The prefix of cssSelector
	 * @return true if element present on site, false if not found.
	 */
	public Boolean isElementPresentByCSS(String prefix, String webElementKey) {
		if (webDriver.findElements(By.cssSelector(prefix + getSelectors(webElementKey))).size() != 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param text
	 * @return
	 */
	public boolean isTextOnPagePresent(String text) {
		WebElement body = webDriver.findElement(By.tagName("body"));
		String bodyText = body.getText();
		return bodyText.contains(text);
	}

	/**
	 * To select value from drop down w.r.t visible text
	 * 
	 * @param webElement
	 *            -Dropdown element
	 * @param value
	 *            -Value want to select
	 */
	public void selectValueInDropDownByVisibleText(WebElement webElement, String value) {
		Select webElementSelect = new Select(webElement);
		webElementSelect.selectByVisibleText(value);
	}

	/**
	 * Used to draw a signature on canvas
	 * 
	 * @param webElementKey
	 *            Selector key for canvas element
	 * @param signatureID
	 *            Signature ID mentioned in YML file to get the coordinates to
	 *            draw signature
	 */
	public void drawOnCanvas(String webElementKey, String signatureID) {
		List<Point> points = PropertiesUtility.getSignatureCoordinates(signatureID);
		WebElement canvas = getElementByCSS(webElementKey);
		Actions drawAction = new Actions(webDriver);
		drawAction.clickAndHold(canvas);
		for (Point coordinate : points) {
			drawAction.moveByOffset(coordinate.x, coordinate.y);
		}
		drawAction.release().perform();
	}

	/**
	 * Used to get Doller value by excluding $ sign
	 * 
	 * @param webElementKey
	 * @return floating value
	 */
	public float getDollerAmountByCSS(String webElementKey) {
		float amount;
		webElementKey = getElementByCSS(webElementKey).getText();
		webElementKey = webElementKey.substring(webElementKey.lastIndexOf("$") + 1);
		webElementKey = webElementKey.replaceAll(",", "");
		amount = Float.valueOf(webElementKey);
		return amount;
	}

	/**
	 * Used to compare two float values
	 * 
	 * @param n1
	 * @param n2
	 * @return
	 */
	public boolean isEqual(float n1, float n2) {
		return Math.abs(n1 - n2) < 0.0001;
	}
}

package com.ui.framework.customListener;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.testng.Reporter;

public class WebEventListener implements WebDriverEventListener {

	public static final Logger log = Logger.getLogger(WebEventListener.class.getName());

	/*This method will log all the items that are mentioned in each of the event
	 * This will reduce the overhead of adding loggers in each and every step in test
	 * method as well as Page Factory classes*/
	
	public void beforeAlertAccept(WebDriver driver) {
		log("before Alert Accept");
	}

	public void afterAlertAccept(WebDriver driver) {
		log("after Alert Accept");
	}

	public void afterAlertDismiss(WebDriver driver) {
		log("after Alert Dismiss");
	}

	public void beforeAlertDismiss(WebDriver driver) {
		log("before Alert Dismiss");
	}

	public void beforeNavigateTo(String url, WebDriver driver) {
		log("Navitage to :" + url);
	}

	public void afterNavigateTo(String url, WebDriver driver) {
		log("After Navitage to :" + url);
	}

	public void beforeNavigateBack(WebDriver driver) {
		log("Navigating back to previous page");
	}

	public void afterNavigateBack(WebDriver driver) {
		log("Navigated back to previous page");
	}

	public void beforeNavigateForward(WebDriver driver) {
		log("Navigating forward to next page");
	}

	public void afterNavigateForward(WebDriver driver) {
		log("Navigated forward to next page");
	}

	public void beforeNavigateRefresh(WebDriver driver) {
		log("before Navigate Refresh");
	}

	public void afterNavigateRefresh(WebDriver driver) {
		log("after Navigate Refresh");
	}

	public void beforeFindBy(By by, WebElement element, WebDriver driver) {
		log("Trying to find element By:" + by.toString());
	}

	public void afterFindBy(By by, WebElement element, WebDriver driver) {
		log("Found element By:" + by.toString());
	}

	public void beforeClickOn(WebElement element, WebDriver driver) {
		log("before Click On"+ element.toString());
	}

	public void afterClickOn(WebElement element, WebDriver driver) {
		log("After Clicked On: " + element.toString());
	}

	public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		log("Value of the WebElement " + element.toString() + " before any changes");
	}

	public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		log("Value changed to " + element.toString());
	}

	public void beforeScript(String script, WebDriver driver) {
		log("before Script");
	}

	public void afterScript(String script, WebDriver driver) {
		log("after Script");
	}

	public void onException(Throwable throwable, WebDriver driver) {
		log("Exception Occured " + throwable);
	}
	
	public void log(String data){
		log.info(data);
		Reporter.log(data);
	}

	public <X> void afterGetScreenshotAs(OutputType<X> arg0, X arg1) {
		log("before Navigate Refresh");	
	}

	public void afterGetText(WebElement element, WebDriver driver, String text) {
		log("after GetText:"+text);
	}

	public void afterSwitchToWindow(String arg0, WebDriver driver) {
		log("after SwitchTo Window");
	}

	public <X> void beforeGetScreenshotAs(OutputType<X> arg0) {
		log("before GetScreenshotAs");	
	}

	public void beforeGetText(WebElement element, WebDriver driver) {
		log("before GetText:"+element.toString());
	}

	public void beforeSwitchToWindow(String arg0, WebDriver driver) {
		log("before SwitchTo Window");	
	}

}

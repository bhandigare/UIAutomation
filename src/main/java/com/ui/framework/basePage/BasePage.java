package com.ui.framework.basePage;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ui.framework.manager.FileReaderManager;


public abstract class BasePage {

	private EventFiringWebDriver driver;
	public static final Logger log = Logger.getLogger(BasePage.class.getName());

	public BasePage(EventFiringWebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	/**
	 * Method : Used Robot class to upload file on mac and windows
	 * 
	 * @param filePath
	 */
	public void uploadFile(String filePath) {
		log.info("*******Start UploadFile*************");
		String path = "src/test/resources";
		File fileAbsolute = new File(path);
		String absolutePath = fileAbsolute.getAbsolutePath();
		File file = new File(absolutePath + '/' + filePath);
		System.out.println(absolutePath + '/' + filePath);
		StringSelection stringSelection = new StringSelection(file.getAbsolutePath());

		// Copy to clipboard
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);

		Robot robot = null;
		if (FileReaderManager.getInstance().getConfigReader().getOSName().equalsIgnoreCase("mac")) {
			try {
				robot = new Robot();
				// Cmd + Tab is needed since it launches a Java app and the browser looses focus
				robot.keyPress(KeyEvent.VK_META);
				robot.keyPress(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_META);
				robot.keyRelease(KeyEvent.VK_TAB);
				robot.delay(500);
				// Open Goto window
				robot.keyPress(KeyEvent.VK_META);
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_G);
				robot.keyRelease(KeyEvent.VK_META);
				robot.keyRelease(KeyEvent.VK_SHIFT);
				robot.keyRelease(KeyEvent.VK_G);
				// Paste the clipboard value
				robot.keyPress(KeyEvent.VK_META);
				robot.keyPress(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_META);
				robot.keyRelease(KeyEvent.VK_V);
				// Press Enter key to close the Goto window and Upload window
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
				robot.delay(1000);
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
			} catch (AWTException e) {
				e.printStackTrace();
			}
		} else {
			try {
				robot = new Robot();
				robot.delay(300);
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.delay(200);
				robot.keyRelease(KeyEvent.VK_ENTER);
			} catch (AWTException e) {
				e.printStackTrace();
			}
		}
		log.info("*******End UploadFile*************");
	}

	/**
	 * Method : The URL will be used to open in the browser of driver(WebDriver)
	 * 
	 * @param url
	 */
	public void getURL(String url) {
		log.info("Navigating to the URL:" + url);
		driver.get(url);
		driver.manage().window().maximize();
	}

	/**
	 * Method : Customize the sendKeys method
	 * 
	 * @param element
	 * @param text
	 */
	public void sendKeys(WebElement element, String text) {
		log.info("*******Sendkeys*************"+element.toString()+" Text:"+text);
		waitforElementVisibility(15, element);
		element.sendKeys(text);
	}

	/**
	 * Method : Customize the click method
	 * 
	 * @param element
	 */
	public void click(WebElement element) {
		log.info("*******Click*************"+element.toString());
		waitforElementVisibility(15, element);
		element.click();
	}

	/**
	 * Method : Customize the getText method
	 * 
	 * @param element
	 * @return
	 */
	public String getText(WebElement element) {
		log.info("*******getText*************"+element.toString());
		waitforElementVisibility(15, element);
		return element.getText();
	}

	/**
	 * Method : Wait for page load
	 */
	public void waitForPageLoaded() {
		log.info("*******Wait for page load*******");
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(expectation);
	}

	/**
	 * Method : Wait for the visibility of the element for a certain amount of time
	 * 
	 * @param timeoutseconds
	 * @param element
	 */
	public void waitforElementVisibility(long timeoutseconds, WebElement element) {
		log.info("Wait for Visibility:"+element.toString());
		WebDriverWait wait = new WebDriverWait(driver, timeoutseconds);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	/**
	 * Method : Wait for the invisibility of the element for a certain amount of
	 * time
	 * 
	 * @param timeoutseconds
	 * @param element
	 */
	public void waitforElementInvisibility(long timeoutseconds, WebElement element) {
		log.info("Wait for invisibility:"+element.toString());
		WebDriverWait wait = new WebDriverWait(driver, timeoutseconds);
		wait.until(ExpectedConditions.invisibilityOf(element));
	}
	
}

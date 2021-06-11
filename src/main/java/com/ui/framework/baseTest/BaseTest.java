package com.ui.framework.baseTest;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import com.ui.framework.manager.DriverManager;
import com.ui.framework.manager.FileReaderManager;

public abstract class BaseTest {

	protected EventFiringWebDriver driver;

	public static final Logger log = Logger.getLogger(BaseTest.class.getName());

	@BeforeClass()
	public void beforeSuite() {
		String path = "src/test/resources";
		File file = new File(path);
		String absolutePath = file.getAbsolutePath();
		String log4jConfPath = absolutePath + "/log4j.properties";
		PropertyConfigurator.configure(log4jConfPath);
		DriverManager manager = new DriverManager();
		log.info("Browser Opened");
		driver = manager.getDriver();
		driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			log.info("Captured failed test case screenshot");
			getScreenCapture(result.getName());
		}
	}

	@AfterClass()
	public void endTest() {
		driver.quit();
		log.info("Browser Closed");
	}

	// Method used for getting the screen capture with the name in a particular
	// format
	public void getScreenCapture(String name) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		try {
			File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

			String reportDirectory = new File(System.getProperty("user.dir")).getAbsolutePath() + "/target/screenshot/";
			File destFile = new File(
					(String) reportDirectory + name + "_" + formater.format(calendar.getTime()) + ".png");
			FileUtils.copyFile(srcFile, destFile);
			Reporter.log("<a href='" + destFile.getAbsolutePath() + "'><img src='" + destFile.getAbsolutePath()
					+ "' height='100' width='100'/> </a");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	// Logging method so that the same log is added in logger as well as syso
//	public void log(String data) {
//		log.info(data);
//		Reporter.log(data);
//	}

}

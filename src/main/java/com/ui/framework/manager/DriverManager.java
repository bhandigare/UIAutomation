package com.ui.framework.manager;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.ui.framework.customListener.WebEventListener;
import com.ui.framework.enums.DriverType;
import com.ui.framework.enums.EnvironmentType;
import com.ui.framework.utility.ConfigFileReader;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverManager {
	private WebDriver dr;
	private WebEventListener eventListener;
	private EventFiringWebDriver driver;
	private static DriverType driverType;
	private static EnvironmentType environmentType;
	public static final Logger log = Logger.getLogger(ConfigFileReader.class.getName());

	public DriverManager() {
		driverType = FileReaderManager.getInstance().getConfigReader().getBrowser();
		environmentType = FileReaderManager.getInstance().getConfigReader().getEnvironment();
	}
	
	/**
	 * Getting new driver public method
	 * @return
	 */
	public EventFiringWebDriver getDriver() {
		if (driver == null)
			driver = createDriver();
		return driver;
	}
	
	/**
	 * Creating driver
	 * @return
	 */
	private EventFiringWebDriver createDriver() {
		switch (environmentType) {
			case LOCAL:
				driver = createLocalDriver();
				break;
			case REMOTE:
				driver = createRemoteDriver();
				break;
		}
		return driver;
	}
	
	/**
	 * Provide ChromeOptions object with all config
	 * @return
	 */
	private ChromeOptions getChromeOptions() {
		ChromeOptions options = new ChromeOptions();
		return options;
	}
	
	
	/**
	 * Create Remote webdriver for GRID support
	 * @return
	 */
	private EventFiringWebDriver createRemoteDriver() {
		ChromeOptions options = this.getChromeOptions();
		String hostName = "localhost";
		if (!System.getProperty("HUB_HOST").isEmpty())
			hostName = System.getProperty("HUB_HOST");
		try {
			dr = new RemoteWebDriver(new URL("http://" + hostName + ":4444/wd/hub"), options);
			((RemoteWebDriver) dr).setFileDetector(new LocalFileDetector());
			log.info("Remote Driver Initialized:"+ hostName);
		} catch (MalformedURLException e) {
			log.error("Malformed URL:"+hostName +" :"+e.getMessage());
		}
		
		driver = new EventFiringWebDriver(dr);
		eventListener = new WebEventListener();
		driver.register(eventListener);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(FileReaderManager.getInstance().getConfigReader().getImplicitlyWait(),
				TimeUnit.SECONDS);
		return driver;
	}
	
	/**
	 * Create Local driver
	 * @return
	 */
	private EventFiringWebDriver createLocalDriver() {
		switch (driverType) {
		case FIREFOX:
			WebDriverManager.firefoxdriver().setup();
			dr = new FirefoxDriver();
			log.info("Local Driver Initialized for firefox");
			break;
		case CHROME:
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = this.getChromeOptions();
			dr = new ChromeDriver(options);
			log.info("Local Driver Initialized for chrome");
			break;
		default:
			break;
		}
		
		driver = new EventFiringWebDriver(dr);
		eventListener = new WebEventListener();
		driver.register(eventListener);
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(FileReaderManager.getInstance().getConfigReader().getImplicitlyWait(),
				TimeUnit.SECONDS);
		return driver;
	}
	
	/**
	 * Close all windows
	 */
	public void quitDriver() {
		driver.quit();
		log.info("Driver quite");
	}

}

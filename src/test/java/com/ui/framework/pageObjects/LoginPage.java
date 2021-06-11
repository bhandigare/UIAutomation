package com.ui.framework.pageObjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.ui.framework.basePage.BasePage;

public class LoginPage extends BasePage {

	public static final Logger log = Logger.getLogger(LoginPage.class.getName());
	
	EventFiringWebDriver driver;

	@FindBy(id = "login-email")
	private WebElement userNameTextbox;

	@FindBy(id="login-submit")
	private WebElement userNameSubmitButton;
	
	@FindBy(id = "password-login")
	private WebElement passwordTextbox;

	@FindBy(id = "login-submit-password")
	private WebElement loginSubmitButton;
	
	@FindBy(css = ".login-container")
	private WebElement loginContainer;

	public LoginPage(EventFiringWebDriver driver){
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	/**
	 * 
	 * @param emailaddress
	 * @param password
	 */
	public void loginToApplication(String emailaddress, String password) {
		log.info("*****loginToApplication******");
		sendKeys(userNameTextbox, emailaddress);
		click(userNameSubmitButton);
		sendKeys(passwordTextbox, password);
		click(loginSubmitButton);
	}
	
	/**
	 * Method : wait For Successful Logout
	 */
	public void waitForSuccessfulLogout() {
		log.info("*****waitForSuccessfulLogout******");
		waitForPageLoaded();
		waitforElementVisibility(25, loginContainer);
	}
	
	/**
	 * Method : check LoginPage Displayed
	 * @return boolean
	 */
	public boolean isLoginPageDisplayed() {
		log.info("*****isLoginPageDisplayed******");
		return loginContainer.isDisplayed();
	}
}

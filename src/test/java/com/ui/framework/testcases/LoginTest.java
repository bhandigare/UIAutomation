package com.ui.framework.testcases;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.ui.framework.baseTest.BaseTest;
import com.ui.framework.manager.FileReaderManager;
import com.ui.framework.pageObjects.HomePage;
import com.ui.framework.pageObjects.LoginPage;
import com.ui.framework.pageObjects.NewFolderPage;

public class LoginTest extends BaseTest {

	HomePage homePage ;
	LoginPage loginPage ;
	NewFolderPage newFolderPage;
	SoftAssert softAssert = new SoftAssert();
	public static final Logger log = Logger.getLogger(LoginTest.class.getName());
	
	
	/**
	 * Test : We are login with users details from Properties file and logout 
	 * 		  Verifying Successful login and logout 
	 */
	@Test
	public void verifyLoginLogutTest() {
		homePage = new HomePage(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToApplication(FileReaderManager.getInstance().getConfigReader().getApplicationUserName(),
				FileReaderManager.getInstance().getConfigReader().getApplicationPassword());
		homePage.waitForSuccessfulLogin();
		softAssert.assertTrue(homePage.isSuccessfulLogin(),"Successful login failed ");
		homePage.logout();
		loginPage.waitForSuccessfulLogout();
		softAssert.assertTrue(loginPage.isLoginPageDisplayed(),"Successful logout failed ");
		softAssert.assertAll();
	}
	
}

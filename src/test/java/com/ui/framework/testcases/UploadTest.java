package com.ui.framework.testcases;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.ui.framework.baseTest.BaseTest;
import com.ui.framework.manager.FileReaderManager;
import com.ui.framework.pageObjects.HomePage;
import com.ui.framework.pageObjects.LoginPage;
import com.ui.framework.pageObjects.NewFolderPage;

public class UploadTest extends BaseTest {

	HomePage homePage ;
	LoginPage loginPage ;
	NewFolderPage newFolderPage;
	SoftAssert softAssert = new SoftAssert();
	public static final Logger log = Logger.getLogger(UploadTest.class.getName());
	
	@BeforeMethod
	public void BeforeMethod() {
		homePage = new HomePage(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToApplication(FileReaderManager.getInstance().getConfigReader().getApplicationUserName(),
				FileReaderManager.getInstance().getConfigReader().getApplicationPassword());
		homePage.waitForSuccessfulLogin();
	}
	
	/**
	 * Test : Uploading new image and verify uploaded successfully 
	 * 		  Verify Uploaded file present on home page
	 */
	@Test
	public void verifyUploadFileTest() {
		newFolderPage = new NewFolderPage(driver);
		int initialCountOfItems = homePage.getCountOfItems();
		String fileName = "MaheshNew.jpg";
		homePage.openDropdownForNewCreate();
		homePage.clickOnUploadFileLink();
		homePage.uploadFile(fileName);
		homePage.waitForUploadComplete();
		softAssert.assertTrue(homePage.newItemAdded(fileName, initialCountOfItems),"New Image is not uploaded "+fileName);
		softAssert.assertAll();
	}
	
	@AfterMethod
	public void AfterMethod() {
		homePage.logout();
		loginPage.waitForSuccessfulLogout();
	}
}

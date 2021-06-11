package com.ui.framework.testcases;

import java.util.Date;

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

public class FolderTest extends BaseTest {

	HomePage homePage ;
	LoginPage loginPage ;
	NewFolderPage newFolderPage;
	SoftAssert softAssert = new SoftAssert();
	public static final Logger log = Logger.getLogger(FolderTest.class.getName());
	
	@BeforeMethod
	public void BeforeMethod() {
		homePage = new HomePage(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToApplication(FileReaderManager.getInstance().getConfigReader().getApplicationUserName(),
				FileReaderManager.getInstance().getConfigReader().getApplicationPassword());
		homePage.waitForSuccessfulLogin();
	}
	
	/**
	 * Test : Creating New Folder
	 *       Verifying SUccsesful banner with name of folder name
	 *       Verifying new folder in the list present
	 * 
	 */
	@Test
	public void verifyNewFolderCreateTest() {
		newFolderPage = new NewFolderPage(driver);
		int initialCountOfItems = homePage.getCountOfItems();
		String name = "Automation_" + new Date().getTime();
		homePage.openDropdownForNewCreate();
		homePage.clickOnNewFolderLink();
		newFolderPage.createNewFolderDetails(name);
		softAssert.assertTrue(homePage.isSuccessBannerDisplayedWithName(name),"Success Banner is not displayed with folder name "+name);
		softAssert.assertTrue(homePage.newItemAdded(name, initialCountOfItems),"New folder is not added "+name);
		softAssert.assertAll();
	}
	
	/**
	 * Test : Creating New Folder and deleting, check present in Trash same folder
	 *       Verify successful delete
	 *       Verifying deleted folder present in the list of Trash
	 * 
	 */
	@Test
	public void verifyDeletedFolderPlaceToTrashTest() {
		newFolderPage = new NewFolderPage(driver);
		int initialCountOfItems = homePage.getCountOfItems();
		String name = "Automation_" + new Date().getTime();
		homePage.openDropdownForNewCreate();
		homePage.clickOnNewFolderLink();
		newFolderPage.createNewFolderDetails(name);
		homePage.clickOnCreateBannerCloseButton();
		homePage.waitForItemAdded(initialCountOfItems);
		homePage.selectItemRow(0);
		homePage.trashSelectedItem();
		homePage.clickOnCreateBannerCloseButton();
		homePage.openTrash();
		softAssert.assertTrue(homePage.itemPresentInTrash(name,0),""+name);
		softAssert.assertAll();
	}
	
	@AfterMethod
	public void AfterMethod() {
		homePage.logout();
		loginPage.waitForSuccessfulLogout();
	}
}

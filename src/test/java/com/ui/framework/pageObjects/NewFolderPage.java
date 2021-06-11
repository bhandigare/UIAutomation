package com.ui.framework.pageObjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.ui.framework.basePage.BasePage;

public class NewFolderPage extends BasePage {

	public static final Logger log = Logger.getLogger(NewFolderPage.class.getName());
	
	EventFiringWebDriver driver;

	@FindBy(css = "input[name='folder-name']")
	private WebElement folderNameTextbox;

	@FindBy(css="button[data-resin-target='primarybutton']")
	private WebElement createButton;
	
	public NewFolderPage(EventFiringWebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	/**
	 * Method : Create New Folder details and submit
	 * @param folderName
	 */
	public void createNewFolderDetails(String folderName) {
		log.info("*****createNewFolderDetails******");
		sendKeys(folderNameTextbox, folderName);
		click(createButton);
	}
	
}

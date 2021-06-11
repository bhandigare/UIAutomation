package com.ui.framework.pageObjects;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ui.framework.basePage.BasePage;

public class HomePage extends BasePage {

	public static final Logger log = Logger.getLogger(HomePage.class.getName());
	
	EventFiringWebDriver driver;

	@FindBy(css = "div.PromoSlide")
	private WebElement slideCarousel;

	@FindBy(css="button.create-dropdown-menu-toggle-button")
	private WebElement dropdownOpenButton;
	
	@FindBy(css = "li[aria-label='Create a new Folder']")
	private WebElement createNewFolderLink;

	@FindBy(css = "a.item-link")
	private List<WebElement> itemsLinkList;

	@FindBy(css = "div.notification-enter-done span")
	private WebElement successBanner;

	@FindBy(css = "button.close-btn")
	private WebElement bannerCloseButton;
	
	@FindBy(css = "li.UploadMenuItem:nth-child(1)")
	private WebElement uploadFileLink;
	
	@FindBy(css = "span.avatar-initials")
	private WebElement avatarLink;
	
	@FindBy(css = "a[href='/logout']")
	private WebElement logoutLink;
	
	@FindBy(css = "div.bcu-uploads-manager-container.bcu-is-visible")
	private WebElement uploadCompleteBanner;
	
	@FindBy(css = "div.notification-enter-done span")
	private WebElement trashCompleteBanner;
	
	@FindBy(xpath = "//span[contains(text(),'Today by')]")
	private List<WebElement> itemsDateUpdatedList;
	
	@FindBy(css = "button[aria-label='Trash']")
	private WebElement trashButton;
	
	@FindBy(css = "button[data-resin-target='primarybutton']")
	private WebElement deleteOkayButton;
	
	@FindBy(css = "a[href='/trash']")
	private WebElement trashLink;
	
	@FindBy(css = "div[aria-describedby='table-header-name']")
	private List<WebElement> headerNamesOfItemsList;
	
	@FindBy(xpath = "//h1[@class='page-title']/span[text()='Trash']")
	private WebElement trashPageTitle;
	
	public HomePage(EventFiringWebDriver driver){
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	public void openDropdownForNewCreate() {
		click(dropdownOpenButton);
	}
	
	public void clickOnUploadFileLink() {
		click(uploadFileLink);
	}
	
	public void clickOnNewFolderLink() {
		click(createNewFolderLink);
	}
	
	public void clickOnAvatarLink() {
		click(avatarLink);
	}
	
	public void clickOnTrashButton() {
		click(trashButton);
	}
	
	public void openTrash() {
		click(trashLink);
		waitforElementVisibility(20, trashPageTitle);
	}
	
	public void trashSelectedItem() {
		click(trashButton);
		click(deleteOkayButton);
	}
	
	public void clickOnLogoutLink() {
		click(logoutLink);
	}
	
	public void uploadFileLink() {
		click(uploadFileLink);
	}
	
	public int getCountOfItems() {
		return itemsLinkList.size();
	}
	
	public void waitForSuccessfulLogin() {
		waitForPageLoaded();
		waitforElementVisibility(25, slideCarousel);
	}
	
	public boolean isSuccessfulLogin() {
		return avatarLink.isDisplayed();
	}
	
	public void waitForUploadComplete() {
		waitforElementInvisibility(30, driver.findElement(By.cssSelector("div.bcu-uploads-manager-container.bcu-is-visible")));
	}
	
	public void selectItemRow(int no) {
		click(itemsDateUpdatedList.get(no));
	}
	
	public void clickOnCreateBannerCloseButton() {
		click(bannerCloseButton);
	}
	
	/**
	 * Method : Logout User 
	 */
	public void logout() {
		log.info("******logout******");
		clickOnAvatarLink();
		clickOnLogoutLink();
	}
	
	/**
	 * Method : Check deleted item banner displayed
	 * @return
	 */
	public boolean isDeletedSuccessBannerDisplayed() {
		log.info("******isDeletedSuccessBannerDisplayed******");
		waitforElementVisibility(20, trashCompleteBanner);
		return true;
	}
	
	/**
	 * Method : Sendkeys method to upload file not working for this website
	 * @param filePath
	 */
	public void uploadsFile(String filePath) {
		log.info("******isSuccessBannerDisplayedWithName******");
		String path = "src/test/resources";
		File fileAbsolute = new File(path);
		String absolutePath = fileAbsolute.getAbsolutePath();
		File file = new File(absolutePath+'/'+filePath); 
		System.out.println(absolutePath+'/'+filePath);
    	String stringSelection= file.getAbsolutePath();
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("document.getElementsByClassName('hidden')[0].setAttribute('multiple', '');");
		jse.executeScript("document.getElementsByClassName('hidden')[0].setAttribute('class', '');");
		driver.findElement(By.cssSelector("input[type='file']")).sendKeys(stringSelection);
	}
	
	/**
	 * Method : CHeck success banner with Name of file/folder
	 * @param name
	 * @return
	 */
	public boolean isSuccessBannerDisplayedWithName(String name) {
		log.info("******isSuccessBannerDisplayedWithName******");
		waitforElementVisibility(20, successBanner);
		String successBannerText = getText(successBanner);
		click(bannerCloseButton);
		return successBannerText.contains(name);
	}
	
	/**
	 * Method  : Check folder/file in trash
	 * @param name
	 * @return
	 */
	public boolean itemPresentInTrash(String name, int count) {
		log.info("***itemPresentInTrash***"+headerNamesOfItemsList.get(0).getText());
		if(name.equals(headerNamesOfItemsList.get(0).getText()))
			return true;
		else 
			return false;
	}
	
	/**
	 * Method  : Check new item added or not
	 * @param name
	 * @return
	 */
	public boolean newItemAdded(String name, int count) {
		log.info("******new Item Added******");
		waitForItemAdded(count);
		List<WebElement> listOfItems = driver.findElements(By.cssSelector("a.item-link"));
		boolean flag = false;
		for (int i = 0; i < listOfItems.size(); i++) {
			if(name.equals(listOfItems.get(i).getText())) {
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	/**
	 * Method : Wait for item folder/file added 
	 */
	public void waitForItemAdded(int count) {
		log.info("Wait for Item added");
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				int newCount = driver.findElements(By.cssSelector("a.item-link")).size();
				System.out.println("Old:"+count+" New:"+newCount);
				return count + 1 == newCount;
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(expectation);
	}

}

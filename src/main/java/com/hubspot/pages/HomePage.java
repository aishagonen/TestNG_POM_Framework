package com.hubspot.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.hubspot.base.BasePage;
import com.hubspot.util.Constants;
import com.hubspot.util.ElementUtil;

public class HomePage extends BasePage {
	
	WebDriver driver;
	ElementUtil elementUtil;

	By header = By.xpath("//h1[@class='private-header__heading private-header__heading--solo']");
	By accountName = By.id("account-menu");
	By contactMainTab = By.id("nav-primary-contacts-branch");
	By contactChildTab = By.id("nav-secondary-contacts");

	
//	public HomePage(WebDriver driver) {
//		this.driver = driver;
//	}
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
	}
	
//	public String getHomePageTitle() {
//		return driver.getTitle();
//	}
	public String getHomePageTitle() {
		return elementUtil.waitForGetPageTitle(Constants.HOME_PAGE_TITLE);
	}
	
//	public String HomePageHeader() {
//		return driver.findElement(header).getText();
//	}
	public String getHomePageHeader() {
		return elementUtil.doGetText(header);
	}
	
//	public String verifyLoggedInAccountName() {
//		return driver.findElement(accountName).getText();
//	}
	public String verifyLoggedInAccountName() {
		elementUtil.waitForElementPresent(accountName);
		return elementUtil.doGetText(accountName);
	}
	
//helper method
	public void clickOnContacts(){
			elementUtil.waitForElementPresent(contactMainTab);
			elementUtil.doClick(contactMainTab);
			elementUtil.waitForElementPresent(contactChildTab);
			elementUtil.doClick(contactChildTab);
	}
	
	public ContactsPage goToContactsPage(){
		clickOnContacts();
		return new ContactsPage(driver);
	}
		
	
	
	
	
	
	
	
	
	
	
	
	
	

}

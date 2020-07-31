package com.hubspot.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.hubspot.base.BasePage;
import com.hubspot.util.Constants;
import com.hubspot.util.Credentials;
import com.hubspot.util.ElementUtil;

public class LoginPage extends BasePage{
	
	public WebDriver driver;
	public ElementUtil elementUtil;
	
	By emailId = By.id("username");
	By password = By.id("password");
	By loginBtn = By.id("loginBtn");
	By signUp = By.xpath("//i18n-string[contains(text(),'Sign up')]");
	By loginErrorText = By.cssSelector("div.private-alert__inner");
	
//	public LoginPage(WebDriver driver) {
//		this.driver = driver;
//	}
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
	}
	
//	public String getPageTitle() {
//		return driver.getTitle();
//	}
	
	public String getPageTitle() { 		// Bu versionu ElementUtil'i olusturduktan sonra kullandik.		
		return elementUtil.waitForGetPageTitle(Constants.LOGIN_PAGE_TITLE);
	}
	
	public boolean checkSignUpLink() {
		elementUtil.waitForElementVisible(signUp);
		//return driver.findElement(signUp).isDisplayed();
		return elementUtil.isElementDisplayed(signUp);
	}

	// 1.'dologin();' :  bu versionu LoginPage icin kullandik.
//	public void doLogin(String username, String pwd) { 			 
//		driver.findElement(emailId).sendKeys(username);
//		driver.findElement(password).sendKeys(pwd);
//		driver.findElement(loginBtn).click();
//	}

	// 2.'dologin();' :  bu versionu HomePage icin kullandik.	
//	public HomePage doLogin(String username, String pwd) { 			 
//		driver.findElement(emailId).sendKeys(username);
//		driver.findElement(password).sendKeys(pwd);
//		driver.findElement(loginBtn).click();
//		return new HomePage(driver); 
//	}
	
	// 3.'dologin();' :  bu versionu ElementUtil'i olusturduktan sonra kullandik.		
//	public HomePage doLogin(String username, String pwd) {
//		elementUtil.doSendKeys(emailId, username);
//		elementUtil.doSendKeys(password, pwd);
//		elementUtil.doClick(loginBtn);
//		return new HomePage(driver);
//	}
	
	// 4. 'doLogin();' : bu versionu Credential page ile kullaniyoruz.
	public HomePage doLogin(Credentials userCred) {
		elementUtil.doSendKeys(emailId, userCred.getAppUsername());
		elementUtil.doSendKeys(password, userCred.getAppPassword());
		elementUtil.doClick(loginBtn);
		return new HomePage(driver);
	}
	
	public boolean checkLoginErrorMessage() {
		return elementUtil.isElementDisplayed(loginErrorText);
	}
	
	
	
	
}

package com.hubspot.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.hubspot.base.BasePage;
import com.hubspot.util.ElementUtilPF;

public class LoginPagePF extends BasePage{

	WebDriver driver;
	ElementUtilPF elementUtilPF;
	
	
	// PageFactory Concept --> @FindBy + @CacheLookup(bunu kullanmak bize bagli)
	// Eger birden fazla locator icin kullanacaksan, '@FindAll' u kullan. 
	
	@FindBy(how=How.ID,using = "username") 	// Finds element.
	@CacheLookup	// provides faster test. manage your cache and decide which cache will run in test case and provides test for you.  				
	WebElement emailID;
	
	@FindBy(how=How.ID,using = "password") 	
	@CacheLookup					
	WebElement password;
	
	@FindBy(how=How.ID,using = "loginBtn") 	
	@CacheLookup					
	WebElement loginBtn;
	
	
	
	public LoginPagePF(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		elementUtilPF = new ElementUtilPF(driver);
	}
	
	public void doLogin(String username, String pwd) {
		elementUtilPF.waitForElementPresent(emailID);
		emailID.sendKeys(username);
		password.sendKeys(pwd);
		loginBtn.click();
	}
	
	
}	

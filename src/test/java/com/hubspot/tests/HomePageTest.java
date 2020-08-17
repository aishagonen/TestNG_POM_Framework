package com.hubspot.tests;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.hubspot.base.BasePage;
import com.hubspot.pages.HomePage;
import com.hubspot.pages.LoginPage;
import com.hubspot.util.Constants;
import com.hubspot.util.Credentials;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;


public class HomePageTest {
	
	WebDriver driver;
	Properties prop;
	BasePage basePage;
	LoginPage loginPage;
	HomePage homePage;
	Credentials userCred;
	
	
	@BeforeTest
	public void setUp() throws InterruptedException{
		basePage = new BasePage();
		prop = basePage.initialize_properties();
		driver = basePage.initialize_driver(prop);
		loginPage = new LoginPage(driver);
		//homePage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		userCred = new Credentials(prop.getProperty("username"), prop.getProperty("password"));
		homePage = loginPage.doLogin(userCred);
		Thread.sleep(7000);
	}
	
	@Test(priority=1, description="This method gets the title from Home page")
	@Description("Verify home page title") 	// comes from Allure report. 
	@Severity(SeverityLevel.NORMAL)
	public void verifyHomePageTitle() {
		String title = homePage.getHomePageTitle();
		System.out.println("Page title is: "+ title);
		Assert.assertEquals(title, Constants.HOME_PAGE_TITLE, "Title is incorrect");
	}
	
	@Test(priority=2, description="This method verifies home page header")
	@Description("Verify home page header") 	// comes from Allure report. 
	@Severity(SeverityLevel.NORMAL)
	public void verifyHomePageHeaderTest(){
		String header = homePage.getHomePageHeader();
		System.out.println("Home page header is: "+ header);
		Assert.assertEquals(header, Constants.HOME_PAGE_HEADER, "Incorrect header");
	}
	
	@Test(priority=3, description="This method verfies user account name in home page")
	@Description("Verify logged user account") 	// comes from Allure report. 
	@Severity(SeverityLevel.CRITICAL)
	public void verifyLoggedInUserInfo(){
		String accountName = homePage.verifyLoggedInAccountName();
		System.out.println("Account name is: "+ accountName);
		Assert.assertEquals(accountName,Constants.ACCOUNT_NAME);
	}
	

	@AfterTest
	public void tearDown(){
		basePage.quitBrowser();
	}
	
//	@AfterMethod
//	public void tearDown(){
//		driver.quit();
//	}
	


}

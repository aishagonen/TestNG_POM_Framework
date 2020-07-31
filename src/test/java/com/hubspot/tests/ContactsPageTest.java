package com.hubspot.tests;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.hubspot.base.BasePage;
import com.hubspot.pages.ContactsPage;
import com.hubspot.pages.HomePage;
import com.hubspot.pages.LoginPage;
import com.hubspot.util.Credentials;
import com.hubspot.util.ExcelUtil;

public class ContactsPageTest {

	public WebDriver driver;
	public Properties prop;
	public BasePage basePage;
	public LoginPage loginPage;
	public HomePage homePage;
	public ContactsPage contactsPage;
	public Credentials userCred;
	
	@BeforeMethod
	public void setUp() {
		basePage = new BasePage();
		prop = basePage.initialize_properties();
		//String browser = prop.getProperty("browser");
		driver = basePage.initialize_driver(prop);
		driver.get(prop.getProperty("url"));
		loginPage = new LoginPage(driver);
		userCred = new Credentials(prop.getProperty("username"), prop.getProperty("password"));	
		homePage = loginPage.doLogin(userCred);
		contactsPage = homePage.goToContactsPage();
	}
	
	@Test(priority=1)
	public void verifyContactsPageTitle() {
		String title = contactsPage.getContactsPageTitle();
		System.out.println(title);
		Assert.assertEquals(title, "Contacts");
	}
	
	@DataProvider
	public Object[][] getContactsTestData() {
		Object[][] data = ExcelUtil.getTestData("contacts");
		return data;
	}
	
	@Test(priority=2, dataProvider="getContactsTestData")
	public  void createContactsTest(String email, String firstname, String lastname, String jobtitle) {
		contactsPage.createNewContact(email, firstname, lastname, jobtitle);
	}
	
	
	
	
	

}

package com.hubspot.tests;

import java.util.Properties;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
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

@Epic("Epic - 101 : create login features")
@Feature("US -501  : Create test  for login on HubSpot")
public class LoginPageTest {

	public WebDriver driver;
	public Properties prop;
	public BasePage basePage;
	public LoginPage loginPage;
	public Credentials userCred;
	
	Logger log = Logger.getLogger(LoginPageTest.class);
	
	@BeforeMethod(alwaysRun= true)
	@Parameters(value= {"browser"})
	public void setUp() {
								//String browser = null;
		basePage = new BasePage();
		//driver = basePage.initialize_driver();
		prop = basePage.initialize_properties();
		
								//		if (browser.equals(null)) {
								//			browser = prop.getProperty("browser"); 
								//		} else {
								//			browser /*browserName*/ = browser;
								//		}
		
		String browser = prop.getProperty("browser");
		//driver  = basePage.initialize_driver(browser);
		driver = basePage.initialize_driver(prop);
		driver.get(prop.getProperty("url"));
		log.info("url is launched "+ prop.getProperty("url"));
		loginPage = new LoginPage(driver);
		userCred = new Credentials(prop.getProperty("username"), prop.getProperty("password"));	
	}	
	
	
	@Test(priority=2, enabled=true, groups="sanity", description="Login test is using valid username and password.")
	@Description("Login page credentials")
	@Severity(SeverityLevel.CRITICAL)
	public void loginTest1() {
		//loginPage.doLogin("kralshakirr@gmail.com", "Lompenlompen1");
		//loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		HomePage homePage = loginPage.doLogin(userCred);
		String accountName = homePage.verifyLoggedInAccountName();
		System.out.println("Logged in account name: " + accountName);
		Assert.assertEquals(accountName, prop.getProperty("accountname"));
	}
	
//	@Test(priority=3, enabled=false, description="Login test is using invalid username and password.")
//	public void loginTest2() {
//		//loginPage.doLogin("kralshakir@gmail.com", "lompenlompen");
//		loginPage.doLogin(prop.getProperty("incorrectuser"), prop.getProperty("password"));
//	}
	
//	@Test(priority=4, enabled=false, description="Login test is using invalid username and invalid password.")
//	public void loginTest3() {
//		//loginPage.doLogin("kralshakir@gmail.com", "lompenlompen");
//		//loginPage.doLogin(prop.getProperty("incorrectuser"), prop.getProperty("incorrectpass"));
//		HomePage homePage = loginPage.doLogin(userCred);
//		String accountName = homePage.verifyLoggedInAccountName();
//		System.out.println("Logged in account name: " + accountName);
//		Assert.assertEquals(accountName, prop.getProperty("accountname"));
//	}
	
	@Test(priority=1, enabled=true, groups="sanity", description="Get title from HubSpot login page.")
	@Description("Verify login page title")
	@Severity(SeverityLevel.NORMAL)
	public void getTitle() {
		log.info("starting --------------->>>>> verifyLoginPageTest");
		String title = loginPage.getPageTitle();
		System.out.println(title);
		//Assert.assertEquals(title, "HubSpot Login");
		//Assert.assertEquals(title, Constants.LOGIN_PAGE_TITLE);
		Assert.assertEquals(title, Constants.LOGIN_PAGE_TITLE, "Title is in-correct"); // Just in case, if test case is failed, you can add message. 
		log.info("ending --------------->>>>> verifyLoginPageTest");
		log.warn("some warning");
		log.error("some error");
		log.fatal("fatal error");
	}
	
	@Test(priority=5, enabled=true, groups="regression", description="Verify signUpLink is displayed or not.")
	public void verifySignUpLinkTest() {
		Assert.assertTrue(loginPage.checkSignUpLink());
	}
	
	@DataProvider
	public Object [][] getLoginInvalidData() {
		Object data[][] = {{"bill@gmail.com", "test123"},
							{"Jimmy@gmail.com", " "},
							{" ", "test12345"},
				            {"yummy", "yummy"},
				            {" ", " "}};
		return data;	
	}
		
	@Test(priority=6, dataProvider="getLoginInvalidData", enabled=true)	// TestNG Annotations 
	public void login_invalidTestCase(String username, String password) {
		userCred.setAppUsername(username);
		userCred.setAppPassword(password);
		loginPage.doLogin(userCred);
		Assert.assertTrue(loginPage.checkLoginErrorMessage());	
	}
	
	@AfterMethod(alwaysRun= true)
	public void tearDown() {
		//driver.quit();
		basePage.quitBrowser();
	}
	
//	@AfterTest
//	public void tearDown() {
//		basePage.quitBrowser();
//	}
		
}

/*
	- TestNG execute the methods according the alphabetical order if you don't add 'priority' keyword to method description. 
	- If you add 'enabled=false' keyword to description of method so program doesn't execute that one. 
	- If you want to execute just one method, so go that method and click right > chose Run As. 
	
	- Assertion concept is used in TestNG. Javada if-else kullaniyoruz. TestNG'de Assertion kullan. 
	- For in case of the test case is failed, you can add message in Assertion. 
	
	
*/











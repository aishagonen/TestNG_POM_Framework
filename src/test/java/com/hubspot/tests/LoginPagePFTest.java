package com.hubspot.tests;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import com.hubspot.base.BasePage;
import com.hubspot.pages.LoginPagePF;

public class LoginPagePFTest {

	public WebDriver driver;
	public Properties prop;
	public BasePage basePage;
	public LoginPagePF loginPagePF;

	public void setUp() {
		basePage = new BasePage();
		prop = basePage.initialize_properties();
		driver = basePage.initialize_driver(prop);
		loginPagePF = new LoginPagePF(driver);
	}
	
	@Test
	public void loginTest() {
		loginPagePF.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@AfterTest
	public void tearDown() {
		basePage.quitBrowser();
	}
	
	
	
	
	
	
}

package com.hubspot.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.omg.Messaging.SyncScopeHelper;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BasePage {

	public WebDriver driver;
	public Properties prop;
	public static boolean flash;
	public OptionsManager optionsManager;
	
//	public WebDriver initialize_driver(Properties prop) { 	// bu baslik, 'public WebDriver initialize_driver()' idi.prop'u yazdiktan sonra degistirdik. 
//		 
//		String browser = prop.getProperty("browser");		// burasi,'String browser = "chrome";'idi. prop'tan sonra degistirdik. 
//		if (browser.equalsIgnoreCase("chrome")) {
//			WebDriverManager.chromedriver().setup();
//			driver = new ChromeDriver();
//		} else if (browser.equalsIgnoreCase("firefox")){
//			WebDriverManager.firefoxdriver().setup();
//			driver = new FirefoxDriver();
//		}
//		driver.manage().window().fullscreen();
//		driver.manage().deleteAllCookies();
//		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
//		driver.get("https://app.hubspot.com/login");	// burayi prop'tan sonra,'driver.get(prop.getProperty("url"));' olarak degistirdik.
//		try {
//			Thread.sleep(9000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		return driver;
//	}
	
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	
	public static synchronized WebDriver getDriver() {
		return tlDriver.get();
	}
	
	public WebDriver initialize_driver(Properties prop) { 		// headless modda bu methodu boyle kullandik:
		String browser = prop.getProperty("browser");
		//String headless = prop.getProperty("headless");
		
		flash = prop.getProperty("flash").equals("yes") ? true : false;
		
		System.out.println("Browser name is: " + browser);
		optionsManager = new OptionsManager(prop);
		
		if (browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			//System.setProperty("webdriver.chrome.driver", "/Users/aishagonen/Documents/Drivers/chromedriver"); 
			//if(headless.equals("yes")){
//			if(prop.getProperty("headless").equals("yes")){
//				ChromeOptions co = new ChromeOptions();
//				co.addArguments("--headless");
//				driver = new ChromeDriver(co);
//			} else{
//				driver = new ChromeDriver();
//			} 
			
			//driver = new ChromeDriver(optionsManager.getChromeOptions());
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
		} 
		
		else if (browser.equalsIgnoreCase("firefox")){
			WebDriverManager.firefoxdriver().setup();
			//System.setProperty("webdriver.gecko.driver","/Users/aishagonen/Documents/Drivers/geckodriver");
			//if(headless.equals("yes")){
//			if(prop.getProperty("headless").equals("yes")){
//				FirefoxOptions fo = new FirefoxOptions();
//				fo.addArguments("--headless");
//				driver = new FirefoxDriver(fo);
//			} else{
//				driver = new FirefoxDriver();
//			}
			
			//driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));

			
		} 
		
		else if (browser.equalsIgnoreCase("safari")) {
			WebDriverManager.getInstance(SafariDriver.class).setup();
			//driver = new SafariDriver();
			tlDriver.set(new SafariDriver());
		} else {
			System.out.println("Browser  name: "+ browser  + "is not found.");
		}
		  
		
//		driver.manage().window().fullscreen();
//		driver.manage().deleteAllCookies();
//		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
//		driver.get(prop.getProperty("url"));	
		
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		return getDriver();
		
//		try {
//			Thread.sleep(9000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		return driver;
	}
	
	
	
	public Properties initialize_properties(){
		prop = new Properties();
		//String path = "/Users/aishagonen/eclipse-workspacep/TestNGFramework/src/main/java/config/hubspot/config/config.properties";
		String path = null;
		String env = null;
		
		try {
			env = System.getProperty("env");
			if (env.equals("qa")) {
				path = "./src/main/java/config/hubspot/config/confiig.qa.properties";
			} else if (env.equals("stg")) {
					path = "./src/main/java/config/hubspot/config/config.stg.properties"; 
			}
		} catch (Exception e) {
			path = "./src/main/java/config/hubspot/config/config.properties";
		}
		
		try {
			FileInputStream ip = new FileInputStream(path);
			prop.load(ip);
		} catch (FileNotFoundException e) {
			System.out.println("Some issue happend with config properties.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;	
	}
	
	
	/*
	 * Screenshot
	 * 
	 * @return
	 */
	public String getScreenshot() {
		File src = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshots/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);
		try {
			FileUtils.copyFile(src, destination);
		} catch (IOException e) {
			System.err.println("screenshot captured failed...");
		}
		return path;
	}
	
	
	public void quitBrowser() {
		try {
			driver.quit();
		} catch (Exception e) {
			System.out.println("Some exception occured while quitting the browser.");
		}
	}
	
	public void closeBrowser() {
		try {
			driver.close();
		} catch (Exception e) {
			System.out.println("Some exception occured while closing the browser.");
		}
	}
	
	
	
	
}

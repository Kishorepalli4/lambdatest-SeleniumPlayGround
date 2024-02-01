package com.lambdatest.tests;

import java.io.File;
import java.io.IOException;
import java.time.Instant;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.google.common.io.Files;
import com.lambdatest.pages.CheckboxDemo;
import com.lambdatest.pages.HomePage;
import com.lambdatest.pages.JavaScriptAlertPage;

public class SimpleTestScenario{
	
	private String homePageTitle;
	private String expectedHomePageTitle= "LambdaTest";
	private SoftAssert softAssert;
	private WebDriver driver;
	private String lambdaURL = "https://www.lambdatest.com/selenium-playground/";
	private HomePage homePage;
	private CheckboxDemo checkboxDemo;
	private JavaScriptAlertPage javaScriptAlertPage;
	
	@Parameters({"browserName"})
	@BeforeMethod
	public void launchBrowser(String browserName) {
		softAssert = new SoftAssert();
		if (browserName.equalsIgnoreCase("Chrome")){
			driver = new ChromeDriver();
		}
		else if (browserName.equalsIgnoreCase("Firefox")) {
			driver = new FirefoxDriver();
					
		}
		else if (browserName.equalsIgnoreCase("MicrosoftEdge")) {
			driver = new EdgeDriver();
		}
		else {
			Assert.fail(browserName + " is not valid or not handled in the test script");
		}
		
		driver.manage().window().maximize();
		driver.get(lambdaURL);	
	}
	
	@Test(timeOut = 20000)
	public void validateTitleOfHomePage() {
		homePage= new HomePage(driver);
		homePage.waitForLogoToLoad();
		homePageTitle= driver.getTitle();
		softAssert.assertEquals(homePageTitle, expectedHomePageTitle, "Expected Title is different from Actual title \n"
				+ "Expected is: " + expectedHomePageTitle + "\nActual is: " + homePageTitle);
		softAssert.assertAll();	
	}
	
	
	
	@Test(timeOut = 20000)
	public void validateCheckBoxSelection() {
		homePage= new HomePage(driver);
		homePage.waitForLogoToLoad();
		homePage.clickCheckboxDemo();
		checkboxDemo = new CheckboxDemo(driver);
		checkboxDemo.waitForCheckboxDemoPageLoad();
		checkboxDemo.clickCheckboxUnderSingleCheckboxDemo();
		softAssert.assertTrue(checkboxDemo.isSingleCheckboxDemoCheckBoxSelected(), 
									"Checkbox is not selected although ticked");
		checkboxDemo.clickCheckboxUnderSingleCheckboxDemo();
		softAssert.assertTrue(!checkboxDemo.isSingleCheckboxDemoCheckBoxSelected(), 
									"Checkbox is selected although unticked");
		softAssert.assertAll();
		
	}
	
	
	@Test(timeOut = 20000)
	public void validateJavaScriptAlertBox() {
		homePage= new HomePage(driver);
		homePage.waitForLogoToLoad();
		homePage.clickJavaScriptAlertsXpath();
		javaScriptAlertPage = new JavaScriptAlertPage(driver);
		String expectedAlertMessage = "I am an alert box!";
		String actualAlertMessage = javaScriptAlertPage.clickJavaScriptAlertAndGetMessage();
		softAssert.assertEquals(expectedAlertMessage, actualAlertMessage,"Alert messages doesn't match.\n"
				+ "Expected: " + expectedAlertMessage + "\n Actual: " + actualAlertMessage  );
		softAssert.assertAll();
		
	}
	
	
	@AfterMethod
	public void takeScreenshot(ITestResult testResult) {
		if(ITestResult.FAILURE == testResult.getStatus()) {
			TakesScreenshot screenshot = (TakesScreenshot) driver;
			File source = screenshot.getScreenshotAs(OutputType.FILE);
			
			Instant timestamp = Instant.now();
			String baseDir = System.getProperty("user.dir");
	        String screenshotsDir = baseDir + File.separator + "test-output" + File.separator + "screenshots";
	        String filePath = screenshotsDir + File.separator + "Error_screen_"+ timestamp.toString().replaceAll(":","-")
	        			+ ".png";
	        System.out.println(filePath);
	        
			File destination = new File(filePath);
			
			try {
				Files.copy(source, destination);
			}
			catch(IOException e ) {
				System.out.println("I/O exception when copying file");
				e.printStackTrace();
				
			}
		}	
	}
	
	@AfterMethod
	public void closeBrowser() {
		homePage = null;
		checkboxDemo = null;
		javaScriptAlertPage = null;
		driver.quit();
	}
	
	
}

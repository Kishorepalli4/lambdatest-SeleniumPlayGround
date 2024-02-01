package com.lambdatest.tests;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
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

public class TestScenario{
	
	private String homePageTitle;
	private String expectedHomePageTitle= "LambdaTest";
	private SoftAssert softAssert;
	private WebDriver driver;
	private String lambdaURL = "https://www.lambdatest.com/selenium-playground/";
	private HomePage homePage;
	private CheckboxDemo checkboxDemo;
	private JavaScriptAlertPage javaScriptAlertPage;
	private String baseDir = System.getProperty("user.dir");
	
	@Parameters({"browser","browserVersion","platform","runOnGrid"})
	@BeforeMethod
	public void setUp(String browser, String browserVersion, String platform, boolean runOnGrid) throws MalformedURLException {
      
        if (runOnGrid) {
            // DesiredCapabilities for RemoteWebDriver
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("browserName", browser);
            capabilities.setCapability("browserVersion", browserVersion);
            capabilities.setCapability("platformName", platform);

            // Set Selenium Grid URL here
            URL gridUrl = new URL("http://your-selenium-grid-url/wd/hub");
            driver = new RemoteWebDriver(gridUrl, capabilities);
        } else {
            // Local WebDriver setup
            switch (browser.toLowerCase()) {
                case "chrome":
                    System.setProperty("webdriver.chrome.driver", baseDir + File.separator + "drivers" 
                    				+ File.separator + "chromedriverV88win32.exe");
                    driver = new ChromeDriver();
                    break;
                case "firefox":
                    System.setProperty("webdriver.gecko.driver", "path/to/geckodriver");
                    driver = new FirefoxDriver();
                    break;
                case "edge":
                    System.setProperty("webdriver.edge.driver", "path/to/edgedriver");
                    driver = new EdgeDriver();
                    break;
                case "internet explorer":
                    System.setProperty("webdriver.ie.driver", "path/to/iedriver");
                    driver = new InternetExplorerDriver();
                    break;
                default:
                    throw new IllegalArgumentException("Browser \"" + browser + "\" isn't supported.");
            }
        }
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
		homePage.clickjavaScriptAlertsXpath();
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

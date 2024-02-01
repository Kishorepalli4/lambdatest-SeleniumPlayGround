package com.lambdatest.pages;

import java.time.Duration;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
	
	private WebDriver driver;
	private WebDriverWait wait;
	private String logoXpath="//img[@alt='Logo']";
	private String checkboxDemoXpath = "//a[.='Checkbox Demo']";
	private String javaScriptAlertsXpath= "//a[.='Javascript Alerts']";
	//private String  

	
	public HomePage(WebDriver driver) {
		this.driver= driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	public void waitForLogoToLoad() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(logoXpath)));
	}
	
	
	public void clickCheckboxDemo() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(checkboxDemoXpath)));
		driver.findElement(By.xpath(checkboxDemoXpath)).click();		
	}
	
	
	public void clickjavaScriptAlertsXpath() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(javaScriptAlertsXpath)));
		driver.findElement(By.xpath(javaScriptAlertsXpath)).click();
	}
	
	

}

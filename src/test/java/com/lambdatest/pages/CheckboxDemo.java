package com.lambdatest.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class CheckboxDemo {
	private WebDriver driver;
	private WebDriverWait wait;
	private String checkboxDemoTitleXpath= "//h1[.='Checkbox Demo']";
	private String checkboxUnderSingleCheckboxDemoID ="isAgeSelected";
	
	public CheckboxDemo(WebDriver driver) {
		this.driver= driver;
	}
	
	public void waitForCheckboxDemoPageLoad() {
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(checkboxDemoTitleXpath)));
		
	}
	
	public void clickCheckboxUnderSingleCheckboxDemo() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(checkboxUnderSingleCheckboxDemoID)));
		driver.findElement(By.id(checkboxUnderSingleCheckboxDemoID)).click();
		//Reporter.log("Checkbox under single Checkbox Demo is clicked",1);
		
	}
	
	public boolean isSingleCheckboxDemoCheckBoxSelected() {
		System.out.println("Checkbox selected = " + 
				driver.findElement(By.id(checkboxUnderSingleCheckboxDemoID)).isSelected());
		return driver.findElement(By.id(checkboxUnderSingleCheckboxDemoID)).isSelected();
	}
	

}

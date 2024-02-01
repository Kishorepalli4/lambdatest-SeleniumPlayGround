package com.lambdatest.pages;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class JavaScriptAlertPage {
	
	private WebDriver driver;
	private String javaScriptAlertsClickMeButtonXpath ="(//p[text()='JavaScript Alerts'])//button[.='Click Me']";
	private WebDriverWait wait;

	public JavaScriptAlertPage(WebDriver driver) {
		this.driver= driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	
	public String clickJavaScriptAlertAndGetMessage() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(javaScriptAlertsClickMeButtonXpath)));
		driver.findElement(By.xpath(javaScriptAlertsClickMeButtonXpath)).click();
		Alert alert = driver.switchTo().alert();
		String alertMessage = alert.getText();
		alert.accept();;
		return alertMessage;
		
	}

}

package com.lambdatest.pages;

import com.lambdatest.tests.TestScenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.Instant;

public class HomePage {
    private static final Logger logger = LogManager.getLogger(TestScenario.class.getName());
    private final WebDriver driver;
    private final WebDriverWait wait;


    public HomePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void waitForLogoToLoad() {
        Instant start = Instant.now();
        // Xpath stored as local variables since single usage detected
        String logoXpath = "//img[@alt='Logo']";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(logoXpath)));
        Instant stop = Instant.now();
        long timeElapsed = Duration.between(start, stop).toMillis();
        logger.debug(String.format("Time elapsed for Logo to load: [%s]", timeElapsed));
    }


    public void clickCheckboxDemo() {
        // Xpath stored as local variables since single usage detected
        String checkboxDemoXpath = "//a[.='Checkbox Demo']";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(checkboxDemoXpath)));
        driver.findElement(By.xpath(checkboxDemoXpath)).click();
    }


    public void clickJavaScriptAlertsXpath() {
        // Xpath stored as local variables since single usage detected
        String javaScriptAlertsXpath = "//a[.='Javascript Alerts']";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(javaScriptAlertsXpath)));
        driver.findElement(By.xpath(javaScriptAlertsXpath)).click();
    }


}

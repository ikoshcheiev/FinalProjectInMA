package org.example.tests;

import io.qameta.allure.Attachment;
import org.example.webDriverManager.Driver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static org.example.webDriverManager.Driver.getDriver;

public class BaseTest {

    public static final String URL = "http://magento.mainacad.com/";

    @BeforeMethod(alwaysRun = true)
    public void webDriverManager() {
        getDriver().get(URL);
    }

    @AfterMethod(alwaysRun = true)
    public void closeDriver(ITestResult result) {
        if (!result.isSuccess()) {
            screenCapture();
        }
        Driver.killDriver();
    }

    @Attachment(type = "image/png")
    private byte[] screenCapture() {
        return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
    }
}

package org.example.pages;

import org.openqa.selenium.By;
import org.testng.Assert;

import static org.example.webDriverManager.Driver.getDriver;

public class MainPage extends AbstractPage {

    private static final By MAIN_PAGE_IDENTIFICATION_TEXT = By.cssSelector("h2.subtitle");
    private static final By MAIN_PAGE = By.cssSelector(".cms-home");


    public MainPage() {
        Assert.assertEquals(getDriver().findElement(MAIN_PAGE_IDENTIFICATION_TEXT).getText(), "NEW PRODUCTS");
    }

    public MainPage isOnThisPage() {
        Assert.assertTrue(isOnThisPage(MAIN_PAGE), "Not " + this.getClass().toString() + "is opened");
        return this;
    }
}

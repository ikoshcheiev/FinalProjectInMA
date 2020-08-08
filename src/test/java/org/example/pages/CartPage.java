package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.HashMap;

import static org.example.webDriverManager.Driver.getDriver;

public class CartPage extends AbstractPage {

    private static final By CART_TITLE = By.cssSelector("");
    private static final By CART_PAGE = By.cssSelector("");
    private static final String EXPECTED_TEXT = "My cart";

    public CartPage() {
        Assert.assertTrue(getDriver().findElement(CART_TITLE).getText().equalsIgnoreCase(EXPECTED_TEXT),
                "Appeared title is " + getDriver().findElement(CART_TITLE).getText() +
                        ", when expected title is " + EXPECTED_TEXT);
    }

    public CartPage isOnThisPage() {
        Assert.assertTrue(isOnThisPage(CART_PAGE), "Not " + this.getClass().toString() + "is opened");
        return this;
    }
}

package org.example.pages;

import org.openqa.selenium.By;
import org.testng.Assert;

import static org.example.webDriverManager.Driver.getDriver;

public class ProductPage extends AbstractPage{
    private static final String EXPECTED_TEXT = "CREATE AN ACCOUNT";
    private static final By PRODUCT_PAGE_TITLE = By.cssSelector("");


    public ProductPage() {
        Assert.assertTrue(getDriver().findElement(PRODUCT_PAGE_TITLE).getText().equalsIgnoreCase(EXPECTED_TEXT),
                "Appeared title is " + getDriver().findElement(PRODUCT_PAGE_TITLE).getText() +
                        ", when expected title is " + EXPECTED_TEXT);
    }
}

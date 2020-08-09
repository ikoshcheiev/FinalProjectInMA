package org.example.pages.homeAndDecor;

import org.example.pages.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import static org.example.WebDriverUtils.scrollToTheElement;
import static org.example.webDriverManager.Driver.getDriver;

public class HomeAndDecorPage extends AbstractPage {

    private static final By SUBSCRIBE_BUTTON = By.cssSelector(".#newsletter-validate-detail button");
    private static final By HOME_AND_DECOR_TITLE = By.cssSelector(".category-title");
    private static final By ELECTRONICS_CATEGORY = By.cssSelector("img[alt='Electronics']");

    public HomeAndDecorPage() {
        Assert.assertEquals(getDriver().findElement(HOME_AND_DECOR_TITLE).getText(), "HOME & DECOR");
    }

    public ElectronicsPage goToElectronicsPage()  {
        WebElement electronics = getDriver().findElement(ELECTRONICS_CATEGORY);
        scrollToTheElement(getDriver(), electronics).click();

        return new ElectronicsPage();
    }

}

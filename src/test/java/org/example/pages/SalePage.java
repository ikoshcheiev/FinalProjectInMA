package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.List;

import static org.example.webDriverManager.Driver.getDriver;

public class SalePage extends AbstractPage {
    private static final By SALE_PAGE_TITLE = By.cssSelector(".category-title");
    private static final String EXPECTED_TEXT = "SALE";
    private static final By VIEW_AS_GRID_VIEW = By.cssSelector(".grid");
    private static final By SHOW_GRID_AMOUNT_SELECT_ELEMENT = By.cssSelector("select[title='Results per page']");
    private static final By SALE_PRICE_OF_ELEMENTS = By.cssSelector("[id^='product-price']");
    private static final By OLD_PRICE_OF_ELEMENTS = By.cssSelector("[id^='old-price']");
    private static final By PRICEBOX_OF_ELEMENTS = By.cssSelector(".product-info .price-box");

    public SalePage() {
        Assert.assertTrue(getDriver().findElement(SALE_PAGE_TITLE).getText().equalsIgnoreCase(EXPECTED_TEXT),
                "Appeared title is " + getDriver().findElement(SALE_PAGE_TITLE).getText() +
                        ", when expected title is " + EXPECTED_TEXT);
    }

    public SalePage selectShowAsGridView() {
        getDriver().findElement(VIEW_AS_GRID_VIEW).click();
        return this;
    }

    public SalePage setGridResultsToShowOnPage(AmountOfGridItemsOnThePage amount) {
        Select s = new Select(getDriver().findElement(SHOW_GRID_AMOUNT_SELECT_ELEMENT));
        s.selectByVisibleText(amount.toString());
        return this;
    }

    public SalePage verifyOldPriceIsHigher() {
        List<WebElement> priceBoxOfElements = getDriver().findElements(PRICEBOX_OF_ELEMENTS);
        List<WebElement> oldPriceOfElements = getDriver().findElements(OLD_PRICE_OF_ELEMENTS);
        List<WebElement> salePriceOfElements = getDriver().findElements(SALE_PRICE_OF_ELEMENTS);
        for (int i = 0; i < priceBoxOfElements.size(); i++) {
            //Works for currency symbol before price
            Assert.assertTrue(Double.parseDouble(oldPriceOfElements.get(i).getText().substring(1).trim()) >
                    Double.parseDouble(salePriceOfElements.get(i).getText().substring(1).trim()),
                    "Sale price is not less than the old one");
        }
        return this;
    }
}

package org.example.pages.homeAndDecor;

import org.example.pages.AbstractPage;
import org.example.pages.ProductPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;

import static org.example.RandomUtils.getRandomElement;
import static org.example.WebDriverUtils.scrollToTheElement;
import static org.example.webDriverManager.Driver.getDriver;

public class ElectronicsPage extends AbstractPage {

    private static final By SHOW_AMOUNT = By.cssSelector(".category-products > .toolbar > .pager > .count-container > .limiter > select[title='Results per page']");
    private static final By ELECTRONICS_TITLE = By.xpath("//html[@id='top']/body/div[@class='wrapper']/div[@class='page']//h1[.='Electronics']");
    private static final By LIST = By.linkText("List");
    private static final By COUNTER_AMOUNT = By.cssSelector(".category-products > .toolbar > .pager > .count-container > .amount.amount--no-pages > strong");
    private static final By ON_PAGE_AMOUNT = By.cssSelector("ol#products-list > li");
    private static final By PAGES_AMOUNT = By.cssSelector(".category-products > .toolbar > .pager > .pages > ol > li");
    private static final By NEXT_PAGE_BTN = By.linkText("NEXT");
    private static final By SORT_OPTION = By.cssSelector(".category-products > .toolbar > .sorter > .sort-by > select[title='Sort By']");
    private static final By PRICE_FILTER = By.cssSelector(".sidebar dd ol li .price");
    //Check if filter was applied - need to check with contains or extract into list of doubles
    // private static final By PRICE_FILTER_SELECTED = By.cssSelector(".sidebar .currently ol li .value");
    private static final By PRICE_OF_ELEMENTS = By.cssSelector(".col-main ol li .regular-price, ol li .price-to");
    private static final By PRODUCT_TITLE_IN_LIST = By.cssSelector("ol#products-list > li .product-name");

    public ElectronicsPage() {
        Assert.assertEquals(getDriver().findElement(ELECTRONICS_TITLE).getText(), "ELECTRONICS");
    }

//    public String chooseRandomItemInList() {
//        List<WebElement> listOfElements = new WebDriverWait(getDriver(), 10)
//                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(PRODUCT_TITLE_IN_LIST));
//        setRandomItem(getRandomElement(listOfElements));
//        return getRandomElement(listOfElements).getText();
//    }
//
//    public ProductPage openRandomItem(){
//        WebElement randomItem = getRandomItem();
//        scrollToTheElement(getDriver(), randomItem).click();
//        return new ProductPage();
//    }
}

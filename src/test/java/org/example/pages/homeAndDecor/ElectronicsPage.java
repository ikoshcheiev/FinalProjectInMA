package org.example.pages.homeAndDecor;

import org.example.pages.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;

import static org.example.WebDriverUtils.scrollToTheElement;
import static org.example.webDriverManager.Driver.getDriver;

public class ElectronicsPage extends AbstractPage {

    private static final By SHOW_AMOUNT_SELECT_ELEMENT = By.cssSelector("select[title='Results per page']");
    private static final By ELECTRONICS_PAGE_TITLE = By.cssSelector(".page-title");
    private static final By SHOW_AS_LIST_BUTTON = By.linkText("List");
    private static final By PRODUCTS_ON_PAGE_AMOUNT_TEXT = By.cssSelector(".amount");
    private static final By PAGES_AMOUNT = By.cssSelector(".category-products > .toolbar > .pager > .pages > ol > li");
    private static final By NEXT_PAGE_BTN = By.linkText("NEXT");
    private static final By SORT_OPTION = By.cssSelector("select[title='Sort By']");
    private static final By PRICE_FILTER = By.cssSelector(".sidebar dd ol li .price");
    //Check if filter was applied - need to check with contains or extract into list of doubles
    // private static final By PRICE_FILTER_SELECTED = By.cssSelector(".sidebar .currently ol li .value");
    private static final By PRICE_OF_ELEMENTS = By.cssSelector(".regular-price .price, .price-to .price");
    private static final By PRODUCT_TITLE_IN_LIST = By.cssSelector("ol#products-list > li .product-name");
    private static final By PRODUCT_NAME_ELEMENT = By.cssSelector(".product-name");

    private static final By PRODUCT_IN_LIST = By.cssSelector("ol#products-list > li");
    private static final By PRODUCT_NAME_ELEMENT_FROM_PRODUCT_IN_LIST = By.cssSelector(".product-name");
    private static final By ADD_TO_WISHLIST_LINK = By.cssSelector(".link-wishlist");
    private static final String EXPECTED_TEXT = "ELECTRONICS";

    public ElectronicsPage() {
        Assert.assertTrue(getDriver().findElement(ELECTRONICS_PAGE_TITLE).getText().equalsIgnoreCase(EXPECTED_TEXT),
                "Appeared title is " + getDriver().findElement(ELECTRONICS_PAGE_TITLE).getText() +
                        ", when expected title is " + EXPECTED_TEXT);
    }

    public ElectronicsPage selectShowAsList() {
        getDriver().findElement(SHOW_AS_LIST_BUTTON).click();
        return this;
    }

    public ElectronicsPage setListResultsToShowOnPage(AmountOfListItemsOnThePage i) {
        Select s = new Select(getDriver().findElement(SHOW_AMOUNT_SELECT_ELEMENT));
        s.selectByVisibleText(i.toString());
        return this;
    }

    public ElectronicsPage checkItemsCountOnPage() {
        new WebDriverWait(getDriver(), 10)
                .until(ExpectedConditions.visibilityOf(getDriver().findElement(PRODUCTS_ON_PAGE_AMOUNT_TEXT)));
        int amountOfProductNames = getDriver().findElements(PRODUCT_NAME_ELEMENT).size();

        Assert.assertTrue(getDriver().findElement(PRODUCTS_ON_PAGE_AMOUNT_TEXT).getText().contains(String.valueOf(amountOfProductNames)),
                "Amount of present items on the page is " + amountOfProductNames + ", but counter shows " +
                        getDriver().findElement(PRODUCTS_ON_PAGE_AMOUNT_TEXT).getText());
        return this;
    }

    public ElectronicsPage checkItemsAmountOnPageAndInSelect(AmountOfListItemsOnThePage valueInSelect) {
        new WebDriverWait(getDriver(), 10)
                .until(ExpectedConditions.visibilityOf(getDriver().findElement(PRODUCTS_ON_PAGE_AMOUNT_TEXT)));
        int amountOfProductNames = getDriver().findElements(PRODUCT_NAME_ELEMENT).size();

        Assert.assertTrue(getDriver().findElement(PRODUCTS_ON_PAGE_AMOUNT_TEXT).getText().contains(String.valueOf(amountOfProductNames)),
                "Amount of present items on the page is " + amountOfProductNames + ", but counter shows " +
                        getDriver().findElement(PRODUCTS_ON_PAGE_AMOUNT_TEXT).getText());
        Assert.assertTrue(amountOfProductNames <= Integer.parseInt(valueInSelect.toString()),
                "Amount of present items on the page is " + amountOfProductNames +
                        " is not less/equal to amount chosen in Select dropdown " +
                        valueInSelect);
        return this;
    }

    public ElectronicsPage setSortBy(String option) {
        Select sortBy = new Select((getDriver().findElement(SORT_OPTION)));
        sortBy.selectByVisibleText(option);
        return this;
    }

    public ElectronicsPage checkSortedByPrice() {
        List<WebElement> priceOfElements = getDriver().findElements(PRICE_OF_ELEMENTS);
        int amountOfPrice = priceOfElements.size();

        for (int i = 0; i < amountOfPrice - 1; i++) {
            //Works for currency symbol before price
            double previous = Double.parseDouble(priceOfElements.get(i).getText().substring(1).trim());
            double next = Double.parseDouble(priceOfElements.get(i + 1).getText().substring(1).trim());
            Assert.assertTrue(previous <= next, "Previous product price is not less/equal to next one:" + next);
        }
        return this;
    }

    public ElectronicsPage setFilterByPriceRangeFrom(Double fromPrice, Double toPrice) {
        List<WebElement> priceRange = getDriver().findElements(PRICE_FILTER);
        double priceMin = 0.0;
        double priceMax = 0.0;
        for(int i = 0; i < priceRange.size(); i++){
            //Works for currency symbol before price
            priceMin = Double.parseDouble(priceRange.get(i).getText().substring(1).trim());
            if(priceMin == fromPrice && i+1 < priceRange.size()){
                priceMax = Double.parseDouble(priceRange.get(i+1).getText().substring(1).trim());
                break;
            }

        }
        Assert.assertTrue(priceMin == fromPrice, "Minimum price range value isn't found in filters");
        Assert.assertTrue(priceMax == toPrice, "Maximum price range value isn't found in filters");
        getDriver().findElement(PRICE_FILTER).click();
        return this;
    }

    public ElectronicsPage checkFilteredProductPrices(double fromPrice, double toPrice) {
        List<WebElement> priceOfElements = getDriver().findElements(PRICE_OF_ELEMENTS);
        for (int i = 0; i < priceOfElements.size(); i=i+2) {
            //Works for currency symbol before price
            Assert.assertTrue(Double.parseDouble(priceOfElements.get(i).getText().substring(1).trim()) >= fromPrice, "Price of the product is not in the selected filter range");
            Assert.assertTrue(Double.parseDouble(priceOfElements.get(i + 1).getText().substring(1).trim()) <= toPrice, "Price of the product is not in the selected filter range");
        }
        return this;
    }
    public String addRandomItemInWishList() {
        List<WebElement> listOfElements = new WebDriverWait(getDriver(), 10)
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(PRODUCT_IN_LIST));
        WebElement randomProductItem = listOfElements.get((int)(Math.random()*(listOfElements.size()-1)));
        String productItemName = randomProductItem.findElement(PRODUCT_NAME_ELEMENT_FROM_PRODUCT_IN_LIST).getText();

        scrollToTheElement(getDriver(), randomProductItem);
        randomProductItem.findElement(ADD_TO_WISHLIST_LINK).click();
        return productItemName;
    }
}

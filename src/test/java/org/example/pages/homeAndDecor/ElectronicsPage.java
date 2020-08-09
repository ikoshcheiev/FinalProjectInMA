package org.example.pages.homeAndDecor;

import org.example.model.Product;
import org.example.pages.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.example.StringUtils.getPriceAsDouble;
import static org.example.WebDriverUtils.scrollToTheElement;
import static org.example.webDriverManager.Driver.getDriver;

public class ElectronicsPage extends AbstractPage {

    private static final By ELECTRONICS_PAGE_TITLE = By.cssSelector(".page-title");
    private static final By SHOW_AS_LIST_BUTTON = By.cssSelector(".list");
    private static final By VIEW_AS_GRID_VIEW = By.cssSelector(".grid");
    private static final By PRODUCTS_ON_PAGE_AMOUNT_TEXT = By.cssSelector(".amount");
    private static final By SORT_OPTION = By.cssSelector("select[title='Sort By']");
    private static final By PRICE_FILTER = By.cssSelector(".sidebar dd ol li .price");
    private static final By PRICE_OF_ELEMENTS = By.cssSelector(".regular-price .price, .price-to .price");

    private static final By PRODUCTS_IN_GRID = By.cssSelector(".item.last");
    private static final By PRODUCT_NAME_ELEMENT_FROM_PRODUCT_IN_LIST = By.cssSelector(".product-name");
    private static final By ADD_TO_WISHLIST_LINK = By.cssSelector(".link-wishlist");
    //ADD_TO_CART_BUTTON has the last one from popup - incorrect
    private static final By ADD_TO_CART_BUTTON = By.cssSelector("button[title='Add to Cart']");
    private static final String EXPECTED_TEXT = "ELECTRONICS";


    private static final By PRODUCT_NAME_ELEMENT = By.cssSelector(".product-name");
    private static final By PRODUCT_REGULAR_PRICE_ELEMENT = By.cssSelector(".regular-price .price");
    private static final By PRODUCT_OLD_PRICE_ELEMENT = By.cssSelector(".old-price price");
    private static final By PRODUCT_SALE_PRICE_ELEMENT = By.cssSelector(".specila-price .price");
    private static final By PRODUCT_FROM_PRICE_ELEMENT = By.cssSelector(".price-from .price");
    private static final By PRODUCT_TO_PRICE_ELEMENT = By.cssSelector(".price-to .price");

    public ElectronicsPage() {
        Assert.assertTrue(getDriver().findElement(ELECTRONICS_PAGE_TITLE).getText().equalsIgnoreCase(EXPECTED_TEXT),
                "Appeared title is " + getDriver().findElement(ELECTRONICS_PAGE_TITLE).getText() +
                        ", when expected title is " + EXPECTED_TEXT);
    }

    public ElectronicsPage selectShowAsListView() {
        getDriver().findElement(SHOW_AS_LIST_BUTTON).click();
        return this;
    }

    public ElectronicsPage selectShowAsGridView() {
        getDriver().findElement(VIEW_AS_GRID_VIEW).click();
        return this;
    }

    public ElectronicsPage setListResultsToShowOnPage(AmountOfListItemsOnThePage amount) {
        Select s = new Select(getDriver().findElement(SHOW_LIST_AMOUNT_SELECT_ELEMENT));
        s.selectByVisibleText(amount.toString());
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
            double previous = getPriceAsDouble(priceOfElements.get(i).getText());
            double next = getPriceAsDouble(priceOfElements.get(i + 1).getText());
            Assert.assertTrue(previous <= next, "Previous product price is not less/equal to next one:" + next);
        }
        return this;
    }

    public ElectronicsPage setFilterByPriceRangeFrom(Double fromPrice, Double toPrice) {
        List<WebElement> priceRange = getDriver().findElements(PRICE_FILTER);
        double priceMin = 0.0;
        double priceMax = 0.0;
        for (int i = 0; i < priceRange.size(); i++) {
            //Works for currency symbol before price
            priceMin = Double.parseDouble(priceRange.get(i).getText().substring(1).trim());
            if (priceMin == fromPrice && i + 1 < priceRange.size()) {
                priceMax = Double.parseDouble(priceRange.get(i + 1).getText().substring(1).trim());
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
        for (int i = 0; i < priceOfElements.size(); i = i + 2) {
            //Works for currency symbol before price
            Assert.assertTrue(Double.parseDouble(priceOfElements.get(i).getText().substring(1).trim()) >= fromPrice, "Price of the product is not in the selected filter range");
            Assert.assertTrue(Double.parseDouble(priceOfElements.get(i + 1).getText().substring(1).trim()) <= toPrice, "Price of the product is not in the selected filter range");
        }
        return this;
    }

    public String addRandomItemInWishList() {
        List<WebElement> listOfElements = new WebDriverWait(getDriver(), 10)
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(PRODUCTS_IN_GRID));
        WebElement randomProductItem = listOfElements.get((int) (Math.random() * (listOfElements.size() - 1)));
        String productItemName = randomProductItem.findElement(PRODUCT_NAME_ELEMENT_FROM_PRODUCT_IN_LIST).getText();

        scrollToTheElement(getDriver(), randomProductItem.findElement(ADD_TO_WISHLIST_LINK));
        try {
            randomProductItem.findElement(ADD_TO_WISHLIST_LINK).click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) getDriver()).executeScript("document.getElementById('close-fixedban').click()");
            scrollToTheElement(getDriver(), getDriver().findElement(ADD_TO_WISHLIST_LINK));
            randomProductItem.findElement(ADD_TO_WISHLIST_LINK).click();
        }
        return productItemName;
    }

    public ElectronicsPage setGridResultsToShowOnPage(AmountOfGridItemsOnThePage amount) {
        WebElement gridElement = getDriver().findElement(SHOW_GRID_AMOUNT_SELECT_ELEMENT);
        Select s = new Select(gridElement);
        s.selectByVisibleText(amount.toString());//probably check presence of the nextSHOW_LIST_AMOUNT_SELECT_ELEMENT
        new WebDriverWait(getDriver(), 10)
                .until(ExpectedConditions.stalenessOf(gridElement));
        return this;
    }

    public Product addRandomItemToCart() {
        //waiter 1
        new WebDriverWait(getDriver(), 10).until(ExpectedConditions.presenceOfAllElementsLocatedBy(ADD_TO_CART_BUTTON));
        //waiter 2
        List<WebElement> listOfElements = new ArrayList<>(new WebDriverWait(getDriver(), 10)
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(PRODUCTS_IN_GRID)));


        listOfElements.removeIf(elem -> elem.findElements(ADD_TO_CART_BUTTON).size() == 0);

        WebElement randomProductItem = listOfElements.get((int) (Math.random() * listOfElements.size()));

        Product productAddedToTheCart = Product.builder()
                .name(randomProductItem.findElement(PRODUCT_NAME_ELEMENT).getText())
                .build();
//        if (randomProductItem.findElements(PRODUCT_FROM_PRICE_ELEMENT).size() > 0)
//            productAddedToTheCart.setFromPrice(getPriceAsDouble(randomProductItem.findElement(PRODUCT_FROM_PRICE_ELEMENT).getText()));
//        if (randomProductItem.findElements(PRODUCT_TO_PRICE_ELEMENT).size() > 0)
//            productAddedToTheCart.setToPrice(getPriceAsDouble(randomProductItem.findElement(PRODUCT_TO_PRICE_ELEMENT).getText()));
//        if (randomProductItem.findElements(PRODUCT_OLD_PRICE_ELEMENT).size() > 0)
//            productAddedToTheCart.setOldPrice(getPriceAsDouble(randomProductItem.findElement(PRODUCT_OLD_PRICE_ELEMENT).getText()));
        if (randomProductItem.findElements(PRODUCT_REGULAR_PRICE_ELEMENT).size() > 0)
            productAddedToTheCart.setRegularPrice(getPriceAsDouble(randomProductItem.findElement(PRODUCT_REGULAR_PRICE_ELEMENT).getText()));
        else if (randomProductItem.findElements(PRODUCT_SALE_PRICE_ELEMENT).size() > 0)
            productAddedToTheCart.setSalePrice(getPriceAsDouble(randomProductItem.findElement(PRODUCT_SALE_PRICE_ELEMENT).getText()));

        scrollToTheElement(getDriver(), getDriver().findElement(ADD_TO_CART_BUTTON));//or scroll to the button? ADD_TO_CART_BUTTON
        try {
            randomProductItem.findElement(ADD_TO_CART_BUTTON).click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) getDriver()).executeScript("document.getElementById('close-fixedban').click()");
            scrollToTheElement(getDriver(), getDriver().findElement(ADD_TO_CART_BUTTON));
            randomProductItem.findElement(ADD_TO_CART_BUTTON).click();
        }
        return productAddedToTheCart;
    }
}

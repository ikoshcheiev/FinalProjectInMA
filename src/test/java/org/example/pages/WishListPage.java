package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

import static org.example.webDriverManager.Driver.getDriver;

public class WishListPage extends AbstractPage {
    private static final By PRODUCT_IN_THE_LIST_NAME = By.cssSelector("#wishlist-view-form .product-name a");
    private static final By PAGE_TITLE = By.cssSelector(".page-title");
    private static final String EXPECTED_TEXT = "My Wishlist";

    public WishListPage() {
        Assert.assertTrue(getDriver().findElement(PAGE_TITLE).getText().equalsIgnoreCase(EXPECTED_TEXT),
                "Appeared title is " + getDriver().findElement(PAGE_TITLE).getText() +
                        ", when expected title is " + EXPECTED_TEXT);
    }

    public WishListPage verifyCorrectItemInWishList(String productName) {
        //Wishlist item is added in the end of the list
        List<WebElement> listOfWishlistProductNames = getDriver().findElements(PRODUCT_IN_THE_LIST_NAME);
        int recentlyAddedProductIndex = 0;
        for (int i = 0; i < listOfWishlistProductNames.size(); i++) {
            if(listOfWishlistProductNames.get(i).getText().equals(productName)){
                recentlyAddedProductIndex = i;
                break;
            }
        }
        Assert.assertEquals(listOfWishlistProductNames.get(recentlyAddedProductIndex).getText(), productName,
                "Added product in wishlist doesn't corresponds to the item added from PLP");
        return this;
    }
}

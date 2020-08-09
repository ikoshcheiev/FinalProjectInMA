package org.example.pages;

import org.example.model.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.example.StringUtils.getPriceAsDouble;
import static org.example.webDriverManager.Driver.getDriver;

public class CartPage extends AbstractPage {

    private static final By CART_TITLE = By.cssSelector(".page-title h1");
    private static final String EXPECTED_TEXT = "SHOPPING CART";

    private static final By PRODUCTS_IN_CART = By.cssSelector("#shopping-cart-table tbody tr");
    private static final By PRODUCT_SUBTOTAL_PRICE = By.cssSelector(".product-cart-total .price");
    private static final By PRODUCT_ONE_PRICE = By.cssSelector(".cart-price .price");
    private static final By PRODUCT_NAME = By.cssSelector(".product-name a");
    private static final By CART_SUBTOTAL_PRICE = By.cssSelector("#shopping-cart-totals-table tbody .price");

    public CartPage() {
        Assert.assertTrue(getDriver().findElement(CART_TITLE).getText().equalsIgnoreCase(EXPECTED_TEXT),
                "Appeared title is " + getDriver().findElement(CART_TITLE).getText() +
                        ", when expected title is " + EXPECTED_TEXT);
    }

    public CartPage verifyCorrectItemInCart(Product product) {
        List<WebElement> listOfCartProducts = new ArrayList<>(new WebDriverWait(getDriver(), 10)
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(PRODUCTS_IN_CART)));
        int recentlyAddedProductIndex = listOfCartProducts.size() - 1;
        if (product.getRegularPrice() != null)
            Assert.assertEquals(
                    product.getRegularPrice(),
                    getPriceAsDouble(listOfCartProducts.get(recentlyAddedProductIndex).findElement(PRODUCT_ONE_PRICE).getText()),
                    "Cart price of product doesn't equal to Regular product price on PLP");
        if (product.getSalePrice() != null)
            Assert.assertEquals(
                    product.getSalePrice(),
                    getPriceAsDouble(listOfCartProducts.get(recentlyAddedProductIndex).findElement(PRODUCT_ONE_PRICE).getText()),
                    "Cart price of product doesn't equal to Sale product price on PLP");
        Assert.assertEquals(
                product.getName(),
                listOfCartProducts.get(recentlyAddedProductIndex).findElement(PRODUCT_NAME).getText(),
                "Cart name of product doesn't equal to product name on PLP");
        return this;
    }

    public CartPage verifyGrandTotal(Product product) {
        List<WebElement> listOfCartProducts = getDriver().findElements(PRODUCTS_IN_CART);
        double priceSumOfProductsSubtotal = 0.0;
        for (int i = 0; i < listOfCartProducts.size(); i++) {
            if (product.getRegularPrice() != null)
                priceSumOfProductsSubtotal +=
                        getPriceAsDouble(listOfCartProducts.get(i).findElement(PRODUCT_SUBTOTAL_PRICE).getText());
            else if (product.getSalePrice() != null)
                priceSumOfProductsSubtotal += getPriceAsDouble(listOfCartProducts.get(i).findElement(PRODUCT_SUBTOTAL_PRICE).getText());

        }
        new WebDriverWait(getDriver(), 5).until(ExpectedConditions.visibilityOf(getDriver().findElement(CART_SUBTOTAL_PRICE)));
        Assert.assertEquals(
                priceSumOfProductsSubtotal,
                getPriceAsDouble(getDriver().findElement(CART_SUBTOTAL_PRICE).getText()),
                "SUM of product's prices in the cart doesn't equal to SUBTOTAL in the CART");
        return this;
    }
}

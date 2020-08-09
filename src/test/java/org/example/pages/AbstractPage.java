package org.example.pages;

import org.example.pages.homeAndDecor.ElectronicsPage;
import org.example.pages.homeAndDecor.HomeAndDecorPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import static org.example.webDriverManager.Driver.getDriver;

public abstract class AbstractPage {
    private static final By LANGUAGE = By.id("select-language");
    private static final By HOME_AND_DECOR_l1 = By.linkText("HOME & DECOR");
    private static final By SALE_l1 = By.linkText("SALE");
    private static final By HOME_AND_DECOR_l1_ELECTRONICS_l2 = By.linkText("HOME & DECOR");
    private static final By ACCOUNT_MENU = By.cssSelector(".skip-account");
    private static final By ACCOUNT_MENU_ITEM_REGISTER = By.cssSelector("a[title='Register']");
    private static final By ACCOUNT_MENU_ITEM_LOGOUT = By.cssSelector("a[title='Log Out']");
    private static final By ACCOUNT_MENU_ITEM_CART = By.cssSelector("a[title='My Cart']");
    private static final By ACCOUNT_MENU_ITEM_WISHLIST = By.linkText("My Wishlist");
    private static final By ACCOUNT_MENU_ITEM_ACCOUNT = By.linkText("My Account");
    private static final By ACCOUNT_MENU_ITEM_LOGIN  = By.linkText("Log In");

    public AbstractPage() {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), 5);
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id("close-fixedban")));
            ((JavascriptExecutor) getDriver()).executeScript("document.getElementById('close-fixedban').click()");
        } catch (Exception e){}
    }

    public HomeAndDecorPage goToHomeDecorPageViaMenu() {
        getDriver().findElement(HOME_AND_DECOR_l1).click();
        return new HomeAndDecorPage();
    }

    public SalePage goToSalePageViaMenu() {
        getDriver().findElement(SALE_l1).click();
        return new SalePage();
    }

    public RegistrationPage openRegistrationForm() {
        getDriver().findElement(ACCOUNT_MENU).click();
        getDriver().findElement(ACCOUNT_MENU_ITEM_REGISTER).click();
        return new RegistrationPage();
    }

    public LoginPage openLoginForm() {
        getDriver().findElement(ACCOUNT_MENU).click();
        Assert.assertTrue(getDriver().findElements(ACCOUNT_MENU_ITEM_LOGIN).size()>0,
                "Can't find Login button as already logged in");
        getDriver().findElement(ACCOUNT_MENU_ITEM_LOGIN).click();
        return new LoginPage();
    }

    public AbstractPage logOut() {
        getDriver().findElement(ACCOUNT_MENU).click();
        Assert.assertTrue(getDriver().findElements(ACCOUNT_MENU_ITEM_LOGOUT).size()>0,
                "Can't find Logout button as not logged in");
        getDriver().findElement(ACCOUNT_MENU_ITEM_LOGOUT).click();
        return this;
    }

    public enum Language {
        ENG("English"),
        LESSON_1("Lesson 1"),
        LESSON_2("Lesson 2"),
        LESSON_3("Lesson 3"),
        LESSON_4("Lesson 4"),
        LESSON_5("Lesson 5"),
        LESSON_6("Lesson 6"),
        LESSON_7("Lesson 7"),
        LESSON_8("Lesson 8"),
        LESSON_9("Lesson 9"),
        LESSON_10("Lesson 10"),
        LESSON_11("Lesson 11"),
        AUTO("Automation");

        private String name;

        Language(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum AmountOfListItemsOnThePage {
        FIVE("5"),
        TEN("10"),
        FIFTEEN("15"),
        TWENTY("20"),
        TWENTYFIVE("25");

        private String amount;

        AmountOfListItemsOnThePage(String amount) {
            this.amount = amount;
        }

        @Override
        public String toString() {
            return amount;
        }
    }

    public enum AmountOfGridItemsOnThePage {
        TWELVE("12"),
        TWENTYFOUR("24"),
        THIRTYSIX("36");

        private String amount;

        AmountOfGridItemsOnThePage(String amount) {
            this.amount = amount;
        }

        @Override
        public String toString() {
            return amount;
        }
    }

    public AbstractPage setLanguage(Language lang) {
        Select language = new Select(getDriver().findElement(LANGUAGE));
        language.selectByVisibleText(lang.toString());
        return this;
    }

    public boolean isOnThisPage(By webElements) {
        return getDriver().findElements(webElements).size() > 0;
    }
}

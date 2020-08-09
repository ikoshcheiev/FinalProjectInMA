package org.example.pages;

import org.example.model.User;
import org.openqa.selenium.By;
import org.testng.Assert;

import static org.example.webDriverManager.Driver.getDriver;

public class LoginPage extends AbstractPage{
    private static final By LOGIN_AND_REGISTRATION_TITLE = By.cssSelector(".customer-account-login .page-title");
    private static final By LOGIN_AND_REGISTRATION_PAGE = By.cssSelector(".customer-account-login");

    private static final By EMAIL_FIELD = By.cssSelector("#email");
    private static final By PASSWORD_FIELD = By.cssSelector("#pass");
    private static final By LOGIN_BUTTON = By.cssSelector("#send2");
    private static final String EXPECTED_TEXT = "LOGIN OR CREATE AN ACCOUNT";

    private static final String EMAIL_FOR_LOGIN  = "koshcheiev.vanya@gmail.com";
    private static final String PASSWORD_FOR_LOGIN  = "Test_1234";

    public LoginPage() {
        Assert.assertTrue(getDriver().findElement(LOGIN_AND_REGISTRATION_TITLE).getText().equalsIgnoreCase(EXPECTED_TEXT),
                "Appeared title is " + getDriver().findElement(LOGIN_AND_REGISTRATION_TITLE).getText() +
                        ", when expected title is " + EXPECTED_TEXT);
    }

    public LoginPage isOnThisPage() {
        Assert.assertTrue(isOnThisPage(LOGIN_AND_REGISTRATION_PAGE), "Not " + this.getClass().toString() + " is opened");
        return this;
    }

    public MyDashboardPage loginUser(User user) {
        getDriver().findElement(EMAIL_FIELD).sendKeys(user.getEmail());
        getDriver().findElement(PASSWORD_FIELD).sendKeys(user.getPassword());
        getDriver().findElement(LOGIN_BUTTON).click();
        return new MyDashboardPage();
    }
    public MyDashboardPage loginDefaultUser() {
        getDriver().findElement(EMAIL_FIELD).sendKeys(EMAIL_FOR_LOGIN);
        getDriver().findElement(PASSWORD_FIELD).sendKeys(PASSWORD_FOR_LOGIN);
        getDriver().findElement(LOGIN_BUTTON).click();
        return new MyDashboardPage();
    }
}

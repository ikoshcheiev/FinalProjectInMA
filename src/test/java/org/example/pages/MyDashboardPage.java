package org.example.pages;

import org.example.model.User;
import org.openqa.selenium.By;
import org.testng.Assert;

import static org.example.webDriverManager.Driver.getDriver;

public class MyDashboardPage extends AbstractPage {
    private static final By MY_DASHBOARD_TITLE = By.cssSelector(".customer-account .page-title");
    private static final By MY_DASHBOARD_PAGE = By.cssSelector(".customer-account");
    private static final By MY_DASHBOARD_HELLO_ELEMENT = By.cssSelector("p.hello");
    private static final String EXPECTED_TEXT = "MY DASHBOARD";

    public MyDashboardPage() {
        Assert.assertTrue(getDriver().findElement(MY_DASHBOARD_TITLE).getText().equalsIgnoreCase(EXPECTED_TEXT),
                "Appeared title is " + getDriver().findElement(MY_DASHBOARD_TITLE).getText() +
                        ", when expected title is " + EXPECTED_TEXT);
    }

    public MyDashboardPage isOnThisPage() {
        Assert.assertTrue(isOnThisPage(MY_DASHBOARD_PAGE), "Not " + this.getClass().toString() + "is opened");
        return this;
    }

    public MyDashboardPage checkHelloUserPresence(User user) {
        //Hello, 123 123!
        Assert.assertTrue(getDriver().findElement(MY_DASHBOARD_HELLO_ELEMENT).getText()
                        .equalsIgnoreCase(String.format("Hello, %s %s!",user.getFirstName(), user.getLastName())),
                "Appeared text is" + getDriver().findElement(MY_DASHBOARD_HELLO_ELEMENT).getText() +
                        ", but expected is " + String.format("Hello, %s %s", user.getFirstName(), user.getLastName()));
        return this;
    }

    public MyDashboardPage checkDefaultHelloUserPresence() {
        //Hello, 123 123!
        Assert.assertTrue(getDriver().findElement(MY_DASHBOARD_HELLO_ELEMENT).getText()
                        .equalsIgnoreCase(String.format("Hello, %s %s!", DEFAULT_LOGGEDIN_FIRSTNAME, DEFAULT_LOGGEDIN_LASTNAME)),
                "Appeared text is" + getDriver().findElement(MY_DASHBOARD_HELLO_ELEMENT).getText() +
                        ", but expected is " + String.format("Hello, %s %s", DEFAULT_LOGGEDIN_FIRSTNAME, DEFAULT_LOGGEDIN_LASTNAME));
        return this;
    }
}

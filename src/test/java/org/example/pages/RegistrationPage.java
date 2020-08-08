package org.example.pages;

import org.example.model.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.example.webDriverManager.Driver.getDriver;

public class RegistrationPage extends AbstractPage {
    private static final By REGISTRATION_PAGE_TITLE = By.cssSelector(".customer-account-create .page-title");
    private static final By REGISTRATION_PAGE_PAGE = By.cssSelector(".customer-account-create");
    private static final By BACK_BUTTON = By.className("back-link");
    private static final By REGISTER_BUTTON = By.cssSelector("button[title='Register']");

    private static final By FIRST_NAME_FIELD = By.cssSelector("#firstname");
    private static final By LAST_NAME_FIELD = By.cssSelector("#lastname");
    private static final By EMAIL_FIELD = By.cssSelector("#email_address");
    private static final By PASSWORD_FIELD = By.cssSelector("#password");
    private static final By PASSWORD_CONFIRM_FIELD = By.cssSelector("#confirmation");
    private static final By SUBSCRIPTION_CHECKBOX = By.cssSelector("input#is_subscribed");
    private static final String EXPECTED_TEXT = "CREATE AN ACCOUNT";


    public RegistrationPage() {
        Assert.assertTrue(getDriver().findElement(REGISTRATION_PAGE_TITLE).getText().equalsIgnoreCase(EXPECTED_TEXT),
                "Appeared title is " + getDriver().findElement(REGISTRATION_PAGE_TITLE).getText() +
                        ", when expected title is " + EXPECTED_TEXT);
    }

    public RegistrationPage isOnThisPage() {
        Assert.assertTrue(isOnThisPage(REGISTRATION_PAGE_PAGE), "Not " + this.getClass().toString() + "is opened");
        return this;
    }

    public MyDashboardPage registerUser(User user) {
        getDriver().findElement(FIRST_NAME_FIELD).sendKeys(user.getFirstName());
        getDriver().findElement(LAST_NAME_FIELD).sendKeys(user.getLastName());
        getDriver().findElement(EMAIL_FIELD).sendKeys(user.getEmail());
        getDriver().findElement(PASSWORD_FIELD).sendKeys(user.getPassword());
        getDriver().findElement(PASSWORD_CONFIRM_FIELD).sendKeys(user.getConfirmPassword());
        getDriver().findElement(SUBSCRIPTION_CHECKBOX).click();
        getDriver().findElement(REGISTER_BUTTON).click();
        return new MyDashboardPage();
    }

    public RegistrationPage checkThatAllFieldsPresentForRegistration() {
        Assert.assertTrue(getDriver().findElements(FIRST_NAME_FIELD).size() > 0,"Element " + FIRST_NAME_FIELD.toString() + "is absent");
        Assert.assertTrue(getDriver().findElements(LAST_NAME_FIELD).size() > 0,"Element " + LAST_NAME_FIELD.toString() + "is absent");
        Assert.assertTrue(getDriver().findElements(EMAIL_FIELD).size() > 0,"Element " + EMAIL_FIELD.toString() + "is absent");
        Assert.assertTrue(getDriver().findElements(PASSWORD_FIELD).size() > 0,"Element " + PASSWORD_FIELD.toString() + "is absent");
        Assert.assertTrue(getDriver().findElements(PASSWORD_CONFIRM_FIELD).size() > 0,"Element " + PASSWORD_CONFIRM_FIELD.toString() + "is absent");
        Assert.assertTrue(getDriver().findElements(SUBSCRIPTION_CHECKBOX).size() > 0,"Element " + SUBSCRIPTION_CHECKBOX.toString() + "is absent");
        Assert.assertTrue(getDriver().findElements(REGISTER_BUTTON).size() > 0,"Element " + REGISTER_BUTTON.toString() + "is absent");
        return this;
    }

    public LoginPage returnToLoginPageByBackButton(){
        getDriver().findElement(BACK_BUTTON).click();
        return new LoginPage();
    }

    public RegistrationPage submitEmptyRegistrationForm() {
        getDriver().findElement(REGISTER_BUTTON).click();
        return this;
    }

    public RegistrationPage checkThatErrorAboutEmptyFieldsAppear() {
        //Check that at least 1 error present and equal to expected
        List<WebElement> elements = new ArrayList<>(getDriver().findElements(By.cssSelector(".form-list .input-box .validation-advice")));
        Assert.assertTrue(elements.size() > 0);
        for (WebElement e:elements) {
            Assert.assertTrue(e.getText().equals("This is a required field."),
                    "Appeared text is" + e.getText() + " but expected to receive 'This is a required field.'");
        }
        return this;
    }

}

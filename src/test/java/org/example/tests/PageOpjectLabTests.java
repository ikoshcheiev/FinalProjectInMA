package org.example.tests;

import org.example.model.User;
import org.example.pages.MainPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.example.StringUtils.generateRandomDigit;
import static org.example.pages.AbstractPage.Language.AUTO;

public class PageOpjectLabTests extends BaseTest{

    MainPage mainPage;
    ThreadLocal<User> tl = new ThreadLocal<>();

    @BeforeMethod(alwaysRun = true)
    public void mainPage() {
        mainPage = new MainPage();
        String random = generateRandomDigit(4);
        tl.set(User.builder()
                .firstName("TestFirst".concat(random))
                .lastName("TestLast".concat(random))
                .email(random.concat("Email@test.com"))
                .password("password".concat(random))
                .confirmPassword("password".concat(random))
                .build());
    }
    @Test
    public void registrationFieldsPresenceTest(){
        mainPage.setLanguage(AUTO)
                .openRegistrationForm()
                .checkThatAllFieldsPresentForRegistration();
    }

    @Test
    public void registrationFormBackButtonTest(){
        mainPage.setLanguage(AUTO)
                .openRegistrationForm()
                .returnToLoginPageByBackButton()
                .isOnThisPage();
    }
    @Test
    public void registrationEmptyFieldErrorsTest(){
        mainPage.setLanguage(AUTO)
                .openRegistrationForm()
                .submitEmptyRegistrationForm()
                .checkThatErrorAboutEmptyFieldsAppear();
    }
    @Test
    public void registrationUserDashboardOpeningTest(){
        mainPage.setLanguage(AUTO)
                .openRegistrationForm()
                .registerUser(tl.get())
                .checkHelloUserPresence(tl.get())
                .isOnThisPage();
    }
    @Test()
    public void loginUserDashboardOpeningTest(){
        mainPage.setLanguage(AUTO)
                .openLoginForm()
                .loginDefaultUser()
                .checkHelloUserPresence(tl.get());
    }
}

package org.example.tests;

import org.example.model.User;
import org.example.pages.MainPage;
import org.example.pages.WishListPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.example.StringUtils.generateRandomDigit;
import static org.example.pages.AbstractPage.AmountOfGridItemsOnThePage.THIRTYSIX;
import static org.example.pages.AbstractPage.AmountOfListItemsOnThePage.FIVE;
import static org.example.pages.AbstractPage.AmountOfListItemsOnThePage.TWENTYFIVE;
import static org.example.pages.AbstractPage.Language.AUTO;

public class FinalProjectLabTests extends BaseTest{

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
    public void checkItemsCounterTest(){
        mainPage.setLanguage(AUTO)
                .goToHomeDecorPageViaMenu()
                .goToElectronicsPage()
                .selectShowAsListView()
                .setListResultsToShowOnPage(TWENTYFIVE)
                .checkItemsCountOnPage();
    }
    @Test
    public void checkShowSelectTest(){
        mainPage.setLanguage(AUTO)
                .goToHomeDecorPageViaMenu()
                .goToElectronicsPage()
                .selectShowAsListView()
                .setListResultsToShowOnPage(FIVE)
                .checkItemsAmountOnPageAndInSelect(FIVE);
    }
    @Test
    public void checkSortByTest(){
        mainPage.setLanguage(AUTO)
                .goToHomeDecorPageViaMenu()
                .goToElectronicsPage()
                .selectShowAsListView()
                .setListResultsToShowOnPage(TWENTYFIVE)
                .setSortBy("Price")
                .checkSortedByPrice();
    }
    @Test
    public void checkPriceFilterTest(){
        mainPage.setLanguage(AUTO)
                .goToHomeDecorPageViaMenu()
                .goToElectronicsPage()
                .selectShowAsListView()
                .setListResultsToShowOnPage(TWENTYFIVE)
                .setFilterByPriceRangeFrom(0.00, 999.99)
                .checkFilteredProductPrices(0.00, 999.99);
    }
    @Test
    public void checkAddToWishListTest() {
        String randomProductTitle = mainPage.setLanguage(AUTO)
                .openRegistrationForm()
                .openLoginForm()
                .loginDefaultUser()
                .goToHomeDecorPageViaMenu()
                .goToElectronicsPage()
                .selectShowAsListView()
                .setListResultsToShowOnPage(TWENTYFIVE)
                .addRandomItemInWishList();
    new WishListPage()
            .verifyCorrectItemInWishList(randomProductTitle);
    }

    @Test
    public void checkSalePricesTest() {
        mainPage.setLanguage(AUTO)
                .goToSalePageViaMenu()
                .selectShowAsGridView()
                .setGridResultsToShowOnPage(THIRTYSIX)
                .verifyOldPriceIsHigher();
    }
}

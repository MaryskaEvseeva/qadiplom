package ru.netology.qadiplom.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.qadiplom.data.DataHelper;
import ru.netology.qadiplom.data.SQLHelper;
import ru.netology.qadiplom.page.StartPage;

import static com.codeborne.selenide.Selenide.open;

public class CreditRequestTest {
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }
    @BeforeEach
    void setup() {
        open("http://localhost:8080");
    }

    @DisplayName("Successful credit card purchase")
    @Test
    public void shouldConfirmCreditApprovedCard() {
        var startPage = new StartPage();
        var buyCredit = startPage.openBuyCredit();
        var approvedCardInformation = DataHelper.getValidCard();
        buyCredit.enterCreditCardData(approvedCardInformation);
        buyCredit.verifySuccessNotificationCreditCard();

        var statusPayment = SQLHelper.getStatusCredit();
        Assertions.assertEquals("APPROVED", statusPayment.getStatus());
    }

    @DisplayName("Successful credit purchase with current M and Y.")
    @Test
    public void shouldConfirmCreditWithCurrentMonthAndYear() {
        var startPage = new StartPage();
        var buyCredit = startPage.openBuyCredit();
        var validCardInformation = DataHelper.getCurrentMonthAndYear();
        buyCredit.enterCreditCardData(validCardInformation);
        buyCredit.verifySuccessNotificationCreditCard();

        var statusPayment = SQLHelper.getStatusCredit();
        Assertions.assertEquals("APPROVED", statusPayment.getStatus());
    }

    @DisplayName("Credit - Declined card")
    @Test
    public void shouldNotCreditDeclinedCard() {
        var startPage = new StartPage();
        var buyCredit = startPage.openBuyCredit();
        var declinedCard = DataHelper.getDeclinedCard();
        buyCredit.enterCreditCardData(declinedCard);
        buyCredit.verifyErrorWarningCreditCard();

        var statusPayment = SQLHelper.getStatusCredit();
        Assertions.assertEquals("DECLINED", statusPayment.getStatus());
    }

    @DisplayName("Credit - All fields empty")
    @Test
    public void shouldNotCreditEmptyForm() {
        var startPage = new StartPage();
        var buyCredit = startPage.openBuyCredit();
        var emptyCardInformation = DataHelper.getAllFieldsEmpty();
        buyCredit.enterCreditCardData(emptyCardInformation);
        buyCredit.verifyIncorrectFormatCreditCard();
    }

    @DisplayName("Credit - field card number empty")
    @Test
    public void shouldNotCreditEmptyCard() {
        var startPage = new StartPage();
        var buyCredit = startPage.openBuyCredit();
        var fieldCardEmpty = DataHelper.getCardNumberEmpty();
        buyCredit.enterCreditCardData(fieldCardEmpty);
        buyCredit.verifyIncorrectFormatCreditCard();
    }

    @DisplayName("Credit - field month empty")
    @Test
    public void shouldNotCreditEmptyMonth() {
        var startPage = new StartPage();
        var buyCredit = startPage.openBuyCredit();
        var fieldMonthEmpty = DataHelper.getMonthEmpty();
        buyCredit.enterCreditCardData(fieldMonthEmpty);
        buyCredit.verifyIncorrectFormatCreditCard();
    }
    @DisplayName("Credit - field year empty")
    @Test
    public void shouldNotCreditEmptyYear() {
        var startPage = new StartPage();
        var buyCredit = startPage.openBuyCredit();
        var fieldYearEmpty = DataHelper.getYearEmpty();
        buyCredit.enterCreditCardData(fieldYearEmpty);
        buyCredit.verifyIncorrectFormatCreditCard();
    }

    @DisplayName("Credit - field holder empty")
    @Test
    public void shouldNotCreditEmptyHolder() {
        var startPage = new StartPage();
        var buyCredit = startPage.openBuyCredit();
        var fieldHolderEmpty = DataHelper.getHolderEmpty();
        buyCredit.enterCreditCardData(fieldHolderEmpty);
        buyCredit.verifyRequiredFieldCreditCard();
    }

    @DisplayName("Credit - field CVV empty")
    @Test
    public void shouldNotCreditEmptyCvv() {
        var startPage = new StartPage();
        var buyCredit = startPage.openBuyCredit();
        var fieldCvvEmpty = DataHelper.getCVVEmpty();
        buyCredit.enterCreditCardData(fieldCvvEmpty);
        buyCredit.verifyIncorrectFormatCreditCard();
    }

    @DisplayName("Credit - Invalid card number")
    @Test
    public void shouldNotCreditInvalidNumber() {
        var startPage = new StartPage();
        var buyCredit = startPage.openBuyCredit();
        var invalidCard = DataHelper.getInvalidNumber();
        buyCredit.enterCreditCardData(invalidCard);
        buyCredit.verifyIncorrectFormatCreditCard();
    }

    @DisplayName("Credit - Invalid month")
    @Test
    public void shouldNotCreditWrongMonth() {
        var startPage = new StartPage();
        var buyCredit = startPage.openBuyCredit();
        var invalidCard = DataHelper.getInvalidMonth();
        buyCredit.enterCreditCardData(invalidCard);
        buyCredit.verifyIncorrectDateCreditCard();
    }

    @DisplayName("Credit - Invalid year")
    @Test
    public void shouldNotCreditWrongYear() {
        var startPage = new StartPage();
        var buyCredit = startPage.openBuyCredit();
        var invalidCard = DataHelper.getWrongYear();
        buyCredit.enterCreditCardData(invalidCard);
        buyCredit.verifyIncorrectDateCreditCard();
    }

    @DisplayName("Credit - Numeric holder's name")
    @Test
    public void shouldNotCreditNumericHolder() {
        var startPage = new StartPage();
        var buyCredit = startPage.openBuyCredit();
        var invalidCard = DataHelper.getNumericName();
        buyCredit.enterCreditCardData(invalidCard);
        buyCredit.verifyIncorrectFormatCreditCard();
    }

    @DisplayName("Credit - Invalid CVV")
    @Test
    public void shouldNotCreditInvalidCVV() {
        var startPage = new StartPage();
        var buyCredit = startPage.openBuyCredit();
        var invalidCard = DataHelper.getInvalidCVV();
        buyCredit.enterCreditCardData(invalidCard);
        buyCredit.verifyIncorrectFormatCreditCard();
    }

    @DisplayName("Credit - Expired month")
    @Test
    public void shouldNotCreditExpiredMonth() {
        var startPage = new StartPage();
        var buyCredit = startPage.openBuyCredit();
        var invalidCard = DataHelper.getExpiredMonth();
        buyCredit.enterCreditCardData(invalidCard);
        buyCredit.verifyIncorrectDateCreditCard();
    }

    @DisplayName("Credit - Expired year")
    @Test
    public void shouldNotCreditExpiredYear() {
        var startPage = new StartPage();
        var buyCredit = startPage.openBuyCredit();
        var invalidCard = DataHelper.getExpiredYear();
        buyCredit.enterCreditCardData(invalidCard);
        buyCredit.expiredCreditCard();
    }

    @DisplayName("Credit - Zero card number")
    @Test
    public void shouldNotCreditZeroNumber() {
        var startPage = new StartPage();
        var buyCredit = startPage.openBuyCredit();
        var invalidCard = DataHelper.getZeroCard();
        buyCredit.enterCreditCardData(invalidCard);
        buyCredit.verifyIncorrectFormatCreditCard();
    }

    @DisplayName("Credit- Zero month")
    @Test
    public void shouldNotCreditZeroMonth() {
        var startPage = new StartPage();
        var buyCredit = startPage.openBuyCredit();
        var invalidCard = DataHelper.getZeroMonth();
        buyCredit.enterCreditCardData(invalidCard);
        buyCredit.verifyIncorrectDateCreditCard();
    }
    @DisplayName("Credit - Zero CVV")
    @Test
    public void shouldNotCreditZeroCVV() {
        var startPage = new StartPage();
        var buyCredit = startPage.openBuyCredit();
        var invalidCard = DataHelper.getZeroCVV();
        buyCredit.enterCreditCardData(invalidCard);
        buyCredit.verifyIncorrectFormatCreditCard();
    }
}

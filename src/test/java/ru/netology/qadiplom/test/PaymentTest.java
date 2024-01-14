package ru.netology.qadiplom.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.qadiplom.data.DataHelper;
import ru.netology.qadiplom.data.SQLHelper;
import ru.netology.qadiplom.page.StartPage;
import static com.codeborne.selenide.Selenide.open;

public class PaymentTest {

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

    @DisplayName("Card - Successful purchase")
    @Test
    public void shouldConfirmPaymentApprovedCard() {
        var startPage = new StartPage();
        var payCard = startPage.openBuyCard();
        var approvedCardInformation = DataHelper.getValidCard();
        payCard.enterCardData(approvedCardInformation);
        payCard.verifySuccessNotificationCard();

        var statusPayment = SQLHelper.getStatusPayment();
        Assertions.assertEquals("APPROVED", statusPayment.getStatus());
    }

    @DisplayName("Card - Successful purchase with current date")
    @Test
    public void shouldConfirmPaymentCurrentMonthAndYear() {
        var startPage = new StartPage();
        var payCard = startPage.openBuyCard();
        var validCardInformation = DataHelper.getCurrentMonthAndYear();
        payCard.enterCardData(validCardInformation);
        payCard.verifySuccessNotificationCard();

        var statusPayment = SQLHelper.getStatusPayment();
        Assertions.assertEquals("APPROVED", statusPayment.getStatus());
    }

    @DisplayName("Card - Declined card")
    @Test
    public void shouldNotPayDeclinedCard() {
        var startPage = new StartPage();
        var payCard = startPage.openBuyCard();
        var declinedCard = DataHelper.getDeclinedCard();
        payCard.enterCardData(declinedCard);
        payCard.verifyErrorWarningCard();

        var statusPayment = SQLHelper.getStatusPayment();
        Assertions.assertEquals("DECLINED", statusPayment.getStatus());
    }

    @DisplayName("Card - All fields empty")
    @Test
    public void shouldNotPayEmptyForm() {
        var startPage = new StartPage();
        var payCard = startPage.openBuyCard();
        var emptyCardInformation = DataHelper.getAllFieldsEmpty();
        payCard.enterCardData(emptyCardInformation);
        payCard.verifyIncorrectFormatCard();
    }

    @DisplayName("Card - field card number empty")
    @Test
    public void shouldNotPayEmptyCard() {
        var startPage = new StartPage();
        var payCard = startPage.openBuyCard();
        var fieldCardEmpty = DataHelper.getCardNumberEmpty();
        payCard.enterCardData(fieldCardEmpty);
        payCard.verifyIncorrectFormatCard();
    }

    @DisplayName("Card - field month empty")
    @Test
    public void shouldNotPayEmptyMonth() {
        var startPage = new StartPage();
        var payCard = startPage.openBuyCard();
        var fieldMonthEmpty = DataHelper.getMonthEmpty();
        payCard.enterCardData(fieldMonthEmpty);
        payCard.verifyIncorrectFormatCard();
    }
    @DisplayName("Card - field year empty")
    @Test
    public void shouldNotPayEmptyYear() {
        var startPage = new StartPage();
        var payCard = startPage.openBuyCard();
        var fieldYearEmpty = DataHelper.getYearEmpty();
        payCard.enterCardData(fieldYearEmpty);
        payCard.verifyIncorrectFormatCard();
    }

    @DisplayName("Card - field holder empty")
    @Test
    public void shouldNotPayEmptyHolder() {
        var startPage = new StartPage();
        var payCard = startPage.openBuyCard();
        var fieldHolderEmpty = DataHelper.getHolderEmpty();
        payCard.enterCardData(fieldHolderEmpty);
        payCard.verifyRequiredFieldCard();
    }

    @DisplayName("Card - field CVV empty")
    @Test
    public void shouldNotPayEmptyCvv() {
        var startPage = new StartPage();
        var payCard = startPage.openBuyCard();
        var fieldCvvEmpty = DataHelper.getCVVEmpty();
        payCard.enterCardData(fieldCvvEmpty);
        payCard.verifyIncorrectFormatCard();
    }

    @DisplayName("Card - Invalid card number")
    @Test
    public void shouldNotPayInvalidNumber() {
        var startPage = new StartPage();
        var payCard = startPage.openBuyCard();
        var invalidCard = DataHelper.getInvalidNumber();
        payCard.enterCardData(invalidCard);
        payCard.verifyIncorrectFormatCard();
    }

    @DisplayName("Card - Invalid month")
    @Test
    public void shouldNotPayWrongMonth() {
        var startPage = new StartPage();
        var payCard = startPage.openBuyCard();
        var invalidCard = DataHelper.getInvalidMonth();
        payCard.enterCardData(invalidCard);
        payCard.verifyIncorrectDateCard();
    }

    @DisplayName("Card - Invalid year")
    @Test
    public void shouldNotPayWrongYear() {
        var startPage = new StartPage();
        var payCard = startPage.openBuyCard();
        var invalidCard = DataHelper.getWrongYear();
        payCard.enterCardData(invalidCard);
        payCard.verifyIncorrectDateCard();
    }

    @DisplayName("Card - Numeric holder's name")
    @Test
    public void shouldNotPayNumericHolder() {
        var startPage = new StartPage();
        var payCard = startPage.openBuyCard();
        var invalidCard = DataHelper.getNumericName();
        payCard.enterCardData(invalidCard);
        payCard.verifyIncorrectFormatCard();
    }

    @DisplayName("Card - Invalid CVV")
    @Test
    public void shouldNotPayInvalidCVV() {
        var startPage = new StartPage();
        var payCard = startPage.openBuyCard();
        var invalidCard = DataHelper.getInvalidCVV();
        payCard.enterCardData(invalidCard);
        payCard.verifyIncorrectFormatCard();
    }

    @DisplayName("Card - Expired month")
    @Test
    public void shouldNotPayExpiredMonth() {
        var startPage = new StartPage();
        var payCard = startPage.openBuyCard();
        var invalidCard = DataHelper.getExpiredMonth();
        payCard.enterCardData(invalidCard);
        payCard.verifyIncorrectDateCard();
    }

    @DisplayName("Card - Expired year")
    @Test
    public void shouldNotPayExpiredYear() {
        var startPage = new StartPage();
        var payCard = startPage.openBuyCard();
        var invalidCard = DataHelper.getExpiredYear();
        payCard.enterCardData(invalidCard);
        payCard.expiredCard();
    }

    @DisplayName("Card - Zero card number")
    @Test
    public void shouldNotPayZeroNumber() {
        var startPage = new StartPage();
        var payCard = startPage.openBuyCard();
        var invalidCard = DataHelper.getZeroCard();
        payCard.enterCardData(invalidCard);
        payCard.verifyIncorrectFormatCard();
    }

    @DisplayName("Card - Zero month")
    @Test
    public void shouldNotPayZeroMonth() {
        var startPage = new StartPage();
        var payCard = startPage.openBuyCard();
        var invalidCard = DataHelper.getZeroMonth();
        payCard.enterCardData(invalidCard);
        payCard.verifyIncorrectDateCard();
    }

    @DisplayName("Card - Zero CVV")
    @Test
    public void shouldNotPayZeroCVV() {
        var startPage = new StartPage();
        var payCard = startPage.openBuyCard();
        var invalidCard = DataHelper.getZeroCVV();
        payCard.enterCardData(invalidCard);
        payCard.verifyIncorrectFormatCard();
    }
}

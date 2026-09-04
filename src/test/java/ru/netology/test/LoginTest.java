package ru.netology.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.pages.DashboardPage;
import ru.netology.pages.LoginPage;
import ru.netology.pages.VerificationPage;
import ru.netology.sql.SQLHelper;

import static com.codeborne.selenide.Selenide.open;

public class LoginTest {

    @AfterEach
    void cleanUp() {
        SQLHelper.cleanAuthCodes();
    }

    @AfterAll
    static void cleanDatabase() {
        SQLHelper.cleanDatabase();
    }

    @Test
    void shouldLoginWithVerificationCodeFromDatabase() {
        open("http://localhost:9999");

        DataHelper.AuthInfo authInfo = DataHelper.getAuthInfo();

        LoginPage loginPage = new LoginPage();
        loginPage.login(
                authInfo.getLogin(),
                authInfo.getPassword()
        );

        VerificationPage verificationPage = new VerificationPage();
        verificationPage.waitForPage();

        String verificationCode = SQLHelper.getVerificationCode();

        verificationPage.verify(verificationCode);

        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.verifyPage();
    }

    @Test
    void shouldShowErrorWithInvalidPassword() {
        open("http://localhost:9999");

        DataHelper.AuthInfo authInfo = DataHelper.getAuthInfo();

        LoginPage loginPage = new LoginPage();
        loginPage.login(
                authInfo.getLogin(),
                DataHelper.getInvalidPassword()
        );

        loginPage.verifyErrorNotification();
    }
}
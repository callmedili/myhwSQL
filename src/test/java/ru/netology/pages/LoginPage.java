package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.text;

public class LoginPage {

    private final SelenideElement loginField =
            $("[data-test-id='login'] input");

    private final SelenideElement passwordField =
            $("[data-test-id='password'] input");

    private final SelenideElement loginButton =
            $("[data-test-id='action-login']");

    public void login(String login, String password) {
        loginField.setValue(login);
        passwordField.setValue(password);
        loginButton.click();
    }

    public void verifyErrorNotification() {
        $("[data-test-id='error-notification']")
                .shouldBe(visible)
                .shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }
}
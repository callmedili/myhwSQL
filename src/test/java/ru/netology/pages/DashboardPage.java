package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {

    private final SelenideElement heading =
            $(".heading");

    public void verifyPage() {
        heading.shouldBe(visible)
                .shouldHave(text("Личный кабинет"));
    }
}
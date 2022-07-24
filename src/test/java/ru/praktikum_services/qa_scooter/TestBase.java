package ru.praktikum_services.qa_scooter;

import io.qameta.allure.Allure;

public class TestBase {
    public void step(String info) {
        Allure.step(info);
    }
}

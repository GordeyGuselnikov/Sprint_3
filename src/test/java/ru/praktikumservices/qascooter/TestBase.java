package ru.praktikumservices.qascooter;

import io.qameta.allure.Allure;

public class TestBase {
    public void step(String info) {
        Allure.step(info);
    }
}

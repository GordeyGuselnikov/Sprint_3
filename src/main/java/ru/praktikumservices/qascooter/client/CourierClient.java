package ru.praktikumservices.qascooter.client;

import io.qameta.allure.Step;
import ru.praktikumservices.qascooter.model.Courier;
import ru.praktikumservices.qascooter.model.CourierCredentials;

import static io.restassured.RestAssured.given;

public class CourierClient extends RestAssuredClient {

    private static final String COURIER_PATH = "courier/";

    @Step("Запрос на создание учетной записи {courier}")
    public boolean create(Courier courier) {
        return post(COURIER_PATH, courier)
                .then()
                .assertThat()
                .statusCode(201)
                .extract()
                .path("ok");
    }

    @Step("Запрос Успешный логин курьера в системе")
    public int login(CourierCredentials credentials) {
        return post(COURIER_PATH + "login/", credentials)
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("id");
    }

    @Step("Запрос Неуспешный логин курьера в системе")
    public String IncorrectLoginAndPassword(CourierCredentials credentials) {
        return post(COURIER_PATH + "login/", credentials)
                .then()
                .assertThat()
                .statusCode(404)
                .extract()
                .path("message");
    }

    @Step("Запрос Неуспешный логин курьера без логина или пароля")
    public String IncorrectLogin(CourierCredentials credentials) {
        return post(COURIER_PATH + "login/", credentials)
                .then()
                .assertThat()
                .statusCode(400)
                .extract()
                .path("message");
    }

    @Step("Запрос на создание учетной записи {courier} с повторяющимся логином")
    public String notCreate(Courier courier) {
        return post(COURIER_PATH, courier)
                .then()
                .assertThat()
                .statusCode(409)
                .extract()
                .path("message");
    }

    @Step("Запрос на создание учетной записи {courier} без логина или пароля")
    public String notCreateWithoutField(Courier courier) {
        return given()
                .spec(getRequestSpecification())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then()
                .assertThat()
                .statusCode(400)
                .extract()
                .path("message");
    }

    @Step("Запрос Удаление курьера")
    public boolean delete(int courierId) {
        return delete(COURIER_PATH + courierId)
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("ok");
    }
}

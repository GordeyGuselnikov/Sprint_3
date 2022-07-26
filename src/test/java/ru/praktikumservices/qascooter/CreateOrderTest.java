package ru.praktikumservices.qascooter;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;

import org.junit.Before;
import org.junit.Test;
import ru.praktikumservices.qascooter.client.RestAssuredClient;
import ru.praktikumservices.qascooter.model.Order;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotNull;

public class CreateOrderTest extends TestBase {

    private static final String ORDER_PATH = "order/";
    RestAssuredClient client;

    @Before
    public void setUp() {
        client = new RestAssuredClient();
    }

    @Test
    @DisplayName("Создание заказа c одним цветом")
    @Description("тело ответа содержит track; можно указать один из цветов — BLACK или GREY")
    public void createOrderWithOneColor() {
        // Arrange
        step("Готовим тестовые данные");
        Order order = Order.getOrderWithColor(new String[]{"GREY"});

        Response response = given()
                .spec(client.getRequestSpecification())
                .body(order)
                .post("orders");
        int actual = response.then()
                .assertThat()
                .statusCode(201)
                .extract()
                .path("track");
        assertNotNull("Заказ не создан!", actual);
    }

    @Test
    @DisplayName("Создание заказа с двумя цветами")
    @Description("можно указать оба цвета;")
    public void createOrderWithTwoColor() {
        // Arrange
        step("Готовим тестовые данные");
        Order order = Order.getOrderWithColor(new String[]{"GREY, BLACK"});

        Response response = given()
                .spec(client.getRequestSpecification())
                .body(order)
                .post("orders");
        int actual = response.then()
                .assertThat()
                .statusCode(201)
                .extract()
                .path("track");
        assertNotNull("Заказ не создан!", actual);
    }

    @Test
    @DisplayName("Создание заказа без цветов")
    @Description("можно совсем не указывать цвет")
    public void createOrderWithoutColor() {
        // Arrange
        step("Готовим тестовые данные");
        Order order = Order.getOrderWithColor(new String[]{});

        Response response = given()
                .spec(client.getRequestSpecification())
                .body(order)
                .post("orders");
        int actual = response.then()
                .assertThat()
                .statusCode(201)
                .extract()
                .path("track");
        assertNotNull("Заказ не создан!", actual);
    }

    @Test
    @DisplayName("Список заказов")
    @Description("тело ответа возвращается список заказов")
    public void getAllOrders() {
        // Arrange
        step("Готовим тестовые данные");
        Order order = Order.getOrderWithColor(new String[]{});

        Response response = given()
                .spec(client.getRequestSpecification())
                .body(order)
                .post("orders?courierId=1"); // ручка бага, но в документации так указано!
        response.then()
                .assertThat()
                .statusCode(200) // и стутс код по документации должен быть 200
                .and()
                .body("orders", notNullValue());
    }
}

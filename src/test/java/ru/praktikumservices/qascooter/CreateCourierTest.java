package ru.praktikumservices.qascooter;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikumservices.qascooter.client.CourierClient;
import ru.praktikumservices.qascooter.model.Courier;
import ru.praktikumservices.qascooter.model.CourierCredentials;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

public class CreateCourierTest extends TestBase {
    private CourierClient courierClient;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
        if (courierId != 0) {
            courierClient.delete(courierId);
        } else return;
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("курьера можно создать; " +
            "чтобы создать курьера, нужно передать в ручку все обязательные поля; " +
            "успешный запрос возвращает ok: true; " +
            "запрос возвращает правильный код ответа;")
    public void courierCanBeCreated() {
        // Arrange
        step("Готовим тестовые данные");
        Courier courier = Courier.getRandom();

        // Act
        boolean isCourierCreated = courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.from(courier));

        // Assert
        assertTrue("Курьер не создался!", isCourierCreated);
        assertThat("Courier ID is incorrect", courierId, is(not(0)));
    }

    @Test
    @DisplayName("Создание одинаковых курьеров")
    @Description("нельзя создать двух одинаковых курьеров;")
    public void identicalCouriersCanNotBeCreated() {
        // Arrange
        step("Готовим тестовые данные");
        Courier courier = Courier.getRandom();

        // Act
        boolean isCourierCreated = courierClient.create(courier);
        String message = courierClient.notCreate(courier);
        courierId = courierClient.login(CourierCredentials.from(courier));

        // Assert
        assertEquals("Тело ответа не совпадает с документацией", message, "Этот логин уже используется");
    }

    @Test
    @DisplayName("Создание курьера без одного поля")
    @Description("если одного из полей нет, запрос возвращает ошибку;")
    public void courierCanNotBeCreatedWithoutRequiredField() {
        // Arrange
        step("Готовим тестовые данные");
        Courier courier = Courier.getRandom();
        courier.password = null;

        // Act
        String messageResponse = courierClient.notCreateWithoutField(courier);

        // Assert
        assertNotNull("Ошибка не возвращается", messageResponse);
        assertEquals("Тело ответа не совпадает с документацией", messageResponse, "Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("Создание курьера с таким же логином")
    @Description("если создать пользователя с логином, который уже есть, возвращается ошибка.")
    public void courierCanNotBeCreatedWithIdenticalLogin() {
        // Arrange
        step("Готовим тестовые данные");
        Courier firstCourier = Courier.getRandom();
        Courier secondCourier = Courier.getRandom();
        secondCourier.login = firstCourier.login;

        // Act
        boolean isFirstCourierCreated = courierClient.create(firstCourier);
        String message = courierClient.notCreate(secondCourier);
        courierId = courierClient.login(CourierCredentials.from(firstCourier));

        // Assert
        assertEquals("Тело ответа не совпадает с документацией", message, "Этот логин уже используется");
    }
}

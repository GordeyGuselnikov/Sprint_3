package ru.praktikumservices.qascooter;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikumservices.qascooter.client.CourierClient;
import ru.praktikumservices.qascooter.model.Courier;
import ru.praktikumservices.qascooter.model.CourierCredentials;

import static io.qameta.allure.Allure.step;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

public class LoginCourierTest {

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
    @Description("")
    @DisplayName("курьер может авторизоваться, " +
            "успешный запрос возвращает id, " +
            "для авторизации нужно передать все обязательные поля")
    public void courierSuccessfullyLoggedIn() {
        // Arrange
        step("Готовим тестовые данные");
        Courier courier = Courier.getRandom();

        // Act
        boolean isCourierCreated = courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.from(courier));

        // Assert
        assertNotNull("Курьер не авторизовался!", courierId);
        assertThat("Courier ID is incorrect", courierId, is(not(0)));
    }

    @Test
    @Description("")
    @DisplayName("система вернёт ошибку, если неправильно указать логин и пароль")
    public void courierLogInWithIncorrectLoginAndPassword() {
        // Arrange
        step("Готовим тестовые данные");
        Courier courier = Courier.getRandom();

        // Act
        String message = courierClient.IncorrectLoginAndPassword(CourierCredentials.from(courier));

        // Assert
        assertEquals("", message, "Учетная запись не найдена");
    }

    @Test
    @Description("")
    @DisplayName("если какого-то поля нет, запрос возвращает ошибку")
    public void courierLogInWithoutRequiredField() {
        // Arrange
        step("Готовим тестовые данные");
        Courier courier = Courier.getRandom();

        // Act
        courier.login = null;
        String message = courierClient.IncorrectLogin(CourierCredentials.from(courier));

        // Assert
        assertEquals("", message, "Недостаточно данных для входа");
    }
}

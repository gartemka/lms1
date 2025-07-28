package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class SteamLoginTest extends BaseTest {

    @Test
    @DisplayName("Тест: Успешный вход в Steam с правильными данными")
    void testSuccessfulLogin() {
        // Мы уже открыли STEAM_BASE_URL в BaseTest.setUp()
        SteamHomePage homePage = new SteamHomePage(driver, wait);

        // Переход на страницу логина
        SteamLoginPage loginPage = homePage.clickLoginButton();

        // Выполняем вход, используя константы
        loginPage.login(Constants.TEST_USERNAME, Constants.TEST_PASSWORD);

        // Проверка: Убеждаемся, что пользователь вошел в систему
        // ВНИМАНИЕ: Если Steam Guard или капча появятся, этот тест может упасть.
        // Убедитесь, что TEST_USERNAME и TEST_PASSWORD - это данные рабочего тестового аккаунта.
        Assertions.assertTrue(homePage.isUserLoggedIn(), "Ошибка: Не удалось успешно войти с правильными данными или индикатор входа не отображается.");
        System.out.println("✓ Успешный вход! Индикатор входа отображается.");

        // После успешного входа, всегда выходим, чтобы тест был изолированным для следующего запуска
        driver.get(Constants.STEAM_LOGOUT_URL); // Используем константу
        // После выхода, возвращаемся на домашнюю страницу, чтобы подтвердить выход или для следующих тестов
        homePage.open();
        System.out.println("Выход из аккаунта после успешного теста для изоляции.");
    }

    @Test
    @DisplayName("Тест: Неудачный вход в Steam с неправильными данными")
    void testFailedLogin() {
        // Открываем страницу входа напрямую для изоляции этого теста
        SteamLoginPage loginPage = new SteamLoginPage(driver, wait);
        loginPage.openLoginPage();

        // Попытка входа с неправильными данными
        loginPage.login("wronguser12345", "wrongpassword12345"); // Неправильные данные не выносим в Constants, т.к. они специфичны для этого теста

        // Проверка 1: Ожидаем, что появится сообщение об ошибке
        Assertions.assertTrue(loginPage.isErrorMessageDisplayed(), "Ошибка: Сообщение об ошибке входа не отобразилось.");

        // Проверка 2: Убеждаемся, что текст ошибки содержит ожидаемые фразы
        String actualErrorMessage = loginPage.getErrorMessage();
        Assertions.assertTrue(actualErrorMessage.contains(Constants.ERROR_MESSAGE_PART_RU) ||
                        actualErrorMessage.contains(Constants.ERROR_MESSAGE_PART_EN),
                "Ошибка: Сообщение об ошибке не содержит ожидаемого текста. Фактический текст: " + actualErrorMessage);
        System.out.println("✓ Ошибка входа ожидаема: '" + actualErrorMessage + "'");

        // Проверка 3: Убеждаемся, что мы остались на странице логина (т.е. URL все еще содержит "login")
        Assertions.assertTrue(driver.getCurrentUrl().contains("login"), "Ошибка: Перешли на другую страницу после неудачного входа.");
        System.out.println("✓ Остались на странице входа после неудачной попытки, как и ожидалось.");
    }
}
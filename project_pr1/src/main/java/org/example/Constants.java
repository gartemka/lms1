package org.example;

public final class Constants {
    private Constants() {
        // Приватный конструктор, чтобы нельзя было создать экземпляры этого класса.
    }

    // --- Константы вашего проекта ---

    // URL для Steam
    public static final String STEAM_BASE_URL = "https://store.steampowered.com/";
    public static final String STEAM_LOGIN_URL = STEAM_BASE_URL + "login/";
    public static final String STEAM_LOGOUT_URL = STEAM_BASE_URL + "logout/";

    // Учетные данные для тестов (для тестового аккаунта, НЕ для реального!)
    // ВНИМАНИЕ: ЗАМЕНИТЕ ЭТИ ЗАГЛУШКИ НА РЕАЛЬНЫЕ УЧЕТНЫЕ ДАННЫЕ ВАШЕГО ТЕСТОВОГО АККАУНТА STEAM
    public static final String TEST_USERNAME = "ВАШ_ТЕСТОВЫЙ_ЛОГИН_STEAM";
    public static final String TEST_PASSWORD = "ВАШ_ТЕСТОВЫЙ_ПАРОЛЬ_STEAM";

    // Пути к драйверам браузеров
    // Обязательно убедитесь, что эти пути верны для вашей системы!
    // Если драйвер в /usr/local/bin/, то "chromedriver" или "geckodriver" достаточно.
    public static final String CHROMEDRIVER_PATH = "/usr/local/bin/chromedriver";
    public static final String GECKODRIVER_PATH = "/usr/local/bin/geckodriver";

    // Тип браузера для запуска тестов
    public static final String BROWSER_TYPE = "firefox"; // <-- Выберите "firefox" или "chrome"

    // Таймауты ожидания (в секундах)
    public static final int DEFAULT_WAIT_TIMEOUT_SECONDS = 15;

    // Сообщения об ошибках логина (Steam может выдавать их на разных языках)
    public static final String ERROR_MESSAGE_PART_RU = "неверное имя аккаунта или пароль";
    public static final String ERROR_MESSAGE_PART_EN = "Incorrect account name or password";
}
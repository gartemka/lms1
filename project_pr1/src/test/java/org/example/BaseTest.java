package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        // Логика выбора и запуска браузера
        if (Constants.BROWSER_TYPE.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", Constants.GECKODRIVER_PATH);
            FirefoxOptions options = new FirefoxOptions();
            // options.addArguments("--private"); // Временно закомментируйте
            // options.addArguments("--headless"); // Временно закомментируйте
            driver = new FirefoxDriver(options);
            System.out.println("Запускаем тесты в Firefox.");
        }   else if (Constants.BROWSER_TYPE.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", Constants.CHROMEDRIVER_PATH);
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--disable-notifications");
            options.addArguments("--incognito"); // Запускаем Chrome в режиме инкогнито для изоляции
            // options.addArguments("--headless"); // Раскомментируйте для запуска в безголовом режиме
            driver = new ChromeDriver(options);
            System.out.println("Запускаем тесты в Chrome.");
        } else {
            throw new IllegalArgumentException("Неизвестный тип браузера, указанный в Constants.BROWSER_TYPE: " + Constants.BROWSER_TYPE);
        }

        // Общие настройки для всех браузеров
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5)); // Неявное ожидание
        wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.DEFAULT_WAIT_TIMEOUT_SECONDS));

        // Первая навигация на базовый URL
        driver.get(Constants.STEAM_BASE_URL);
        // Ждем, пока URL содержит часть базового адреса, чтобы убедиться, что страница загружается
        wait.until(ExpectedConditions.urlContains(Constants.STEAM_BASE_URL.substring(8, Constants.STEAM_BASE_URL.length() - 1))); // Например, "store.steampowered.com"
        System.out.println("WebDriver и WebDriverWait инициализированы. Браузер открыт на: " + driver.getCurrentUrl());
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("WebDriver закрыт.");
        }
    }
}
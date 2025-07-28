package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SteamHomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Локаторы для главной страницы
    private By loginButton = By.xpath("//a[contains(@class, 'global_action_link') and text()='войти']");
    // Локатор для проверки, вошел ли пользователь (появляется только после логина)
    // Вам может понадобиться уточнить этот локатор, залогинившись вручную и проверив HTML
    private By loggedInAccountPulldown = By.xpath("//div[@id='account_pulldown']"); // Это распространенный элемент после логина

    public SteamHomePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    /**
     * Открывает главную страницу Steam.
     */
    public void open() {
        driver.get(Constants.STEAM_BASE_URL); // Используем константу
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginButton));
        System.out.println("Открыта домашняя страница Steam.");
    }

    /**
     * Нажимает кнопку "Войти" и возвращает объект страницы логина.
     */
    public SteamLoginPage clickLoginButton() {
        System.out.println("Нажимаем кнопку 'Войти' на домашней странице.");
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        // После клика мы ожидаем, что URL изменится на страницу логина
        wait.until(ExpectedConditions.urlContains("login"));
        return new SteamLoginPage(driver, wait); // Возвращаем новый Page Object для страницы логина
    }

    /**
     * Проверяет, вошел ли пользователь в систему.
     * Проверяем, что кнопка "Войти" стала невидимой ИЛИ появился элемент,
     * который виден только залогиненным пользователям (например, выпадающее меню аккаунта).
     */
    public boolean isUserLoggedIn() {
        try {
            // Ожидаем, что кнопка логина исчезнет
            boolean loginButtonInvisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(loginButton));
            // ИЛИ что появится индикатор залогиненного пользователя
            boolean loggedInElementVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(loggedInAccountPulldown)).isDisplayed();
            return loginButtonInvisible && loggedInElementVisible;
        } catch (Exception e) {
            // Если что-то пошло не так (например, кнопка логина осталась видимой),
            // значит, пользователь не залогинен
            System.out.println("Не удалось подтвердить вход: " + e.getMessage());
            return false;
        }
    }
}
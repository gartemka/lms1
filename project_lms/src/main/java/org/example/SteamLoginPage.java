package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SteamLoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // --- Локаторы с @FindBy ---
    @FindBy(how = How.XPATH, using = "//div[text()='Войдите, используя имя аккаунта']/following-sibling::input[@type='text']")
    private WebElement usernameField;

    @FindBy(how = How.XPATH, using = "//div[text()='Пароль']/following-sibling::input[@type='password']")
    private WebElement passwordField;

    @FindBy(how = How.XPATH, using = "//button[@type='submit' and text()='Войти']")
    private WebElement signInButton;

    @FindBy(how = How.XPATH, using = "//div[text()='" + Constants.INVALID_CREDENTIALS_FULL_MESSAGE_RU + "']")
    private WebElement errorMessage;

    @FindBy(how = How.XPATH, using = "//div[contains(@class, '_3zQ9hnkyXJEv7nN0oBU56M')]")
    private WebElement steamGuardContainer;

    @FindBy(how = How.XPATH, using = "//div[contains(@class, '_3zQ9hnkyXJEv7nN0oBU56M')]//div[contains(text(), 'Используйте мобильное приложение Steam, чтобы подтвердить вход')]")
    private WebElement steamGuardMessageTextElement;


    public SteamLoginPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this); // Инициализация элементов PageFactory
        System.out.println("Создан Page Object SteamLoginPage. Текущий URL: " + driver.getCurrentUrl());
    }

    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOf(usernameField)).sendKeys(username);
        System.out.println("Введено имя пользователя.");
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordField)).sendKeys(password);
        System.out.println("Введен пароль.");
    }

    public void clickSignInButton() {
        wait.until(ExpectedConditions.elementToBeClickable(signInButton)).click();
        System.out.println("Нажата кнопка 'Войти'.");
    }

    /**
     * Выполняет полную операцию входа на странице.
     */
    public void login(String username, String password) {
        System.out.println("Попытка входа с именем: " + username);
        enterUsername(username);
        enterPassword(password);
        clickSignInButton();
    }

    /**
     * Получает текст сообщения об ошибке.
     */
    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOf(errorMessage)).getText();
    }

    /**
     * Проверяет, отображается ли сообщение об ошибке.
     */
    public boolean isErrorMessageDisplayed() {
        try {
            // Используем более короткий таймаут для проверки отсутствия/видимости ошибки
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(Constants.SHORT_WAIT_TIMEOUT_SECONDS));
            return shortWait.until(ExpectedConditions.visibilityOf(errorMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Проверяет, отображается ли сообщение Steam Guard после ввода учетных данных.
     */
    public boolean isSteamGuardMessageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(steamGuardContainer));
            return wait.until(ExpectedConditions.visibilityOf(steamGuardMessageTextElement)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Возвращает WebElement сообщения Steam Guard (если нужен прямой доступ)
     */
    public WebElement getSteamGuardMessageTextElement() {
        return steamGuardMessageTextElement;
    }

    /**
     * Открывает страницу логина напрямую.
     */
    public void openLoginPage() {
        driver.get(Constants.STEAM_LOGIN_URL);
        System.out.println("Открыта страница входа напрямую: " + driver.getCurrentUrl());
    }
}
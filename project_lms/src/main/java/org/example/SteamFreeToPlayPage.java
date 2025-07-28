package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SteamFreeToPlayPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // --- Локаторы с @FindBy ---
    @FindBy(how = How.XPATH, using = "//div[contains(@class, 'ContentHubTitle') and text()='Бесплатные игры']")
    private WebElement pageHeader;

    @FindBy(how = How.XPATH, using = "//video[contains(@class, '_3sG-J5T8SrzM0Hjkda7sgL') and @autoplay]")
    private WebElement mainVideoPlayer;

    @FindBy(how = How.XPATH, using = "//div[@role='checkbox' and .//span[text()='Автовоспроизведение']]")
    private WebElement autoplayCheckbox;


    public SteamFreeToPlayPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this); // Инициализация элементов PageFactory
        // Убедимся, что мы на странице бесплатных игр
        wait.until(ExpectedConditions.urlContains(Constants.STEAM_FREE_TO_PLAY_URL));
        wait.until(ExpectedConditions.visibilityOf(pageHeader));
        System.out.println("Создан Page Object SteamFreeToPlayPage. Текущий URL: " + driver.getCurrentUrl());
    }

    /**
     * Получает заголовок страницы "Бесплатные игры".
     */
    public String getPageHeader() {
        return wait.until(ExpectedConditions.visibilityOf(pageHeader)).getText();
    }

    /**
     * Проверяет, отображается ли основной видео-плеер на странице.
     */
    public boolean isMainVideoPlayerDisplayed() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(Constants.SHORT_WAIT_TIMEOUT_SECONDS));
            return shortWait.until(ExpectedConditions.visibilityOf(mainVideoPlayer)).isDisplayed();
        } catch (Exception e) {
            System.out.println("Видео-плеер не отображается: " + e.getMessage());
            return false;
        }
    }

    /**
     * Проверяет, отображается ли чекбокс "Автовоспроизведение".
     */
    public boolean isAutoplayCheckboxDisplayed() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(Constants.SHORT_WAIT_TIMEOUT_SECONDS));
            return shortWait.until(ExpectedConditions.visibilityOf(autoplayCheckbox)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Кликает по чекбоксу "Автовоспроизведение".
     */
    public void clickAutoplayCheckbox() {
        wait.until(ExpectedConditions.elementToBeClickable(autoplayCheckbox)).click();
        System.out.println("Клик по чекбоксу 'Автовоспроизведение'.");
    }
}
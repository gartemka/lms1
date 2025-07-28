package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SteamGamePage {
    private WebDriver driver;
    private WebDriverWait wait;

    // --- Локаторы с @FindBy ---
    @FindBy(how = How.ID, using = "appHubAppName")
    private WebElement gameTitleElement;

    @FindBy(how = How.XPATH, using = "//div[@class='release_date']/div[@class='date']")
    private WebElement releaseDateElement;

    @FindBy(how = How.XPATH, using = "//div[@id='developers_and_publishers']//a[contains(@href, '/developer/')]")
    private WebElement developerElement;

    @FindBy(how = How.XPATH, using = "//div[@class='glance_tags popular_tags']//a[contains(@href, '/tags/')][1]")
    private WebElement mainGenreElement;

    @FindBy(how = How.XPATH, using = "//div[contains(@class, 'game_area_purchase_price')]")
    private WebElement gamePriceElement;


    public SteamGamePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this); // Инициализация элементов PageFactory
        // Ожидаем, что страница игры загрузилась (URL содержит "app/")
        wait.until(ExpectedConditions.urlContains("/app/"));
        wait.until(ExpectedConditions.visibilityOf(gameTitleElement)); // Ждем появления заголовка игры
        System.out.println("Создан Page Object SteamGamePage. Текущий URL: " + driver.getCurrentUrl());
    }

    /**
     * Получает заголовок (название) игры.
     */
    public String getGameTitle() {
        return wait.until(ExpectedConditions.visibilityOf(gameTitleElement)).getText();
    }

    /**
     * Получает дату выхода игры.
     */
    public String getReleaseDate() {
        return wait.until(ExpectedConditions.visibilityOf(releaseDateElement)).getText();
    }

    /**
     * Получает имя разработчика игры.
     */
    public String getDeveloper() {
        return wait.until(ExpectedConditions.visibilityOf(developerElement)).getText();
    }

    /**
     * Получает основной жанр игры.
     */
    public String getMainGenre() {
        return wait.until(ExpectedConditions.visibilityOf(mainGenreElement)).getText();
    }

    /**
     * Получает цену игры со страницы.
     */
    public String getGamePrice() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(gamePriceElement)).getText();
        } catch (Exception e) {
            System.out.println("Цена на странице игры не найдена: " + e.getMessage());
            return "Цена не найдена"; // Возвращаем, если цена не найдена
        }
    }
}
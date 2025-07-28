package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory; // Импорт PageFactory
import org.openqa.selenium.support.FindBy;     // Импорт FindBy
import org.openqa.selenium.support.How;        // Импорт How
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

public class SteamHomePage {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    // --- Локаторы с @FindBy ---
    @FindBy(how = How.XPATH, using = "//a[contains(@class, 'global_action_link') and text()='войти']")
    private WebElement loginButton;

    @FindBy(how = How.ID, using = "account_pulldown")
    private WebElement loggedInAccountPulldown;

    @FindBy(how = How.XPATH, using = "//div[@id='store_nav_area']//a[text()='Магазин']")
    private WebElement storeMenuButton;

    @FindBy(how = How.XPATH, using = "//div[@id='foryou_flyout']//a[text()='Главная страница']")
    private WebElement homePageSubMenuItem;

    @FindBy(how = How.XPATH, using = "//div[@id='noteworthy_tab']/span[@class='pulldown']/a[@class='pulldown_desktop' and text()='Новое и интересное']")
    private WebElement noteworthyMenuButton;

    @FindBy(how = How.XPATH, using = "//a[text()='Лидеры продаж']")
    private WebElement bestsellersSubMenuItem;

    @FindBy(how = How.XPATH, using = "//div[@id='genre_tab']/span[@class='pulldown']/a[@class='pulldown_desktop' and text()='Категории']")
    private WebElement categoriesMenuButton;

    @FindBy(how = How.XPATH, using = "//a[contains(@class, 'popup_menu_item') and text()='Бесплатные']")
    private WebElement freeToPlaySubMenuItem;

    @FindBy(how = How.ID, using = "store_nav_search_term")
    private WebElement searchInputField;

    // Используется для assert на главной странице, что header виден
    @FindBy(how = How.XPATH, using = "//h2[text()='Скидки и мероприятия']")
    private WebElement discountsAndEventsHeader;

    @FindBy(how = How.XPATH, using = "//h2[text()='Скидки и мероприятия']//a[contains(., 'Ещё') or contains(., 'Больше продуктов')]")
    private WebElement moreDiscountsButton;

    @FindBy(how = How.XPATH, using = "//div[@id='home_maincap_v7']//div[@class='app_name']/div")
    private WebElement firstFeaturedGameTitle;

    // Локаторы для вкладок "Популярные новинки", "Лидеры продаж" и т.д.
    @FindBy(how = How.ID, using = "tab_newreleases_content_trigger")
    private WebElement newReleasesTab;

    @FindBy(how = How.ID, using = "tab_topsellers_content_trigger")
    private WebElement topSellersTab;

    @FindBy(how = How.ID, using = "tab_upcoming_content_trigger")
    private WebElement upcomingTab;

    @FindBy(how = How.ID, using = "tab_specials_content_trigger")
    private WebElement specialsTab;

    @FindBy(how = How.ID, using = "tab_trendingfree_content_trigger")
    private WebElement trendingFreeTab;

    @FindBy(how = How.ID, using = "home_tabs_content")
    private WebElement tabContentContainer;

    @FindBy(how = How.XPATH, using = "//div[@class='home_tabs_row']//button[contains(@class, 'active')]//div[@class='tab_content']")
    private WebElement activeTabTitleElement;


    public SteamHomePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this); // Инициализация элементов PageFactory
    }

    public WebElement getLoginButton() { // Геттер теперь возвращает WebElement
        return loginButton;
    }

    public WebElement getCategoriesMenuButton() { // Геттер для кнопки "Категории"
        return categoriesMenuButton;
    }

    /**
     * Открывает главную страницу Steam. (Обычно вызывается в BaseTest)
     */
    public void open() {
        driver.get(Constants.STEAM_BASE_URL);
        wait.until(ExpectedConditions.visibilityOf(loginButton)); // Ожидание видимости элемента
        System.out.println("Открыта домашняя страница Steam.");
    }

    /**
     * Нажимает кнопку "Войти" и возвращает объект страницы логина.
     */
    public SteamLoginPage clickLoginButton() {
        System.out.println("Нажимаем кнопку 'Войти' на домашней странице.");
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        wait.until(ExpectedConditions.urlContains("login"));
        return new SteamLoginPage(driver, wait);
    }

    public boolean isUserLoggedIn() {
        try {
            boolean loginButtonInvisible = wait.until(ExpectedConditions.invisibilityOf(loginButton));
            boolean loggedInElementVisible = wait.until(ExpectedConditions.visibilityOf(loggedInAccountPulldown)).isDisplayed();
            return loginButtonInvisible && loggedInElementVisible;
        } catch (Exception e) {
            System.out.println("Не удалось подтвердить вход: " + e.getMessage());
            return false;
        }
    }

    /**
     * Наводит курсор на кнопку "Магазин" для активации выпадающего меню.
     */
    public void hoverOverStoreMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(storeMenuButton));
        actions.moveToElement(storeMenuButton).perform();
        System.out.println("Наведен курсор на меню 'Магазин'.");
        wait.until(ExpectedConditions.elementToBeClickable(homePageSubMenuItem));
    }

    /**
     * Кликает по пункту "Главная страница" в выпадающем меню "Магазин".
     */
    public void clickHomePageSubMenuItem() {
        wait.until(ExpectedConditions.elementToBeClickable(homePageSubMenuItem)).click();
        System.out.println("Клик по пункту 'Главная страница' в подменю 'Магазин'.");
        wait.until(ExpectedConditions.urlToBe(Constants.STEAM_BASE_URL));
    }

    /**
     * Вводит текст в поле поиска и нажимает Enter.
     */
    public void enterSearchTerm(String term) {
        wait.until(ExpectedConditions.elementToBeClickable(searchInputField)).sendKeys(term + Keys.ENTER);
        System.out.println("Введен текст поиска: " + term + " и нажата Enter.");
    }

    /**
     * Проверяет видимость заголовка "Скидки и мероприятия".
     */
    public boolean isDiscountsAndEventsHeaderDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(discountsAndEventsHeader)).isDisplayed();
    }

    /**
     * Кликает по кнопке "Ещё" или "Больше продуктов" в секции "Скидки и мероприятия".
     */
    public void clickMoreDiscountsButton() {
        wait.until(ExpectedConditions.elementToBeClickable(moreDiscountsButton)).click();
        wait.until(ExpectedConditions.urlContains("specials"));
        System.out.println("Нажата кнопка 'Ещё/Больше продуктов' в секции скидок.");
    }

    /**
     * Получает название первой игры в карусели "Популярное и рекомендуемое".
     */
    public String getFirstFeaturedGameTitle() {
        return wait.until(ExpectedConditions.visibilityOf(firstFeaturedGameTitle)).getText();
    }

    /**
     * Наводит курсор на меню "Новое и интересное" и кликает на "Лидеры продаж".
     */
    public SteamSearchResultsPage navigateToBestsellers() {
        wait.until(ExpectedConditions.elementToBeClickable(noteworthyMenuButton));
        actions.moveToElement(noteworthyMenuButton).perform();
        System.out.println("Наведен курсор на меню 'Новое и интересное'.");

        try {
            wait.until(ExpectedConditions.elementToBeClickable(bestsellersSubMenuItem)).click();
        } catch (Exception e) {
            System.out.println("Не удалось кликнуть по 'Лидеры продаж' обычным способом, пробуем JS-клик. Ошибка: " + e.getMessage());
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", bestsellersSubMenuItem);
        }
        System.out.println("Клик по пункту 'Лидеры продаж'.");
        wait.until(ExpectedConditions.urlContains("search/?filter=topsellers"));
        return new SteamSearchResultsPage(driver, wait);
    }

    /**
     * Наводит курсор на меню "Категории" и кликает на "Бесплатные".
     */
    public SteamFreeToPlayPage navigateToFreeToPlay() {
        wait.until(ExpectedConditions.elementToBeClickable(categoriesMenuButton));
        actions.moveToElement(categoriesMenuButton).perform();
        System.out.println("Наведен курсор на меню 'Категории'.");
        try {
            wait.until(ExpectedConditions.elementToBeClickable(freeToPlaySubMenuItem)).click();
        } catch (Exception e) {
            System.out.println("Не удалось кликнуть по 'Бесплатные' обычным способом, пробуем JS-клик. Ошибка: " + e.getMessage());
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", freeToPlaySubMenuItem);
        }
        System.out.println("Клик по пункту 'Бесплатные'.");
        return new SteamFreeToPlayPage(driver, wait);
    }

    /**
     * Кликает по вкладке "Популярные новинки" и проверяет её активность.
     */
    public void clickNewReleasesTab() {
        wait.until(ExpectedConditions.elementToBeClickable(newReleasesTab)).click();
        wait.until(ExpectedConditions.attributeContains(newReleasesTab, "class", "active"));
        wait.until(ExpectedConditions.visibilityOf(tabContentContainer));
        System.out.println("Клик по вкладке 'Популярные новинки'.");
    }

    /**
     * Кликает по вкладке "Лидеры продаж" и проверяет её активность.
     */
    public void clickTopSellersTab() {
        wait.until(ExpectedConditions.elementToBeClickable(topSellersTab)).click();
        wait.until(ExpectedConditions.attributeContains(topSellersTab, "class", "active"));
        wait.until(ExpectedConditions.visibilityOf(tabContentContainer));
        System.out.println("Клик по вкладке 'Лидеры продаж'.");
    }

    /**
     * Кликает по вкладке "Популярные будущие новинки" и проверяет её активность.
     */
    public void clickUpcomingTab() {
        wait.until(ExpectedConditions.elementToBeClickable(upcomingTab)).click();
        wait.until(ExpectedConditions.attributeContains(upcomingTab, "class", "active"));
        wait.until(ExpectedConditions.visibilityOf(tabContentContainer));
        System.out.println("Клик по вкладке 'Популярные будущие новинки'.");
    }

    /**
     * Кликает по вкладке "Скидки" и проверяет её активность.
     */
    public void clickSpecialsTab() {
        wait.until(ExpectedConditions.elementToBeClickable(specialsTab)).click();
        wait.until(ExpectedConditions.attributeContains(specialsTab, "class", "active"));
        wait.until(ExpectedConditions.visibilityOf(tabContentContainer));
        System.out.println("Клик по вкладке 'Скидки'.");
    }

    /**
     * Кликает по вкладке "Популярные бесплатные игры" и проверяет её активность.
     */
    public void clickTrendingFreeTab() {
        wait.until(ExpectedConditions.elementToBeClickable(trendingFreeTab)).click();
        wait.until(ExpectedConditions.attributeContains(trendingFreeTab, "class", "active"));
        wait.until(ExpectedConditions.visibilityOf(tabContentContainer));
        System.out.println("Клик по вкладке 'Популярные бесплатные игры'.");
    }

    /**
     * Получает заголовок активной вкладки.
     */
    public String getActiveTabTitle() {
        return wait.until(ExpectedConditions.visibilityOf(activeTabTitleElement)).getText();
    }
}
package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;

import java.io.File;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.Month;

public class PracticeFormPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // --- Локаторы формы с @FindBy ---
    @FindBy(how = How.ID, using = "firstName")
    private WebElement firstNameField;

    @FindBy(how = How.ID, using = "lastName")
    private WebElement lastNameField;

    @FindBy(how = How.ID, using = "userEmail")
    private WebElement userEmailField;

    // Радио-кнопки для пола - кликаем по LABEL, это надежнее
    @FindBy(how = How.CSS, using = "label[for='gender-radio-1']")
    private WebElement genderMaleRadioLabel;

    @FindBy(how = How.CSS, using = "label[for='gender-radio-2']")
    private WebElement genderFemaleRadioLabel;

    @FindBy(how = How.CSS, using = "label[for='gender-radio-3']")
    private WebElement genderOtherRadioLabel;

    @FindBy(how = How.ID, using = "userNumber")
    private WebElement userNumberField;

    @FindBy(how = How.ID, using = "dateOfBirthInput")
    private WebElement dateOfBirthInputField;

    @FindBy(how = How.ID, using = "subjectsInput")
    private WebElement subjectsInput;

    // Хобби - кликаем по LABEL, это надежнее
    @FindBy(how = How.CSS, using = "label[for='hobbies-checkbox-1']")
    private WebElement hobbiesSportsCheckboxLabel;

    @FindBy(how = How.CSS, using = "label[for='hobbies-checkbox-2']")
    private WebElement hobbiesReadingCheckboxLabel;

    @FindBy(how = How.CSS, using = "label[for='hobbies-checkbox-3']")
    private WebElement hobbiesMusicCheckboxLabel;

    @FindBy(how = How.ID, using = "uploadPicture")
    private WebElement uploadPictureInput;

    @FindBy(how = How.ID, using = "currentAddress")
    private WebElement currentAddressField;

    @FindBy(how = How.ID, using = "react-select-3-input") // Поле ввода для State
    private WebElement stateInput;

    @FindBy(how = How.ID, using = "react-select-4-input") // Поле ввода для City
    private WebElement cityInput;

    @FindBy(how = How.ID, using = "submit")
    private WebElement submitButton;

    // --- Локаторы модального окна подтверждения ---
    @FindBy(how = How.ID, using = "example-modal-sizes-title-lg")
    private WebElement modalTitle;

    @FindBy(how = How.XPATH, using = "//div[@class='table-responsive']//tbody/tr")
    private List<WebElement> modalTableRows; // List<WebElement> для строк таблицы

    @FindBy(how = How.ID, using = "closeLargeModal")
    private WebElement closeSubmitModalButton;


    public PracticeFormPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this); // Инициализация элементов PageFactory
        wait.until(ExpectedConditions.visibilityOf(firstNameField));
        System.out.println("Инициализирован Page Object: Practice Form Page.");
    }

    // --- Приватные методы для динамического создания локаторов ---
    private By getSubjectOptionByText(String text) {
        return By.xpath(String.format("//div[contains(@id, 'react-select') and contains(@id, 'option') and text()='%s']", text));
    }

    private By getStateOptionByText(String text) {
        return By.xpath(String.format("//div[contains(@id, 'react-select-3-option') and text()='%s']", text));
    }

    private By getCityOptionByText(String text) {
        return By.xpath(String.format("//div[contains(@id, 'react-select-4-option') and text()='%s']", text));
    }


    public void setFirstName(String firstName) {
        wait.until(ExpectedConditions.elementToBeClickable(firstNameField)).sendKeys(firstName);
        System.out.println("Заполнено имя: " + firstName);
    }

    public void setLastName(String lastName) {
        wait.until(ExpectedConditions.elementToBeClickable(lastNameField)).sendKeys(lastName);
        System.out.println("Заполнена фамилия: " + lastName);
    }

    public void setEmail(String email) {
        wait.until(ExpectedConditions.elementToBeClickable(userEmailField)).sendKeys(email);
        System.out.println("Заполнен Email: " + email);
    }

    public void setGender(String gender) {
        WebElement genderElement;
        switch (gender.toLowerCase()) {
            case "male":
                genderElement = genderMaleRadioLabel;
                break;
            case "female":
                genderElement = genderFemaleRadioLabel;
                break;
            case "other":
                genderElement = genderOtherRadioLabel;
                break;
            default:
                throw new IllegalArgumentException("Неверный пол: " + gender);
        }
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", genderElement);
        System.out.println("Выбран пол: " + gender);
    }

    public void setMobileNumber(String mobileNumber) {
        wait.until(ExpectedConditions.elementToBeClickable(userNumberField)).sendKeys(mobileNumber);
        System.out.println("Заполнен номер телефона: " + mobileNumber);
    }

    public void setDateOfBirth(int year, int month, int day) {
        wait.until(ExpectedConditions.elementToBeClickable(dateOfBirthInputField));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dateOfBirthInputField);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dateOfBirthInputField);

        WebElement monthDropdownElement = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".react-datepicker__month-select")));
        Select monthSelect = new Select(monthDropdownElement);
        String monthName = Month.of(month).toString().charAt(0) + Month.of(month).toString().substring(1).toLowerCase();
        monthSelect.selectByVisibleText(monthName);

        WebElement yearDropdownElement = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".react-datepicker__year-select")));
        Select yearSelect = new Select(yearDropdownElement);
        yearSelect.selectByVisibleText(String.valueOf(year));

        By dayLocator = By.xpath(String.format("//div[contains(@class, 'react-datepicker__day') and not(contains(@class, 'outside-month')) and text()='%d']", day));
        wait.until(ExpectedConditions.elementToBeClickable(dayLocator)).click();
        System.out.println("Заполнена дата рождения: " + day + "/" + month + "/" + year);
    }

    public void setSubjects(String... subjects) {
        for (String subject : subjects) {
            wait.until(ExpectedConditions.elementToBeClickable(subjectsInput)).sendKeys(subject);
            wait.until(ExpectedConditions.elementToBeClickable(getSubjectOptionByText(subject))).click();
            System.out.println("Добавлен предмет: " + subject);
        }
    }

    public void setHobbies(String... hobbies) {
        for (String hobby : hobbies) {
            WebElement hobbyCheckbox;
            switch (hobby.toLowerCase()) {
                case "sports":
                    hobbyCheckbox = hobbiesSportsCheckboxLabel;
                    break;
                case "reading":
                    hobbyCheckbox = hobbiesReadingCheckboxLabel;
                    break;
                case "music":
                    hobbyCheckbox = hobbiesMusicCheckboxLabel;
                    break;
                default:
                    throw new IllegalArgumentException("Неверное хобби: " + hobby);
            }
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", hobbyCheckbox);
            System.out.println("Выбрано хобби: " + hobby);
        }
    }

    public void uploadPicture(String filePath) {
        File uploadFile = new File(filePath);
        if (!uploadFile.exists()) {
            throw new IllegalArgumentException("Файл для загрузки не найден по пути: " + filePath);
        }
        uploadPictureInput.sendKeys(uploadFile.getAbsolutePath());
        System.out.println("Загружено изображение: " + uploadFile.getName());
    }

    public void setCurrentAddress(String address) {
        wait.until(ExpectedConditions.elementToBeClickable(currentAddressField)).sendKeys(address);
        System.out.println("Заполнен текущий адрес.");
    }

    public void setStateAndCity(String state, String city) {
        // Выбираем State
        wait.until(ExpectedConditions.elementToBeClickable(stateInput));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", stateInput);
        stateInput.sendKeys(state);
        wait.until(ExpectedConditions.elementToBeClickable(getStateOptionByText(state))).click();
        System.out.println("Выбрана область: " + state);

        // Выбираем City
        wait.until(ExpectedConditions.elementToBeClickable(cityInput));
        cityInput.sendKeys(city);
        wait.until(ExpectedConditions.elementToBeClickable(getCityOptionByText(city))).click();
        System.out.println("Выбран город: " + city);
    }

    public void submitForm() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);
        System.out.println("Нажата кнопка 'Submit'.");
    }

    public Map<String, String> getSubmissionData() {
        wait.until(ExpectedConditions.visibilityOf(modalTitle));
        System.out.println("Модальное окно подтверждения отображается.");

        Map<String, String> submittedData = new HashMap<>();
        // Используем List<WebElement> modalTableRows, который уже инициализирован PageFactory
        // Просто ждем, что эти элементы станут видимыми
        wait.until(ExpectedConditions.visibilityOfAllElements(modalTableRows));
        for (WebElement row : modalTableRows) {
            String label = row.findElement(By.xpath("./td[1]")).getText();
            String value = row.findElement(By.xpath("./td[2]")).getText();
            submittedData.put(label, value);
        }
        System.out.println("Получены данные из модального окна.");
        return submittedData;
    }

    public void closeSubmissionModal() {
        wait.until(ExpectedConditions.elementToBeClickable(closeSubmitModalButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", closeSubmitModalButton);
        System.out.println("Модальное окно подтверждения закрыто.");
        wait.until(ExpectedConditions.invisibilityOf(modalTitle));
    }
}
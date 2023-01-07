package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderCardNegativeTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldTestV1() {
        driver.findElement(By.cssSelector("[data-test-id = name] input")).sendKeys("Aleksei Zverev");
        driver.findElement(By.cssSelector("[data-test-id = phone] input")).sendKeys("+79119294414");
        driver.findElement(By.cssSelector("[data-test-id = agreement]")).click();
        driver.findElement(By.className("button")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id = name].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldTestV2() {
        driver.findElement(By.cssSelector("[data-test-id = name] input")).clear();
        driver.findElement(By.cssSelector("[data-test-id = phone] input")).sendKeys("+79119294414");
        driver.findElement(By.cssSelector("[data-test-id = agreement]")).click();
        driver.findElement(By.className("button")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id = name].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldTestV3() {
        driver.findElement(By.cssSelector("[data-test-id = name] input")).sendKeys("Алексей Зверев");
        driver.findElement(By.cssSelector("[data-test-id = phone] input")).sendKeys("+791119294414");
        driver.findElement(By.cssSelector("[data-test-id = agreement]")).click();
        driver.findElement(By.className("button")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id = phone].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldTestV4() {
        driver.findElement(By.cssSelector("[data-test-id = name] input")).sendKeys("Алексей Зверев");
        driver.findElement(By.cssSelector("[data-test-id = phone] input")).clear();
        driver.findElement(By.cssSelector("[data-test-id = agreement]")).click();
        driver.findElement(By.className("button")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id = phone].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldTestV5() {
        driver.findElement(By.cssSelector("[data-test-id = name] input")).sendKeys("Алексей Зверев");
        driver.findElement(By.cssSelector("[data-test-id = phone] input")).sendKeys("+79119294414");
        driver.findElement(By.className("button")).click();
        assertTrue(driver.findElement(By.cssSelector("[data-test-id = agreement].input_invalid")).isDisplayed());
    }

}


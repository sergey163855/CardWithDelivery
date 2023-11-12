package ru.netology.selenide;

import com.codeborne.selenide.Configuretion;
import io.github.bonigarcia.wdm.WebdriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.locks.Condition;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class CardWithDeliveryTest {
    private String generateDate(int addDays,String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void shouldBeSuccessfullyCompleted() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Иркутск");
        String currentDate = generateDate(4,"dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME),Keys.DELETE);
        $("[data-test-id=date] input").sendKeys(currentDate);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79500000000");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("notification__content")
                .shouldBe(Condition.visible,Duration.ofSeconds(25))
                .shouldHave(Condition.exactText("Встреча успешно заплонирована на " + currentDate));
    }
}

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class WikipediaTest {

    @BeforeAll
    static void beforeAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        Configuration.headless = true;
    }

    @Test
    public void testWikipediaHomePage() {
        step("Шаг 01 - Открыть википедию", () -> {
            open("https://www.wikipedia.org");
        });

        step("Шаг 02 - Проверить текст на странице", () -> {
            $("body").shouldHave(text("Wikipedia"));
        });
    }
}
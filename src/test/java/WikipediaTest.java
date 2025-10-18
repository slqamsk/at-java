import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class WikipediaTest {

    @BeforeAll
    static void beforeAll() {
        // Allure listener
        SelenideLogger.addListener("allure", new AllureSelenide());

        // Настройки для CI
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 10000;

        // Устанавливаем remote только если он указан и не пустой
        String remoteUrl = System.getProperty("selenide.remote");
        if (remoteUrl != null && !remoteUrl.trim().isEmpty()) {
            Configuration.remote = remoteUrl;
        }

        // Для CI устанавливаем headless режим
        boolean isCi = System.getProperty("selenide.headless", "false").equals("true");
        Configuration.headless = isCi;

        // Дополнительные настройки для стабильности в CI
        Configuration.browserCapabilities.setCapability("acceptInsecureCerts", true);
        Configuration.pageLoadStrategy = "eager";

        // Уникальный user data directory для избежания конфликтов
        if (isCi) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments(
                    "--user-data-dir=/tmp/chrome-user-data-" + System.currentTimeMillis(),
                    "--no-sandbox",
                    "--disable-dev-shm-usage"
            );
            Configuration.browserCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
        }
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


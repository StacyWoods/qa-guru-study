package tests.allure;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.BaseTests;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.By.linkText;
import static org.openqa.selenium.By.partialLinkText;

@Owner("StacyWoods")
@Severity(SeverityLevel.BLOCKER)
@Feature("QAGURU 12-7")
@Story("Просмотр PR в репозитории")
public class SelenideTest extends BaseTests {

    @Test
    @DisplayName("Чистый Selenide (с Listener)")
    public void testGithubPR() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        open("https://github.com");

        $(".header-search-input").click();
        $(".header-search-input").sendKeys(REPOSITORY);
        $(".header-search-input").submit();

        $(linkText("StacyWoods/qa-guru-study")).click();
        $(partialLinkText("Pull requests")).click();
        $(partialLinkText("Closed")).click();
        $(withText("#" + PR_NUMBER)).click();
    }
}

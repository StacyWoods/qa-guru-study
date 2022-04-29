package tests.allure;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.WebDriverRunner;
import helpers.Attach;
import io.qameta.allure.*;
import org.junit.jupiter.api.Tag;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.openqa.selenium.By.linkText;
import static org.openqa.selenium.By.partialLinkText;

@Tag("demoqa-jenkins")
public class WebSteps {
    @Step("Открываем главную страницу github")
    public void openMainPage() {
        open("https://github.com");
    }

    @Step("Ищем реопзиторий {repo}")
    public void searchForRepository(String repo) {
        $(".header-search-input").click();
        $(".header-search-input").sendKeys(repo);
        $(".header-search-input").submit();
    }

    @Step("Переходим по ссылке репозитория {repo}")
    public void clickOnRepositoryLink(String repo) {
        $(linkText(repo)).click();
    }

    @Step("Переходим на таб Pull requests, Closed (2 клика)")
    public void openPRClosedTab() {
        $(partialLinkText("Pull requests")).click();
        $(partialLinkText("Closed")).click();
    }

    @Step("Проверяем что существует Pull request с номером {number}")
    public void shouldSeePRWithNumber(int number) {
        $(withText("#" + number)).should(Condition.visible);
        attachScreenshot();
    }

    @Attachment(value = "Идеальный скриншот", type = "image/png", fileExtension = "png")
    public byte[] attachScreenshot() {
        return ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }
}

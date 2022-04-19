package tests.allure;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import tests.BaseTests;

public class AnnotatedStepTest extends BaseTests {

    @ValueSource(booleans = { true, false })
    @DisplayName("Шаги с аннотацией @Step")
    @ParameterizedTest
    public void testGithubIssue(boolean listenerFlag) {
        if (listenerFlag) SelenideLogger.addListener("allure", new AllureSelenide());
        WebSteps steps = new WebSteps();

        steps.openMainPage();
        steps.searchForRepository(REPOSITORY);
        steps.clickOnRepositoryLink(REPOSITORY);
        steps.openPRClosedTab();
        steps.shouldSeePRWithNumber(PR_NUMBER);
    }
}

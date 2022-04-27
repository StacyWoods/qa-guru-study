package tests;

import enums.LinkedInMenuItem;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import pages.LinkedinPage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;

@Tag("demoqa-jenkins")
public class ProvidersTest extends BaseTests {

    protected LinkedinPage linkedinPage = new LinkedinPage();

    @ValueSource(strings = {
            "Discover",
            "People",
            "Learning",
            "Jobs"
    })
    @ParameterizedTest(name = "Test By ValueSource {0}")
    void menuTest(String testData) {
        linkedinPage.openPage();

        linkedinPage.menuItemList.find(text(testData)).shouldBe(visible);
    }

    @CsvSource(value = {
            "Explore topics you are interested in|Science and Environment",
            "Find the right job or internship for you|Engineering"
    },
            delimiter = '|'
    )
    @ParameterizedTest(name = "Test By CsvSource: testing {0}, expecting {1}")
    void  comProductComplexTest(String testData, String expectedResult) {
        linkedinPage.openPage();

        $$("h2").find(text(testData)).scrollTo();
        $$("a").find(text(expectedResult)).shouldBe(visible);
    }

    @EnumSource(LinkedInMenuItem.class)
    @ParameterizedTest()
    void  navComMenuTest(LinkedInMenuItem testData) {
        linkedinPage.openPage();

        linkedinPage.menuItemList.find(text(String.valueOf(testData))).shouldBe(visible);
    }
}
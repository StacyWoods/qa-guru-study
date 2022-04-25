package tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import enums.MenuItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class SearchTests extends BaseTests {

    @Test
    void successfulSearchTest() {
        open("https://www.google.com/");
        $(byName("q")).setValue("selenide").pressEnter();
        $("#search").shouldHave(text("https://selenide.org"));
        System.out.println("Google was successfully found!");
    }

    @ValueSource(strings = {
            "Selenide",
            "JUnit"
    })
    @ParameterizedTest(name = "Проверка поиска в яндексе по слову {0}")
    void yaSearchTest(String testData) {
        // Предусловия:
        open("https://ya.ru");
        // Шаги:
        $("#text").setValue(testData);
        $("button[type='submit']").click();
        // Ожидаемый результат:
        $$(".serp-item")
                .find(Condition.text(testData))
                .shouldBe(visible);
    }

    @CsvSource(value = {
            "Selenide, is an open source library for test",
            "JUnit, Support JUnit"
    })
    @ParameterizedTest(name = "Проверка поиска в яндексе по слову {0}, ожидаем результат: {1}")
    void yaSearchComplexTest(String testData, String expectedResult) {
        // Предусловия:
        open("https://ya.ru");
        // Шаги:
        $("#text").setValue(testData);
        $("button[type='submit']").click();
        // Ожидаемый результат:
        $$(".serp-item")
                .find(Condition.text(expectedResult))
                .shouldBe(visible);
    }

    static Stream<Arguments> methodSourceExampleTest() {
        return Stream.of(
                Arguments.of("first string", List.of(42, 13)),
                Arguments.of("second string", List.of(1, 2))
        );
    }

    @MethodSource("methodSourceExampleTest")
    @ParameterizedTest
    void methodSourceExampleTest(String first, List<Integer> second) {
        System.out.println(first + " and list: " + second);
    }

    @EnumSource(MenuItem.class)
    @ParameterizedTest()
    void yaSearchMenuTest(MenuItem testData) {
        // Предусловия:
        open("https://ya.ru");
        // Шаги:
        $("#text").setValue("Allure TestOps");
        $("button[type='submit']").click();
        // Ожидаемый результат:
        $$(".navigation__item")
                .find(Condition.text(testData.rusName))
                .click();

        System.out.println(MenuItem.IMG.rusName);

        Assertions.assertEquals(
                2,
                WebDriverRunner.getWebDriver().getWindowHandles().size()
        );
    }

    @Test
    void githubSelenideSearchTest() {
        open("https://github.com/selenide/selenide");
        $("#wiki-tab").click();
        $(".markdown-body").find(byText("Soft assertions")).click();
        $("#wiki-wrapper").shouldHave(text("JUnit5 extension"), text("Using JUnit5 extend test class"));
    }
    @Test

    void dragAndDropTest() {
        open("https://the-internet.herokuapp.com/drag_and_drop");

        SelenideElement elem1 = $("#column-a");
        SelenideElement elem2 = $("#column-b");

        // исходное состояние
        elem1.$("header").shouldBe(text("A"));
        elem2.$("header").shouldBe(text("B"));

        elem1.dragAndDropTo(elem2);

        // перемещенный результат
        elem1.$("header").shouldBe(text("B"));
        elem2.$("header").shouldBe(text("A"));
    }

    @Test
    void shouldFindSelenideAsFirstRepository(){
        open("https://github.com");
        $("[data-test-selector=nav-search-input]").setValue("selenide").pressEnter();
        $$("ul.repo-list li").first().$("a").click();
        $("h2").shouldHave(text("selenide / selenide"));
    }
}
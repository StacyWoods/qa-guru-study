package tests.allure;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.BaseTests;

import java.lang.annotation.*;

public class LabelsTest extends BaseTests {

    @Test
    @Owner(OWNER)
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Задачи в репозитории")
    @Story("Просмотр созданных задач в репозитории")
    @DisplayName("Tест лейблов @")
    @Link(value = "Тестинг", url = "https://github.com")
    public void testAnnotated() {
    }

    @Test
    @DisplayName("Tест лейблов через Allure")
    public void testCode() {
        Allure.label("owner", OWNER);
        Allure.label("severity", SeverityLevel.CRITICAL.value());
        Allure.feature("Задачи в репозитории");
        Allure.story("Просмотр созданных задач в репозитории");
        Allure.link("Тестинг", "https://github.com");
    }

    @CustomAnnotationSW
    @Test
    @DisplayName("Test CustomAnnotationSW")
    public void testCustomAnnotation() {
        Allure.link("Тестинг", "https://github.com");
    }

    @Test
    @DisplayName("Annotated By Parameter")
    public void testAnnotatedByParameter() {
        Allure.parameter("Регион", "Москва и Московская область");
        Allure.parameter("Город", "Москва");
    }

    @Documented
    @Owner(OWNER)
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Задачи в custom репозитории")
    @Story("Просмотр созданных задач в custom репозитории")
    @Target({ ElementType.TYPE, ElementType.METHOD })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface CustomAnnotationSW {
    }
}

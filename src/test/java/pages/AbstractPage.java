package pages;

import org.openqa.selenium.By;
import pages.components.CalendarComponent;
import tests.BaseTests;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;

public abstract class AbstractPage {
    protected CalendarComponent calendar = new CalendarComponent();

    protected BaseTests baseTests = new BaseTests();

    public void scrollToById(String value) {
        $(By.id(value)).scrollTo();
    }

    public void removeBy(String value) {
        executeJavaScript("$('"+value+"').remove()");
//        executeJavaScript("$('"+value+"').css('display', 'none')");
    }

    public void submitById(String value) {
        $(By.id(value)).click();
    }
}

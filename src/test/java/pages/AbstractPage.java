package pages;

import org.openqa.selenium.By;
import pages.components.CalendarComponent;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;

public abstract class AbstractPage {
    protected CalendarComponent calendar = new CalendarComponent();

    public void scrollToById(String value) {
        $(By.id(value)).scrollTo();
    }

    public void removeBy(String value) {
        executeJavaScript("$('"+value+"').remove()");
    }

    public void removeById(String id) {
        executeJavaScript("document.getElementById('"+id+"').remove()");
    }

    public void submitById(String value) {
        $(By.id(value)).click();
    }
}

package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class LinkedinPage extends AbstractPage {

    public SelenideElement acceptButton = $x("//div[@id='artdeco-global-alert-container']/*/*/*/*/button[@data-control-name='ga-cookie.consent.accept.v3']");
    public ElementsCollection menuItemList = $$(".top-nav-link__label-text");

    public LinkedinPage openPage() {
        setBaseUrl("https://www.linkedin.com");
        open(getBaseUrl());
        if(acceptButton.isDisplayed()) {
            removeById("artdeco-global-alert-container");
        }
        if(acceptButton.isDisplayed()) {
            acceptButton.click();
        }

        return this;
    }
}

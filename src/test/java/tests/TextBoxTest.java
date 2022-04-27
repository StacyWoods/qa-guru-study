package tests;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("demoqa-jenkins")
public class TextBoxTest extends BaseTests {

    @Test
    void fillTextForm() {
        open("/text-box");
        executeJavaScript("$('footer').css('display', 'none')");
        executeJavaScript("$('#adplus-anchor').css('display', 'none')");
        executeJavaScript("$('#close-fixedban').css('display', 'none')");
        executeJavaScript("$('#adplus-anchor').remove()");

        $("[id=userName]").setValue("Stacy Woods");
        $("[id=userEmail]").setValue("stacy.skytten@gmail.com");
        $("[id=currentAddress]").setValue("Montenegro, Sutomore, plaz");
        $("[id=permanentAddress]").setValue("Planet Earth");
        $("[id=submit]").click();

        //Asserts
        $("[id=output]").shouldHave(text(userData.get("full_name")), text(userData.get("email")), text("Montenegro, Sutomore, plaz"))
                .shouldHave(text("Planet Earth"));
        $("[id=output] [id=name]").shouldHave(text(userData.get("full_name")));
        $("[id=output]").$("[id=name]").shouldHave(text(userData.get("full_name")));

        $("p[id=permanentAddress]").shouldHave(text("Permananet Address :Planet Earth"));
        $("[id=permanentAddress]", 1).shouldHave(text("Permananet Address :Planet Earth"));

        String expectedAddress = "Permananet Address :Planet Earth";
        String actualAddress = $("p[id=permanentAddress]").text();
        assertEquals(expectedAddress, actualAddress);
    }
}

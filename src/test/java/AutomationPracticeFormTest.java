import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.io.File;
import java.util.*;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$x;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AutomationPracticeFormTest extends BaseTests {

    @Test
    void checkStudentsRegistrationForm() {
        open("/automation-practice-form");

        fillStudentsRegistrationForm();

        String[] expectedResultsData = {ADDRESS, BIRTH, GENDER, HOBBIES, PHONE, FILE_NAME, STATE_AND_CITY, EMAIL, FULL_NAME, SUBJECTS};
        List<String> expectedResults = Arrays.asList(expectedResultsData);
        var submittedData = $(new By.ByTagName("html")).innerHtml();
        var mappedResults = getResponseData(submittedData).values().stream().toList();

        assertEquals(mappedResults, expectedResults);
    }

    private void fillStudentsRegistrationForm() {
        $("[id=firstName]").setValue(NAME);
        $("[id=lastName]").setValue(SURNAME);
        $("[id=userEmail]").setValue(EMAIL);
        $("#gender-radio-2").parent().click();
        $("[id=userNumber]").setValue(PHONE);

        executeJavaScript("$('footer').css('display', 'none')");
        executeJavaScript("$('#adplus-anchor').css('display', 'none')");
        executeJavaScript("$('#close-fixedban').css('display', 'none')");

        $("[id=dateOfBirthInput]").click();
        $x(String.format("//select[@class='react-datepicker__year-select']/option[@value=%s]", YEAR)).click();
        $x(String.format("//select[@class='react-datepicker__month-select']/option[@value=%s]", MONTH_NUMBER)).click();
        $x(String.format("//div[@class='react-datepicker__day react-datepicker__day--%s']", DAY_NUMBER)).click();

        $("[id=subjectsInput]").setValue("d").click();
        $("[id=react-select-2-option-0]").click(); // Hindi
//        $("[id=subjectsInput]").setValue("Hindi").pressEnter();

        $x("//input[@id='hobbies-checkbox-2']/..").click(); // Reading

        $x("//input[@id='uploadPicture']").uploadFile(new File(getImagesPath() + "/" + FILE_NAME));

        $("[id=currentAddress]").setValue(ADDRESS);

        $("[id=submit]").scrollTo();

        $x("//*[@id='state']/div/div/div").click();
        if ($x("//div[@id='react-select-3-option-3']").isDisplayed()) {
            $x("//div[@id='react-select-3-option-3']").click(); // Rajasthan
        }
        $x("//*[@id='city']/div/div/div").click();
        if ($x("//div[@id='react-select-4-option-0']").isDisplayed()) {
            $x("//div[@id='react-select-4-option-0']").click(); // Jaipur
        }

        $("[id=submit]").click();
    }

    private TreeMap<String, String> getResponseData (String submittedData) {
        var elements = Jsoup.parseBodyFragment(submittedData).getElementsByTag("tbody").get(0)
                .getElementsByTag("tr");
        var result = new HashMap<String, String>();
        elements.forEach(element -> {
            result.put(
                    element.getElementsByTag("td").get(0).text(),
                    element.getElementsByTag("td").get(1).text()
            );
        });
        return new TreeMap<> (result);
    }
}

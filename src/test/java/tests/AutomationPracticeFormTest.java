package tests;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AutomationPracticeFormTest extends BaseTests {

    private String[] expectedResultsData = {
            userData.get("address"),
            userData.get("birth"),
            userData.get("gender"),
            userData.get("hobbies"),
            userData.get("phone"),
            userData.get("file_name"),
            userData.get("state_and_city"),
            userData.get("email"),
            userData.get("full_name"),
            userData.get("subjects")
    };

    @Test
    void checkStudentsRegistrationForm() {
        open("/automation-practice-form");

        fillStudentsRegistrationForm();


        List<String> expectedResults = Arrays.asList(expectedResultsData);
        var submittedData = $(new By.ByTagName("html")).innerHtml();
        var mappedResults = getResponseData(submittedData).values().stream().collect(Collectors.toList());

        assertEquals(mappedResults, expectedResults);
    }

    private void fillStudentsRegistrationForm() {
        $("[id=firstName]").setValue(userData.get("name"));
        $("[id=lastName]").setValue(userData.get("surname"));
        $("[id=userEmail]").setValue(userData.get("email"));
        $("#gender-radio-2").parent().click();
        $("[id=userNumber]").setValue(userData.get("phone"));

        executeJavaScript("$('footer').css('display', 'none')");
        executeJavaScript("$('#adplus-anchor').css('display', 'none')");
        executeJavaScript("$('#close-fixedban').css('display', 'none')");

        $("[id=dateOfBirthInput]").click();
//        $(".react-datepicker__year-select").selectOption(YEAR);
        $x(String.format("//select[@class='react-datepicker__year-select']/option[@value=%s]", userData.get("year"))).click();
        $x(String.format("//select[@class='react-datepicker__month-select']/option[@value=%s]", userData.get("month_number"))).click();
        $x(String.format("//div[@class='react-datepicker__day react-datepicker__day--0%s']", userData.get("day"))).click();

        $("[id=subjectsInput]").setValue("d").click();
        $("[id=react-select-2-option-0]").click(); // Hindi
//        $("[id=subjectsInput]").setValue("Hindi").pressEnter();

//        $("#hobbiesWrapper").$(byText("Reading")).click(); // Reading
        $x("//input[@id='hobbies-checkbox-2']/..").click(); // Reading


//        $x("//input[@id='uploadPicture']").uploadFromClasspath("images/" + FILE_NAME);
        $x("//input[@id='uploadPicture']").uploadFile(new File(getImagesPath() + "/" + userData.get("file_name")));

        $("[id=currentAddress]").setValue(userData.get("address"));

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

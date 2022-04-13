package tests;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import pages.RegistrationFormPage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AutomationPracticeFormByPageObjectTest extends BaseTests {

    RegistrationFormPage registrationFormPage = new RegistrationFormPage();

    @Test
    void checkStudentsRegistrationForm() {
        registrationFormPage.openPage();

        fillStudentsRegistrationForm();

        String[] expectedResultsData = {ADDRESS, BIRTH, GENDER, HOBBIES, PHONE, FILE_NAME, STATE_AND_CITY, EMAIL, FULL_NAME, SUBJECTS};
        List<String> expectedResults = Arrays.asList(expectedResultsData);
        var submittedData = $(new By.ByTagName("html")).innerHtml();
        var mappedResults = getResponseData(submittedData).values().stream().toList();

        assertEquals(mappedResults, expectedResults);
    }

    private void fillStudentsRegistrationForm() {
        registrationFormPage
                .setFirstName(NAME)
                .setLastName(SURNAME)
                .setEmail(EMAIL)
                .setGender(GENDER)
                .setUserNumber(PHONE)
                .setBirthDate(DAY, MONTH, YEAR);

        registrationFormPage.removeBy("#adplus-anchor");
        registrationFormPage.scrollToById("submit");

        registrationFormPage
                .setSubjects(SUBJECTS)
                .setHobbies(HOBBIES)
                .uploadPicture(FILE_NAME)
                .setCurrentAddress(ADDRESS)
                .setState(STATE)
                .setCity(CITY);

        registrationFormPage.submitById("submit");
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

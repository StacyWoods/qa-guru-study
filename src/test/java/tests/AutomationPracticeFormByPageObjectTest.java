package tests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import pages.RegistrationFormPage;

import java.util.Arrays;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AutomationPracticeFormByPageObjectTest extends BaseTests {

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

    protected RegistrationFormPage registrationFormPage = new RegistrationFormPage();

    @Test
    void checkStudentsRegistrationForm() {
        registrationFormPage.openPage();

        fillStudentsRegistrationForm();

        List<String> expectedResults = Arrays.asList(expectedResultsData);
        var submittedData = $(new By.ByTagName("html")).innerHtml();
        var mappedResults = getResponseDataFromTable(submittedData).values().stream().toList();

        assertEquals(expectedResults, mappedResults);
    }

    private void fillStudentsRegistrationForm() {
        registrationFormPage
                .setFirstName(userData.get("name"))
                .setLastName(userData.get("surname"))
                .setEmail(userData.get("email"))
                .setGender(userData.get("gender"))
                .setUserNumber(userData.get("phone"))
                .setBirthDate(userData.get("day"), userData.get("month"), userData.get("year"));

        registrationFormPage.removeBy("#adplus-anchor");
        registrationFormPage.scrollToById("submit");

        registrationFormPage
                .setSubjects(userData.get("subjects"))
                .setHobbies(userData.get("hobbies"))
                .uploadPicture(userData.get("file_name"))
                .setCurrentAddress(userData.get("address"))
                .setState(userData.get("state"))
                .setCity(userData.get("city"));

        registrationFormPage.submitById("submit");
    }
}

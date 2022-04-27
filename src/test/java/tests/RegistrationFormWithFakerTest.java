package tests;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import pages.RegistrationFormPage;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.$;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("demoqa-jenkins")
public class RegistrationFormWithFakerTest extends BaseTests {

    protected RegistrationFormPage registrationFormPage = new RegistrationFormPage();

    private final Faker faker = new Faker();
    private final LocalDate localDate = faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    private final String month = localDate.getMonth().toString();

    private final String firstName = faker.name().firstName(),
            lastName = faker.name().lastName(),
            email = faker.internet().emailAddress(),
            currentAddress = faker.address().fullAddress(),
            gender = faker.demographic().sex(),
            phone = faker.phoneNumber().subscriberNumber(10),
            day = String.valueOf(localDate.getDayOfMonth()),
            monthCorrected = month.substring(0, 1).toUpperCase() + month.substring(1).toLowerCase(),
            year = String.valueOf(localDate.getYear());

    private String[] expectedResultsData = {
            currentAddress,
            format("%s %s,%s", day, monthCorrected, year),
            gender,
            userData.get("hobbies"),
            phone,
            userData.get("file_name"),
            userData.get("state_and_city"),
            email,
            format("%s %s", firstName, lastName),
            userData.get("subjects")
    };

    @Test
    void checkStudentsRegistrationForm() {
        registrationFormPage.openPage();

        fillStudentsRegistrationForm();

        List<String> expectedResults = Arrays.asList(expectedResultsData);
        var submittedData = $(new By.ByTagName("html")).innerHtml();
        var mappedResults = getResponseDataFromTable(submittedData).values().stream().collect(Collectors.toList());

        assertEquals(expectedResults, mappedResults);
    }

    protected void fillStudentsRegistrationForm() {
        registrationFormPage
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setGender(gender)
                .setUserNumber(phone)
                .setBirthDate(day, monthCorrected, year);

        registrationFormPage.removeBy("#adplus-anchor");
        registrationFormPage.scrollToById("submit");

        registrationFormPage
                .setSubjects(userData.get("subjects"))
                .setHobbies(userData.get("hobbies"))
                .uploadPicture(userData.get("file_name"))
                .setCurrentAddress(currentAddress)
                .setState(userData.get("state"))
                .setCity(userData.get("city"));

        registrationFormPage.submitById("submit");
    }
}

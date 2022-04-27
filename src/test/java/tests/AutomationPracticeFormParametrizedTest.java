package tests;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import pages.RegistrationFormPage;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.$;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("demoqa-jenkins")
public class AutomationPracticeFormParametrizedTest extends BaseTests {

    protected RegistrationFormPage registrationFormPage = new RegistrationFormPage();

    @MethodSource("getParametrizedData")
    @ParameterizedTest(name = "checkStudentsRegistrationForm {0}")
    void checkStudentsRegistrationForm(TreeMap<String, String> userData, TreeMap<String, String> expectedData) {
        registrationFormPage.openPage();

        fillStudentsRegistrationForm(userData);

        var submittedData = $(new By.ByTagName("html")).innerHtml();

        assertTrue(matchResults(getResponseDataFromTable(submittedData), expectedData));
    }

    static Stream<Arguments> getParametrizedData() {
        LocalDate localDate = faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String month = localDate.getMonth().toString();
        String firstName = faker.name().firstName(),
                lastName = faker.name().lastName(),
                day = String.valueOf(localDate.getDayOfMonth()),
                monthCorrected = month.substring(0, 1).toUpperCase() + month.substring(1).toLowerCase(),
                year = String.valueOf(localDate.getYear());

        List<String> defaultFakerList = Arrays.asList(
                faker.address().fullAddress(),
                format("%s %s,%s", day, monthCorrected, year),
                faker.demographic().sex(),
                faker.phoneNumber().subscriberNumber(10),
                faker.internet().emailAddress(),
                format("%s %s", firstName, lastName));
        List<String> defaultList = Arrays.asList("Montenegro, Sutomore, plaz", "03 December,1990", "Female",
                "0777351544", "stacy.skytten@gmail.com", "Stacy Woods");

        return Stream.of(
                Arguments.of(
                        generateDataByList(defaultList, Arrays.asList("Stacy", "Woods", "03", "December", "1990")),
                        generateDataByList(defaultList)),
                Arguments.of(
                        generateDataByList(defaultFakerList, Arrays.asList(firstName, lastName, day, monthCorrected, year)),
                        generateDataByList(defaultFakerList))
        );
    }

    private void fillStudentsRegistrationForm(TreeMap<String, String> userData) {
        registrationFormPage
                .setFirstName(userData.get("name"))
                .setLastName(userData.get("surname"))
                .setEmail(userData.get("student email"))
                .setGender(userData.get("gender"))
                .setUserNumber(userData.get("mobile"));

        registrationFormPage.removeBy("#adplus-anchor");
        registrationFormPage.removeBy("#fixedban");
        registrationFormPage.scrollToById("submit");

        registrationFormPage
                .setBirthDate(userData.get("day"), userData.get("month"), userData.get("year"))
                .setSubjects(userData.get("subjects"))
                .setHobbies(userData.get("hobbies"))
                .uploadPicture(userData.get("picture"))
                .setCurrentAddress(userData.get("address"))
                .setState(userData.get("state"))
                .setCity(userData.get("city"));

        registrationFormPage.submitById("submit");
    }

    @SafeVarargs
    private static TreeMap<String, String> generateDataByList(List<String>... values) {
        TreeMap<String, String> generatedData = new TreeMap<String, String>();
        var list = values[0];

        // values for output
        generatedData.put("address", list.get(0));
        generatedData.put("date of birth", list.get(1));
        generatedData.put("gender", list.get(2));
        generatedData.put("mobile", list.get(3));
        generatedData.put("student email", list.get(4));
        generatedData.put("student name", list.get(5));

        if(values.length > 1) {
            var additionalList = values[1];
            // additional values for input
            generatedData.put("name", additionalList.get(0));
            generatedData.put("surname", additionalList.get(1));
            generatedData.put("day", additionalList.get(2));
            generatedData.put("month", additionalList.get(3));
            generatedData.put("year", additionalList.get(4));

            // default values
            generatedData.put("state", "Rajasthan");
            generatedData.put("city", "Jaipur");
        }

        // default values
        generatedData.put("hobbies", "Reading");
        generatedData.put("picture", "its_ok.jpg");
        generatedData.put("state and city", "Rajasthan Jaipur");
        generatedData.put("subjects", "Hindi");

        return generatedData;
    }
}

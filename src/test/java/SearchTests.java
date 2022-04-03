import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchTests {

    private final String NAME = "Stacy";
    private final String SURNAME = "Woods";
    private final String FULL_NAME = "Stacy Woods";
    private final String GENDER = "Female";
    private final String EMAIL = "stacy.skytten@gmail.com";
    private final String PHONE = "0777351544";
    private final String ADDRESS = "Montenegro, Sutomore, plaz";
    private final String BIRTH = "03 December,1990";
    private final String YEAR = "1990";
    private final String MONTH_NUMBER = "11";
    private final String DAY_NUMBER = "003";
    private final String FILE_NAME = "its_ok.jpg";
    private final String SUBJECTS = "Hindi";
    private final String HOBBIES = "Reading";
    private final String STATE_AND_CITY = "Rajasthan Jaipur";

    @Test
    void successfulSearchTest() {
        open("https://www.google.com/");
        $(byName("q")).setValue("selenide").pressEnter();
        $("#search").shouldHave(text("https://selenide.org"));
        System.out.println("Google was found");
    }

    @BeforeAll
    static void setUp() {
        Configuration.holdBrowserOpen = true;
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.timeout = 600000;
    }

    @AfterAll
    static void closeBrowser() {
        closeWindow();
    }

    @Test
    void checkStudentsRegistrationForm() {
        open("/automation-practice-form");

        fillStudentsRegistrationForm();
        var expectedResults = "Label Values\n" +
                "Student Name " + FULL_NAME + "\n" +
                "Student Email " + EMAIL + "\n" +
                "Gender " + GENDER + "\n" +
                "Mobile " + PHONE + "\n" +
                "Date of Birth " + BIRTH + "\n" +
                "Subjects " + SUBJECTS + "\n" +
                "Hobbies " + HOBBIES + "\n" +
                "Picture " + FILE_NAME + "\n" +
                "Address " + ADDRESS + "\n" +
                "State and City " + STATE_AND_CITY + "";
        var submittedForm = $("table[class='table table-dark table-striped table-bordered table-hover']").getText();
        assertEquals(expectedResults, submittedForm);
    }

    @Test
    void fillTextForm() {
        open("/text-box");
        $("[id=userName]").setValue("Stacy Woods");
        $("[id=userEmail]").setValue("stacy.skytten@gmail.com");
        $("[id=currentAddress]").setValue("Montenegro, Sutomore, plaz");
        $("[id=permanentAddress]").setValue("Planet Earth");
        $("[id=submit]").click();

        //Asserts
        $("[id=output]").shouldHave(text(FULL_NAME), text(EMAIL), text("Montenegro, Sutomore, plaz"))
                .shouldHave(text("Planet Earth"));
        $("[id=output] [id=name]").shouldHave(text(FULL_NAME));
        $("[id=output]").$("[id=name]").shouldHave(text(FULL_NAME));

        $("p[id=permanentAddress]").shouldHave(text("Permananet Address :Planet Earth"));
        $("[id=permanentAddress]", 1).shouldHave(text("Permananet Address :Planet Earth"));

        String expectedAddress = "Permananet Address :Planet Earth";
        String actualAddress = $("p[id=permanentAddress]").text();
        assertEquals(expectedAddress, actualAddress);
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

        $x("//input[@id='uploadPicture']").uploadFile(new File("/Users/anastasiia/Pictures/" + FILE_NAME));

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
}

import org.junit.jupiter.api.Test;

import java.io.File;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$x;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AutomationPracticeFormTest extends BaseTests {

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

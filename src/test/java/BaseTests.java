import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.Selenide.closeWindow;

public class BaseTests {

    protected final String NAME = "Stacy";
    protected final String SURNAME = "Woods";
    protected final String FULL_NAME = "Stacy Woods";
    protected final String GENDER = "Female";
    protected final String EMAIL = "stacy.skytten@gmail.com";
    protected final String PHONE = "0777351544";
    protected final String ADDRESS = "Montenegro, Sutomore, plaz";
    protected final String BIRTH = "03 December,1990";
    protected final String YEAR = "1990";
    protected final String MONTH_NUMBER = "11";
    protected final String DAY_NUMBER = "003";
    protected final String FILE_NAME = "its_ok.jpg";
    protected final String SUBJECTS = "Hindi";
    protected final String HOBBIES = "Reading";
    protected final String STATE_AND_CITY = "Rajasthan Jaipur";

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

    public String getFilePath() {
        return System.getProperty("user.dir");
    }
    public String getImagesPath() {
        return getFilePath() + "/src/test/resources/images";
    }
}

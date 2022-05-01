package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.github.javafaker.Faker;
import configs.CredentialsConfigs;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static com.codeborne.selenide.Selenide.closeWindow;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.hasWebDriverStarted;

public class BaseTests {
    private static final CredentialsConfigs configs = ConfigFactory.create(CredentialsConfigs.class);

    public static final String OWNER = "StacyWoods";
    protected static final String REPOSITORY = OWNER + "/qa-guru-study";
    protected static final int PR_NUMBER = 13;
    protected static Faker faker = new Faker();
    protected static HashMap<String, String> userData = new HashMap<>();

    public BaseTests() {
        setUserData();
    }

    @BeforeAll
    static void setUp() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        Configuration.browserSize = System.getProperty("browserSize", configs.browserSize());
        Configuration.remote = getRemoteHub();
//        Configuration.holdBrowserOpen = true;
//        Configuration.timeout = 300000;

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;
        open();
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
    }

    @AfterAll
    static void closeBrowser() {
        if (hasWebDriverStarted()) {
            closeWindow();
        }
    }

    public static String getUserDir() { return System.getProperty("user.dir");}
    public static String getResourcesDir() { return getUserDir() + "/src/test/resources/";}

    public String getImagesPath() {
        return getUserDir() + "/src/test/resources/images";
    }

    protected Boolean matchResults(Map<String, String> actualResult, Map<String, String> expectedResult) {
        return actualResult.entrySet().stream().allMatch(actual ->
                expectedResult.containsKey(actual.getKey())
                && expectedResult.get(actual.getKey()).equals(actual.getValue()));
    }

    protected TreeMap<String, String> getResponseDataFromTable(String submittedData) {
        var elements = Jsoup.parseBodyFragment(submittedData).getElementsByTag("tbody").get(0)
                .getElementsByTag("tr");
        var result = new HashMap<String, String>();
        elements.forEach(element -> {
            result.put(
                    element.getElementsByTag("td").get(0).text().toLowerCase(),
                    element.getElementsByTag("td").get(1).text()
            );
        });

        return new TreeMap<> (result);
    }

    private void setUserData() {
        userData.put("name", "Stacy");
        userData.put("surname", "Woods");
        userData.put("full_name", "Stacy Woods");
        userData.put("gender", "Female");
        userData.put("email", "stacy.skytten@gmail.com");
        userData.put("phone", "0777351544");
        userData.put("address", "Montenegro, Sutomore, plaz");
        userData.put("birth", "03 December,1990");
        userData.put("year", "1990");
        userData.put("month", "December");
        userData.put("month_number", "11");
        userData.put("day", "03");
        userData.put("file_name", "its_ok.jpg");
        userData.put("subjects", "Hindi");
        userData.put("hobbies", "Reading");
        userData.put("state", "Rajasthan");
        userData.put("city", "Jaipur");
        userData.put("state_and_city", "Rajasthan Jaipur");
    }

    protected static String getRemoteHub() {
        if (System.getProperty("remoteHub") != null) {
            return System.getProperty("remoteHub");
        } else if (System.getProperty("remoteHubUser") != null && System.getProperty("remoteHubPass") != null) {
            return String.format(configs.selenoidParsedUrl(), System.getProperty("remoteHubUser"), System.getProperty("remoteHubPass"));
        } else {
            return String.format(configs.selenoidParsedUrl(), configs.selenoidLogin(), configs.selenoidPass());
        }
    }
}

package tests;

import com.codeborne.selenide.Configuration;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.util.HashMap;
import java.util.TreeMap;

import static com.codeborne.selenide.Selenide.closeWindow;
import static java.lang.String.format;

public class BaseTests {

    protected HashMap<String, String> userData = new HashMap<>();

    public BaseTests() {
        setUserData();
    }

    @BeforeAll
    static void setUp() {
        Configuration.holdBrowserOpen = true;
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1420x780";
        Configuration.timeout = 300000;
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

    protected TreeMap<String, String> getResponseDataFromTable(String submittedData) {
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
}

package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import providers.WebDriverProvider;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("owner")
public class SeleniumTest {

    private final WebDriver driver = new WebDriverProvider().get();

    @Test
    public void testGithubTitle() {
        // код выполнения теста
        String title = driver.getTitle();
        assertEquals(title, "GitHub: Where the world builds software · GitHub");
    }

    @AfterEach
    public void stopDriver() {
        driver.quit();
    }

}
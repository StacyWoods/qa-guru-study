package configs;

import enums.Browser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

/**
 * Class for using WebDriwerConfigs through System.getProperty without aenobits.owner library
 */
public class PropertiesWebDriverConfig {

    public Browser getBrowser() {
        String browserName = System.getProperty("browser");
        if (Objects.isNull(browserName)) {
            browserName = "CHROME";
        }
        return Browser.valueOf(browserName);
    }

    public String getBaseUrl() {
        String browserUrl = System.getProperty("baseUrl");
        if (Objects.isNull(browserUrl)){
            browserUrl = "https://github.com";
        }
        return browserUrl;
    }

    public URL getRemoteURL() {
        String remoteUrl = System.getProperty("remoteUrl");
        if (Objects.isNull(remoteUrl)){
            remoteUrl = "https://selenium:4444/wd/hub";
        }
        try {
            return new URL(remoteUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

}


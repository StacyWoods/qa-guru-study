package tests;

import configs.MobileConfig;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("examples")
public class MobileEndToEndTest {

    @Test
    public void testAndroid() {
        // проходит в случае запуска из консоли
        // ./gradlew clean test --tests MobileEndToEndTest -Ddevice=pixel
        MobileConfig config = ConfigFactory
                .create(MobileConfig.class, System.getProperties());

        assertThat(config.platformName()).isEqualTo("Android");
        assertThat(config.platformVersion()).isEqualTo("27.0");
        assertThat(config.deviceName()).isEqualTo("Google Pixel XL");
    }

    @Test
    public void testIos() {
        // проходит в случае запуска из консоли
        // ./gradlew clean test --tests MobileEndToEndTest -Ddevice=iphone12
        MobileConfig config = ConfigFactory
                .create(MobileConfig.class, System.getProperties());

        assertThat(config.platformName()).isEqualTo("IOS");
        assertThat(config.platformVersion()).isEqualTo("15.0");
        assertThat(config.deviceName()).isEqualTo("iPhone 12 Pro");
    }
}
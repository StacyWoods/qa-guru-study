package configs;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:pixel.properties"
})
public interface AndroidConfig extends Config {

    @Key("platform.name")
    String platformName();

    @Key("platform.version")
    String platformVersion();

    @Key("device.name")
    String deviceName();

}


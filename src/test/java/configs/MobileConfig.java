package configs;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:configs/${device}.properties"
})
public interface MobileConfig extends Config {

    @Key("platform.name")
    String platformName();

    @Key("platform.version")
    String platformVersion();

    @Key("device.name")
    String deviceName();

}


package configs;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:configs/credentials.properties")
public interface CredentialsConfigs extends Config {
    String login();
    String password();
}

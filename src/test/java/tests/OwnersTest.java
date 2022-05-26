package tests;

import configs.CredentialsConfig;
import org.aeonbits.owner.ConfigFactory;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class OwnersTest {
    CredentialsConfig configs = ConfigFactory.create(CredentialsConfig.class);

    @Test
    @Tag("owner")
    void loginTest() {
        String login = configs.login();
        String password = configs.password();

        System.out.println("Login: " + login);
        System.out.println("Password: " + password);

        String message = "I logged in as " + login + " with password " + password;
        System.out.println(message);
    }
}

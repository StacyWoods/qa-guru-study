package tests;

import configs.AuthConfig;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("owner")
public class AuthTest {

    @Test
    public void testClasspath() {
        AuthConfig config = ConfigFactory
                .create(AuthConfig.class, System.getProperties());

        assertThat(config.username()).isEqualTo("stacy");
        assertThat(config.password()).isEqualTo("woods");
    }

    @Test
    public void testLocalFile() throws IOException {
        // то, что выполняется в Jenkins до исполнения теста
        String content = "username=secret-user-stacy\npassword=secret-pass-woods";
        Path propsPath = Paths.get("/tmp/secret.properties");
        Files.writeString(propsPath, content);

        // непосредственно тело теста
        AuthConfig config = ConfigFactory
                .create(AuthConfig.class, System.getProperties());
        assertThat(config.username()).isEqualTo("secret-user-stacy");
        assertThat(config.password()).isEqualTo("secret-pass-woods");

        // то, что выполняется в Jenkins после исполнения теста
        Files.delete(propsPath);
    }

}

package pl.edu.pg.chor.strefa_chorzysty.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app.settings")
public class DefaultSettingsProperties {
    private Duration jwtTtl = Duration.ofMinutes(15);
    private Duration registrationTokenTtl = Duration.ofDays(7);
    private Duration unactivatedUserTtl = Duration.ofDays(7);
    private String registrationTokenCleanupCron = "0 0 3 * * *";
    private String unactivatedUserCleanupCron = "0 0 4 * * *";
}

package pl.edu.pg.chor.strefa_chorzysty.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {

    private Duration jwtTtl = Duration.ofMinutes(15);
    private Duration registrationTokenTtl = Duration.ofHours(48);
    private String registrationTokenCleanupCron = "0 0 3 * * *";

    public Duration getJwtTtl() {
        return jwtTtl;
    }

    public void setJwtTtl(Duration jwtTtl) {
        this.jwtTtl = jwtTtl;
    }

    public Duration getRegistrationTokenTtl() {
        return registrationTokenTtl;
    }

    public void setRegistrationTokenTtl(Duration registrationTokenTtl) {
        this.registrationTokenTtl = registrationTokenTtl;
    }

    public String getRegistrationTokenCleanupCron() {
        return registrationTokenCleanupCron;
    }

    public void setRegistrationTokenCleanupCron(String registrationTokenCleanupCron) {
        this.registrationTokenCleanupCron = registrationTokenCleanupCron;
    }
}

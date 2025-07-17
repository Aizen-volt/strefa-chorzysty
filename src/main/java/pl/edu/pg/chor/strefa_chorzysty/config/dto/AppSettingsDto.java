package pl.edu.pg.chor.strefa_chorzysty.config.dto;

import java.time.Duration;

public record AppSettingsDto(
        Duration jwtTtl,
        Duration registrationTokenTtl,
        Duration unactivatedUserTtl,
        String registrationTokenCleanupCron,
        String unactivatedUserCleanupCron
) {}

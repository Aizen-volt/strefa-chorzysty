package pl.edu.pg.chor.strefa_chorzysty.user.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.edu.pg.chor.strefa_chorzysty.user.service.RegistrationTokenService;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenCleanupScheduler {

    private final RegistrationTokenService registrationTokenService;

    @Scheduled(cron = "${security.registration-token-cleanup-cron}")
    public void removeExpiredTokens() {
        log.info("Started clean up of expired registration tokens");
        try {
            registrationTokenService.deleteExpiredTokens();
        } catch (Exception e) {
            log.error("Error during clean up of expired registration tokens", e);
        }
        log.info("Finished clean up of expired registration tokens");
    }
}
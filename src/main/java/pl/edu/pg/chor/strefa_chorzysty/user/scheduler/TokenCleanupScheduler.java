package pl.edu.pg.chor.strefa_chorzysty.user.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import pl.edu.pg.chor.strefa_chorzysty.config.service.AppSettingsService;
import pl.edu.pg.chor.strefa_chorzysty.user.service.RegistrationTokenService;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class TokenCleanupScheduler implements SchedulingConfigurer {

    private final RegistrationTokenService registrationTokenService;
    private final AppSettingsService settingsService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(
                () -> {
                    log.info("Running token cleanup");
                    registrationTokenService.deleteExpiredTokens();
                },
                (TriggerContext triggerContext) -> {
                    String cron = settingsService.getSettings().registrationTokenCleanupCron();
                    CronTrigger trigger = new CronTrigger(cron);
                    return trigger.nextExecutionTime(triggerContext).toInstant();
                }
        );
    }
}

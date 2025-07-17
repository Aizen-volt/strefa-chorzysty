package pl.edu.pg.chor.strefa_chorzysty.user.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import pl.edu.pg.chor.strefa_chorzysty.config.service.AppSettingsService;
import pl.edu.pg.chor.strefa_chorzysty.user.service.UserService;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class UserCleanupScheduler implements SchedulingConfigurer {

    private final UserService userService;
    private final AppSettingsService settingsService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar registrar) {
        registrar.addTriggerTask(
                () -> {
                    log.info("Cleaning up inactive users");
                    userService.deleteExpiredUnactivatedUsers();
                },
                context -> {
                    String cron = settingsService.getSettings().unactivatedUserCleanupCron();
                    return new CronTrigger(cron).nextExecutionTime(context).toInstant();
                }
        );
    }
}

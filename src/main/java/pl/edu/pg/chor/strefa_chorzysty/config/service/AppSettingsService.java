package pl.edu.pg.chor.strefa_chorzysty.config.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pg.chor.strefa_chorzysty.config.DefaultSettingsProperties;
import pl.edu.pg.chor.strefa_chorzysty.config.dto.AppSettingsDto;
import pl.edu.pg.chor.strefa_chorzysty.config.model.AppSettings;
import pl.edu.pg.chor.strefa_chorzysty.config.repository.AppSettingsRepository;

@Service
@RequiredArgsConstructor
public class AppSettingsService {

    private final AppSettingsRepository repository;
    private final DefaultSettingsProperties defaults;

    public AppSettings getSettingsEntity() {
        return repository.findAll().stream().findFirst().orElseGet(() -> repository.save(buildDefaultSettings()));
    }

    public AppSettingsDto getSettings() {
        AppSettings s = getSettingsEntity();
        return new AppSettingsDto(
                s.getJwtTtl(),
                s.getRegistrationTokenTtl(),
                s.getUnactivatedUserTtl(),
                s.getRegistrationTokenCleanupCron(),
                s.getUnactivatedUserCleanupCron()
        );
    }

    public AppSettingsDto update(AppSettingsDto updated) {
        AppSettings entity = getSettingsEntity();
        entity.setJwtTtl(updated.jwtTtl());
        entity.setRegistrationTokenTtl(updated.registrationTokenTtl());
        entity.setUnactivatedUserTtl(updated.unactivatedUserTtl());
        entity.setRegistrationTokenCleanupCron(updated.registrationTokenCleanupCron());
        entity.setUnactivatedUserCleanupCron(updated.unactivatedUserCleanupCron());
        return new AppSettingsDto(
                repository.save(entity).getJwtTtl(),
                entity.getRegistrationTokenTtl(),
                entity.getUnactivatedUserTtl(),
                entity.getRegistrationTokenCleanupCron(),
                entity.getUnactivatedUserCleanupCron()
        );
    }

    private AppSettings buildDefaultSettings() {
        return AppSettings.builder()
                .jwtTtl(defaults.getJwtTtl())
                .registrationTokenTtl(defaults.getRegistrationTokenTtl())
                .unactivatedUserTtl(defaults.getUnactivatedUserTtl())
                .registrationTokenCleanupCron(defaults.getRegistrationTokenCleanupCron())
                .unactivatedUserCleanupCron(defaults.getUnactivatedUserCleanupCron())
                .build();
    }
}


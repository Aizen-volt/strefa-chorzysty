package pl.edu.pg.chor.strefa_chorzysty.config.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import pl.edu.pg.chor.strefa_chorzysty.config.DefaultSettingsProperties;
import pl.edu.pg.chor.strefa_chorzysty.config.dto.AppSettingsDto;
import pl.edu.pg.chor.strefa_chorzysty.config.model.AppSettings;
import pl.edu.pg.chor.strefa_chorzysty.config.repository.AppSettingsRepository;

import java.time.Duration;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AppSettingsServiceTest {

    private AppSettingsRepository repository;
    private DefaultSettingsProperties defaults;
    private AppSettingsService service;

    @BeforeEach
    void setUp() {
        repository = mock(AppSettingsRepository.class);
        defaults = new DefaultSettingsProperties();
        defaults.setJwtTtl(Duration.ofMinutes(15));
        defaults.setRegistrationTokenTtl(Duration.ofHours(24));
        defaults.setUnactivatedUserTtl(Duration.ofDays(3));
        defaults.setRegistrationTokenCleanupCron("0 0 3 * * *");
        defaults.setUnactivatedUserCleanupCron("0 0 4 * * *");

        service = new AppSettingsService(repository, defaults);
    }

    @Test
    void shouldInitializeDefaultSettingsIfNotPresent() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        AppSettingsDto dto = service.getSettings();

        ArgumentCaptor<AppSettings> captor = ArgumentCaptor.forClass(AppSettings.class);
        verify(repository).save(captor.capture());

        AppSettings saved = captor.getValue();
        assertThat(saved.getJwtTtl()).isEqualTo(Duration.ofMinutes(15));
        assertThat(dto.registrationTokenCleanupCron()).isEqualTo("0 0 3 * * *");
    }

    @Test
    void shouldReturnExistingSettings() {
        AppSettings existing = AppSettings.builder()
                .jwtTtl(Duration.ofMinutes(10))
                .registrationTokenTtl(Duration.ofHours(48))
                .unactivatedUserTtl(Duration.ofDays(7))
                .registrationTokenCleanupCron("0 5 * * *")
                .unactivatedUserCleanupCron("0 6 * * *")
                .build();

        when(repository.findAll()).thenReturn(Collections.singletonList(existing));

        AppSettingsDto dto = service.getSettings();

        assertThat(dto.jwtTtl()).isEqualTo(Duration.ofMinutes(10));
        assertThat(dto.registrationTokenTtl()).isEqualTo(Duration.ofHours(48));
        assertThat(dto.registrationTokenCleanupCron()).isEqualTo("0 5 * * *");
    }

    @Test
    void shouldUpdateSettings() {
        AppSettings existing = AppSettings.builder()
                .id(1L)
                .jwtTtl(Duration.ofMinutes(5))
                .registrationTokenTtl(Duration.ofHours(12))
                .unactivatedUserTtl(Duration.ofDays(2))
                .registrationTokenCleanupCron("0 0 * * *")
                .unactivatedUserCleanupCron("0 0 1 * * *")
                .build();

        when(repository.findAll()).thenReturn(Collections.singletonList(existing));
        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));

        AppSettingsDto update = new AppSettingsDto(
                Duration.ofMinutes(20),
                Duration.ofHours(48),
                Duration.ofDays(5),
                "0 0 3 * * *",
                "0 0 4 * * *"
        );

        AppSettingsDto updated = service.update(update);

        assertThat(updated.jwtTtl()).isEqualTo(Duration.ofMinutes(20));
        assertThat(updated.unactivatedUserTtl()).isEqualTo(Duration.ofDays(5));
    }
}

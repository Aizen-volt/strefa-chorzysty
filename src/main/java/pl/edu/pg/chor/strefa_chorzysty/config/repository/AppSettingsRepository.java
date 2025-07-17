package pl.edu.pg.chor.strefa_chorzysty.config.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pg.chor.strefa_chorzysty.config.model.AppSettings;

public interface AppSettingsRepository extends JpaRepository<AppSettings, Long> {
}

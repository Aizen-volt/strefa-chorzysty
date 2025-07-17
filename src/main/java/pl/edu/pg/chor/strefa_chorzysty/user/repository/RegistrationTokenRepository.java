package pl.edu.pg.chor.strefa_chorzysty.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pg.chor.strefa_chorzysty.user.token.RegistrationToken;

import java.time.LocalDateTime;
import java.util.Optional;

public interface RegistrationTokenRepository extends JpaRepository<RegistrationToken, Long> {

    Optional<RegistrationToken> findByToken(String token);

    void deleteAllByExpiryDateBefore(LocalDateTime time);
}

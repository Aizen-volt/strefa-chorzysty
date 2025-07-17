package pl.edu.pg.chor.strefa_chorzysty.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pg.chor.strefa_chorzysty.user.model.User;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    void deleteAllByEnabledFalseAndCreatedAtBefore(LocalDateTime time);

}

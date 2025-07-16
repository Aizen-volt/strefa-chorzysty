package pl.edu.pg.chor.strefa_chorzysty.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pg.chor.strefa_chorzysty.user.model.Email;
import pl.edu.pg.chor.strefa_chorzysty.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> listAll();

    Optional<User> find(Email email);

    Optional<User> findByFullNamePrefix(String namePrefix);

    boolean exists(Email email);
}

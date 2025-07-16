package pl.edu.pg.chor.strefa_chorzysty.user.token;

import jakarta.persistence.*;
import lombok.*;
import pl.edu.pg.chor.strefa_chorzysty.user.model.User;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @OneToOne
    private User user;

    private LocalDateTime expiryDate;
}
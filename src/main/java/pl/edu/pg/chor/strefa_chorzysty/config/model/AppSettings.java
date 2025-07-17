package pl.edu.pg.chor.strefa_chorzysty.config.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Duration jwtTtl;
    private Duration registrationTokenTtl;
    private Duration unactivatedUserTtl;

    private String registrationTokenCleanupCron;
    private String unactivatedUserCleanupCron;
}

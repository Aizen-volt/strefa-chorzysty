package pl.edu.pg.chor.strefa_chorzysty.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.chor.strefa_chorzysty.config.SecurityProperties;
import pl.edu.pg.chor.strefa_chorzysty.user.model.User;
import pl.edu.pg.chor.strefa_chorzysty.user.repository.RegistrationTokenRepository;
import pl.edu.pg.chor.strefa_chorzysty.user.token.RegistrationToken;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationTokenService {

    private final RegistrationTokenRepository tokenRepository;
    private final SecurityProperties securityProperties;

    public RegistrationToken createToken(User user) {
        Duration ttl = securityProperties.getRegistrationTokenTtl();
        LocalDateTime expiry = LocalDateTime.now().plus(ttl);
        var token = new RegistrationToken(UUID.randomUUID().toString(), user, expiry);
        return tokenRepository.save(token);
    }

    @Transactional
    public void deleteExpiredTokens() {
        tokenRepository.deleteAllByExpiryDateBefore(LocalDateTime.now());
    }
}
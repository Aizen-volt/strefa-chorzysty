package pl.edu.pg.chor.strefa_chorzysty.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.chor.strefa_chorzysty.config.service.AppSettingsService;
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
    private final AppSettingsService settingsService;

    public RegistrationToken createToken(User user) {
        Duration ttl = settingsService.getSettings().registrationTokenTtl();
        LocalDateTime expiry = LocalDateTime.now().plus(ttl);
        var token = RegistrationToken.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expiryDate(expiry)
                .build();
        return tokenRepository.save(token);
    }

    public RegistrationToken getByToken(String token) {
        return tokenRepository.findByToken(token)
                .filter(t -> !t.isExpired())
                .orElseThrow(() -> new IllegalArgumentException("Token jest nieprawidłowy lub wygasł"));
    }

    @Transactional
    public void deleteExpiredTokens() {
        tokenRepository.deleteAllByExpiryDateBefore(LocalDateTime.now());
    }

    @Transactional
    public void deleteToken(RegistrationToken token) {
        tokenRepository.delete(token);
    }
}

package pl.edu.pg.chor.strefa_chorzysty.user.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.chor.strefa_chorzysty.config.service.AppSettingsService;
import pl.edu.pg.chor.strefa_chorzysty.user.dto.RegisterRequest;
import pl.edu.pg.chor.strefa_chorzysty.user.dto.RoleChangeRequest;
import pl.edu.pg.chor.strefa_chorzysty.user.dto.UserProfileDto;
import pl.edu.pg.chor.strefa_chorzysty.user.dto.UserUpdateRequest;
import pl.edu.pg.chor.strefa_chorzysty.user.model.Role;
import pl.edu.pg.chor.strefa_chorzysty.user.model.User;
import pl.edu.pg.chor.strefa_chorzysty.user.repository.RegistrationTokenRepository;
import pl.edu.pg.chor.strefa_chorzysty.user.repository.UserRepository;
import pl.edu.pg.chor.strefa_chorzysty.user.token.RegistrationToken;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AppSettingsService appSettingsService;
    private final UserRepository userRepository;
    private final RegistrationTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(String tokenValue, RegisterRequest request) {
        RegistrationToken token = tokenRepository.findByToken(tokenValue)
                .orElseThrow(() -> new IllegalArgumentException("Nieprawidłowy token"));

        User user = token.getUser();
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setEnabled(true);

        userRepository.save(user);
        tokenRepository.delete(token);
    }

    @Transactional
    public void deleteExpiredUnactivatedUsers() {
        Duration ttl = appSettingsService.getSettings().unactivatedUserTtl();
        LocalDateTime threshold = LocalDateTime.now().minus(ttl);

        userRepository.deleteAllByEnabledFalseAndCreatedAtBefore(threshold);
    }

    public void updateUser(UserUpdateRequest request, User currentUser) {
        User user = userRepository.findById(request.id())
                .orElseThrow(() -> new EntityNotFoundException("Nie znaleziono użytkownika"));

        boolean isAdminOrBoard = currentUser.getRole() == Role.ADMIN || currentUser.getRole() == Role.ZARZAD;

        if (!isAdminOrBoard && !user.getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("Brak dostępu do edycji tego użytkownika");
        }

        if (request.firstName() != null) user.setFirstName(request.firstName());
        if (request.lastName() != null) user.setLastName(request.lastName());
        if (request.familyName() != null) user.setFamilyName(request.familyName());
        if (request.phoneNumber() != null) user.setPhoneNumber(request.phoneNumber());
        if (request.voice() != null) user.setVoice(request.voice());

        if (user.getPhoneNumber() == null || user.getPhoneNumber().isBlank()) {
            throw new IllegalArgumentException("Numer telefonu jest wymagany");
        }

        userRepository.save(user);
    }

    public void changeUserRole(RoleChangeRequest request, User currentUser) {
        if (currentUser.getRole() != Role.ADMIN && currentUser.getRole() != Role.ZARZAD) {
            throw new AccessDeniedException("Brak uprawnień do zmiany ról");
        }

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new EntityNotFoundException("Nie znaleziono użytkownika"));

        if (request.newRole() == Role.ADMIN || request.newRole() == Role.DYRYGENT) {
            throw new AccessDeniedException("Nie można nadać roli ADMIN ani DYRYGENT");
        }

        user.setRole(request.newRole());
        userRepository.save(user);
    }

    public UserProfileDto getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Nie znaleziono użytkownika"));

        return toDto(user);
    }

    public List<UserProfileDto> getAllUsers() {
        return userRepository.findAll().stream().map(this::toDto).toList();
    }

    public UserProfileDto toDto(User user) {
        return new UserProfileDto(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getFamilyName(),
                user.getPhoneNumber(),
                user.getVoice(),
                user.getRole()
        );
    }
}

package pl.edu.pg.chor.strefa_chorzysty.user.dto;

import pl.edu.pg.chor.strefa_chorzysty.user.model.Role;
import pl.edu.pg.chor.strefa_chorzysty.user.model.Voice;

public record UserProfileDto(
        Long id,
        String email,
        String firstName,
        String lastName,
        String familyName,
        String phoneNumber,
        Voice voice,
        Role role
) {}

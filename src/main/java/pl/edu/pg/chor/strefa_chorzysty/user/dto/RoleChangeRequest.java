package pl.edu.pg.chor.strefa_chorzysty.user.dto;

import jakarta.validation.constraints.NotNull;
import pl.edu.pg.chor.strefa_chorzysty.user.model.Role;

public record RoleChangeRequest(
        @NotNull Long userId,
        @NotNull Role newRole
) {}

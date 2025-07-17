package pl.edu.pg.chor.strefa_chorzysty.authorization.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.chor.strefa_chorzysty.user.dto.RegisterRequest;
import pl.edu.pg.chor.strefa_chorzysty.user.service.UserService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthorizationController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestParam("token") String token, @RequestBody @Valid RegisterRequest request) {
        userService.registerUser(token, request);
        return ResponseEntity.ok().build();
    }
}

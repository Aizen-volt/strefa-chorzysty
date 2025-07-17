package pl.edu.pg.chor.strefa_chorzysty.config.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.chor.strefa_chorzysty.config.dto.AppSettingsDto;
import pl.edu.pg.chor.strefa_chorzysty.config.service.AppSettingsService;

@RestController
@RequestMapping("/api/settings")
@RequiredArgsConstructor
public class AppSettingsController {

    private final AppSettingsService service;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AppSettingsDto> getSettings() {
        return ResponseEntity.ok(service.getSettings());
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AppSettingsDto> updateSettings(@RequestBody AppSettingsDto dto) {
        return ResponseEntity.ok(service.update(dto));
    }
}

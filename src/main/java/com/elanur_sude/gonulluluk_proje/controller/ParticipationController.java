package com.elanur_sude.gonulluluk_proje.controller;

import com.elanur_sude.gonulluluk_proje.model.Participation;
import com.elanur_sude.gonulluluk_proje.service.ParticipationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/participations")
@RequiredArgsConstructor
public class ParticipationController {

    private final ParticipationService participationService;

    // Basit: kullanıcı doğrudan katılır (admin onayı yok)
    // Örnek: POST /api/participations/join?projectId=1&userId=2
    @PostMapping("/join")
    public ResponseEntity<Participation> joinProject(@RequestParam Long projectId,
                                                     @RequestParam Long userId) {
        Participation participation = participationService.joinProject(projectId, userId);
        return ResponseEntity.ok(participation);
    }
}


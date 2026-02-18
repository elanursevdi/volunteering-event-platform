package com.elanur_sude.gonulluluk_proje.controller;

import com.elanur_sude.gonulluluk_proje.model.Project;
import com.elanur_sude.gonulluluk_proje.model.Participation;
import com.elanur_sude.gonulluluk_proje.model.Status;
import com.elanur_sude.gonulluluk_proje.service.ProjectService;
import com.elanur_sude.gonulluluk_proje.service.ParticipationService;
import com.elanur_sude.gonulluluk_proje.repository.ParticipationRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final ParticipationService participationService;
    private final ParticipationRepository participationRepository;

    @PostMapping
    public ResponseEntity<Project> create(@RequestBody Project project) {
        return ResponseEntity.ok(projectService.createProject(project));
    }

    @GetMapping
    public ResponseEntity<List<Project>> getAll() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @PostMapping("/{projectId}/join")
    public ResponseEntity<Participation> join(@PathVariable Long projectId, @RequestParam Long userId){
        return ResponseEntity.ok(participationService.joinProject(projectId, userId));
    }

    // Toplam proje ve katılımcı
    @GetMapping("/stats")
    public ResponseEntity<StatsDTO> stats(){
        long participants = participationRepository.countByStatus(Status.APPROVED);
        long projects = projectService.getAllProjects().size();
        return ResponseEntity.ok(new StatsDTO(participants, projects));
    }

    // En çok katılım alan projeler
    @GetMapping("/top")
public ResponseEntity<List<TopProjectDTO>> getTopProjects() {
    List<Object[]> results = participationRepository.findTopProjects(PageRequest.of(0, 3)); // ✅ sadece ilk 3
    List<TopProjectDTO> dtoList = results.stream()
            .map(r -> new TopProjectDTO((String) r[0], ((Number) r[1]).longValue()))
            .toList();

    return ResponseEntity.ok(dtoList);
}


    public record StatsDTO(long totalParticipants, long activeProjects) {}
    public record TopProjectDTO(String name, long participants) {}
}



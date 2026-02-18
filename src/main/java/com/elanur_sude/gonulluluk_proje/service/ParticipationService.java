package com.elanur_sude.gonulluluk_proje.service;

import com.elanur_sude.gonulluluk_proje.model.Participation;
import com.elanur_sude.gonulluluk_proje.model.Project;
import com.elanur_sude.gonulluluk_proje.model.User;
import com.elanur_sude.gonulluluk_proje.model.Status;
import com.elanur_sude.gonulluluk_proje.repository.ParticipationRepository;
import com.elanur_sude.gonulluluk_proje.repository.ProjectRepository;
import com.elanur_sude.gonulluluk_proje.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipationService {

    private final ParticipationRepository participationRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public Participation joinProject(Long projectId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Proje bulunamadı"));

        // Aynı kullanıcı aynı projeye tekrar yazılmasın
        if (participationRepository.existsByProject_IdAndUser_Id(projectId, userId)) {
            return participationRepository.findByProject_Id(projectId)
                .stream()
                .filter(p -> p.getUser().getId().equals(userId))
                .findFirst()
                .orElseThrow();
        }

        Participation participation = new Participation();
        participation.setUser(user);
        participation.setProject(project);
        participation.setStatus(Status.APPROVED); // artık Status enum kullanıyorum

        return participationRepository.save(participation);
    }
}




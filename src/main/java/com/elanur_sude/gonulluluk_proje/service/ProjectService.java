package com.elanur_sude.gonulluluk_proje.service;

import com.elanur_sude.gonulluluk_proje.model.Project;
import com.elanur_sude.gonulluluk_proje.model.User;
import com.elanur_sude.gonulluluk_proje.repository.ProjectRepository;
import com.elanur_sude.gonulluluk_proje.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public Project createProject(Project project) {
        // createdBy sadece id ile geliyor, user DB’den bulunup set edilmeli
        Long userId = project.getCreatedBy().getId();
        User creator = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));

        project.setCreatedBy(creator);
        return projectRepository.save(project); // INSERT
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll(); // SELECT *
    }
}



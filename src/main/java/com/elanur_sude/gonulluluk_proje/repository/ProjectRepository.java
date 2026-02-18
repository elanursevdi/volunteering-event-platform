package com.elanur_sude.gonulluluk_proje.repository;

import org.springframework.stereotype.Repository;
import com.elanur_sude.gonulluluk_proje.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> { }
// CRUD metotlarını Spring otomatik yazar (save, findAll, findById vs.)

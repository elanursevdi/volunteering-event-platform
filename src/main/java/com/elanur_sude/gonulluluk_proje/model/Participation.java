package com.elanur_sude.gonulluluk_proje.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "participations_new")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Enumerated(EnumType.STRING)
    private Status status;
}

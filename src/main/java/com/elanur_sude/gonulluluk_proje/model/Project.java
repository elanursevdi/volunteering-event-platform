package com.elanur_sude.gonulluluk_proje.model ;
import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "projects_new")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String city;
    private Double latitude;
    private Double longitude;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;
}

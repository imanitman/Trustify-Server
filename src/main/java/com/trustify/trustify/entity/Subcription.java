package com.trustify.trustify.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trustify.trustify.enums.SubcriptionStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Entity
@Getter
@Setter
public class Subcription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String price;
    @Enumerated(EnumType.STRING)

    @Builder.Default
    private SubcriptionStatus status = SubcriptionStatus.INACTIVE;

    private boolean isBookDemo;
    private LocalDateTime expiryDemo;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

//    RELATIONSHIP
    @OneToMany(mappedBy = "subcription", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Plan> plans;

    @ManyToMany(mappedBy = "subcriptions", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Company> companies;
}

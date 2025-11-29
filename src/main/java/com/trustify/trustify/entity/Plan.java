package com.trustify.trustify.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Long price;
    private String description;

    @ManyToMany
    @JoinTable(
            name = "plan_feature",
            joinColumns = @JoinColumn(name = "plan_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "feature_id", referencedColumnName = "id")
    )
    private Set<Feature> features;

    @ManyToOne
    @JoinColumn(name =  "subcription_id", nullable = false, foreignKey = @ForeignKey(name = "fk_plan_subcription"))
    private Subcription subcription;
}

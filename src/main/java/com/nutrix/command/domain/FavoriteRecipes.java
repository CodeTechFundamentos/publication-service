package com.nutrix.command.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="favorite_recipes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteRecipes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="patient_id", nullable = false)
    private Integer patientId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="recipe_id", nullable = false)
    private Recipe recipe;
    //@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
}
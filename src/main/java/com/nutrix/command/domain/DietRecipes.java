package com.nutrix.command.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="diet_recipes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DietRecipes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="diet_id", nullable = false)
    private Integer dietId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="recipe_id", nullable = false)
    private Recipe recipe;
}
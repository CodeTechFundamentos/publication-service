package com.nutrix.command.infra;

import com.nutrix.command.domain.FavoriteRecipes;
import com.nutrix.command.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFavoriteRecipesRepository extends JpaRepository<FavoriteRecipes, Integer> {
    @Query("Select b.recipe from FavoriteRecipes b where b.patientId = :patient_id")
    public List<Recipe> findByPatient(@Param("patient_id") Integer patient_id);

    @Query("Select b from FavoriteRecipes b where b.patientId = :patient_id and b.recipe.id = :recipe_id")
    public FavoriteRecipes findByPatientAndRecipe(@Param("patient_id") Integer patient_id, @Param("recipe_id") Integer recipe_id);
}
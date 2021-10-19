package com.nutrix.command.infra;

import com.nutrix.command.domain.DietRecipes;
import com.nutrix.command.domain.FavoriteRecipes;
import com.nutrix.command.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDietRecipesRepository extends JpaRepository<DietRecipes, Integer> {
    @Query("Select b.recipe from DietRecipes b where b.dietId = :diet_id")
    public List<Recipe> findByDiet(@Param("diet_id") Integer diet_id);

    @Query("Select b from DietRecipes b where b.dietId = :diet_id and b.recipe.id = :recipe_id")
    public DietRecipes findByDietAndRecipe(@Param("diet_id") Integer diet_id, @Param("recipe_id") Integer recipe_id);
}

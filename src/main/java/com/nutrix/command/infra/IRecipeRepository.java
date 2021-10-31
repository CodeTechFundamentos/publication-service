package com.nutrix.command.infra;

import com.nutrix.command.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface IRecipeRepository extends JpaRepository<Recipe, Integer> {
    @Query("Select b from Recipe b where b.nutritionistId = :nutritionist_id")
    public List<Recipe> findAllByNutritionist(@Param("nutritionist_id") Integer nutritionist_id);
    public Optional<Recipe> findRecipeById(Integer id);
    public Recipe findByName(String name);
    @Query("Select b from Recipe b where b.created_at BETWEEN :date1 AND :date2")
    public List<Recipe> findBetweenDates(@Param("date1") Date date1,
                                         @Param("date2") Date date2);
}
package com.nutrix.command.application.services;

import com.nutrix.command.domain.Recipe;
import com.nutrix.command.infra.IRecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RecipeCommandService extends Recipe {

    @Autowired
    private IRecipeRepository recipeRepository;

    @Override
    @Transactional
    public Recipe save(Recipe recipe) throws Exception {
        return recipeRepository.save(recipe);
    }

    @Override
    @Transactional
    public void delete(Integer id) throws Exception {
        recipeRepository.deleteById(id);
    }
}
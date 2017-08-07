package com.bogovich.recipe.services;

import com.bogovich.recipe.models.Recipe;
import com.bogovich.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<Recipe> getAllRecipes() {
        log.debug("Get all recipe");
        return (List<Recipe>) recipeRepository.findAll();
    }
}

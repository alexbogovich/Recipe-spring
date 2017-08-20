package com.bogovich.recipe.services;

import com.bogovich.recipe.models.Ingredient;
import com.bogovich.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService{

    private final RecipeRepository recipeRepository;

    public IngredientServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Ingredient findByRecipeIdAndIngridientId(Long recipeId, Long ingredientId) {
        return recipeRepository
                .findById(recipeId)
                .orElseThrow(() -> new RuntimeException(String.format("No such Recipe id = %d", recipeId)))
                .getIngredients()
                .stream()
                .filter(ing -> ing.getId().equals(ingredientId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String.format("No such Ingidient id = %d for recipe id = %d", ingredientId, recipeId)));
    }
}

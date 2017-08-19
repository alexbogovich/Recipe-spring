package com.bogovich.recipe.services;

import com.bogovich.recipe.models.Recipe;

import java.util.List;

public interface RecipeService {
    List<Recipe> getAllRecipes();
    Recipe findById(Long l);
    Recipe saveRecipe(Recipe recipe);
    void deleteById(Long l);
}

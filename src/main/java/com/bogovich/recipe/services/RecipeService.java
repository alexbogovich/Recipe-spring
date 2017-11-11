package com.bogovich.recipe.services;

import com.bogovich.recipe.models.Recipe;

import java.util.List;

public interface RecipeService {
    List<Recipe> getAllRecipes();

    Recipe findById(String l);

    Recipe saveRecipe(Recipe recipe);

    Recipe saveRecipe(Recipe recipe, Boolean loadIngr);

    void deleteById(String l);
}

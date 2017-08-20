package com.bogovich.recipe.services;

import com.bogovich.recipe.models.Ingredient;

public interface IngredientService {
    Ingredient findByRecipeIdAndIngridientId(Long recipeId, Long ingredientId);
    void saveIngredient(Long rid, Ingredient ingredient);
}

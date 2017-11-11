package com.bogovich.recipe.services;

import com.bogovich.recipe.models.Ingredient;

public interface IngredientService {
    Ingredient findByRecipeIdAndIngridientId(String recipeId, String ingredientId);

    void saveIngredient(String rid, Ingredient ingredient);

    void deleteIngredient(String rid, Ingredient ingredient);

    void deleteIngredient(String rid, String iid);
}

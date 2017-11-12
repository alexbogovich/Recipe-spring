package com.bogovich.recipe.services;

import com.bogovich.recipe.models.Ingredient;
import reactor.core.publisher.Mono;

public interface IngredientService {
    Mono<Ingredient> findByRecipeIdAndIngridientId(String recipeId, String ingredientId);

    Mono<Void> saveIngredient(String rid, Ingredient ingredient);

    Mono<Void> deleteIngredient(String rid, String iid);
}

package com.bogovich.recipe.services;

import com.bogovich.recipe.models.Recipe;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RecipeService {
    Flux<Recipe> getAllRecipes();

    Mono<Recipe> findById(String l);

    Mono<Recipe> saveRecipe(Recipe recipe);

    Mono<Recipe> saveRecipe(Recipe recipe, Boolean loadIngr);

    Mono<Void> deleteById(String l);
}

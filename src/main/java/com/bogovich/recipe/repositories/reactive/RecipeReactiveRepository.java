package com.bogovich.recipe.repositories.reactive;

import com.bogovich.recipe.models.Recipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, String> {}

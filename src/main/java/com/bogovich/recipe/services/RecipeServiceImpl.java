package com.bogovich.recipe.services;

import com.bogovich.recipe.models.Recipe;
import com.bogovich.recipe.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private RecipeReactiveRepository recipeRepository;

    public RecipeServiceImpl(RecipeReactiveRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Flux<Recipe> getAllRecipes() {
        log.debug("Get all recipe");
        return recipeRepository.findAll();
    }

    @Override
    public Mono<Recipe> findById(String id) {
        log.debug(String.format("Get recipe by id %s", id));
        return recipeRepository.findById(id);
    }

    @Override
    public Mono<Recipe> saveRecipe(Recipe recipe) {
        log.debug(String.format("Save recipe id:%s desc:%s",
                                recipe.getId(),
                                recipe.getDescription()));
        return recipeRepository.save(recipe);
    }

    @Override
    public Mono<Recipe> saveRecipe(Recipe recipe, Boolean loadIngr) {
        return findById(recipe.getId())
                .flatMap(r -> {
                    if(loadIngr)
                        recipe.setIngredients(r.getIngredients());
                    return Mono.just(recipe);
                })
        .flatMap(this::saveRecipe);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return recipeRepository.deleteById(id);
    }
}

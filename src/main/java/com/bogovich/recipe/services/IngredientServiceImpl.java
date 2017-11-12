package com.bogovich.recipe.services;

import com.bogovich.recipe.models.Ingredient;
import com.bogovich.recipe.models.Recipe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {
    private final RecipeService recipeService;

    public IngredientServiceImpl(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Override
    public Mono<Ingredient> findByRecipeIdAndIngridientId(String recipeId, String ingredientId) {

        return recipeService.findById(recipeId)
                .flatMapIterable(Recipe::getIngredients)
                .filter(i -> i.getId().equals(ingredientId))
                .single();
    }

    @Override
    public Mono<Void> saveIngredient(String rid, Ingredient ingredient) {
        return recipeService.findById(rid)
                .flatMap(r -> Mono.just(r.updateIngredient(ingredient)))
                .flatMap(recipeService::saveRecipe)
                .flatMap(r -> Mono.empty());
    }

    @Override
    public Mono<Void> deleteIngredient(String rid, String iid) {
        return recipeService.findById(rid)
                .flatMap(r -> {
                    r.setIngredients(r.getIngredients()
                        .stream()
                        .filter(i -> !i.getId().equals(iid))
                        .collect(Collectors.toSet()));
                    return Mono.just(r);
                })
                .flatMap(recipeService::saveRecipe)
                .then();
    }
}

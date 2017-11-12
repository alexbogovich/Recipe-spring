package com.bogovich.recipe.services;

import com.bogovich.recipe.exceptions.NotFoundException;
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
        return Mono.just(recipeService.findById(recipeId)
                            .getIngredients()
                            .stream()
                            .filter(ing -> ing.getId().equals(ingredientId))
                            .findFirst()
                            .orElseThrow(() -> new NotFoundException(String.format(
                                    "No such Ingredient id = %s for recipe id = %s",
                                    ingredientId,
                                    recipeId))));
    }

    @Override
    public Mono<Void> saveIngredient(String rid, Ingredient ingredient) {
        recipeService.saveRecipe(recipeService.findById(rid).updateIngredient(ingredient));
        return Mono.empty();
    }

    @Override
    public Mono<Void> deleteIngredient(String rid, String iid) {
        Recipe recipe = recipeService.findById(rid);
        recipe.setIngredients(recipe.getIngredients()
                .stream()
                .filter(i -> !i.getId().equals(iid))
                .collect(Collectors.toSet()));
        recipeService.saveRecipe(recipe);
        return Mono.empty();
    }
}

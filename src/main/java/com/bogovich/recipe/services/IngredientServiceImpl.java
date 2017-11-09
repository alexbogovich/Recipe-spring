package com.bogovich.recipe.services;

import com.bogovich.recipe.models.Ingredient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {
    private final RecipeService recipeService;

    public IngredientServiceImpl(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Override
    public Ingredient findByRecipeIdAndIngridientId(Long recipeId, Long ingredientId) {
        return recipeService.findById(recipeId)
                            .getIngredients()
                            .stream()
                            .filter(ing -> ing.getId().equals(ingredientId))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException(String.format(
                                    "No such Ingredient id = %d for recipe id = %d",
                                    ingredientId,
                                    recipeId)));
    }

    @Override
    @Transactional
    public void saveIngredient(Long rid, Ingredient ingredient) {
        recipeService.findById(rid).updateIngredient(ingredient);
    }

    @Override
    public void deleteIngredient(Long rid, Ingredient ingredient) {
        deleteIngredient(rid, ingredient.getId());
    }

    @Override
    @Transactional
    public void deleteIngredient(Long rid, Long iid) {
        Ingredient ingredient = findByRecipeIdAndIngridientId(rid, iid);
        ingredient.getRecipe().getIngredients().remove(ingredient);
        ingredient.setRecipe(null);
    }
}
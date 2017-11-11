package com.bogovich.recipe.services;

import com.bogovich.recipe.exceptions.NotFoundException;
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
    public Ingredient findByRecipeIdAndIngridientId(String recipeId, String ingredientId) {
        return recipeService.findById(recipeId)
                            .getIngredients()
                            .stream()
                            .filter(ing -> ing.getId().equals(ingredientId))
                            .findFirst()
                            .orElseThrow(() -> new NotFoundException(String.format(
                                    "No such Ingredient id = %s for recipe id = %s",
                                    ingredientId,
                                    recipeId)));
    }

    @Override
    public void saveIngredient(String rid, Ingredient ingredient) {
        recipeService.saveRecipe(recipeService.findById(rid).updateIngredient(ingredient));
    }

    @Override
    public void deleteIngredient(String rid, Ingredient ingredient) {
        deleteIngredient(rid, ingredient.getId());
    }

    @Override
    public void deleteIngredient(String rid, String iid) {
//        Ingredient ingredient = findByRecipeIdAndIngridientId(rid, iid);
//        ingredient.getRecipe().getIngredients().remove(ingredient);
//        ingredient.setRecipe(null);
    }
}

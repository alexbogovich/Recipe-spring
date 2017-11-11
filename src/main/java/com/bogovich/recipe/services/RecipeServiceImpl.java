package com.bogovich.recipe.services;

import com.bogovich.recipe.exceptions.NotFoundException;
import com.bogovich.recipe.models.Recipe;
import com.bogovich.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<Recipe> getAllRecipes() {
        log.debug("Get all recipe");
        return (List<Recipe>) recipeRepository.findAll();
    }

    @Override
    public Recipe findById(String id) {
        log.debug(String.format("Get recipe by id %s", id));
        return recipeRepository.findById(id)
                               .orElseThrow(() -> new NotFoundException("Recipe not found. For id = " + id));
    }

    @Override
    public Recipe saveRecipe(Recipe recipe) {
        log.debug(String.format("Save recipe id:%s desc:%s",
                                recipe.getId(),
                                recipe.getDescription()));
        return recipeRepository.save(recipe);
    }

    @Override
    public void deleteById(String id) {
        recipeRepository.deleteById(id);
    }
}

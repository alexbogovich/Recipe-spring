package com.bogovich.recipe.services;

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
    public Recipe findById(Long l) {
        log.debug(String.format("Get recipe by id %d", l));
        return recipeRepository.findById(l)
                               .orElseThrow(() -> new RuntimeException("No such recipe id"));
    }

    @Override
    @Transactional
    public Recipe saveRecipe(Recipe recipe) {
        log.debug(String.format("Save recipe id:%d desc:%s",
                                recipe.getId(),
                                recipe.getDescription()));
        return recipeRepository.save(recipe);
    }

    @Override
    public void deleteById(Long l) {
        recipeRepository.deleteById(l);
    }
}

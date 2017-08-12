package com.bogovich.recipe.services;

import com.bogovich.recipe.models.Recipe;
import com.bogovich.recipe.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RecipeServiceImplTest {
    RecipeService recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    public void getAllRecipes() throws Exception {
        Recipe recipe = new Recipe();
        List<Recipe> receiptData = new ArrayList<>();
        receiptData.add(recipe);
        when(recipeService.getAllRecipes()).thenReturn(receiptData);

        List<Recipe> recipes = recipeService.getAllRecipes();

        assertEquals(1, recipes.size());
        verify(recipeRepository, times(1)).findAll();
    }

}
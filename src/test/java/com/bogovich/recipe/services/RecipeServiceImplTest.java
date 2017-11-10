package com.bogovich.recipe.services;

import com.bogovich.recipe.exceptions.NotFoundException;
import com.bogovich.recipe.models.Recipe;
import com.bogovich.recipe.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {
    private RecipeService recipeService;

    @Mock
    private RecipeRepository recipeRepository;

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
        assertEquals(receiptData, recipes);
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void deleteById() throws Exception {
        Long id = new Random().nextLong();
        recipeService.deleteById(id);
        verify(recipeRepository, times(1)).deleteById(id);
    }


    @Test(expected = NotFoundException.class)
    public void findByIdException() throws Exception {
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.empty());
        recipeService.findById(1L);
    }

}

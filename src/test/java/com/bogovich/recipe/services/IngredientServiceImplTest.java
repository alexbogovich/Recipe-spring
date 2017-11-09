package com.bogovich.recipe.services;

import com.bogovich.recipe.models.Ingredient;
import com.bogovich.recipe.models.Recipe;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class IngredientServiceImplTest {

    private IngredientService ingredientService;

    @Mock
    private RecipeService recipeService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ingredientService = new IngredientServiceImpl(recipeService);
    }


    @Test
    public void findByRecipeIdAndIngridientId() throws Exception {
        Recipe recipe = setUpRecipe(11L);
        Ingredient ingredient1 = setUpIngredient(1L, recipe);
        Ingredient ingredient2 = setUpIngredient(2L, recipe);

        when(recipeService.findById(recipe.getId())).thenReturn(recipe);

        assertEquals(ingredient1,
                     ingredientService.findByRecipeIdAndIngridientId(recipe.getId(),
                                                                     ingredient1.getId()));
        verify(recipeService, times(1)).findById(recipe.getId());

        assertEquals(ingredient2,
                     ingredientService.findByRecipeIdAndIngridientId(recipe.getId(),
                                                                     ingredient2.getId()));
        verify(recipeService, times(2)).findById(recipe.getId());
    }

    @Test(expected = RuntimeException.class)
    public void findByRecipeIdAndIngridientIdInvalidRecipeId() throws Exception {
        when(recipeService.findById(anyLong())).thenReturn(null);
        ingredientService.findByRecipeIdAndIngridientId(1L, 2L);
    }

    @Test(expected = RuntimeException.class)
    public void findByRecipeIdAndIngridientIdRecipeNotContainSuchIng() throws Exception {
        when(recipeService.findById(anyLong())).thenReturn(new Recipe());
        ingredientService.findByRecipeIdAndIngridientId(1L, 2L);
    }

    @Test
    public void saveIngredientNew() throws Exception {
        Recipe recipe = setUpRecipe(11L);
        Ingredient ingredient1 = setUpIngredient(1L, recipe);
        Ingredient ingredient2 = setUpIngredient(2L, recipe);
        Ingredient ingredientNew = new Ingredient();

        when(recipeService.findById(recipe.getId())).thenReturn(recipe);
        ingredientService.saveIngredient(recipe.getId(), ingredientNew);

        assertEquals(recipe, ingredientNew.getRecipe());
        assertTrue(recipe.getIngredients().contains(ingredientNew));
        assertEquals(3, recipe.getIngredients().size());
    }

    @Test
    public void saveIngredientUpdate() throws Exception {
        Recipe recipe = setUpRecipe(11L);
        setUpIngredient(1L, recipe);
        setUpIngredient(2L, recipe);
        Ingredient ingredientUpdate = new Ingredient();
        ingredientUpdate.setId(1L);
        ingredientUpdate.setAmount(new BigDecimal(100));
        ingredientUpdate.setDescription("123");

        when(recipeService.findById(recipe.getId())).thenReturn(recipe);
        ingredientService.saveIngredient(recipe.getId(), ingredientUpdate);

        assertTrue(recipe.getIngredients()
                         .stream()
                         .anyMatch(i -> Ingredient.isEqualByValue(i, ingredientUpdate)));
        assertEquals(2, recipe.getIngredients().size());
    }

    @Test(expected = RuntimeException.class)
    public void saveIngredientRecipeNotExist() throws Exception {
        when(recipeService.findById(anyLong())).thenReturn(null);
        ingredientService.saveIngredient(1L, new Ingredient());
    }

    @Test
    public void deleteIngredient() throws Exception {
        Recipe recipe = setUpRecipe(22L);
        Ingredient ingredient1 = setUpIngredient(1L, recipe);
        Ingredient ingredient2 = setUpIngredient(2L, recipe);

        when(recipeService.findById(recipe.getId())).thenReturn(recipe);
        ingredientService.deleteIngredient(recipe.getId(), ingredient1.getId());

        assertFalse(recipe.getIngredients()
                          .stream()
                          .anyMatch(i -> Ingredient.isEqualByValue(i, ingredient1)));
        assertEquals(1, recipe.getIngredients().size());
        verify(recipeService, times(1)).findById(recipe.getId());

        ingredientService.deleteIngredient(recipe.getId(), ingredient2.getId());
        assertFalse(recipe.getIngredients()
                          .stream()
                          .anyMatch(i -> Ingredient.isEqualByValue(i, ingredient2)));
        assertEquals(0, recipe.getIngredients().size());
        verify(recipeService, times(2)).findById(recipe.getId());
    }

    @Test(expected = RuntimeException.class)
    public void deleteIngredientException() throws Exception {
        when(recipeService.findById(anyLong())).thenReturn(new Recipe());
        ingredientService.deleteIngredient(1L, 2L);
    }

    private Recipe setUpRecipe(Long id) {
        Recipe recipe = new Recipe();
        recipe.setId(id);
        return recipe;
    }

    private Ingredient setUpIngredient(Long id, Recipe recipe) {
        final Ingredient ingredient = new Ingredient();
        ingredient.setId(id);
        recipe.addIngredient(ingredient);
        return ingredient;
    }
}
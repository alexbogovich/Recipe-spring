package com.bogovich.recipe.services;

import com.bogovich.recipe.models.Ingredient;
import com.bogovich.recipe.models.Recipe;
import com.bogovich.recipe.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class IngredientServiceImplTest {

    private IngredientService ingredientService;

    @Mock
    private RecipeRepository recipeRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ingredientService = new IngredientServiceImpl(recipeRepository);
    }


    @Test
    public void findByRecipeIdAndIngridientId() throws Exception {
        Recipe recipe = setUpRecipe(11L);
        Ingredient ingredient1 = setUpIngredient(1L, recipe);
        Ingredient ingredient2 = setUpIngredient(2L, recipe);

        when(recipeRepository.findById(recipe.getId())).thenReturn(Optional.of(recipe));

        assertEquals(ingredient1,
                     ingredientService.findByRecipeIdAndIngridientId(recipe.getId(),
                                                                     ingredient1.getId()));
        verify(recipeRepository, times(1)).findById(recipe.getId());

        assertEquals(ingredient2,
                     ingredientService.findByRecipeIdAndIngridientId(recipe.getId(),
                                                                     ingredient2.getId()));
        verify(recipeRepository, times(2)).findById(recipe.getId());
    }

    @Test(expected = RuntimeException.class)
    public void findByRecipeIdAndIngridientIdInvalidRecipeId() throws Exception {
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.empty());
        ingredientService.findByRecipeIdAndIngridientId(1L, 2L);
    }

    @Test(expected = RuntimeException.class)
    public void findByRecipeIdAndIngridientIdRecipeNotContainSuchIng() throws Exception {
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(new Recipe()));
        ingredientService.findByRecipeIdAndIngridientId(1L, 2L);
    }

    @Test
    public void saveIngredientNew() throws Exception {
        Recipe recipe = setUpRecipe(11L);
        Ingredient ingredient1 = setUpIngredient(1L, recipe);
        Ingredient ingredient2 = setUpIngredient(2L, recipe);
        Ingredient ingredientNew = new Ingredient();

        when(recipeRepository.findById(recipe.getId())).thenReturn(Optional.of(recipe));
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

        when(recipeRepository.findById(recipe.getId())).thenReturn(Optional.of(recipe));
        ingredientService.saveIngredient(recipe.getId(), ingredientUpdate);

        assertTrue(recipe.getIngredients()
                         .stream()
                         .anyMatch(i -> Ingredient.isEqual(i, ingredientUpdate)));
        assertEquals(2, recipe.getIngredients().size());
    }

    @Test(expected = RuntimeException.class)
    public void saveIngredientRecipeNotExist() throws Exception {
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.empty());
        ingredientService.saveIngredient(1L, new Ingredient());
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
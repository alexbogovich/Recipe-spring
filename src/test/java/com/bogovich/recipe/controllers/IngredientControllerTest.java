package com.bogovich.recipe.controllers;

import com.bogovich.recipe.models.Ingredient;
import com.bogovich.recipe.models.Recipe;
import com.bogovich.recipe.services.IngredientService;
import com.bogovich.recipe.services.RecipeService;
import com.bogovich.recipe.services.UnitOfMeasureService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Random;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IngredientControllerTest {

    @Mock
    private IngredientService ingredientService;

    @Mock
    private UnitOfMeasureService unitOfMeasureService;

    @Mock
    private RecipeService recipeService;

    private IngredientController controller;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new IngredientController(recipeService,
                                              ingredientService,
                                              unitOfMeasureService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void listIngredients() throws Exception {
        Long id = new Random().nextLong();
        Recipe recipe = new Recipe();
        recipe.setId(id);

        when(recipeService.findById(id)).thenReturn(recipe);

        mockMvc.perform(get(String.format("/recipe/%d/ingredients", id)))
               .andExpect(status().isOk())
               .andExpect(view().name("recipe/ingredient/list"))
               .andExpect(model().attributeExists("recipe"));

        verify(recipeService, times(1)).findById(recipe.getId());
    }

    @Test
    public void showOneRecipeIngredient() throws Exception {
        Recipe recipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        recipe.setId(new Random().nextLong());
        ingredient.setId(new Random().nextLong());
        recipe.getIngredients().add(ingredient);

        when(ingredientService.findByRecipeIdAndIngridientId(recipe.getId(),
                                                             ingredient.getId())).thenReturn(
                ingredient);

        mockMvc.perform(get(String.format("/recipe/%d/ingredient/%d/show",
                                          recipe.getId(),
                                          ingredient.getId())))
               .andExpect(status().isOk())
               .andExpect(view().name("recipe/ingredient/show"))
               .andExpect(model().attributeExists("ingredient"));

        verify(ingredientService, times(1)).findByRecipeIdAndIngridientId(recipe.getId(),
                                                                          ingredient.getId());
    }

    @Test
    public void updateRecipeIngredient() throws Exception {
        Recipe recipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        recipe.setId(new Random().nextLong());
        ingredient.setId(new Random().nextLong());
        recipe.getIngredients().add(ingredient);

        when(ingredientService.findByRecipeIdAndIngridientId(recipe.getId(),
                                                             ingredient.getId())).thenReturn(
                ingredient);
        when(unitOfMeasureService.listAllUoms()).thenReturn(new ArrayList<>());

        mockMvc.perform(get(String.format("/recipe/%d/ingredient/%d/update",
                                          recipe.getId(),
                                          ingredient.getId())))
               .andExpect(status().isOk())
               .andExpect(view().name("recipe/ingredient/ingredientform"))
               .andExpect(model().attributeExists("ingredient"))
               .andExpect(model().attributeExists("uomList"));

        verify(ingredientService, times(1)).findByRecipeIdAndIngridientId(recipe.getId(),
                                                                          ingredient.getId());
        verify(unitOfMeasureService, times(1)).listAllUoms();
    }

    @Test
    public void newIngredient() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(new Random().nextLong());

        when(recipeService.findById(recipe.getId())).thenReturn(recipe);
        when(unitOfMeasureService.listAllUoms()).thenReturn(new ArrayList<>());

        mockMvc.perform(get(String.format("/recipe/%d/ingredient/new", recipe.getId())))
               .andExpect(status().isOk())
               .andExpect(view().name("recipe/ingredient/ingredientform"))
               .andExpect(model().attributeExists("ingredient"))
               .andExpect(model().attributeExists("uomList"));

        verify(recipeService, times(1)).findById(recipe.getId());
        verify(unitOfMeasureService, times(1)).listAllUoms();
    }

    @Test
    public void saveOrUpdateNewIngredient() throws Exception {
        Recipe recipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        recipe.setId(new Random().nextLong());


        mockMvc.perform(post(String.format("/recipe/%d/ingredient", recipe.getId())).flashAttr(
                "ingredient",
                ingredient))
               .andExpect(status().is3xxRedirection())
               .andExpect(view().name(String.format("redirect:/recipe/%d/ingredients",
                                                    recipe.getId())));

        verify(ingredientService, times(1)).saveIngredient(recipe.getId(), ingredient);
    }

    @Test
    public void saveOrUpdateExistingIngredient() throws Exception {
        Recipe recipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId(new Random().nextLong());
        recipe.setId(new Random().nextLong());

        mockMvc.perform(post(String.format("/recipe/%d/ingredient", recipe.getId())).flashAttr(
                "ingredient",
                ingredient))
               .andExpect(status().is3xxRedirection())
               .andExpect(view().name(String.format("redirect:/recipe/%d/ingredient/%d/show",
                                                    recipe.getId(),
                                                    ingredient.getId())));

        verify(ingredientService, times(1)).saveIngredient(recipe.getId(), ingredient);
    }
}
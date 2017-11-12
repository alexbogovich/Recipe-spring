package com.bogovich.recipe.controllers;

import com.bogovich.recipe.exceptions.NotFoundException;
import com.bogovich.recipe.models.Ingredient;
import com.bogovich.recipe.models.Recipe;
import com.bogovich.recipe.models.UnitOfMeasure;
import com.bogovich.recipe.services.IngredientService;
import com.bogovich.recipe.services.RecipeService;
import com.bogovich.recipe.services.UnitOfMeasureService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Flux;

import static com.bogovich.recipe.controllers.ExceptionTest.testGet;
import static java.util.UUID.randomUUID;
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
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ExceptionHandlerController())
                .build();
    }

    @Test
    public void listIngredients() throws Exception {
        String id = randomUUID().toString();
        Recipe recipe = new Recipe();
        recipe.setId(id);

        when(recipeService.findById(id)).thenReturn(recipe);

        mockMvc.perform(get(String.format("/recipe/%s/ingredients", id)))
               .andExpect(status().isOk())
               .andExpect(view().name("recipe/ingredient/list"))
               .andExpect(model().attributeExists("recipe"));

        verify(recipeService, times(1)).findById(recipe.getId());
    }

    @Test
    public void listIngredientsNotFound() throws Exception {
        when(recipeService.findById(anyString())).thenThrow(NotFoundException.class);
        testGet(mockMvc,"/recipe/1/ingredients", status().isNotFound());
    }

//    @Test
//    public void listIngredientsNumberException() throws Exception {
//        testGet(mockMvc,"/recipe/abc/ingredients", status().isBadRequest());
//    }

    @Test
    public void showOneRecipeIngredient() throws Exception {
        Recipe recipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        recipe.setId(randomUUID().toString());
        recipe.getIngredients().add(ingredient);

        when(ingredientService.findByRecipeIdAndIngridientId(recipe.getId(),
                                                             ingredient.getId())).thenReturn(
                ingredient);

        mockMvc.perform(get(String.format("/recipe/%s/ingredient/%s/show",
                                          recipe.getId(),
                                          ingredient.getId())))
               .andExpect(status().isOk())
               .andExpect(view().name("recipe/ingredient/show"))
               .andExpect(model().attributeExists("ingredient"));

        verify(ingredientService, times(1)).findByRecipeIdAndIngridientId(recipe.getId(),
                                                                          ingredient.getId());
    }

    @Test
    public void showOneRecipeIngredientRecipeNotFound() throws Exception {
        when(ingredientService.findByRecipeIdAndIngridientId(anyString(), anyString())).thenThrow(NotFoundException.class);
        testGet(mockMvc,"/recipe/1/ingredient/1/show", status().isNotFound());
    }

//    @Test
//    public void showOneRecipeIngredientRecipeNumberFormat() throws Exception {
//        testGet(mockMvc,"/recipe/a/ingredient/1/show", status().isBadRequest());
//    }

    @Test
    public void showOneRecipeIngredientIngredientNotFound() throws Exception {
        when(ingredientService.findByRecipeIdAndIngridientId(anyString(), anyString())).thenThrow(NotFoundException.class);
        testGet(mockMvc,"/recipe/1/ingredient/1/show", status().isNotFound());
    }

//    @Test
//    public void showOneRecipeIngredientIngredientNumberFormat() throws Exception {
//        testGet(mockMvc,"/recipe/1/ingredient/a/show", status().isBadRequest());
//    }

    @Test
    public void updateRecipeIngredient() throws Exception {
        Recipe recipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        recipe.setId(randomUUID().toString());
        ingredient.setId(randomUUID().toString());
        recipe.getIngredients().add(ingredient);

        when(ingredientService.findByRecipeIdAndIngridientId(recipe.getId(),
                                                             ingredient.getId())).thenReturn(
                ingredient);
        when(unitOfMeasureService.listAllUoms()).thenReturn(Flux.just(new UnitOfMeasure()));

        mockMvc.perform(get(String.format("/recipe/%s/ingredient/%s/update",
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
        when(unitOfMeasureService.listAllUoms()).thenReturn(Flux.just(new UnitOfMeasure()));

        mockMvc.perform(get(String.format("/recipe/%s/ingredient/new", randomUUID().toString())))
               .andExpect(status().isOk())
               .andExpect(view().name("recipe/ingredient/ingredientform"))
               .andExpect(model().attributeExists("ingredient"))
               .andExpect(model().attributeExists("uomList"));

        verify(unitOfMeasureService, times(1)).listAllUoms();
    }

    @Test
    public void saveOrUpdateNewIngredient() throws Exception {
        Recipe recipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        recipe.setId(randomUUID().toString());


        mockMvc.perform(post(String.format("/recipe/%s/ingredient", recipe.getId())).flashAttr(
                "ingredient",
                ingredient))
               .andExpect(status().is3xxRedirection())
               .andExpect(view().name(String.format("redirect:/recipe/%s/ingredients",
                                                    recipe.getId())));

        verify(ingredientService, times(1)).saveIngredient(recipe.getId(), ingredient);
    }

    @Test
    public void saveOrUpdateExistingIngredient() throws Exception {
        Recipe recipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId(randomUUID().toString());
        recipe.setId(randomUUID().toString());

        mockMvc.perform(post(String.format("/recipe/%s/ingredient", recipe.getId())).flashAttr(
                "ingredient",
                ingredient))
               .andExpect(status().is3xxRedirection())
               .andExpect(view().name(String.format("redirect:/recipe/%s/ingredients",
                                                    recipe.getId())));

        verify(ingredientService, times(1)).saveIngredient(recipe.getId(), ingredient);
    }
}

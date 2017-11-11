package com.bogovich.recipe.controllers;

import com.bogovich.recipe.exceptions.NotFoundException;
import com.bogovich.recipe.models.Recipe;
import com.bogovich.recipe.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Random;

import static com.bogovich.recipe.controllers.ExceptionTest.testGet;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RecipeControllerTest {
    @Mock
    private RecipeService recipeService;

    private RecipeController recipeController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeController = new RecipeController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController)
                .setControllerAdvice(new ExceptionHandlerController())
                .build();
    }

    @Test
    public void showById() throws Exception {
        Long id = new Random().nextLong();
        Recipe recipe = new Recipe();
        recipe.setId(id);

        when(recipeService.findById(id)).thenReturn(recipe);

        mockMvc.perform(get(String.format("/recipe/%d/show", id)))
               .andExpect(status().isOk())
               .andExpect(view().name("recipe/show"))
               .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void showByIdNotFound() throws Exception {
        when(recipeService.findById(anyLong())).thenThrow(NotFoundException.class);
        testGet(mockMvc,"/recipe/1/show", status().isNotFound());
    }

    @Test
    public void showByIdNumberFormat() throws Exception {
        testGet(mockMvc,"/recipe/a/show", status().isBadRequest());
    }

    @Test
    public void getNewRecipe() throws Exception {
        mockMvc.perform(get("/recipe/new")).andExpect(status().isOk()).andExpect(view().name(
                "recipe/recipeform")).andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void postNewRecipe() throws Exception {
        Long id = new Random().nextLong();
        Recipe recipe = new Recipe();
        recipe.setId(id);

        when(recipeService.saveRecipe(any())).thenReturn(recipe);

        mockMvc.perform(post("/recipe").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                       .param("id", "")
                                       .param("description", "some string")
                                       .param("directions", "dirs"))
               .andExpect(status().is3xxRedirection())
               .andExpect(view().name(String.format("redirect:/recipe/%d/show", id)));
    }

    @Test
    public void postNewRecipeValidationBlankDirections() throws Exception {
        mockMvc.perform(post("/recipe").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string"))
                .andExpect(view().name("recipe/recipeform"));
    }

    @Test
    public void postNewRecipeValidationBlankDescription() throws Exception {
        mockMvc.perform(post("/recipe").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("directions", "132"))
                .andExpect(view().name("recipe/recipeform"));
    }

    @Test
    public void postNewRecipeValidationEmptyDirectionsAndDirections() throws Exception {
        mockMvc.perform(post("/recipe").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("directions", "")
                .param("description", ""))
                .andExpect(view().name("recipe/recipeform"));
    }

    @Test
    public void postNewRecipeValidationMinusPrepTime() throws Exception {
        mockMvc.perform(post("/recipe").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("directions", "valid")
                .param("description", "valid")
                .param("prepTime", "-1")
        )
                .andExpect(view().name("recipe/recipeform"));
    }

    @Test
    public void postNewRecipeValidationMinusCookTime() throws Exception {
        mockMvc.perform(post("/recipe").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("directions", "valid")
                .param("description", "valid")
                .param("cookTime", "-1")
        )
                .andExpect(view().name("recipe/recipeform"));
    }

    @Test
    public void postNewRecipeValidationBadUrl() throws Exception {
        mockMvc.perform(post("/recipe").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("directions", "valid")
                .param("description", "valid")
                .param("url", "123")
        )
                .andExpect(view().name("recipe/recipeform"));
    }


    @Test
    public void saveOrUpdate() throws Exception {
        Long id = new Random().nextLong();
        Recipe recipe = new Recipe();
        recipe.setId(id);

        when(recipeService.findById(id)).thenReturn(recipe);

        mockMvc.perform(get(String.format("/recipe/%d/update", id)))
               .andExpect(status().isOk())
               .andExpect(view().name("recipe/recipeform"))
               .andExpect(model().attributeExists("recipe"));
    }


    @Test
    public void deleteById() throws Exception {
        Long id = new Random().nextLong();
        mockMvc.perform(get(String.format("/recipe/%d/delete", id)))
               .andExpect(status().is3xxRedirection())
               .andExpect(view().name("redirect:/"));
        verify(recipeService, times(1)).deleteById(id);
    }
}

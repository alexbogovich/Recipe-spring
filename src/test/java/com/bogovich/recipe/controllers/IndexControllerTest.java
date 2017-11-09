package com.bogovich.recipe.controllers;

import com.bogovich.recipe.models.Recipe;
import com.bogovich.recipe.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class IndexControllerTest {
    private IndexController indexController;

    @Mock
    private RecipeService recipeService;
    @Mock
    private Model model;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        indexController = new IndexController(recipeService);
    }

    @Test
    public void testMockMVC() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
        mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("index"));
    }

    @Test
    public void getIndexPage() throws Exception {
        //given
        List<Recipe> recipeList = new ArrayList<>();
        recipeList.add(new Recipe());
        recipeList.add(new Recipe());
        recipeList.add(new Recipe());

        ArgumentCaptor<List<Recipe>> captor = ArgumentCaptor.forClass(List.class);

        //when
        when(recipeService.getAllRecipes()).thenReturn(recipeList);

        //then
        assertEquals("index", indexController.getIndexPage(model));
        verify(recipeService, times(1)).getAllRecipes();
        verify(model, times(1)).addAttribute(eq("recipeList"), captor.capture());
        assertEquals(recipeList.size(), captor.getValue().size());
    }

}
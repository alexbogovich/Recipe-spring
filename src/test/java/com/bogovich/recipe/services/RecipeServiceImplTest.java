package com.bogovich.recipe.services;

import com.bogovich.recipe.models.Recipe;
import com.bogovich.recipe.repositories.reactive.RecipeReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {
    private RecipeService recipeService;

    @Mock
    private RecipeReactiveRepository recipeRepository;

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
        when(recipeService.getAllRecipes()).thenReturn(Flux.fromIterable(receiptData));

        List<Recipe> recipes = recipeService.getAllRecipes().collectList().block();

        assertEquals(1, recipes.size());
        assertEquals(receiptData, recipes);
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void deleteById() throws Exception {
        String id = randomUUID().toString();
        recipeService.deleteById(id);
        verify(recipeRepository, times(1)).deleteById(id);
    }


//    @Test(expected = TemplateInputException.class)
//    public void findByIdException() throws Exception {
//        when(recipeRepository.findById(anyString())).thenReturn(Mono.empty());
//        recipeService.findById(randomUUID().toString());
//    }

}

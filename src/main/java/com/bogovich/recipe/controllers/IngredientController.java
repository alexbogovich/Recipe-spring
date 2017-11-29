package com.bogovich.recipe.controllers;


import com.bogovich.recipe.models.Ingredient;
import com.bogovich.recipe.models.UnitOfMeasure;
import com.bogovich.recipe.services.IngredientService;
import com.bogovich.recipe.services.RecipeService;
import com.bogovich.recipe.services.UnitOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import reactor.core.publisher.Flux;

import javax.validation.Valid;

//@Slf4j
@Controller
public class IngredientController {

    private static final String RECIPE_INGREDIENTFORM_URL = "recipe/ingredient/ingredientform";
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService,
                                IngredientService ingredientService,
                                UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable String recipeId, Model model) {
//        log.debug("Getting ingredient list for recipe id: " + recipeId);
        model.addAttribute("recipe", recipeService.findById(recipeId));
        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showOneRecipeIngredient(@PathVariable String recipeId,
                                          @PathVariable String ingredientId,
                                          Model model) {
        model.addAttribute("ingredient",
                           ingredientService.findByRecipeIdAndIngridientId(recipeId, ingredientId));
        return "recipe/ingredient/show";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId,
                                         @PathVariable String ingredientId,
                                         Model model) {
        model.addAttribute("ingredient",
                           ingredientService.findByRecipeIdAndIngridientId(recipeId, ingredientId).block());
        model.addAttribute("recipeId", recipeId);
        return RECIPE_INGREDIENTFORM_URL;
    }

    @GetMapping("recipe/{recipeId}/ingredient/new")
    public String newIngredient(@PathVariable String recipeId, Model model) {
        model.addAttribute("ingredient", new Ingredient());
        model.addAttribute("recipeId", recipeId);
        return RECIPE_INGREDIENTFORM_URL;
    }

    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@PathVariable String recipeId,
                               @Valid @ModelAttribute Ingredient ingredient, BindingResult bindingResult, Model model) {
//        log.info(ingredient.toString());
        if(!bindingResult.hasErrors()) {
            ingredientService.saveIngredient(recipeId, ingredient).block();
            return String.format("redirect:/recipe/%s/ingredients", recipeId);
        } else {
//            log.debug(bindingResult.getAllErrors().toString());
            return RECIPE_INGREDIENTFORM_URL;
        }
    }

    @PostMapping("recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteIngredient(@PathVariable String recipeId, @PathVariable String ingredientId) {
//        log.debug("Delete ingredient " + ingredientId + " for recipe " + recipeId);
        ingredientService.deleteIngredient(recipeId, ingredientId).block();
        return String.format("redirect:/recipe/%s/ingredients", recipeId);
    }

    @ModelAttribute("uomList")
    public Flux<UnitOfMeasure> populateUOM(){
        return unitOfMeasureService.listAllUoms();
    }
}

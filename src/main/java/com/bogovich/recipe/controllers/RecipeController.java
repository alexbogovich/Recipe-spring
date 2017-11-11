package com.bogovich.recipe.controllers;

import com.bogovich.recipe.models.Recipe;
import com.bogovich.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
public class RecipeController {
    private final static String RECIPE_FORM_URL = "recipe/recipeform";

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{id}/show")
    public String showById(@PathVariable Long id, Model model) {
        log.debug(String.format("Get request for %s id recipe", id));
        model.addAttribute("recipe", recipeService.findById(id));
        return "recipe/show";
    }

    @GetMapping("recipe/new")
    public String newRecipe(Model model) {
        log.debug("Create empoty Recipe");
        model.addAttribute("recipe", new Recipe());
        return RECIPE_FORM_URL;
    }

    @GetMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable Long id, Model model) {
        log.debug(String.format("Get request for %s id recipe", id));
        model.addAttribute("recipe", recipeService.findById(id));
        return RECIPE_FORM_URL;
    }

    @PostMapping("recipe")
    public String saveOrUpdate(@Valid @ModelAttribute Recipe recipe, BindingResult bindingResult) {
        if(!bindingResult.hasErrors()) {
            Recipe saveOrUpdateRecipe = recipeService.saveRecipe(recipe);
            return "redirect:/recipe/" + saveOrUpdateRecipe.getId() + "/show";
        } else {
            log.debug(bindingResult.getAllErrors().toString());
            return RECIPE_FORM_URL;
        }
    }

    @GetMapping("recipe/{id}/delete")
    public String deleteById(@PathVariable Long id) {
        log.debug("Deleting id: " + id);
        recipeService.deleteById(id);
        return "redirect:/";
    }
}

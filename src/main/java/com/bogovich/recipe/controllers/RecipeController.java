package com.bogovich.recipe.controllers;

import com.bogovich.recipe.models.Recipe;
import com.bogovich.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model) {
        log.debug(String.format("Get request for %s id recipe", id));
        model.addAttribute("recipe", recipeService.findById(new Long(id)));
        return "recipe/show";
    }

    @RequestMapping("recipe/new")
    public String newRecipe(Model model){
        log.debug("Create empoty Recipe");
        model.addAttribute("recipe", new Recipe());
        return "recipe/recipeform";
    }

    @RequestMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model){
        log.debug(String.format("Get request for %s id recipe", id));
        model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));
        return "recipe/recipeform";
    }

    @PostMapping
    @RequestMapping("recipe")
    public String saveOrUpdate(@ModelAttribute Recipe recipe){
        log.debug(String.format("Receive %s recipe", recipe.getDescription()));
        Recipe saveOrUpdateRecipe = recipeService.saveRecipe(recipe);
        log.debug(String.format("Save and return %s recipe with id:", recipe.getDescription(), recipe.getId()));
        return "redirect:/recipe/"+saveOrUpdateRecipe.getId()+"/show";
    }
}

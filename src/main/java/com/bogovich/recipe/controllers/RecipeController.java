package com.bogovich.recipe.controllers;

import com.bogovich.recipe.exceptions.NotFoundException;
import com.bogovich.recipe.models.Recipe;
import com.bogovich.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class RecipeController {
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
        return "recipe/recipeform";
    }

    @GetMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable Long id, Model model) {
        log.debug(String.format("Get request for %s id recipe", id));
        model.addAttribute("recipe", recipeService.findById(id));
        return "recipe/recipeform";
    }

    @PostMapping("recipe")
    public String saveOrUpdate(@ModelAttribute Recipe recipe) {
        log.debug(String.format("Receive %s recipe", recipe.getDescription()));
        Recipe saveOrUpdateRecipe = recipeService.saveRecipe(recipe);
        log.debug(String.format("Save and return %s recipe with id:%d",
                                recipe.getDescription(),
                                recipe.getId()));
        return "redirect:/recipe/" + saveOrUpdateRecipe.getId() + "/show";
    }

    @GetMapping("recipe/{id}/delete")
    public String deleteById(@PathVariable Long id) {
        log.debug("Deleting id: " + id);
        recipeService.deleteById(id);
        return "redirect:/";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception ex){
        log.error(ex.getMessage());
        return new ModelAndView("errorPage")
                .addObject("exception", ex)
                .addObject("errorCode", 404)
                .addObject("errorTitle", "404 NOT FOUND");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleNumberFormat(Exception ex){
        log.error(ex.getMessage());
        return new ModelAndView("errorPage")
                .addObject("exception", ex)
                .addObject("errorCode", 400)
                .addObject("errorTitle", "400 BAD FORMAT");
    }
}

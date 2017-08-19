package com.bogovich.recipe.controllers;

import com.bogovich.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class IndexController {

    private RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
        log.debug("Call index");
        model.addAttribute("recipeList", recipeService.getAllRecipes());
//        System.out.println(recipeService.getAllRecipes().get(0).getIngredients());
        //System.out.println(recipeService.getAllRecipes());
        log.debug("Return index");
        return "index";
    }
}

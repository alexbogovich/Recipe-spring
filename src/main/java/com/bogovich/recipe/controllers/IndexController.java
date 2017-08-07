package com.bogovich.recipe.controllers;

import com.bogovich.recipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    private RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
        model.addAttribute("recipeList", recipeService.getAllRecipes());
//        System.out.println(recipeService.getAllRecipes().get(0).getIngredients());
        //System.out.println(recipeService.getAllRecipes());
        return "index";
    }
}

package com.bogovich.recipe.controllers;

import com.bogovich.recipe.models.Category;
import com.bogovich.recipe.models.UnitOfMeasure;
import com.bogovich.recipe.repositories.CategoryRepository;
import com.bogovich.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {

    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }


    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(){
        Optional<Category> categoryOptional = categoryRepository.findByDescription("American");
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Tablespoon");

        System.out.println("Cat id is: " + categoryOptional.get().getId());
        System.out.println("Cat desc is: " + categoryOptional.get().getDescription());

        System.out.println("Uom id is: " + unitOfMeasureOptional.get().getId());
        System.out.println("Uom desc is: " + unitOfMeasureOptional.get().getDescription());
        return "index";
    }
}

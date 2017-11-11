package com.bogovich.recipe.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Document
public class Recipe {

    @Id
    private String id;
    @NotBlank
    private String description;
    @Min(0)
    private Integer prepTime;
    @Min(0)
    private Integer cookTime;
    @Min(0)
    private String servings;
    private String source;
    @URL
    private String url;
    @NotBlank
    private String directions;
    private Difficulty difficulty;
    private Set<Ingredient> ingredients = new HashSet<>();
    private Byte[] image;
    private Notes notes;
    @DBRef
    private Set<Category> categories = new HashSet<>();

    public Recipe addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        return this;
    }

    public Recipe updateIngredient(Ingredient newIngredientValue) {
        final Ingredient ingredient = ingredients.stream()
                                                 .filter(i -> i.getId()
                                                               .equals(newIngredientValue.getId()))
                                                 .findFirst()
                                                 .map(i -> i.updateValue(newIngredientValue))
                                                 .orElse(newIngredientValue);
        if (ingredient.getId() == null) {
            this.addIngredient(ingredient);
        }
        return this;
    }
}


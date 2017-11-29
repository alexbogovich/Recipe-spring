package com.bogovich.recipe.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

//@Getter
//@Setter
@Document
//@NoArgsConstructor
public class Category {
    @Id
    private String id;
    private String description;

    @DBRef
    private Set<Recipe> recipes = new HashSet<>();

    public Category() {
    }

    private Category(String description) {
        this.description = description;
    }

    public static Category of(String description) {
        return new Category(description);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
    }
}

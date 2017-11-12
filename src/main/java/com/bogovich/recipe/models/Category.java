package com.bogovich.recipe.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Document
@NoArgsConstructor
public class Category {
    @Id
    private String id;
    private String description;

    @DBRef
    private Set<Recipe> recipes = new HashSet<>();

    private Category(String description) {
        this.description = description;
    }

    public static Category of(String description) {
        return new Category(description);
    }
}

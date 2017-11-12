package com.bogovich.recipe.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
@NoArgsConstructor
public class UnitOfMeasure {
    @Id
    private String id;
    private String description;

    private UnitOfMeasure(String description) {
        this.description = description;
    }

    public static UnitOfMeasure of(String description) {
        return new UnitOfMeasure(description);
    }
}

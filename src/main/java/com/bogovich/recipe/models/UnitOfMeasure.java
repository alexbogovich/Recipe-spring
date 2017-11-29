package com.bogovich.recipe.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//@Getter
//@Setter
@Document
//@NoArgsConstructor
public class UnitOfMeasure {
    @Id
    private String id;
    private String description;

    private UnitOfMeasure(String description) {
        this.description = description;
    }

    public UnitOfMeasure() {
    }

    public static UnitOfMeasure of(String description) {
        return new UnitOfMeasure(description);
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
}

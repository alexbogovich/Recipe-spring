package com.bogovich.recipe.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
public class Ingredient {
    private String id = UUID.randomUUID().toString();
    private String description;
    private BigDecimal amount;

    @DBRef
    private UnitOfMeasure uom;

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
    }

    public static Boolean isEqualByValue(Ingredient i1, Ingredient i2) {
        return Objects.equals(i1.getId(), i2.getId()) &&
               Objects.equals(i1.getUom(), i2.getUom()) &&
               Objects.equals(i1.getAmount(), i2.getAmount()) &&
               Objects.equals(i1.getDescription(), i2.getDescription());

    }

    public Ingredient updateValue(Ingredient ingredient) {
        return this.updateValue(ingredient.getDescription(),
                                ingredient.getAmount(),
                                ingredient.getUom());
    }

    public Ingredient updateValue(String description, BigDecimal amount, UnitOfMeasure uom) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
        return this;
    }
}

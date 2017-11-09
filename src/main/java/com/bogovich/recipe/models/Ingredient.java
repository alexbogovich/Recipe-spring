package com.bogovich.recipe.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Data
@EqualsAndHashCode(exclude = "recipe")
@ToString(of = {"id", "description"})
@Entity
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private BigDecimal amount;

    @ManyToOne
    private Recipe recipe;

    @OneToOne
    private UnitOfMeasure uom;

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
    }

    public Ingredient() {
    }

    private Ingredient(Recipe recipe) {
        this.recipe = recipe;
    }

    public static Ingredient of(Recipe recipe) {
        final Ingredient ingredient = new Ingredient(recipe);
        ingredient.setUom(new UnitOfMeasure());
        return ingredient;
    }

    public static Boolean isEqual(Ingredient i1, Ingredient i2) {
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

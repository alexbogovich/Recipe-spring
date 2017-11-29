package com.bogovich.recipe.models;

import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

//@Setter
//@Getter
//@NoArgsConstructor
public class Ingredient {
    private String id = UUID.randomUUID().toString();
    @NotBlank
    @NotNull
    private String description;
    @NotNull
    @Min(1)
    private BigDecimal amount;

    @DBRef
    @NotNull
    private UnitOfMeasure uom;

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
    }

    public Ingredient() {
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public UnitOfMeasure getUom() {
        return uom;
    }

    public void setUom(UnitOfMeasure uom) {
        this.uom = uom;
    }
}

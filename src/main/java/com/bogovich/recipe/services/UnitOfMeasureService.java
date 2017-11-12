package com.bogovich.recipe.services;

import com.bogovich.recipe.models.UnitOfMeasure;
import reactor.core.publisher.Flux;

public interface UnitOfMeasureService {
    Flux<UnitOfMeasure> listAllUoms();
}

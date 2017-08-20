package com.bogovich.recipe.services;

import com.bogovich.recipe.models.UnitOfMeasure;
import com.bogovich.recipe.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UnitOfMeasureServiceImpl implements  UnitOfMeasureService{

    UnitOfMeasureRepository unitOfMeasureRepository;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public List<UnitOfMeasure> listAllUoms() {
        return (List<UnitOfMeasure>) unitOfMeasureRepository.findAll();
    }
}

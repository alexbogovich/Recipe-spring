package com.bogovich.recipe.repositories;

import com.bogovich.recipe.models.UnitOfMeasure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryTest {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Test
    public void findByDescriptionTablespoon() throws Exception {
        Optional<UnitOfMeasure> unitOfMeasureOptional =
                unitOfMeasureRepository.findByDescription("Tablespoon");
        assertEquals("Tablespoon", unitOfMeasureOptional.get().getDescription());
    }

    @Test(expected = Exception.class)
    public void findByDescriptionException() throws Exception {
        unitOfMeasureRepository.findByDescription("ThisWillFail...MayBe").orElseThrow(() -> new Exception());
    }
}
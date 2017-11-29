package com.bogovich.recipe.services;

import com.bogovich.recipe.repositories.reactive.RecipeReactiveRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

//@Slf4j
@Service
public class ImageServiceImpl implements ImageService {
        private final RecipeReactiveRepository recipeReactiveRepository;

        public ImageServiceImpl(RecipeReactiveRepository recipeService) {
            this.recipeReactiveRepository = recipeService;
        }

        @Override
        public Mono<Void> saveImageFile(String recipeId, MultipartFile file) {
            return recipeReactiveRepository.findById(recipeId)
                    .flatMap(recipe -> {
                        try {
                            Byte[] byteObjects = new Byte[file.getBytes().length];
                            int i = 0;
                            for (byte b : file.getBytes()) {
                                byteObjects[i++] = b;
                            }
                            recipe.setImage(byteObjects);
                            return Mono.just(recipe);
                        } catch (IOException e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                    })
                    .flatMap(recipeReactiveRepository::save)
                    .then();
        }
    }

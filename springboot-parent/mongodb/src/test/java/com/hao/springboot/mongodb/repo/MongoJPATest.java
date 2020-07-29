package com.hao.springboot.mongodb.repo;

import com.hao.springboot.mongodb.entity.Animal;
import com.hao.springboot.mongodb.entity.Cat;
import com.hao.springboot.mongodb.entity.Dog;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootTest
public class MongoJPATest {

    @Autowired
    MongoTemplate mongoTemplate;


    @Autowired
    AnimalRepository animalRepository;


    @BeforeEach
    public void beforeEach() {
        mongoTemplate.dropCollection(Animal.class);
        mongoTemplate.insertAll(Arrays.asList(
                Dog.builder()
                        .canBark(true)
                        .canSwim(true)
                        .breed("canine")
                        .age(3)
                .build(),
                Cat.builder()
                        .canClimb(true)
                        .breed("feline")
                        .age(5)
                        .build()
        ));
    }



    @Test
    public void testAutoInheritanceMethods() {
        System.err.println("animalRepository.countDogs(): " + animalRepository.countDogs());
        System.err.println("animalRepository.byBreed(canine): " + animalRepository.findAnimalsByBreed("canine"));

    }

}

package com.hao.springboot.mongodb.repo;

import com.hao.springboot.mongodb.entity.Animal;
import com.hao.springboot.mongodb.entity.Dog;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository extends MongoRepository<Animal, ObjectId>, AnimalOperations {

    // @Query(value = "{'breed': ?0}", fields = "{'breed': 1, 'canBark': 1}")
    List<Dog> findAnimalsByBreed(String breed);



}

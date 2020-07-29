package com.hao.springboot.mongodb.repo;

import com.hao.springboot.mongodb.entity.Animal;
import com.hao.springboot.mongodb.entity.Dog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

public class AnimalRepositoryImpl implements AnimalOperations {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public long countDogs() {
        Query query = new Query();
        query.restrict(Dog.class);
        return mongoTemplate.count(query, Animal.class);
    }
}

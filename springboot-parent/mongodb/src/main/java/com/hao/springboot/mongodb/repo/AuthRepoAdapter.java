package com.hao.springboot.mongodb.repo;


import com.hao.springboot.mongodb.entity.Auth;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.CollectionUtils;

public class AuthRepoAdapter<T extends Auth> {

    private Class<T> persistentClass = (Class<T>) ((ParameterizedType) getClass()
            .getGenericSuperclass()).getActualTypeArguments()[0];;

    @Autowired
    MongoTemplate mongoTemplate;

    final private static String collection =
            AnnotationUtils.findAnnotation(Auth .class, Document.class).collection();


    public Optional<T> find() {
        Query query = new Query();
        // query.restrict(getAuthClass());
        query.restrict(persistentClass);
        List<Auth> auths = mongoTemplate.find(query, Auth.class);
        if (CollectionUtils.isEmpty(auths)) {
            return Optional.empty();
        }
        return Optional.of(((T) auths.get(0)));
    }
}

package com.hao.springboot.mongodb.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "auths")
public class Auth {

    @Id
    ObjectId _id;

    String type;
}

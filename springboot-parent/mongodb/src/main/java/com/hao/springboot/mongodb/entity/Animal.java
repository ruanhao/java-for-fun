package com.hao.springboot.mongodb.entity;

import java.util.Date;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("Animal")
@Document(collection = "animals")
public class Animal {

    @Id
    ObjectId id;

    String breed;

    Integer age;



    @Version
    long version;

    @CreatedDate
    Date createdDate;

    @LastModifiedDate
    Date lastModifiedDate;

}

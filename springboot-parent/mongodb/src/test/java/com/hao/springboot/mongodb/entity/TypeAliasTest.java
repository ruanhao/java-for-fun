package com.hao.springboot.mongodb.entity;

import com.hao.springboot.mongodb.repo.AdAuthRepo;
import com.hao.springboot.mongodb.repo.LocalAuthRepo;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootTest
public class TypeAliasTest {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    LocalAuthRepo localAuthRepo;

    @Autowired
    AdAuthRepo adAuthRepo;

    private void _save(Auth auth) {
        mongoTemplate.save(auth);
    }


    @Test
    public void testInheritance() {
        mongoTemplate.dropCollection("auths");

        LocalAuth localAuth = LocalAuth.builder()
                .username("peter")
                .password("123")
                .build();
        AdAuth adAuth = AdAuth.builder()
                .clientId("client")
                .secret("secret")
                .build();

        _save(localAuth);
        _save(adAuth);

        Optional<LocalAuth> localAuthOpt = localAuthRepo.find();

        Optional<AdAuth> adAuthOpt = adAuthRepo.find();
        assert localAuthOpt.isPresent();
        assert adAuthOpt.isPresent();
        System.err.println("localAuthOpt.get(): " + localAuthOpt.get());
        System.err.println("adAuthOpt.get(): " + adAuthOpt.get());
    }

    /**
     * 通过 mongoTemplate.findAll(Auth.class, "auths"); 可以获取正确的子类实例。<br/>
     * 如果这么写 mongoTemplate.findAll(LocalAuth.class, "auths"); 则会获取到：<br/>
     * [LocalAuth(username=peter, password=123), LocalAuth(username=null, password=null)] <br/>
     * 原因在于 {@link org.springframework.data.convert.DefaultTypeMapper DefaultTypeMapper:160}
     *
     */
    @Test
    public void testTypeAlias() {
        mongoTemplate.dropCollection("auths");

        LocalAuth localAuth = LocalAuth.builder()
                .username("peter")
                .password("123")
                .build();
        AdAuth adAuth = AdAuth.builder()
                .clientId("client")
                .secret("secret")
                .build();

        _save(localAuth);
        _save(adAuth);

        List<Auth> auths = mongoTemplate.findAll(Auth.class, "auths");
        System.err.println("auths: " + auths);

        List<LocalAuth> localAuths = mongoTemplate.findAll(LocalAuth.class, "auths");
        System.err.println("localAuths: " + localAuths);


    }

}

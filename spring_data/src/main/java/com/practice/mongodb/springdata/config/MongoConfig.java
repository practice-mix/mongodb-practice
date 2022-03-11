package com.practice.mongodb.springdata.config;

import com.practice.mongodb.springdata.config.component.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class MongoConfig /*extends AbstractMongoClientConfiguration*/ {


    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }

//    @Override
//    protected Collection<String> getMappingBasePackages() {// need not.  spring boot have done it
//        return Arrays.asList("com.practice.mongodb.springdata.entity");
//    }
}

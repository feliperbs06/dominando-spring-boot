package com.felipesouza.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import test.outside.Connection;

@Configuration
public class BeanConfig {
    @Bean
//    @Primary
    public Connection connectionMySql() {
        return new Connection("localhost", "user", "xxxx");
    }

    @Bean(name = "mongoDB")
    public Connection connectionMongoDb() {
        return new Connection("localhost", "user", "xxxx");
    }

}

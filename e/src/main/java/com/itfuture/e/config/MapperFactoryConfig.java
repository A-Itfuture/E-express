package com.itfuture.config;

import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ma.glasnost.orika.MapperFactory;
/**
 * orika的mapperFactory
 * @author： wxh
 * @version：v1.0
 * @date： 2022/11/11 08:24
 */

@Configuration
public class MapperFactoryConfig {

    @Bean
    public MapperFactory getFactory(){
        return new DefaultMapperFactory.Builder().build();
    }
}

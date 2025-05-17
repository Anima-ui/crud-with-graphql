package com.anima.graphql.config;

import com.anima.graphql.scalar.LocalDateTimeScalar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphQLConfig {

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiring -> wiring.scalar(LocalDateTimeScalar.LOCAL_DATE_TIME);
    }
}

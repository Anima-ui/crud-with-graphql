package com.anima.graphql.scalar;

import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.GraphQLScalarType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Locale;

@Component
public class LocalDateTimeScalar {

    public static final GraphQLScalarType LOCAL_DATE_TIME = GraphQLScalarType.newScalar()
            .name("LocalDateTime")
            .description("Custom LocalDateTime scalar")
            .coercing(new Coercing<LocalDateTime, String>() {
            @Override
            public String serialize(Object dataFetcherResult, GraphQLContext graphQLContext, Locale locale){
                return ((LocalDateTime) dataFetcherResult).toString();
            } // отдаем клиенту ответ (Java -> JSON)

            @Override
            public LocalDateTime parseValue(Object input, GraphQLContext graphQLContext, Locale locale) {
                if (input instanceof String) {
                    return LocalDateTime.parse(input.toString());
                }
                throw new CoercingParseLiteralException("Expected a string but got " + input);
            } // получаем переменные (JSON -> Java)

            @Override
            public LocalDateTime parseLiteral(Value input, CoercedVariables variables,
                                              GraphQLContext graphQLContext, Locale locale) {
                if (input instanceof StringValue){
                    return LocalDateTime.parse(((StringValue) input).getValue());
                }
                throw new CoercingParseLiteralException("Expected a string but got " + input);
            } // GraphQL literal -> Java
    }).build();
}

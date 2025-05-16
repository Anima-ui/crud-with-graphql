package com.anima.graphql.exceptionHandling;

import graphql.GraphQLError;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler extends DataFetcherExceptionResolverAdapter {

    @ExceptionHandler(NoSuchElementException.class)
    public GraphQLError handleNoSuchElementException(NoSuchElementException e) {
        return GraphQLError
                .newError()
                .message(e.getMessage())
                .errorType(ErrorType.NOT_FOUND)
                .build();
    }
}

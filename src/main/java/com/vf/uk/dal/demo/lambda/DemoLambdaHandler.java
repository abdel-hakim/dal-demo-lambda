package com.vf.uk.dal.demo.lambda;

import com.vf.uk.dal.core.lambda.LambdaEventService;
import com.vf.uk.dal.core.lambda.LambdaInvocationHandler;
import com.vf.uk.dal.demo.lambda.service.helloworld.HelloWorldExampleService;

import java.util.Collection;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DemoLambdaHandler extends LambdaInvocationHandler {
    public static final String LAMBDA_NAME = "dal-demo-lambda";

    @Override
    protected String getServiceName() {
        return LAMBDA_NAME;
    }

    @Override
    protected Collection<LambdaEventService<?>> getServiceList() {
        return List.of(new HelloWorldExampleService());
    }
}
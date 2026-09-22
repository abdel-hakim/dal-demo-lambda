package com.vf.uk.dal.demo.lambda.service.apigw;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.model.MultiValuedTreeMap;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vf.uk.dal.core.lambda.LambdaEventCaughtException;
import com.vf.uk.dal.core.lambda.LambdaEventContext;
import com.vf.uk.dal.core.lambda.LambdaEventService;
import com.vf.uk.dal.demo.lambda.configuration.properties.PropertyKeys;
import com.vf.uk.dal.demo.springboot.SpringBootExampleApplication;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;

@Slf4j
public class APIGatewayExampleService extends LambdaEventService<AwsProxyRequest> {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;

    static {
        try {
            System.setProperty("server.servlet.context-path", "/" + PropertyKeys.CONTEXT_PATH.getValue());
            handler = SpringBootLambdaContainerHandler.getAwsProxyHandler(SpringBootExampleApplication.class);
        } catch (ContainerInitializationException e) {
            // if we fail here. We re-throw the exception to force another cold start
            e.printStackTrace();
            throw new RuntimeException("Could not initialize Spring Boot application", e);
        }
    }

    @Override
    public String getEventName() {
        return "APIGatewayExampleService";
    }

    @Override
    protected Class<AwsProxyRequest> getClazz() {
        return AwsProxyRequest.class;
    }

    @Override
    protected void performFunction(final AwsProxyRequest event, final OutputStream outputStream, final LambdaEventContext tracer)
        event.setPath(event.getPath().replace("/" + PropertyKeys.CONTEXT_PATH.getValue(), ""));
        if((event.getMultiValueHeaders() == null || event.getMultiValueHeaders().isEmpty()) && !event.getQueryStringParameters().isEmpty()) {
            final MultiValuedTreeMap<String, String> map = new MultiValuedTreeMap<>();
            event.getQueryStringParameters().forEach(map::putSingle);
            event.setMultiValueQueryStringParameters(map);
        }
        AwsProxyResponse resp = handler.proxy(event, tracer.getContext());
        MAPPER.writeValue(outputStream, resp);
    }

    @Override
    protected boolean logEventJson() {
        return true;
    }
}
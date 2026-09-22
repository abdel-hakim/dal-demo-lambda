package com.vf.uk.dal.demo.lambda.service.helloworld;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vf.uk.dal.core.lambda.LambdaEventCaughtException;
import com.vf.uk.dal.core.lambda.LambdaEventContext;
import com.vf.uk.dal.core.lambda.LambdaEventService;
import io.opentelemetry.api.internal.StringUtils;
import io.opentelemetry.api.trace.Span;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.OutputStream;

import static com.vf.uk.dal.demo.lambda.configuration.properties.PropertyKeys.ENV_NAME;
import static com.vf.uk.dal.demo.lambda.configuration.properties.PropertyKeys.VERSION;

@Slf4j
public class HelloWorldExampleService extends LambdaEventService<HelloWorldExampleService.HelloWorldModel> {
    @Override
    protected String getEventName() {
        return "HelloWorldExample";
    }

    @Override
    protected Class<HelloWorldModel> getClazz() {
        return HelloWorldModel.class;
    }

    @Override
    protected void performFunction(final HelloWorldModel eventModel, final OutputStream outputStream, final LambdaEventContext tracer)
            throws LambdaEventCaughtException {
        final Span span = tracer.startSpan("internal");
        tracer.getMetrics().incrementCounter("internal");

        try {
            log.info("Hello - {} / {}", ENV_NAME.getValue(), VERSION.getValue());
            log.info("Data key1={}, key2={}, key3={}", eventModel.key1, eventModel.key2, eventModel.key3);
            if (StringUtils.isNullOrEmpty(eventModel.key1)) {
                throw new IllegalStateException("Key1 is null");
            }
            if (StringUtils.isNullOrEmpty(eventModel.key2)) {
                throw new LambdaEventCaughtException("Key2 is null");
            }
            if (StringUtils.isNullOrEmpty(eventModel.key3)) {
                throw new RuntimeException("Key3 is null");
            }
        }
        finally {
            span.end();
        }
    }

    @Setter
    @NoArgsConstructor
    public static class HelloWorldModel {
        @JsonProperty(required = true)
        private String key1;
        @JsonProperty(required = true)
        private String key2;
        @JsonProperty(required = true)
        private String key3;
    }
}
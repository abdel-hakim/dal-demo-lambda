package com.vf.uk.dal.demo.lambda.service.helloworld;

import com.vf.uk.dal.core.lambda.LambdaContext;
import com.vf.uk.dal.core.lambda.logging.CaptureSystemOutputTester;
import com.vf.uk.dal.core.lambda.logging.ContextLoggingTester;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static com.vf.uk.dal.demo.lambda.DemoLambdaHandler.LAMBDA_NAME;
import static org.assertj.core.api.Assertions.assertThat;


class HelloWorldExampleServiceTest extends CaptureSystemOutputTester {

    private static final HelloWorldExampleService SERVICE = new HelloWorldExampleService();

    private static final String VALID_EVENT =
            "{\"key1\":\"value1\",\"key2\":\"value2\",\"key3\":\"value3\"}";
    private static final String INVALID_EVENT =
            "{\"key4\":\"value4\"}";

    @Test
    void shouldHandleEvent() throws Exception {
        boolean processed = SERVICE.handleRequest(VALID_EVENT, new ByteArrayOutputStream(),
                new LambdaContext(new ContextLoggingTester(), LAMBDA_NAME));
        assertThat(processed).isTrue();

        assertLogs(">>>> HelloWorldExample :: LAMBDA STARTING <<<<");
        assertLogs("Hello -");
        assertLogs("Data key1=value1, key2=value2, key3=value3");
        assertLogs(">>>> HelloWorldExample :: LAMBDA COMPLETED <<<<");
    }

    @Test
    void shouldIgnoreEvent() throws Exception {
        boolean processed = SERVICE.handleRequest(INVALID_EVENT, new ByteArrayOutputStream(),
                new LambdaContext(new ContextLoggingTester(), LAMBDA_NAME));
        assertThat(processed).isFalse();
        assertLogSize(0);
    }
}

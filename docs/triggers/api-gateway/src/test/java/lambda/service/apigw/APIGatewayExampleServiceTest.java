package com.vf.uk.dal.demo.lambda.service.apigw;

import com.vf.uk.dal.core.lambda.LambdaContext;
import com.vf.uk.dal.core.lambda.logging.CaptureSystemOutputTester;
import com.vf.uk.dal.core.lambda.logging.ContextLoggingTester;
import com.vf.uk.dal.core.lambda.logging.LoggingOutputStream;
import com.vf.uk.dal.demo.lambda.configuration.properties.PropertyKeys;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;

import java.io.ByteArrayOutputStream;

import static com.vf.uk.dal.demo.lambda.DemoLambdaHandler.LAMBDA_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;


class APIGatewayExampleServiceTest extends CaptureSystemOutputTester {

    private static final APIGatewayExampleService SERVICE = new APIGatewayExampleService();

    private static final String INVALID_EVENT =
            "{\"key4\":\"value4\"}";

    @Test
    void shouldHandleEvent_200() {
        System.setProperty(PropertyKeys.ENV_NAME.getEnvironmentVariableKey(), "testEnv");
        System.setProperty(PropertyKeys.VERSION.getEnvironmentVariableKey(), "testVer");
        System.setProperty(PropertyKeys.CONTEXT_PATH.getEnvironmentVariableKey(), "demo");

        boolean processed = SERVICE.handleRequest(getEvent(GET, "/demo/api/get"),
                new ByteArrayOutputStream(),
                new LambdaContext(new ContextLoggingTester(), LAMBDA_NAME));

        assertThat(processed).isTrue();

        assertLogs(">>>> APIGatewayExampleService :: LAMBDA STARTING <<<<");
        assertLogs("\"GET /api/get null\" 200");
        assertLogs(">>>> APIGatewayExampleService :: LAMBDA COMPLETED <<<<");
    }

    @Test
    void shouldHandleEvent_404() {
        System.setProperty(PropertyKeys.ENV_NAME.getEnvironmentVariableKey(), "testEnv");
        System.setProperty(PropertyKeys.VERSION.getEnvironmentVariableKey(), "testVer");
        System.setProperty(PropertyKeys.CONTEXT_PATH.getEnvironmentVariableKey(), "demo");

        boolean processed = SERVICE.handleRequest(getEvent(GET, "/demo/invalid"),
                new ByteArrayOutputStream(),
                new LambdaContext(new ContextLoggingTester(), LAMBDA_NAME));
        assertThat(processed).isTrue();

        assertLogs(">>>> APIGatewayExampleService :: LAMBDA STARTING <<<<");
        assertLogs("\"POST /invalid null\" 404");
        assertLogs(">>>> APIGatewayExampleService :: LAMBDA COMPLETED <<<<");
    }

    private static String getEvent(final HttpMethod method, final String path) {
        return "{\n" +
                        "  \"requestContext\": {\n" +
                        "    \"elb\": {\n" +
                        "      \"targetGroupArn\": \"arn:aws:elasticloadbalancing:eu-west-1:855389350164:targetgroup/dal-re-ElbTa-GOUKBNUMET9X/d0f8e808090f03ff\"\n" +
                        "    }\n" +
                        "  },\n" +
                        "  \"httpMethod\": \""+method.name()+"\",\n" +
                        "  \"path\": \""+path+"\",\n" +
                        "  \"queryStringParameters\": {\n" +
                        "    \"param\": \"ValueOfParameter\"\n" +
                        "  },\n" +
                        "  \"headers\": {}" +
                        "}";
    }

    @Test
    void shouldIgnoreEvent() {
        boolean processed = SERVICE.handleRequest(INVALID_EVENT,
                new ByteArrayOutputStream(),
                new LambdaContext(new ContextLoggingTester(), LAMBDA_NAME));
        assertThat(processed).isFalse();
        assertLogSize(0);
    }
}

package com.vf.uk.dal.demo.lambda.service.cron;

import com.vf.uk.dal.core.lambda.LambdaContext;
import com.vf.uk.dal.core.lambda.logging.CaptureSystemOutputTester;
import com.vf.uk.dal.core.lambda.logging.ContextLoggingTester;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static com.vf.uk.dal.demo.lambda.DemoLambdaHandler.LAMBDA_NAME;
import static org.assertj.core.api.Assertions.assertThat;

class CronJobExampleServiceTest extends CaptureSystemOutputTester {

    private static final CronJobExampleService SERVICE = new CronJobExampleService();

    private static final String VALID_EVENT =
            "{\n" +
                    "  \"version\": \"0\",\n" +
                    "  \"id\": \"402a56e9-b7c2-4f03-01fe-a9b0fab4123f\",\n" +
                    "  \"detail-type\": \"Scheduled Event\",\n" +
                    "  \"source\": \"aws.events\",\n" +
                    "  \"account\": \"855389350164\",\n" +
                    "  \"time\": \"2023-12-06T09:50:00Z\",\n" +
                    "  \"region\": \"eu-west-1\",\n" +
                    "  \"resources\": [\n" +
                    "    \"arn:aws:events:eu-west-1:123456789:rule/dal-demo-lambda-dev1-CronScheduleRule-ABCDEFGHI\"\n" +
                    "  ],\n" +
                    "  \"detail\": {}\n" +
                    "}";
    public static final String INVALID_EVENT =
            "{\"key4\":\"value4\"}";

    @Test
    void shouldHandleEvent() {
        boolean processed = SERVICE.handleRequest(VALID_EVENT, new ByteArrayOutputStream(),
                new LambdaContext(new ContextLoggingTester(), LAMBDA_NAME));
        assertThat(processed).isTrue();

        assertLogSize(3);
        assertLogs(">>>> CronJobExampleService :: LAMBDA STARTING <<<<");
        assertLogs("CronJob Running - Scheduled Event");
        assertLogs(">>>> CronJobExampleService :: LAMBDA COMPLETED <<<<");
    }

    @Test
    void shouldIgnoreEvent() {
        boolean processed = SERVICE.handleRequest(INVALID_EVENT, new ByteArrayOutputStream(),
                new LambdaContext(new ContextLoggingTester(), LAMBDA_NAME));
        assertThat(processed).isFalse();

        assertLogSize(0);
    }
}

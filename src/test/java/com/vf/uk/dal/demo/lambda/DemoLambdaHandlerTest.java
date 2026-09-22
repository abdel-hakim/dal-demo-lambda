package com.vf.uk.dal.demo.lambda;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DemoLambdaHandlerTest {
    @Test
    void shouldReturnAService() {
        DemoLambdaHandler handler = new DemoLambdaHandler();
        assertThat(handler.getServiceList()).isNotEmpty();
    }
}
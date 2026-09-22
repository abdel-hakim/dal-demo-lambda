package com.vf.uk.dal.demo.lambda.service.cron;

import com.amazonaws.services.lambda.runtime.events.ScheduledEvent;
import lombok.extern.slf4j.Slf4j;
import com.vf.uk.dal.core.lambda.LambdaEventCaughtException;
import com.vf.uk.dal.core.lambda.LambdaEventContext;
import com.vf.uk.dal.core.lambda.LambdaEventService;

import java.io.OutputStream;

import static com.vf.uk.dal.demo.lambda.configuration.properties.PropertyKeys.ENV_NAME;
import static com.vf.uk.dal.demo.lambda.configuration.properties.PropertyKeys.VERSION;

@Slf4j
public class CronJobExampleService extends LambdaEventService<ScheduledEvent> {
    @Override
    public String getEventName() {
        return "CronJobExampleService";
    }

    @Override
    protected Class<ScheduledEvent> getClazz() {
        return ScheduledEvent.class;
    }

    @Override
    protected void performFunction(final ScheduledEvent event, final OutputStream outputStream, final LambdaEventContext tracer)
        log.info("CronJob Running - {} / {} / {}", event.getDetailType(), ENV_NAME.getValue(), VERSION.getValue());
    }
}
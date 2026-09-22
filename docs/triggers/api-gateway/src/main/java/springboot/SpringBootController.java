package com.vf.uk.dal.demo.springboot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.vf.uk.dal.demo.lambda.configuration.properties.PropertyKeys.ENV_NAME;
import static com.vf.uk.dal.demo.lambda.configuration.properties.PropertyKeys.VERSION;

@Slf4j
@RestController
@RequestMapping(value = {"api"})
@RequiredArgsConstructor
public class SpringBootController {
    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public SpringBootResponse getProfileNames(@RequestParam(name = "param") String param) {
        return new SpringBootResponse(ENV_NAME.getValue(), VERSION.getValue(), param);
    }

    @AllArgsConstructor
    public static class SpringBootResponse {
        @Getter
        String environment;
        @Getter
        String version;
        @Getter
        String parameter;
    }
}
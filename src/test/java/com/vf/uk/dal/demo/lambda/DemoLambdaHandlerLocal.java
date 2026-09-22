package com.vf.uk.dal.demo.lambda;

import com.vf.uk.dal.core.lambda.LambdaHandlerLocalRunner;

import static com.vf.uk.dal.demo.lambda.configuration.properties.PropertyKeys.ENV_NAME;

public class DemoLambdaHandlerLocal {
  private static final DemoLambdaHandler LAMBDA = new DemoLambdaHandler();

  public static void main (final String[] args) throws Exception {
    System.setProperty(ENV_NAME.envKey(), "LOCAL");
    LambdaHandlerLocalRunner.run(LAMBDA, args);
  }
}

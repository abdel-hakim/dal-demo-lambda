package com.vf.uk.dal.demo.lambda.configuration.properties;

import com.vf.uk.dal.core.lambda.properties.model.PropertyKeyString;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PropertyKeys {
  public static final PropertyKeyString ENV_NAME = new PropertyKeyString("ENV_NAME", null, "");
  public static final PropertyKeyString VERSION = new PropertyKeyString("VERSION", null, "");
}
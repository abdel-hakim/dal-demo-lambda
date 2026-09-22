package com.vf.uk.dal.demo.springboot;

import com.vf.uk.dal.demo.lambda.configuration.properties.PropertyKeys;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class SpringBootControllerTest {

  private static final String ENDPOINT_URL = "/api/get";

  @Autowired
  private MockMvc mockMvc;

  @Test
  void shouldCallAPI() throws Exception {
    System.setProperty(PropertyKeys.ENV_NAME.getEnvironmentVariableKey(), "testEnv");
    System.setProperty(PropertyKeys.VERSION.getEnvironmentVariableKey(), "testVer");
    System.setProperty(PropertyKeys.CONTEXT_PATH.getEnvironmentVariableKey(), "demo");

    mockMvc.perform(get(ENDPOINT_URL)
        .param("param", "testValue")
        .contentType(APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.environment").value("testEnv"))
      .andExpect(jsonPath("$.version").value("testVer"))
      .andExpect(jsonPath("$.parameter").value("testValue"));
  }
}
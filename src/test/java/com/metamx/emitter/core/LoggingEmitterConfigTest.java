/*
 * Copyright 2012 - 2015 Metamarkets Group Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.metamx.emitter.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Properties;

public class LoggingEmitterConfigTest
{
  @Test
  public void testDefaults()
  {
    final Properties props = new Properties();
    final ObjectMapper objectMapper = new ObjectMapper();
    final LoggingEmitterConfig config = objectMapper.convertValue(
        Emitters.makeLoggingMap(props),
        LoggingEmitterConfig.class
    );

    Assert.assertEquals(LoggingEmitter.class.getName(), config.getLoggerClass());
    Assert.assertEquals("debug", config.getLogLevel());
  }

  @Test
  public void testSettingEverything()
  {
    final Properties props = new Properties();
    props.setProperty("com.metamx.emitter.logging.class", "Foo");
    props.setProperty("com.metamx.emitter.logging.level", "INFO");

    final ObjectMapper objectMapper = new ObjectMapper();
    final LoggingEmitterConfig config = objectMapper.convertValue(
        Emitters.makeLoggingMap(props),
        LoggingEmitterConfig.class
    );

    Assert.assertEquals("Foo", config.getLoggerClass());
    Assert.assertEquals("INFO", config.getLogLevel());
  }
}

/*
 * Copyright 2012 Metamarkets Group Inc.
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

package com.metamx.emitter.service;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.joda.time.DateTime;

import java.util.Map;

/**
*/
public class AlertBuilder
{
  protected final Map<String, Object> dataMap = Maps.newLinkedHashMap();
  protected final String description;
  protected final ServiceEmitter emitter;

  protected AlertEvent.Severity severity = AlertEvent.Severity.DEFAULT;

  public static AlertBuilder create(String descriptionFormat, Object... objects)
  {
    return AlertBuilder.createEmittable(null, descriptionFormat, objects);
  }

  public static AlertBuilder createEmittable(ServiceEmitter emitter, String descriptionFormat, Object... objects)
  {
    return new AlertBuilder(String.format(descriptionFormat, objects), emitter);
  }

  protected AlertBuilder(
      String description,
      ServiceEmitter emitter
  )
  {
    this.description = description;
    this.emitter = emitter;
  }

  public AlertBuilder addData(String identifier, Object value)
  {
    dataMap.put(identifier, value);
    return this;
  }

  public AlertBuilder severity(AlertEvent.Severity severity)
  {
    this.severity = severity;
    return this;
  }

  public ServiceEventBuilder<AlertEvent> build()
  {
    return new ServiceEventBuilder<AlertEvent>()
    {
      @Override
      public AlertEvent build(ImmutableMap<String, String> serviceDimensions)
      {
        return new AlertEvent(new DateTime(), serviceDimensions, severity, description, dataMap);
      }
    };
  }

  public void emit()
  {
    if (emitter == null) {
      throw new UnsupportedOperationException("Emitter is null, cannot emit.");
    }

    emitter.emit(build());
  }
}

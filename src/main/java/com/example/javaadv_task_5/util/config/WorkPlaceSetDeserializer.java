package com.example.javaadv_task_5.util.config;

import com.example.javaadv_task_5.dto.WorkPlaceDto;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class WorkPlaceSetDeserializer extends StdDeserializer<Set<WorkPlaceDto>> {

  public WorkPlaceSetDeserializer() {
    this(null);
  }

  public WorkPlaceSetDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public Set<WorkPlaceDto> deserialize(JsonParser jsonParser,
      DeserializationContext deserializationContext) throws IOException, JacksonException {
    Set<WorkPlaceDto> list = new HashSet<>();

    return list;
  }
}

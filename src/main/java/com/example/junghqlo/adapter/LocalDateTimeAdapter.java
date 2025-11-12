package com.example.junghqlo.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Configuration;

@Component
@Configuration
public class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
  private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

  @Override
  public void write(JsonWriter out, LocalDateTime value) throws IOException {
    if (value == null) {
      out.nullValue();
    }
    else {
      out.value(value.format(formatter));
    }
  }

  @Override
  public LocalDateTime read(JsonReader in) throws IOException {
    String dateTimeString = in.nextString();
    return dateTimeString != null ? LocalDateTime.parse(dateTimeString, formatter) : null;
  }
}

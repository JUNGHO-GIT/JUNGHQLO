package com.example.junghqlo.adapter;

import java.io.File;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Configuration;

@Component
@Configuration
public class FileAdapter extends TypeAdapter<File> {

  @Override
  public void write(JsonWriter out, File value) throws IOException {
    if (value == null) {
      out.nullValue();
      return;
    }
    out.value(value.getAbsolutePath());
  }

  @Override
  public File read(JsonReader in) throws IOException {
    return new File(in.nextString());
  }
}
package org.mcvoid.json.processors;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.mcvoid.json.JsonValue;
import org.mcvoid.json.Processor;
import org.mcvoid.json.ast.*;
import org.mcvoid.json.parser.JsonLexer;
import org.mcvoid.json.parser.JsonParseTreeVisitor;
import org.mcvoid.json.parser.JsonParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DataProcessor implements Processor {

  @Override
  public void process(JsonObject json) {
    if (json.value == null) {
      return;
    }
    Map<String, Object> m = new HashMap<>();
    json.value.entrySet().forEach(e -> {
      DataProcessor p = new DataProcessor();
      e.getValue().accept(p);
      m.put(e.getKey(), p.data);
    });
    data = m;
  }

  @Override
  public void process(JsonArray json) {
    if (json.value == null) {
      return;
    }
    Object[] arr = new Object[json.value.length];
    for (int i = 0; i < arr.length; i++) {
      DataProcessor p = new DataProcessor();
      json.value[i].accept(p);
      arr[i] = p.data;
    }
    data = arr;
  }

  @Override
  public void process(JsonNumber json) {
    data = json.value;
  }

  @Override
  public void process(JsonString json) {
    data = json.value;
  }

  @Override
  public void process(JsonBoolean json) {
    data = json.value;
  }

  public Object data = null;

  public static void main(String[] args) {
    JsonLexer lexer = null;
    try {
      lexer = new JsonLexer(new ANTLRInputStream(System.in));
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
    JsonParser parser = new JsonParser(new CommonTokenStream(lexer));
    JsonValue val = new JsonParseTreeVisitor().visitJson(parser.json());
    DataProcessor processor = new DataProcessor();
    val.accept(processor);
    Object o = processor.data;
    System.out.println(o);
  }
}

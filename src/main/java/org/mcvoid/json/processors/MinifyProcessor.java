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
import java.io.PrintStream;
import java.util.Map;

public class MinifyProcessor implements Processor {

  @Override
  public void process(JsonObject json) {
    if (json.value == null) {
      out.print("null");
      return;
    }
    out.print("{");
    int count = 0;
    for (Map.Entry<String, JsonValue> e : json.value.entrySet()) {
      out.print(String.format("\"%s\":", e.getKey()));
      e.getValue().accept(this);
      out.print((++count < json.value.entrySet().size()) ? "," : "");
    }
    out.print("}");
  }

  @Override
  public void process(JsonArray json) {
    out.print("[");
    for (int i = 0; i < json.value.length; i++) {
      json.value[i].accept(this);
      out.print((i < json.value.length - 1) ? "," : "");
    }
    out.print("]");
  }

  @Override
  public void process(JsonNumber json) {
    out.print(json.value);
  }

  @Override
  public void process(JsonString json) {
    out.print("\"" + json.value + "\"");
  }

  @Override
  public void process(JsonBoolean json) {
    out.print(json.value ? "true" : "false");
  }

  public MinifyProcessor(PrintStream out) {
    this.out = out;
  }

  private PrintStream out;

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
    MinifyProcessor printer = new MinifyProcessor(System.out);
    val.accept(printer);
  }
}

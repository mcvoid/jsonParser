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
import java.io.SyncFailedException;
import java.util.Map;

public class PrettyPrintProcessor implements Processor {

  @Override
  public void process(JsonObject json) {
    if (json.value == null) {
      out.print("null");
      return;
    }
    if (json.value.size() == 0) {
      out.print("{}");
      return;
    }
    out.print("{");
    int count = 0;
    for (Map.Entry<String, JsonValue> e : json.value.entrySet()) {
      out.println();
      for (int i = 0; i < indentLevel; i++) {
        out.print(indent);
      }
      out.print(String.format("\"%s\": ", e.getKey()));
      e.getValue().accept(new PrettyPrintProcessor(out, indent, indentLevel + 1));
      out.print((++count < json.value.entrySet().size()) ? "," : "\n");
    }
    for (int i = 0; i < indentLevel - 1; i++) {
      out.print(indent);
    }
    out.print("}");
  }

  @Override
  public void process(JsonArray json) {
    if (json.value.length == 0) {
      out.print("[]");
      return;
    }
    out.print("[");
    for (int i = 0; i < json.value.length; i++) {
      out.println();
      for (int j = 0; j < indentLevel; j++) {
        out.print(indent);
      }
      json.value[i].accept(new PrettyPrintProcessor(out, indent, indentLevel + 1));
      out.print((i < json.value.length - 1) ? "," : "\n");
    }
    for (int j = 0; j < indentLevel - 1; j++) {
      out.print(indent);
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

  public PrettyPrintProcessor(PrintStream out) {
    this(out, "    ");
  }

  public PrettyPrintProcessor(PrintStream out, String indent) {
    this(out, indent, 1);
  }

  private PrettyPrintProcessor(PrintStream out, String indent, int indentLevel) {
    this.out = out;
    this.indent = indent;
    this.indentLevel = indentLevel;
  }

  private PrintStream out;
  private String indent;
  private int indentLevel;

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
    PrettyPrintProcessor printer = new PrettyPrintProcessor(System.out);
    val.accept(printer);
    System.out.println();
  }
}

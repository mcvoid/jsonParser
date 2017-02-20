package org.mcvoid.json;

import org.junit.Test;
import org.mcvoid.json.ast.*;
import org.mcvoid.json.processors.PrettyPrintProcessor;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;


import static org.junit.Assert.*;

public class PrettyPrintProcessorTest {
  @Test
  public void printNull() {
    ByteArrayOutputStream s = new ByteArrayOutputStream();
    Processor p = new PrettyPrintProcessor(new PrintStream(s));
    new JsonObject(null).accept(p);
    String expected = "null";
    String actual = new String(s.toByteArray(), Charset.defaultCharset());
    assertEquals(expected, actual);
  }

  @Test
  public void printNumber() {
    ByteArrayOutputStream s = new ByteArrayOutputStream();
    Processor p = new PrettyPrintProcessor(new PrintStream(s));
    new JsonNumber(3.5).accept(p);
    String expected = "3.5";
    String actual = new String(s.toByteArray(), Charset.defaultCharset());
    assertEquals(expected, actual);
  }

  @Test
  public void printBoolean() {
    ByteArrayOutputStream s = new ByteArrayOutputStream();
    Processor p = new PrettyPrintProcessor(new PrintStream(s));
    new JsonBoolean(true).accept(p);
    String expected = "true";
    String actual = new String(s.toByteArray(), Charset.defaultCharset());
    assertEquals(expected, actual);

    s = new ByteArrayOutputStream();
    p = new PrettyPrintProcessor(new PrintStream(s));
    new JsonBoolean(false).accept(p);
    expected = "false";
    actual = new String(s.toByteArray(), Charset.defaultCharset());
    assertEquals(expected, actual);
  }

  @Test
  public void printString() {
    ByteArrayOutputStream s = new ByteArrayOutputStream();
    Processor p = new PrettyPrintProcessor(new PrintStream(s));
    new JsonString("abc").accept(p);
    String expected = "\"abc\"";
    String actual = new String(s.toByteArray(), Charset.defaultCharset());
    assertEquals(expected, actual);
  }

  @Test
  public void printArray() {
    ByteArrayOutputStream s = new ByteArrayOutputStream();
    Processor p = new PrettyPrintProcessor(new PrintStream(s));
    new JsonArray(new JsonValue[]{}).accept(p);
    String expected = "[]";
    String actual = new String(s.toByteArray(), Charset.defaultCharset());
    assertEquals(expected, actual);

    s = new ByteArrayOutputStream();
    p = new PrettyPrintProcessor(new PrintStream(s));
    new JsonArray(new JsonValue[]{
      new JsonBoolean(false)
    }).accept(p);
    expected = "[\n    false\n]";
    actual = new String(s.toByteArray(), Charset.defaultCharset());
    assertEquals(expected, actual);

    s = new ByteArrayOutputStream();
    p = new PrettyPrintProcessor(new PrintStream(s));
    new JsonArray(new JsonValue[]{
      new JsonBoolean(false),
      new JsonBoolean(true)
    }).accept(p);
    expected = "[\n    false,\n    true\n]";
    actual = new String(s.toByteArray(), Charset.defaultCharset());
    assertEquals(expected, actual);

    s = new ByteArrayOutputStream();
    p = new PrettyPrintProcessor(new PrintStream(s));
    new JsonArray(new JsonValue[]{
      new JsonBoolean(false),
      new JsonBoolean(true),
      new JsonObject(null),
    }).accept(p);
    expected = "[\n    false,\n    true,\n    null\n]";
    actual = new String(s.toByteArray(), Charset.defaultCharset());
    assertEquals(expected, actual);

    s = new ByteArrayOutputStream();
    p = new PrettyPrintProcessor(new PrintStream(s), "  ");
    new JsonArray(new JsonValue[]{
      new JsonBoolean(false)
    }).accept(p);
    expected = "[\n  false\n]";
    actual = new String(s.toByteArray(), Charset.defaultCharset());
    assertEquals(expected, actual);

    s = new ByteArrayOutputStream();
    p = new PrettyPrintProcessor(new PrintStream(s), "  ");
    new JsonArray(new JsonValue[]{
      new JsonBoolean(false),
      new JsonBoolean(true)
    }).accept(p);
    expected = "[\n  false,\n  true\n]";
    actual = new String(s.toByteArray(), Charset.defaultCharset());
    assertEquals(expected, actual);

    s = new ByteArrayOutputStream();
    p = new PrettyPrintProcessor(new PrintStream(s), "  ");
    new JsonArray(new JsonValue[]{
      new JsonBoolean(false),
      new JsonBoolean(true),
      new JsonObject(null),
    }).accept(p);
    expected = "[\n  false,\n  true,\n  null\n]";
    actual = new String(s.toByteArray(), Charset.defaultCharset());
    assertEquals(expected, actual);

    s = new ByteArrayOutputStream();
    p = new PrettyPrintProcessor(new PrintStream(s), "  ");
    new JsonArray(new JsonValue[]{
      new JsonArray(new JsonValue[]{})
    }).accept(p);
    expected = "[\n  []\n]";
    actual = new String(s.toByteArray(), Charset.defaultCharset());
    assertEquals(expected, actual);

    s = new ByteArrayOutputStream();
    p = new PrettyPrintProcessor(new PrintStream(s), "  ");
    new JsonArray(new JsonValue[]{
      new JsonArray(new JsonValue[]{}),
      new JsonArray(new JsonValue[]{})
    }).accept(p);
    expected = "[\n  [],\n  []\n]";
    actual = new String(s.toByteArray(), Charset.defaultCharset());
    assertEquals(expected, actual);

    s = new ByteArrayOutputStream();
    p = new PrettyPrintProcessor(new PrintStream(s), "  ");
    new JsonArray(new JsonValue[]{
      new JsonArray(new JsonValue[]{}),
      new JsonArray(new JsonValue[]{}),
      new JsonArray(new JsonValue[]{})
    }).accept(p);
    expected = "[\n  [],\n  [],\n  []\n]";
    actual = new String(s.toByteArray(), Charset.defaultCharset());
    assertEquals(expected, actual);

    s = new ByteArrayOutputStream();
    p = new PrettyPrintProcessor(new PrintStream(s), "  ");
    new JsonArray(new JsonValue[]{
      new JsonArray(new JsonValue[]{
        new JsonBoolean(false),
        new JsonBoolean(true),
        new JsonObject(null)
      }),
      new JsonArray(new JsonValue[]{}),
      new JsonArray(new JsonValue[]{
        new JsonArray(new JsonValue[]{
          new JsonArray(new JsonValue[]{
            new JsonString("abc"),
            new JsonString("def")
          })
        })
      })
    }).accept(p);
    expected = "[\n" +
      "  [\n" +
      "    false,\n" +
      "    true,\n" +
      "    null\n" +
      "  ],\n" +
      "  [],\n" +
      "  [\n" +
      "    [\n" +
      "      [\n" +
      "        \"abc\",\n" +
      "        \"def\"\n" +
      "      ]\n" +
      "    ]\n" +
      "  ]\n" +
      "]";
    actual = new String(s.toByteArray(), Charset.defaultCharset());
    assertEquals(expected, actual);
  }

  @Test
  public void printObject() {
    ByteArrayOutputStream s = new ByteArrayOutputStream();
    Processor p = new PrettyPrintProcessor(new PrintStream(s));
    Map<String, JsonValue> m = new HashMap<>();
    new JsonObject(m).accept(p);
    String expected = "{}";
    String actual = new String(s.toByteArray(), Charset.defaultCharset());
    assertEquals(expected, actual);

    s = new ByteArrayOutputStream();
    p = new PrettyPrintProcessor(new PrintStream(s));
    m = new HashMap<>();
    m.put("bool", new JsonBoolean(false));
    new JsonObject(m).accept(p);
    expected = "{\n    \"bool\": false\n}";
    actual = new String(s.toByteArray(), Charset.defaultCharset());
    assertEquals(expected, actual);

    s = new ByteArrayOutputStream();
    p = new PrettyPrintProcessor(new PrintStream(s));
    m = new HashMap<>();
    m.put("bool", new JsonBoolean(false));
    new JsonObject(m).accept(p);
    expected = "{\n    \"bool\": false\n}";
    actual = new String(s.toByteArray(), Charset.defaultCharset());
    assertEquals(expected, actual);

    s = new ByteArrayOutputStream();
    p = new PrettyPrintProcessor(new PrintStream(s), " ");
    m = new HashMap<>();
    m.put("bool", new JsonBoolean(false));
    m.put("arr", new JsonArray(new JsonValue[]{
      new JsonString("abc"),
      new JsonString("abc"),
      new JsonString("abc")
    }));
    Map<String, JsonValue> subObj = new HashMap<>();
    subObj.put("num", new JsonNumber(1));
    subObj.put("null", new JsonObject(null));
    m.put("obj", new JsonObject(subObj));
    new JsonObject(m).accept(p);
    expected = "{\n" +
      " \"arr\": [\n" +
      "  \"abc\",\n" +
      "  \"abc\",\n" +
      "  \"abc\"\n" +
      " ],\n" +
      " \"bool\": false,\n" +
      " \"obj\": {\n" +
      "  \"null\": null,\n" +
      "  \"num\": 1.0\n" +
      " }\n" +
      "}";
    actual = new String(s.toByteArray(), Charset.defaultCharset());
    assertEquals(expected, actual);
  }
}

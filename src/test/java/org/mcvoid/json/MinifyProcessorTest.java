package org.mcvoid.json;

import org.junit.Test;
import org.mcvoid.json.ast.*;
import org.mcvoid.json.processors.MinifyProcessor;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class MinifyProcessorTest {
  @Test
  public void printNull() {
    ByteArrayOutputStream s = new ByteArrayOutputStream();
    Processor p = new MinifyProcessor(new PrintStream(s));
    new JsonObject(null).accept(p);
    String expected = "null";
    String actual = new String(s.toByteArray(), Charset.defaultCharset());
    assertEquals(expected, actual);
  }

  @Test
  public void printNumber() {
    ByteArrayOutputStream s = new ByteArrayOutputStream();
    Processor p = new MinifyProcessor(new PrintStream(s));
    new JsonNumber(3.5).accept(p);
    String expected = "3.5";
    String actual = new String(s.toByteArray(), Charset.defaultCharset());
    assertEquals(expected, actual);
  }

  @Test
  public void printBoolean() {
    ByteArrayOutputStream s = new ByteArrayOutputStream();
    Processor p = new MinifyProcessor(new PrintStream(s));
    new JsonBoolean(true).accept(p);
    String expected = "true";
    String actual = new String(s.toByteArray(), Charset.defaultCharset());
    assertEquals(expected, actual);

    s = new ByteArrayOutputStream();
    p = new MinifyProcessor(new PrintStream(s));
    new JsonBoolean(false).accept(p);
    expected = "false";
    actual = new String(s.toByteArray(), Charset.defaultCharset());
    assertEquals(expected, actual);
  }

  @Test
  public void printString() {
    ByteArrayOutputStream s = new ByteArrayOutputStream();
    Processor p = new MinifyProcessor(new PrintStream(s));
    new JsonString("abc").accept(p);
    String expected = "\"abc\"";
    String actual = new String(s.toByteArray(), Charset.defaultCharset());
    assertEquals(expected, actual);
  }

  @Test
  public void printArray() {
    ByteArrayOutputStream s = new ByteArrayOutputStream();
    Processor p = new MinifyProcessor(new PrintStream(s));
    new JsonArray(new JsonValue[]{}).accept(p);
    String expected = "[]";
    String actual = new String(s.toByteArray(), Charset.defaultCharset());
    assertEquals(expected, actual);

    s = new ByteArrayOutputStream();
    p = new MinifyProcessor(new PrintStream(s));
    new JsonArray(new JsonValue[]{
      new JsonBoolean(false)
    }).accept(p);
    expected = "[false]";
    actual = new String(s.toByteArray(), Charset.defaultCharset());
    assertEquals(expected, actual);

    s = new ByteArrayOutputStream();
    p = new MinifyProcessor(new PrintStream(s));
    new JsonArray(new JsonValue[]{
      new JsonBoolean(false),
      new JsonBoolean(true)
    }).accept(p);
    expected = "[false,true]";
    actual = new String(s.toByteArray(), Charset.defaultCharset());
    assertEquals(expected, actual);

    s = new ByteArrayOutputStream();
    p = new MinifyProcessor(new PrintStream(s));
    new JsonArray(new JsonValue[]{
      new JsonBoolean(false),
      new JsonBoolean(true),
      new JsonObject(null),
    }).accept(p);
    expected = "[false,true,null]";
    actual = new String(s.toByteArray(), Charset.defaultCharset());
    assertEquals(expected, actual);

    s = new ByteArrayOutputStream();
    p = new MinifyProcessor(new PrintStream(s));
    new JsonArray(new JsonValue[]{
      new JsonBoolean(false)
    }).accept(p);
    expected = "[false]";
    actual = new String(s.toByteArray(), Charset.defaultCharset());
    assertEquals(expected, actual);

    s = new ByteArrayOutputStream();
    p = new MinifyProcessor(new PrintStream(s));
    new JsonArray(new JsonValue[]{
      new JsonBoolean(false),
      new JsonBoolean(true)
    }).accept(p);
    expected = "[false,true]";
    actual = new String(s.toByteArray(), Charset.defaultCharset());
    assertEquals(expected, actual);

    s = new ByteArrayOutputStream();
    p = new MinifyProcessor(new PrintStream(s));
    new JsonArray(new JsonValue[]{
      new JsonBoolean(false),
      new JsonBoolean(true),
      new JsonObject(null),
    }).accept(p);
    expected = "[false,true,null]";
    actual = new String(s.toByteArray(), Charset.defaultCharset());
    assertEquals(expected, actual);

    s = new ByteArrayOutputStream();
    p = new MinifyProcessor(new PrintStream(s));
    new JsonArray(new JsonValue[]{
      new JsonArray(new JsonValue[]{})
    }).accept(p);
    expected = "[[]]";
    actual = new String(s.toByteArray(), Charset.defaultCharset());
    assertEquals(expected, actual);

    s = new ByteArrayOutputStream();
    p = new MinifyProcessor(new PrintStream(s));
    new JsonArray(new JsonValue[]{
      new JsonArray(new JsonValue[]{}),
      new JsonArray(new JsonValue[]{})
    }).accept(p);
    expected = "[[],[]]";
    actual = new String(s.toByteArray(), Charset.defaultCharset());
    assertEquals(expected, actual);

    s = new ByteArrayOutputStream();
    p = new MinifyProcessor(new PrintStream(s));
    new JsonArray(new JsonValue[]{
      new JsonArray(new JsonValue[]{}),
      new JsonArray(new JsonValue[]{}),
      new JsonArray(new JsonValue[]{})
    }).accept(p);
    expected = "[[],[],[]]";
    actual = new String(s.toByteArray(), Charset.defaultCharset());
    assertEquals(expected, actual);

    s = new ByteArrayOutputStream();
    p = new MinifyProcessor(new PrintStream(s));
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
    expected = "[[false,true,null],[],[[[\"abc\",\"def\"]]]]";
    actual = new String(s.toByteArray(), Charset.defaultCharset());
    assertEquals(expected, actual);
  }

  @Test
  public void printObject() {
    ByteArrayOutputStream s = new ByteArrayOutputStream();
    Processor p = new MinifyProcessor(new PrintStream(s));
    Map<String, JsonValue> m = new HashMap<>();
    new JsonObject(m).accept(p);
    String expected = "{}";
    String actual = new String(s.toByteArray(), Charset.defaultCharset());
    assertEquals(expected, actual);

    s = new ByteArrayOutputStream();
    p = new MinifyProcessor(new PrintStream(s));
    m = new HashMap<>();
    m.put("bool", new JsonBoolean(false));
    new JsonObject(m).accept(p);
    expected = "{\"bool\":false}";
    actual = new String(s.toByteArray(), Charset.defaultCharset());
    assertEquals(expected, actual);

    s = new ByteArrayOutputStream();
    p = new MinifyProcessor(new PrintStream(s));
    m = new HashMap<>();
    m.put("bool", new JsonBoolean(false));
    new JsonObject(m).accept(p);
    expected = "{\"bool\":false}";
    actual = new String(s.toByteArray(), Charset.defaultCharset());
    assertEquals(expected, actual);

    s = new ByteArrayOutputStream();
    p = new MinifyProcessor(new PrintStream(s));
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
    expected = "{\"arr\":[\"abc\",\"abc\",\"abc\"],\"bool\":false,\"obj\":{\"null\":null,\"num\":1.0}}";
    actual = new String(s.toByteArray(), Charset.defaultCharset());
    assertEquals(expected, actual);
  }
}

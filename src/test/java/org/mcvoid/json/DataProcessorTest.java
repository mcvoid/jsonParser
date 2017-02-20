package org.mcvoid.json;

import org.junit.Test;
import org.mcvoid.json.ast.*;
import org.mcvoid.json.processors.DataProcessor;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class DataProcessorTest {
  @Test
  public void testNull() {
    DataProcessor p = new DataProcessor();
    new JsonObject(null).accept(p);
    Object actual = p.data;
    assertEquals(null, actual);
  }

  @Test
  public void testNumber() {
    DataProcessor p = new DataProcessor();
    new JsonNumber(3.5).accept(p);
    Object expected = 3.5;
    Object actual = p.data;
    assertEquals(expected, actual);
  }

  @Test
  public void testBoolean() {
    DataProcessor p = new DataProcessor();
    new JsonBoolean(true).accept(p);
    Object actual = p.data;
    assertEquals(true, actual);

    new JsonBoolean(false).accept(p);
    actual = p.data;
    assertEquals(false, actual);
  }

  @Test
  public void testString() {
    DataProcessor p = new DataProcessor();
    new JsonString("abc").accept(p);
    Object expected = "abc";
    Object actual = p.data;
    assertEquals(expected, actual);
  }

  @Test
  public void testArray() {
    DataProcessor p = new DataProcessor();
    new JsonArray(new JsonValue[]{

    }).accept(p);
    Object expected = new Object[]{};
    Object actual = p.data;
    assertArrayEquals((Object[])expected, (Object[])actual);

    new JsonArray(new JsonValue[]{
      new JsonNumber(3.5),
      new JsonString("abc"),
      new JsonBoolean(true),
      new JsonObject(null)
    }).accept(p);
    expected = new Object[]{3.5, "abc", true, null};
    actual = p.data;
    assertArrayEquals((Object[])expected, (Object[])actual);
  }

  @Test
  public void testObject() {
    DataProcessor p = new DataProcessor();
    new JsonObject(Collections.unmodifiableMap(Stream.of(
      new AbstractMap.SimpleEntry<String, JsonValue>("num", new JsonNumber(3.5)),
      new AbstractMap.SimpleEntry<String, JsonValue>("bool", new JsonBoolean(false)),
      new AbstractMap.SimpleEntry<String, JsonValue>("string", new JsonString("abc")),
      new AbstractMap.SimpleEntry<String, JsonValue>("null", new JsonObject(null))
    ).collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue)))).accept(p);
    assertTrue(p.data instanceof Map);
    Map<String, Object> m = (Map<String, Object>)p.data;
    assertEquals(3.5, m.get("num"));
    assertEquals("abc", m.get("string"));
    assertEquals(3.5, m.get("num"));
    assertNull(m.get("null"));
  }
}

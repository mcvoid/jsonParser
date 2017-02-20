package org.mcvoid.json;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;
import org.mcvoid.json.ast.*;
import org.mcvoid.json.parser.JsonLexer;
import org.mcvoid.json.parser.JsonParseTreeVisitor;
import org.mcvoid.json.parser.JsonParser;

import java.util.Map;

import static org.junit.Assert.*;

public class ParserTest {
  @Test
  public void testParseBoolean() {
    JsonParseTreeVisitor visitor = new JsonParseTreeVisitor();

    JsonLexer lexer = new JsonLexer(new ANTLRInputStream("false"));
    JsonParser parser = new JsonParser(new CommonTokenStream(lexer));
    JsonValue val = visitor.visitJson(parser.json());
    assertNotNull(val);
    assertTrue(val instanceof JsonBoolean);
    assertFalse(((JsonBoolean) val).value);

    lexer = new JsonLexer(new ANTLRInputStream("true"));
    parser = new JsonParser(new CommonTokenStream(lexer));
    val = visitor.visitJson(parser.json());
    assertNotNull(val);
    assertTrue(val instanceof JsonBoolean);
    assertTrue(((JsonBoolean) val).value);
  }

  @Test
  public void testParseNull() {
    JsonParseTreeVisitor visitor = new JsonParseTreeVisitor();

    JsonLexer lexer = new JsonLexer(new ANTLRInputStream("null"));
    JsonParser parser = new JsonParser(new CommonTokenStream(lexer));
    JsonValue val = visitor.visitJson(parser.json());
    assertTrue(val instanceof JsonObject);
    assertNull(((JsonObject) val).value);
  }

  @Test
  public void testParseString() {
    JsonParseTreeVisitor visitor = new JsonParseTreeVisitor();

    JsonLexer lexer = new JsonLexer(new ANTLRInputStream("\"abc\""));
    JsonParser parser = new JsonParser(new CommonTokenStream(lexer));
    JsonValue val = visitor.visitJson(parser.json());
    assertNotNull(val);
    assertTrue(val instanceof JsonString);
    assertEquals("abc", ((JsonString) val).value);
  }

  @Test
  public void testParseNumber() {
    JsonParseTreeVisitor visitor = new JsonParseTreeVisitor();

    JsonLexer lexer = new JsonLexer(new ANTLRInputStream("3.5"));
    JsonParser parser = new JsonParser(new CommonTokenStream(lexer));
    JsonValue val = visitor.visitJson(parser.json());
    assertNotNull(val);
    assertTrue(val instanceof JsonNumber);
    assertEquals(3.5, ((JsonNumber) val).value, 0.0000001);
  }

  @Test
  public void testParseArray() {
    JsonParseTreeVisitor visitor = new JsonParseTreeVisitor();

    JsonLexer lexer = new JsonLexer(new ANTLRInputStream("[1, 2, 3]"));
    JsonParser parser = new JsonParser(new CommonTokenStream(lexer));
    JsonValue val = visitor.visitJson(parser.json());
    assertNotNull(val);
    assertTrue(val instanceof JsonArray);
    JsonValue[] arr = ((JsonArray) val).value;
    double[] expected = new double[]{1, 2, 3};
    double[] actual = new double[arr.length];
    for (int i = 0; i < arr.length; i++) {
      JsonValue v = arr[i];
      assertTrue(v instanceof JsonNumber);
      actual[i] = ((JsonNumber) v).value;
    }
    assertArrayEquals(expected, actual, 0.0000001);
  }

  @Test
  public void testParseObject() {
    JsonParseTreeVisitor visitor = new JsonParseTreeVisitor();

    JsonLexer lexer = new JsonLexer(new ANTLRInputStream("{\"a\":true, \"b\":false, \"c\":3}"));
    JsonParser parser = new JsonParser(new CommonTokenStream(lexer));
    JsonValue val = visitor.visitJson(parser.json());
    assertNotNull(val);
    assertTrue(val instanceof JsonObject);
    Map<String, JsonValue> m = ((JsonObject) val).value;
    assertTrue(((JsonBoolean) m.get("a")).value);
    assertFalse(((JsonBoolean) m.get("b")).value);
    assertEquals(3, ((JsonNumber) m.get("c")).value, 0.0000001);
  }
}

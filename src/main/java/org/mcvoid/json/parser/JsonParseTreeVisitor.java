package org.mcvoid.json.parser;

import org.apache.commons.lang3.StringEscapeUtils;
import org.mcvoid.json.JsonValue;
import org.mcvoid.json.ast.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class JsonParseTreeVisitor extends JsonBaseVisitor<JsonValue> {
  public JsonValue visitJson(JsonParser.JsonContext ctx) {
    return visitValue(ctx.value());
  }

  public JsonValue visitObj(JsonParser.ObjContext ctx) {
    Map<String, JsonValue> obj = new HashMap<>();
    ctx.pair().stream().map(this::visitPair).map(v -> (JsonObject) v).map(o -> o.value).forEach(obj::putAll);
    return new JsonObject(obj);
  }

  public JsonValue visitPair(JsonParser.PairContext ctx) {
    Map<String, JsonValue> obj = new HashMap<>();
    String s = ctx.STRING().getText();
    obj.put(s.substring(1, s.length() - 1), visitValue(ctx.value()));
    return new JsonObject(obj);
  }

  public JsonValue visitArray(JsonParser.ArrayContext ctx) {
    List<JsonParser.ValueContext> children = ctx.value();
    JsonValue[] arr = new JsonValue[children.size()];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = visitValue(children.get(i));
    }
    return new JsonArray(arr);
  }

  public JsonValue visitValue(JsonParser.ValueContext ctx) {
    if (ctx.array() != null) {
      return visitArray(ctx.array());
    }
    if (ctx.obj() != null) {
      return visitObj(ctx.obj());
    }
    if (ctx.NUMBER() != null) {
      return new JsonNumber(Double.parseDouble(ctx.NUMBER().getText()));
    }
    if (ctx.STRING() != null) {
      String s = ctx.STRING().getText();
      return new JsonString(StringEscapeUtils.unescapeJava(s.substring(1, s.length() - 1)));
    }
    if (Objects.equals(ctx.getText(), "false")) {
      return new JsonBoolean(false);
    }
    if (Objects.equals(ctx.getText(), "true")) {
      return new JsonBoolean(true);
    }
    if (Objects.equals(ctx.getText(), "null")) {
      return new JsonObject(null);
    }
    throw new IllegalArgumentException("Invalid json value");
  }
}

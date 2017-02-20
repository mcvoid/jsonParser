package org.mcvoid.json.ast;

import org.mcvoid.json.JsonValue;
import org.mcvoid.json.Processor;

import java.util.Map;

public class JsonObject implements JsonValue {
  public JsonObject(Map<String, JsonValue> value) {
    this.value = value;
  }

  public void accept(Processor processor) {
    processor.process(this);
  }

  public Map<String, JsonValue> value;
}

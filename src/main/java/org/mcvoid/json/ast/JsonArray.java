package org.mcvoid.json.ast;

import org.mcvoid.json.JsonValue;
import org.mcvoid.json.Processor;

public class JsonArray implements JsonValue {
  public JsonArray(JsonValue[] value) {
    this.value = value;
  }

  public void accept(Processor processor) {
    processor.process(this);
  }

  public JsonValue[] value;
}

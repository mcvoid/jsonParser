package org.mcvoid.json.ast;

import org.mcvoid.json.JsonValue;
import org.mcvoid.json.Processor;

public class JsonString implements JsonValue {
  public JsonString(String value) {
    this.value = value;
  }

  public void accept(Processor processor) {
    processor.process(this);
  }

  public String value;
}

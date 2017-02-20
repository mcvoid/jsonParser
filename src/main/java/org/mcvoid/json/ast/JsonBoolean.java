package org.mcvoid.json.ast;

import org.mcvoid.json.JsonValue;
import org.mcvoid.json.Processor;

public class JsonBoolean implements JsonValue {
  public JsonBoolean(boolean value) {
    this.value = value;
  }

  public void accept(Processor processor) {
    processor.process(this);
  }

  public boolean value;
}

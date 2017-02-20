package org.mcvoid.json.ast;

import org.mcvoid.json.JsonValue;
import org.mcvoid.json.Processor;

public class JsonNumber implements JsonValue {
  public JsonNumber(double value) {
    this.value = value;
  }

  public void accept(Processor processor) {
    processor.process(this);
  }

  public double value;
}

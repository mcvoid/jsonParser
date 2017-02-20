package org.mcvoid.json;

public interface JsonValue {
  void accept(Processor processor);
}

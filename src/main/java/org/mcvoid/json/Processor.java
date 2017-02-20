package org.mcvoid.json;

import org.mcvoid.json.ast.*;

public interface Processor {
  void process(JsonObject json);

  void process(JsonArray json);

  void process(JsonNumber json);

  void process(JsonString json);

  void process(JsonBoolean json);
}

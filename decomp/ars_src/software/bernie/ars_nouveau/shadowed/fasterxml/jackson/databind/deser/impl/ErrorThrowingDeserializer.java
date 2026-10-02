package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.deser.impl;

import java.io.IOException;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonParser;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.DeserializationContext;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JsonDeserializer;

public class ErrorThrowingDeserializer extends JsonDeserializer<Object> {
   private final Error _cause;

   public ErrorThrowingDeserializer(NoClassDefFoundError cause) {
      this._cause = cause;
   }

   @Override
   public Object deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
      throw this._cause;
   }
}

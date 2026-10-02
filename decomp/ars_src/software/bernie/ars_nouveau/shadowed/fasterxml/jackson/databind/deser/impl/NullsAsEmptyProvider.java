package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.deser.impl;

import java.io.Serializable;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.DeserializationContext;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JsonDeserializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JsonMappingException;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.deser.NullValueProvider;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.util.AccessPattern;

public class NullsAsEmptyProvider implements NullValueProvider, Serializable {
   private static final long serialVersionUID = 1L;
   protected final JsonDeserializer<?> _deserializer;

   public NullsAsEmptyProvider(JsonDeserializer<?> deser) {
      this._deserializer = deser;
   }

   @Override
   public AccessPattern getNullAccessPattern() {
      return AccessPattern.DYNAMIC;
   }

   @Override
   public Object getNullValue(DeserializationContext ctxt) throws JsonMappingException {
      return this._deserializer.getEmptyValue(ctxt);
   }
}

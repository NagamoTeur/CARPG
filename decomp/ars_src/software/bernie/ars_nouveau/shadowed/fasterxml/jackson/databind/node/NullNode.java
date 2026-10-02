package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.node;

import java.io.IOException;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonGenerator;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonToken;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.SerializerProvider;

public final class NullNode extends ValueNode {
   public static final NullNode instance = new NullNode();

   private NullNode() {
   }

   public static NullNode getInstance() {
      return instance;
   }

   @Override
   public JsonNodeType getNodeType() {
      return JsonNodeType.NULL;
   }

   @Override
   public JsonToken asToken() {
      return JsonToken.VALUE_NULL;
   }

   @Override
   public String asText(String defaultValue) {
      return defaultValue;
   }

   @Override
   public String asText() {
      return "null";
   }

   @Override
   public final void serialize(JsonGenerator g, SerializerProvider provider) throws IOException {
      provider.defaultSerializeNull(g);
   }

   @Override
   public boolean equals(Object o) {
      return o == this;
   }

   @Override
   public int hashCode() {
      return JsonNodeType.NULL.ordinal();
   }
}

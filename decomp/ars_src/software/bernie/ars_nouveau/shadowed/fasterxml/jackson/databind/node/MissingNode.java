package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.node;

import java.io.IOException;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonGenerator;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonProcessingException;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonToken;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JsonNode;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.SerializerProvider;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;

public final class MissingNode extends ValueNode {
   private static final MissingNode instance = new MissingNode();

   private MissingNode() {
   }

   @Override
   public <T extends JsonNode> T deepCopy() {
      return (T)this;
   }

   public static MissingNode getInstance() {
      return instance;
   }

   @Override
   public JsonNodeType getNodeType() {
      return JsonNodeType.MISSING;
   }

   @Override
   public JsonToken asToken() {
      return JsonToken.NOT_AVAILABLE;
   }

   @Override
   public String asText() {
      return "";
   }

   @Override
   public String asText(String defaultValue) {
      return defaultValue;
   }

   @Override
   public final void serialize(JsonGenerator jg, SerializerProvider provider) throws IOException, JsonProcessingException {
      jg.writeNull();
   }

   @Override
   public void serializeWithType(JsonGenerator g, SerializerProvider provider, TypeSerializer typeSer) throws IOException, JsonProcessingException {
      g.writeNull();
   }

   @Override
   public boolean equals(Object o) {
      return o == this;
   }

   @Override
   public String toString() {
      return "";
   }

   @Override
   public int hashCode() {
      return JsonNodeType.MISSING.ordinal();
   }
}

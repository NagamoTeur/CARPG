package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.jsonschema;

import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.annotation.JsonCreator;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.annotation.JsonValue;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JsonNode;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.node.JsonNodeFactory;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.node.ObjectNode;

@Deprecated
public class JsonSchema {
   private final ObjectNode schema;

   @JsonCreator
   public JsonSchema(ObjectNode schema) {
      this.schema = schema;
   }

   @JsonValue
   public ObjectNode getSchemaNode() {
      return this.schema;
   }

   @Override
   public String toString() {
      return this.schema.toString();
   }

   @Override
   public int hashCode() {
      return this.schema.hashCode();
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (o == null) {
         return false;
      } else if (!(o instanceof JsonSchema)) {
         return false;
      } else {
         JsonSchema other = (JsonSchema)o;
         return this.schema == null ? other.schema == null : this.schema.equals(other.schema);
      }
   }

   public static JsonNode getDefaultSchemaNode() {
      ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
      objectNode.put("type", "any");
      return objectNode;
   }
}

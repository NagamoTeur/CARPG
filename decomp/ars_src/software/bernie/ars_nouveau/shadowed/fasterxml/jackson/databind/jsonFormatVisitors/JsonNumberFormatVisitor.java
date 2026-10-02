package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.jsonFormatVisitors;

import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonParser;

public interface JsonNumberFormatVisitor extends JsonValueFormatVisitor {
   void numberType(JsonParser.NumberType var1);

   public static class Base extends JsonValueFormatVisitor.Base implements JsonNumberFormatVisitor {
      @Override
      public void numberType(JsonParser.NumberType type) {
      }
   }
}

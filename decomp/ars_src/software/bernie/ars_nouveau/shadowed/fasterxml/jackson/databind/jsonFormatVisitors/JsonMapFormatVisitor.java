package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.jsonFormatVisitors;

import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JavaType;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JsonMappingException;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.SerializerProvider;

public interface JsonMapFormatVisitor extends JsonFormatVisitorWithSerializerProvider {
   void keyFormat(JsonFormatVisitable var1, JavaType var2) throws JsonMappingException;

   void valueFormat(JsonFormatVisitable var1, JavaType var2) throws JsonMappingException;

   public static class Base implements JsonMapFormatVisitor {
      protected SerializerProvider _provider;

      public Base() {
      }

      public Base(SerializerProvider p) {
         this._provider = p;
      }

      @Override
      public SerializerProvider getProvider() {
         return this._provider;
      }

      @Override
      public void setProvider(SerializerProvider p) {
         this._provider = p;
      }

      @Override
      public void keyFormat(JsonFormatVisitable handler, JavaType keyType) throws JsonMappingException {
      }

      @Override
      public void valueFormat(JsonFormatVisitable handler, JavaType valueType) throws JsonMappingException {
      }
   }
}

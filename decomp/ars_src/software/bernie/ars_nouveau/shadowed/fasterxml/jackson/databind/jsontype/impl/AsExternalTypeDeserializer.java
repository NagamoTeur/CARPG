package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.jsontype.impl;

import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.annotation.JsonTypeInfo;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.BeanProperty;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JavaType;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.jsontype.TypeIdResolver;

public class AsExternalTypeDeserializer extends AsArrayTypeDeserializer {
   private static final long serialVersionUID = 1L;

   public AsExternalTypeDeserializer(JavaType bt, TypeIdResolver idRes, String typePropertyName, boolean typeIdVisible, JavaType defaultImpl) {
      super(bt, idRes, typePropertyName, typeIdVisible, defaultImpl);
   }

   public AsExternalTypeDeserializer(AsExternalTypeDeserializer src, BeanProperty property) {
      super(src, property);
   }

   @Override
   public TypeDeserializer forProperty(BeanProperty prop) {
      return prop == this._property ? this : new AsExternalTypeDeserializer(this, prop);
   }

   @Override
   public JsonTypeInfo.As getTypeInclusion() {
      return JsonTypeInfo.As.EXTERNAL_PROPERTY;
   }

   @Override
   protected boolean _usesExternalId() {
      return true;
   }
}

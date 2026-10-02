package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.jsontype.impl;

import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.annotation.JsonTypeInfo;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.BeanProperty;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.jsontype.TypeIdResolver;

public class AsArrayTypeSerializer extends TypeSerializerBase {
   public AsArrayTypeSerializer(TypeIdResolver idRes, BeanProperty property) {
      super(idRes, property);
   }

   public AsArrayTypeSerializer forProperty(BeanProperty prop) {
      return this._property == prop ? this : new AsArrayTypeSerializer(this._idResolver, prop);
   }

   @Override
   public JsonTypeInfo.As getTypeInclusion() {
      return JsonTypeInfo.As.WRAPPER_ARRAY;
   }
}

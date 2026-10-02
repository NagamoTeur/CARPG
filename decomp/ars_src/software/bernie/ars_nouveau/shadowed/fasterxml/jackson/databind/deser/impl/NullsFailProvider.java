package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.deser.impl;

import java.io.Serializable;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.BeanProperty;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.DeserializationContext;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JavaType;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JsonMappingException;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.PropertyName;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.deser.NullValueProvider;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.exc.InvalidNullException;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.util.AccessPattern;

public class NullsFailProvider implements NullValueProvider, Serializable {
   private static final long serialVersionUID = 1L;
   protected final PropertyName _name;
   protected final JavaType _type;

   protected NullsFailProvider(PropertyName name, JavaType type) {
      this._name = name;
      this._type = type;
   }

   public static NullsFailProvider constructForProperty(BeanProperty prop) {
      return new NullsFailProvider(prop.getFullName(), prop.getType());
   }

   public static NullsFailProvider constructForRootValue(JavaType t) {
      return new NullsFailProvider(null, t);
   }

   @Override
   public AccessPattern getNullAccessPattern() {
      return AccessPattern.DYNAMIC;
   }

   @Override
   public Object getNullValue(DeserializationContext ctxt) throws JsonMappingException {
      throw InvalidNullException.from(ctxt, this._name, this._type);
   }
}

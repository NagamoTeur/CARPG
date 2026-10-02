package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.jsontype.impl;

import java.io.IOException;
import java.util.EnumMap;
import java.util.EnumSet;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.annotation.JsonTypeInfo;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.DatabindContext;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.DeserializationContext;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JavaType;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.type.TypeFactory;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.util.ClassUtil;

public class ClassNameIdResolver extends TypeIdResolverBase {
   public ClassNameIdResolver(JavaType baseType, TypeFactory typeFactory) {
      super(baseType, typeFactory);
   }

   @Override
   public JsonTypeInfo.Id getMechanism() {
      return JsonTypeInfo.Id.CLASS;
   }

   public void registerSubtype(Class<?> type, String name) {
   }

   @Override
   public String idFromValue(Object value) {
      return this._idFrom(value, value.getClass(), this._typeFactory);
   }

   @Override
   public String idFromValueAndType(Object value, Class<?> type) {
      return this._idFrom(value, type, this._typeFactory);
   }

   @Override
   public JavaType typeFromId(DatabindContext context, String id) throws IOException {
      return this._typeFromId(id, context);
   }

   protected JavaType _typeFromId(String id, DatabindContext ctxt) throws IOException {
      JavaType t = ctxt.resolveSubType(this._baseType, id);
      return t == null && ctxt instanceof DeserializationContext
         ? ((DeserializationContext)ctxt).handleUnknownTypeId(this._baseType, id, this, "no such class found")
         : t;
   }

   protected final String _idFrom(Object value, Class<?> cls, TypeFactory typeFactory) {
      if (Enum.class.isAssignableFrom(cls) && !cls.isEnum()) {
         cls = cls.getSuperclass();
      }

      String str = cls.getName();
      if (str.startsWith("java.util")) {
         if (value instanceof EnumSet) {
            Class<?> enumClass = ClassUtil.findEnumType((EnumSet<?>)value);
            str = typeFactory.constructCollectionType(EnumSet.class, enumClass).toCanonical();
         } else if (value instanceof EnumMap) {
            Class<?> enumClass = ClassUtil.findEnumType((EnumMap<?, ?>)value);
            Class<?> valueClass = Object.class;
            str = typeFactory.constructMapType(EnumMap.class, enumClass, valueClass).toCanonical();
         } else {
            String end = str.substring(9);
            if ((end.startsWith(".Arrays$") || end.startsWith(".Collections$")) && str.indexOf("List") >= 0) {
               str = "java.util.ArrayList";
            }
         }
      } else if (str.indexOf(36) >= 0) {
         Class<?> outer = ClassUtil.getOuterClass(cls);
         if (outer != null) {
            Class<?> staticType = this._baseType.getRawClass();
            if (ClassUtil.getOuterClass(staticType) == null) {
               cls = this._baseType.getRawClass();
               str = cls.getName();
            }
         }
      }

      return str;
   }

   @Override
   public String getDescForKnownTypeIds() {
      return "class name used as type id";
   }
}

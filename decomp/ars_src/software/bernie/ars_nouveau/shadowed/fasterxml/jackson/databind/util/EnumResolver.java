package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.AnnotationIntrospector;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMember;

public class EnumResolver implements Serializable {
   private static final long serialVersionUID = 1L;
   protected final Class<Enum<?>> _enumClass;
   protected final Enum<?>[] _enums;
   protected final HashMap<String, Enum<?>> _enumsById;
   protected final Enum<?> _defaultValue;

   protected EnumResolver(Class<Enum<?>> enumClass, Enum<?>[] enums, HashMap<String, Enum<?>> map, Enum<?> defaultValue) {
      this._enumClass = enumClass;
      this._enums = enums;
      this._enumsById = map;
      this._defaultValue = defaultValue;
   }

   public static EnumResolver constructFor(Class<Enum<?>> enumCls, AnnotationIntrospector ai) {
      Enum<?>[] enumValues = enumCls.getEnumConstants();
      if (enumValues == null) {
         throw new IllegalArgumentException("No enum constants for class " + enumCls.getName());
      } else {
         String[] names = ai.findEnumValues(enumCls, enumValues, new String[enumValues.length]);
         HashMap<String, Enum<?>> map = new HashMap<>();
         int i = 0;

         for (int len = enumValues.length; i < len; i++) {
            String name = names[i];
            if (name == null) {
               name = enumValues[i].name();
            }

            map.put(name, enumValues[i]);
         }

         Enum<?> defaultEnum = ai.findDefaultEnumValue(enumCls);
         return new EnumResolver(enumCls, enumValues, map, defaultEnum);
      }
   }

   @Deprecated
   public static EnumResolver constructUsingToString(Class<Enum<?>> enumCls) {
      return constructUsingToString(enumCls, null);
   }

   public static EnumResolver constructUsingToString(Class<Enum<?>> enumCls, AnnotationIntrospector ai) {
      Enum<?>[] enumValues = enumCls.getEnumConstants();
      HashMap<String, Enum<?>> map = new HashMap<>();
      int i = enumValues.length;

      while (--i >= 0) {
         Enum<?> e = enumValues[i];
         map.put(e.toString(), e);
      }

      Enum<?> defaultEnum = ai == null ? null : ai.findDefaultEnumValue(enumCls);
      return new EnumResolver(enumCls, enumValues, map, defaultEnum);
   }

   public static EnumResolver constructUsingMethod(Class<Enum<?>> enumCls, AnnotatedMember accessor, AnnotationIntrospector ai) {
      Enum<?>[] enumValues = enumCls.getEnumConstants();
      HashMap<String, Enum<?>> map = new HashMap<>();
      int i = enumValues.length;

      while (--i >= 0) {
         Enum<?> en = enumValues[i];

         try {
            Object o = accessor.getValue(en);
            if (o != null) {
               map.put(o.toString(), en);
            }
         } catch (Exception var8) {
            throw new IllegalArgumentException("Failed to access @JsonValue of Enum value " + en + ": " + var8.getMessage());
         }
      }

      Enum<?> defaultEnum = ai != null ? ai.findDefaultEnumValue(enumCls) : null;
      return new EnumResolver(enumCls, enumValues, map, defaultEnum);
   }

   public static EnumResolver constructUnsafe(Class<?> rawEnumCls, AnnotationIntrospector ai) {
      return constructFor((Class<Enum<?>>)rawEnumCls, ai);
   }

   public static EnumResolver constructUnsafeUsingToString(Class<?> rawEnumCls, AnnotationIntrospector ai) {
      return constructUsingToString((Class<Enum<?>>)rawEnumCls, ai);
   }

   public static EnumResolver constructUnsafeUsingMethod(Class<?> rawEnumCls, AnnotatedMember accessor, AnnotationIntrospector ai) {
      return constructUsingMethod((Class<Enum<?>>)rawEnumCls, accessor, ai);
   }

   public CompactStringObjectMap constructLookup() {
      return CompactStringObjectMap.construct(this._enumsById);
   }

   public Enum<?> findEnum(String key) {
      return this._enumsById.get(key);
   }

   public Enum<?> getEnum(int index) {
      return index >= 0 && index < this._enums.length ? this._enums[index] : null;
   }

   public Enum<?> getDefaultValue() {
      return this._defaultValue;
   }

   public Enum<?>[] getRawEnums() {
      return this._enums;
   }

   public List<Enum<?>> getEnums() {
      ArrayList<Enum<?>> enums = new ArrayList<>(this._enums.length);

      for (Enum<?> e : this._enums) {
         enums.add(e);
      }

      return enums;
   }

   public Collection<String> getEnumIds() {
      return this._enumsById.keySet();
   }

   public Class<Enum<?>> getEnumClass() {
      return this._enumClass;
   }

   public int lastValidIndex() {
      return this._enums.length - 1;
   }
}

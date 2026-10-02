package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.jsontype.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.annotation.JsonTypeInfo;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.BeanDescription;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.DatabindContext;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JavaType;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.cfg.MapperConfig;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.jsontype.NamedType;

public class TypeNameIdResolver extends TypeIdResolverBase {
   protected final MapperConfig<?> _config;
   protected final Map<String, String> _typeToId;
   protected final Map<String, JavaType> _idToType;

   protected TypeNameIdResolver(MapperConfig<?> config, JavaType baseType, Map<String, String> typeToId, Map<String, JavaType> idToType) {
      super(baseType, config.getTypeFactory());
      this._config = config;
      this._typeToId = typeToId;
      this._idToType = idToType;
   }

   public static TypeNameIdResolver construct(MapperConfig<?> config, JavaType baseType, Collection<NamedType> subtypes, boolean forSer, boolean forDeser) {
      if (forSer == forDeser) {
         throw new IllegalArgumentException();
      } else {
         Map<String, String> typeToId = null;
         Map<String, JavaType> idToType = null;
         if (forSer) {
            typeToId = new HashMap<>();
         }

         if (forDeser) {
            idToType = new HashMap<>();
            typeToId = new TreeMap<>();
         }

         if (subtypes != null) {
            for (NamedType t : subtypes) {
               Class<?> cls = t.getType();
               String id = t.hasName() ? t.getName() : _defaultTypeId(cls);
               if (forSer) {
                  typeToId.put(cls.getName(), id);
               }

               if (forDeser) {
                  JavaType prev = idToType.get(id);
                  if (prev == null || !cls.isAssignableFrom(prev.getRawClass())) {
                     idToType.put(id, config.constructType(cls));
                  }
               }
            }
         }

         return new TypeNameIdResolver(config, baseType, typeToId, idToType);
      }
   }

   @Override
   public JsonTypeInfo.Id getMechanism() {
      return JsonTypeInfo.Id.NAME;
   }

   @Override
   public String idFromValue(Object value) {
      return this.idFromClass(value.getClass());
   }

   protected String idFromClass(Class<?> clazz) {
      if (clazz == null) {
         return null;
      } else {
         Class<?> cls = this._typeFactory.constructType(clazz).getRawClass();
         String key = cls.getName();
         synchronized (this._typeToId) {
            String name = this._typeToId.get(key);
            if (name == null) {
               if (this._config.isAnnotationProcessingEnabled()) {
                  BeanDescription beanDesc = this._config.introspectClassAnnotations(cls);
                  name = this._config.getAnnotationIntrospector().findTypeName(beanDesc.getClassInfo());
               }

               if (name == null) {
                  name = _defaultTypeId(cls);
               }

               this._typeToId.put(key, name);
            }

            return name;
         }
      }
   }

   @Override
   public String idFromValueAndType(Object value, Class<?> type) {
      return value == null ? this.idFromClass(type) : this.idFromValue(value);
   }

   @Override
   public JavaType typeFromId(DatabindContext context, String id) {
      return this._typeFromId(id);
   }

   protected JavaType _typeFromId(String id) {
      return this._idToType.get(id);
   }

   @Override
   public String getDescForKnownTypeIds() {
      return new TreeSet<>(this._idToType.keySet()).toString();
   }

   @Override
   public String toString() {
      return String.format("[%s; id-to-type=%s]", this.getClass().getName(), this._idToType);
   }

   protected static String _defaultTypeId(Class<?> cls) {
      String n = cls.getName();
      int ix = n.lastIndexOf(46);
      return ix < 0 ? n : n.substring(ix + 1);
   }
}

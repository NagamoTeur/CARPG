package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.module;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.BeanDescription;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.DeserializationConfig;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JavaType;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JsonDeserializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JsonMappingException;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JsonNode;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.KeyDeserializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.deser.Deserializers;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.type.ArrayType;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.type.ClassKey;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.type.CollectionLikeType;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.type.CollectionType;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.type.MapLikeType;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.type.MapType;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.type.ReferenceType;

public class SimpleDeserializers implements Deserializers, Serializable {
   private static final long serialVersionUID = 1L;
   protected HashMap<ClassKey, JsonDeserializer<?>> _classMappings = null;
   protected boolean _hasEnumDeserializer = false;

   public SimpleDeserializers() {
   }

   public SimpleDeserializers(Map<Class<?>, JsonDeserializer<?>> desers) {
      this.addDeserializers(desers);
   }

   public <T> void addDeserializer(Class<T> forClass, JsonDeserializer<? extends T> deser) {
      ClassKey key = new ClassKey(forClass);
      if (this._classMappings == null) {
         this._classMappings = new HashMap<>();
      }

      this._classMappings.put(key, deser);
      if (forClass == Enum.class) {
         this._hasEnumDeserializer = true;
      }
   }

   public void addDeserializers(Map<Class<?>, JsonDeserializer<?>> desers) {
      for (Entry<Class<?>, JsonDeserializer<?>> entry : desers.entrySet()) {
         Class<?> cls = entry.getKey();
         JsonDeserializer<Object> deser = (JsonDeserializer<Object>)entry.getValue();
         this.addDeserializer(cls, deser);
      }
   }

   @Override
   public JsonDeserializer<?> findArrayDeserializer(
      ArrayType type, DeserializationConfig config, BeanDescription beanDesc, TypeDeserializer elementTypeDeserializer, JsonDeserializer<?> elementDeserializer
   ) throws JsonMappingException {
      return this._find(type);
   }

   @Override
   public JsonDeserializer<?> findBeanDeserializer(JavaType type, DeserializationConfig config, BeanDescription beanDesc) throws JsonMappingException {
      return this._find(type);
   }

   @Override
   public JsonDeserializer<?> findCollectionDeserializer(
      CollectionType type,
      DeserializationConfig config,
      BeanDescription beanDesc,
      TypeDeserializer elementTypeDeserializer,
      JsonDeserializer<?> elementDeserializer
   ) throws JsonMappingException {
      return this._find(type);
   }

   @Override
   public JsonDeserializer<?> findCollectionLikeDeserializer(
      CollectionLikeType type,
      DeserializationConfig config,
      BeanDescription beanDesc,
      TypeDeserializer elementTypeDeserializer,
      JsonDeserializer<?> elementDeserializer
   ) throws JsonMappingException {
      return this._find(type);
   }

   @Override
   public JsonDeserializer<?> findEnumDeserializer(Class<?> type, DeserializationConfig config, BeanDescription beanDesc) throws JsonMappingException {
      if (this._classMappings == null) {
         return null;
      } else {
         JsonDeserializer<?> deser = this._classMappings.get(new ClassKey(type));
         if (deser == null && this._hasEnumDeserializer && type.isEnum()) {
            deser = this._classMappings.get(new ClassKey(Enum.class));
         }

         return deser;
      }
   }

   @Override
   public JsonDeserializer<?> findTreeNodeDeserializer(Class<? extends JsonNode> nodeType, DeserializationConfig config, BeanDescription beanDesc) throws JsonMappingException {
      return this._classMappings == null ? null : this._classMappings.get(new ClassKey(nodeType));
   }

   @Override
   public JsonDeserializer<?> findReferenceDeserializer(
      ReferenceType refType,
      DeserializationConfig config,
      BeanDescription beanDesc,
      TypeDeserializer contentTypeDeserializer,
      JsonDeserializer<?> contentDeserializer
   ) throws JsonMappingException {
      return this._find(refType);
   }

   @Override
   public JsonDeserializer<?> findMapDeserializer(
      MapType type,
      DeserializationConfig config,
      BeanDescription beanDesc,
      KeyDeserializer keyDeserializer,
      TypeDeserializer elementTypeDeserializer,
      JsonDeserializer<?> elementDeserializer
   ) throws JsonMappingException {
      return this._find(type);
   }

   @Override
   public JsonDeserializer<?> findMapLikeDeserializer(
      MapLikeType type,
      DeserializationConfig config,
      BeanDescription beanDesc,
      KeyDeserializer keyDeserializer,
      TypeDeserializer elementTypeDeserializer,
      JsonDeserializer<?> elementDeserializer
   ) throws JsonMappingException {
      return this._find(type);
   }

   private final JsonDeserializer<?> _find(JavaType type) {
      return this._classMappings == null ? null : this._classMappings.get(new ClassKey(type.getRawClass()));
   }
}

package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.ser;

import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.BeanDescription;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JavaType;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JsonSerializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.SerializationConfig;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.type.ArrayType;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.type.CollectionLikeType;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.type.CollectionType;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.type.MapLikeType;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.type.MapType;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.type.ReferenceType;

public interface Serializers {
   JsonSerializer<?> findSerializer(SerializationConfig var1, JavaType var2, BeanDescription var3);

   JsonSerializer<?> findReferenceSerializer(
      SerializationConfig var1, ReferenceType var2, BeanDescription var3, TypeSerializer var4, JsonSerializer<Object> var5
   );

   JsonSerializer<?> findArraySerializer(SerializationConfig var1, ArrayType var2, BeanDescription var3, TypeSerializer var4, JsonSerializer<Object> var5);

   JsonSerializer<?> findCollectionSerializer(
      SerializationConfig var1, CollectionType var2, BeanDescription var3, TypeSerializer var4, JsonSerializer<Object> var5
   );

   JsonSerializer<?> findCollectionLikeSerializer(
      SerializationConfig var1, CollectionLikeType var2, BeanDescription var3, TypeSerializer var4, JsonSerializer<Object> var5
   );

   JsonSerializer<?> findMapSerializer(
      SerializationConfig var1, MapType var2, BeanDescription var3, JsonSerializer<Object> var4, TypeSerializer var5, JsonSerializer<Object> var6
   );

   JsonSerializer<?> findMapLikeSerializer(
      SerializationConfig var1, MapLikeType var2, BeanDescription var3, JsonSerializer<Object> var4, TypeSerializer var5, JsonSerializer<Object> var6
   );

   public static class Base implements Serializers {
      @Override
      public JsonSerializer<?> findSerializer(SerializationConfig config, JavaType type, BeanDescription beanDesc) {
         return null;
      }

      @Override
      public JsonSerializer<?> findReferenceSerializer(
         SerializationConfig config,
         ReferenceType type,
         BeanDescription beanDesc,
         TypeSerializer contentTypeSerializer,
         JsonSerializer<Object> contentValueSerializer
      ) {
         return this.findSerializer(config, type, beanDesc);
      }

      @Override
      public JsonSerializer<?> findArraySerializer(
         SerializationConfig config,
         ArrayType type,
         BeanDescription beanDesc,
         TypeSerializer elementTypeSerializer,
         JsonSerializer<Object> elementValueSerializer
      ) {
         return null;
      }

      @Override
      public JsonSerializer<?> findCollectionSerializer(
         SerializationConfig config,
         CollectionType type,
         BeanDescription beanDesc,
         TypeSerializer elementTypeSerializer,
         JsonSerializer<Object> elementValueSerializer
      ) {
         return null;
      }

      @Override
      public JsonSerializer<?> findCollectionLikeSerializer(
         SerializationConfig config,
         CollectionLikeType type,
         BeanDescription beanDesc,
         TypeSerializer elementTypeSerializer,
         JsonSerializer<Object> elementValueSerializer
      ) {
         return null;
      }

      @Override
      public JsonSerializer<?> findMapSerializer(
         SerializationConfig config,
         MapType type,
         BeanDescription beanDesc,
         JsonSerializer<Object> keySerializer,
         TypeSerializer elementTypeSerializer,
         JsonSerializer<Object> elementValueSerializer
      ) {
         return null;
      }

      @Override
      public JsonSerializer<?> findMapLikeSerializer(
         SerializationConfig config,
         MapLikeType type,
         BeanDescription beanDesc,
         JsonSerializer<Object> keySerializer,
         TypeSerializer elementTypeSerializer,
         JsonSerializer<Object> elementValueSerializer
      ) {
         return null;
      }
   }
}

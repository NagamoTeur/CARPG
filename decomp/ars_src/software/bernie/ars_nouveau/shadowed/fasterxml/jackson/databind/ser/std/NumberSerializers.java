package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.ser.std;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.annotation.JsonFormat;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonGenerator;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonParser;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.BeanProperty;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JavaType;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JsonMappingException;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JsonNode;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JsonSerializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.SerializerProvider;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.ser.ContextualSerializer;

public class NumberSerializers {
   protected NumberSerializers() {
   }

   public static void addAll(Map<String, JsonSerializer<?>> allDeserializers) {
      allDeserializers.put(Integer.class.getName(), new NumberSerializers.IntegerSerializer(Integer.class));
      allDeserializers.put(int.class.getName(), new NumberSerializers.IntegerSerializer(int.class));
      allDeserializers.put(Long.class.getName(), new NumberSerializers.LongSerializer(Long.class));
      allDeserializers.put(long.class.getName(), new NumberSerializers.LongSerializer(long.class));
      allDeserializers.put(Byte.class.getName(), NumberSerializers.IntLikeSerializer.instance);
      allDeserializers.put(byte.class.getName(), NumberSerializers.IntLikeSerializer.instance);
      allDeserializers.put(Short.class.getName(), NumberSerializers.ShortSerializer.instance);
      allDeserializers.put(short.class.getName(), NumberSerializers.ShortSerializer.instance);
      allDeserializers.put(Double.class.getName(), new NumberSerializers.DoubleSerializer(Double.class));
      allDeserializers.put(double.class.getName(), new NumberSerializers.DoubleSerializer(double.class));
      allDeserializers.put(Float.class.getName(), NumberSerializers.FloatSerializer.instance);
      allDeserializers.put(float.class.getName(), NumberSerializers.FloatSerializer.instance);
   }

   protected abstract static class Base<T> extends StdScalarSerializer<T> implements ContextualSerializer {
      protected final JsonParser.NumberType _numberType;
      protected final String _schemaType;
      protected final boolean _isInt;

      protected Base(Class<?> cls, JsonParser.NumberType numberType, String schemaType) {
         super(cls, false);
         this._numberType = numberType;
         this._schemaType = schemaType;
         this._isInt = numberType == JsonParser.NumberType.INT || numberType == JsonParser.NumberType.LONG || numberType == JsonParser.NumberType.BIG_INTEGER;
      }

      @Override
      public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
         return this.createSchemaNode(this._schemaType, true);
      }

      @Override
      public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
         if (this._isInt) {
            this.visitIntFormat(visitor, typeHint, this._numberType);
         } else {
            this.visitFloatFormat(visitor, typeHint, this._numberType);
         }
      }

      @Override
      public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
         JsonFormat.Value format = this.findFormatOverrides(prov, property, this.handledType());
         if (format != null) {
            switch (format.getShape()) {
               case STRING:
                  return ToStringSerializer.instance;
            }
         }

         return this;
      }
   }

   @JacksonStdImpl
   public static final class DoubleSerializer extends NumberSerializers.Base<Object> {
      public DoubleSerializer(Class<?> cls) {
         super(cls, JsonParser.NumberType.DOUBLE, "number");
      }

      @Override
      public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
         gen.writeNumber((Double)value);
      }

      @Override
      public void serializeWithType(Object value, JsonGenerator gen, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
         this.serialize(value, gen, provider);
      }
   }

   @JacksonStdImpl
   public static final class FloatSerializer extends NumberSerializers.Base<Object> {
      static final NumberSerializers.FloatSerializer instance = new NumberSerializers.FloatSerializer();

      public FloatSerializer() {
         super(Float.class, JsonParser.NumberType.FLOAT, "number");
      }

      @Override
      public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
         gen.writeNumber((Float)value);
      }
   }

   @JacksonStdImpl
   public static final class IntLikeSerializer extends NumberSerializers.Base<Object> {
      static final NumberSerializers.IntLikeSerializer instance = new NumberSerializers.IntLikeSerializer();

      public IntLikeSerializer() {
         super(Number.class, JsonParser.NumberType.INT, "integer");
      }

      @Override
      public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
         gen.writeNumber(((Number)value).intValue());
      }
   }

   @JacksonStdImpl
   public static final class IntegerSerializer extends NumberSerializers.Base<Object> {
      public IntegerSerializer(Class<?> type) {
         super(type, JsonParser.NumberType.INT, "integer");
      }

      @Override
      public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
         gen.writeNumber((Integer)value);
      }

      @Override
      public void serializeWithType(Object value, JsonGenerator gen, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
         this.serialize(value, gen, provider);
      }
   }

   @JacksonStdImpl
   public static final class LongSerializer extends NumberSerializers.Base<Object> {
      public LongSerializer(Class<?> cls) {
         super(cls, JsonParser.NumberType.LONG, "number");
      }

      @Override
      public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
         gen.writeNumber((Long)value);
      }
   }

   @JacksonStdImpl
   public static final class ShortSerializer extends NumberSerializers.Base<Object> {
      static final NumberSerializers.ShortSerializer instance = new NumberSerializers.ShortSerializer();

      public ShortSerializer() {
         super(Short.class, JsonParser.NumberType.INT, "number");
      }

      @Override
      public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
         gen.writeNumber((Short)value);
      }
   }
}

package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.ser.std;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.annotation.JsonFormat;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.annotation.JsonIgnoreProperties;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.annotation.JsonInclude;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonGenerator;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonToken;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.type.WritableTypeId;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.AnnotationIntrospector;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.BeanProperty;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JavaType;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JsonMappingException;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JsonNode;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JsonSerializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.SerializationFeature;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.SerializerProvider;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMember;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonMapFormatVisitor;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.ser.ContainerSerializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.ser.ContextualSerializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.ser.PropertyFilter;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.type.TypeFactory;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.util.ArrayBuilders;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.util.BeanUtil;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.util.ClassUtil;

@JacksonStdImpl
public class MapSerializer extends ContainerSerializer<Map<?, ?>> implements ContextualSerializer {
   private static final long serialVersionUID = 1L;
   protected static final JavaType UNSPECIFIED_TYPE = TypeFactory.unknownType();
   public static final Object MARKER_FOR_EMPTY = JsonInclude.Include.NON_EMPTY;
   protected final BeanProperty _property;
   protected final boolean _valueTypeIsStatic;
   protected final JavaType _keyType;
   protected final JavaType _valueType;
   protected JsonSerializer<Object> _keySerializer;
   protected JsonSerializer<Object> _valueSerializer;
   protected final TypeSerializer _valueTypeSerializer;
   protected PropertySerializerMap _dynamicValueSerializers;
   protected final Set<String> _ignoredEntries;
   protected final Object _filterId;
   protected final Object _suppressableValue;
   protected final boolean _suppressNulls;
   protected final boolean _sortKeys;

   protected MapSerializer(
      Set<String> ignoredEntries,
      JavaType keyType,
      JavaType valueType,
      boolean valueTypeIsStatic,
      TypeSerializer vts,
      JsonSerializer<?> keySerializer,
      JsonSerializer<?> valueSerializer
   ) {
      super(Map.class, false);
      this._ignoredEntries = ignoredEntries != null && !ignoredEntries.isEmpty() ? ignoredEntries : null;
      this._keyType = keyType;
      this._valueType = valueType;
      this._valueTypeIsStatic = valueTypeIsStatic;
      this._valueTypeSerializer = vts;
      this._keySerializer = (JsonSerializer<Object>)keySerializer;
      this._valueSerializer = (JsonSerializer<Object>)valueSerializer;
      this._dynamicValueSerializers = PropertySerializerMap.emptyForProperties();
      this._property = null;
      this._filterId = null;
      this._sortKeys = false;
      this._suppressableValue = null;
      this._suppressNulls = false;
   }

   protected MapSerializer(
      MapSerializer src, BeanProperty property, JsonSerializer<?> keySerializer, JsonSerializer<?> valueSerializer, Set<String> ignoredEntries
   ) {
      super(Map.class, false);
      this._ignoredEntries = ignoredEntries != null && !ignoredEntries.isEmpty() ? ignoredEntries : null;
      this._keyType = src._keyType;
      this._valueType = src._valueType;
      this._valueTypeIsStatic = src._valueTypeIsStatic;
      this._valueTypeSerializer = src._valueTypeSerializer;
      this._keySerializer = (JsonSerializer<Object>)keySerializer;
      this._valueSerializer = (JsonSerializer<Object>)valueSerializer;
      this._dynamicValueSerializers = src._dynamicValueSerializers;
      this._property = property;
      this._filterId = src._filterId;
      this._sortKeys = src._sortKeys;
      this._suppressableValue = src._suppressableValue;
      this._suppressNulls = src._suppressNulls;
   }

   protected MapSerializer(MapSerializer src, TypeSerializer vts, Object suppressableValue, boolean suppressNulls) {
      super(Map.class, false);
      this._ignoredEntries = src._ignoredEntries;
      this._keyType = src._keyType;
      this._valueType = src._valueType;
      this._valueTypeIsStatic = src._valueTypeIsStatic;
      this._valueTypeSerializer = vts;
      this._keySerializer = src._keySerializer;
      this._valueSerializer = src._valueSerializer;
      this._dynamicValueSerializers = src._dynamicValueSerializers;
      this._property = src._property;
      this._filterId = src._filterId;
      this._sortKeys = src._sortKeys;
      this._suppressableValue = suppressableValue;
      this._suppressNulls = suppressNulls;
   }

   protected MapSerializer(MapSerializer src, Object filterId, boolean sortKeys) {
      super(Map.class, false);
      this._ignoredEntries = src._ignoredEntries;
      this._keyType = src._keyType;
      this._valueType = src._valueType;
      this._valueTypeIsStatic = src._valueTypeIsStatic;
      this._valueTypeSerializer = src._valueTypeSerializer;
      this._keySerializer = src._keySerializer;
      this._valueSerializer = src._valueSerializer;
      this._dynamicValueSerializers = src._dynamicValueSerializers;
      this._property = src._property;
      this._filterId = filterId;
      this._sortKeys = sortKeys;
      this._suppressableValue = src._suppressableValue;
      this._suppressNulls = src._suppressNulls;
   }

   public MapSerializer _withValueTypeSerializer(TypeSerializer vts) {
      if (this._valueTypeSerializer == vts) {
         return this;
      } else {
         this._ensureOverride("_withValueTypeSerializer");
         return new MapSerializer(this, vts, this._suppressableValue, this._suppressNulls);
      }
   }

   public MapSerializer withResolved(
      BeanProperty property, JsonSerializer<?> keySerializer, JsonSerializer<?> valueSerializer, Set<String> ignored, boolean sortKeys
   ) {
      this._ensureOverride("withResolved");
      MapSerializer ser = new MapSerializer(this, property, keySerializer, valueSerializer, ignored);
      if (sortKeys != ser._sortKeys) {
         ser = new MapSerializer(ser, this._filterId, sortKeys);
      }

      return ser;
   }

   public MapSerializer withFilterId(Object filterId) {
      if (this._filterId == filterId) {
         return this;
      } else {
         this._ensureOverride("withFilterId");
         return new MapSerializer(this, filterId, this._sortKeys);
      }
   }

   public MapSerializer withContentInclusion(Object suppressableValue, boolean suppressNulls) {
      if (suppressableValue == this._suppressableValue && suppressNulls == this._suppressNulls) {
         return this;
      } else {
         this._ensureOverride("withContentInclusion");
         return new MapSerializer(this, this._valueTypeSerializer, suppressableValue, suppressNulls);
      }
   }

   public static MapSerializer construct(
      Set<String> ignoredEntries,
      JavaType mapType,
      boolean staticValueType,
      TypeSerializer vts,
      JsonSerializer<Object> keySerializer,
      JsonSerializer<Object> valueSerializer,
      Object filterId
   ) {
      JavaType keyType;
      JavaType valueType;
      if (mapType == null) {
         valueType = UNSPECIFIED_TYPE;
         keyType = UNSPECIFIED_TYPE;
      } else {
         keyType = mapType.getKeyType();
         valueType = mapType.getContentType();
      }

      if (!staticValueType) {
         staticValueType = valueType != null && valueType.isFinal();
      } else if (valueType.getRawClass() == Object.class) {
         staticValueType = false;
      }

      MapSerializer ser = new MapSerializer(ignoredEntries, keyType, valueType, staticValueType, vts, keySerializer, valueSerializer);
      if (filterId != null) {
         ser = ser.withFilterId(filterId);
      }

      return ser;
   }

   protected void _ensureOverride(String method) {
      ClassUtil.verifyMustOverride(MapSerializer.class, this, method);
   }

   @Deprecated
   protected void _ensureOverride() {
      this._ensureOverride("N/A");
   }

   @Deprecated
   protected MapSerializer(MapSerializer src, TypeSerializer vts, Object suppressableValue) {
      this(src, vts, suppressableValue, false);
   }

   @Deprecated
   public MapSerializer withContentInclusion(Object suppressableValue) {
      return new MapSerializer(this, this._valueTypeSerializer, suppressableValue, this._suppressNulls);
   }

   @Deprecated
   public static MapSerializer construct(
      String[] ignoredList,
      JavaType mapType,
      boolean staticValueType,
      TypeSerializer vts,
      JsonSerializer<Object> keySerializer,
      JsonSerializer<Object> valueSerializer,
      Object filterId
   ) {
      Set<String> ignoredEntries = ArrayBuilders.arrayToSet(ignoredList);
      return construct(ignoredEntries, mapType, staticValueType, vts, keySerializer, valueSerializer, filterId);
   }

   @Override
   public JsonSerializer<?> createContextual(SerializerProvider provider, BeanProperty property) throws JsonMappingException {
      JsonSerializer<?> ser = null;
      JsonSerializer<?> keySer = null;
      AnnotationIntrospector intr = provider.getAnnotationIntrospector();
      AnnotatedMember propertyAcc = property == null ? null : property.getMember();
      if (_neitherNull(propertyAcc, intr)) {
         Object serDef = intr.findKeySerializer(propertyAcc);
         if (serDef != null) {
            keySer = provider.serializerInstance(propertyAcc, serDef);
         }

         serDef = intr.findContentSerializer(propertyAcc);
         if (serDef != null) {
            ser = provider.serializerInstance(propertyAcc, serDef);
         }
      }

      if (ser == null) {
         ser = this._valueSerializer;
      }

      ser = this.findContextualConvertingSerializer(provider, property, ser);
      if (ser == null && this._valueTypeIsStatic && !this._valueType.isJavaLangObject()) {
         ser = provider.findValueSerializer(this._valueType, property);
      }

      if (keySer == null) {
         keySer = this._keySerializer;
      }

      if (keySer == null) {
         keySer = provider.findKeySerializer(this._keyType, property);
      } else {
         keySer = provider.handleSecondaryContextualization(keySer, property);
      }

      Set<String> ignored = this._ignoredEntries;
      boolean sortKeys = false;
      if (_neitherNull(propertyAcc, intr)) {
         JsonIgnoreProperties.Value ignorals = intr.findPropertyIgnorals(propertyAcc);
         if (ignorals != null) {
            Set<String> newIgnored = ignorals.findIgnoredForSerialization();
            if (_nonEmpty(newIgnored)) {
               ignored = ignored == null ? new HashSet<>() : new HashSet<>(ignored);

               for (String str : newIgnored) {
                  ignored.add(str);
               }
            }
         }

         Boolean b = intr.findSerializationSortAlphabetically(propertyAcc);
         sortKeys = Boolean.TRUE.equals(b);
      }

      JsonFormat.Value format = this.findFormatOverrides(provider, property, Map.class);
      if (format != null) {
         Boolean B = format.getFeature(JsonFormat.Feature.WRITE_SORTED_MAP_ENTRIES);
         if (B != null) {
            sortKeys = B;
         }
      }

      MapSerializer mser = this.withResolved(property, keySer, ser, ignored, sortKeys);
      if (property != null) {
         AnnotatedMember m = property.getMember();
         if (m != null) {
            Object filterId = intr.findFilterId(m);
            if (filterId != null) {
               mser = mser.withFilterId(filterId);
            }
         }

         JsonInclude.Value inclV = property.findPropertyInclusion(provider.getConfig(), null);
         if (inclV != null) {
            JsonInclude.Include incl = inclV.getContentInclusion();
            if (incl != JsonInclude.Include.USE_DEFAULTS) {
               Object valueToSuppress;
               boolean suppressNulls;
               switch (incl) {
                  case NON_DEFAULT:
                     valueToSuppress = BeanUtil.getDefaultValue(this._valueType);
                     suppressNulls = true;
                     if (valueToSuppress != null && valueToSuppress.getClass().isArray()) {
                        valueToSuppress = ArrayBuilders.getArrayComparator(valueToSuppress);
                     }
                     break;
                  case NON_ABSENT:
                     suppressNulls = true;
                     valueToSuppress = this._valueType.isReferenceType() ? MARKER_FOR_EMPTY : null;
                     break;
                  case NON_EMPTY:
                     suppressNulls = true;
                     valueToSuppress = MARKER_FOR_EMPTY;
                     break;
                  case CUSTOM:
                     valueToSuppress = provider.includeFilterInstance(null, inclV.getContentFilter());
                     if (valueToSuppress == null) {
                        suppressNulls = true;
                     } else {
                        suppressNulls = provider.includeFilterSuppressNulls(valueToSuppress);
                     }
                     break;
                  case NON_NULL:
                     valueToSuppress = null;
                     suppressNulls = true;
                     break;
                  case ALWAYS:
                  default:
                     valueToSuppress = null;
                     suppressNulls = false;
               }

               mser = mser.withContentInclusion(valueToSuppress, suppressNulls);
            }
         }
      }

      return mser;
   }

   @Override
   public JavaType getContentType() {
      return this._valueType;
   }

   @Override
   public JsonSerializer<?> getContentSerializer() {
      return this._valueSerializer;
   }

   public boolean isEmpty(SerializerProvider prov, Map<?, ?> value) {
      if (value.isEmpty()) {
         return true;
      } else {
         Object supp = this._suppressableValue;
         if (supp == null && !this._suppressNulls) {
            return false;
         } else {
            JsonSerializer<Object> valueSer = this._valueSerializer;
            boolean checkEmpty = MARKER_FOR_EMPTY == supp;
            if (valueSer != null) {
               for (Object elemValue : value.values()) {
                  if (elemValue != null) {
                     if (!checkEmpty) {
                        if (supp == null || !supp.equals(value)) {
                           return false;
                        }
                     } else if (!valueSer.isEmpty(prov, elemValue)) {
                        return false;
                     }
                  } else if (!this._suppressNulls) {
                     return false;
                  }
               }

               return true;
            } else {
               for (Object elemValuex : value.values()) {
                  if (elemValuex != null) {
                     try {
                        valueSer = this._findSerializer(prov, elemValuex);
                     } catch (JsonMappingException var9) {
                        return false;
                     }

                     if (!checkEmpty) {
                        if (supp == null || !supp.equals(value)) {
                           return false;
                        }
                     } else if (!valueSer.isEmpty(prov, elemValuex)) {
                        return false;
                     }
                  } else if (!this._suppressNulls) {
                     return false;
                  }
               }

               return true;
            }
         }
      }
   }

   public boolean hasSingleElement(Map<?, ?> value) {
      return value.size() == 1;
   }

   public JsonSerializer<?> getKeySerializer() {
      return this._keySerializer;
   }

   public void serialize(Map<?, ?> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
      gen.writeStartObject(value);
      if (!value.isEmpty()) {
         if (this._sortKeys || provider.isEnabled(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)) {
            value = this._orderEntries(value, gen, provider);
         }

         PropertyFilter pf;
         if (this._filterId != null && (pf = this.findPropertyFilter(provider, this._filterId, value)) != null) {
            this.serializeFilteredFields(value, gen, provider, pf, this._suppressableValue);
         } else if (this._suppressableValue != null || this._suppressNulls) {
            this.serializeOptionalFields(value, gen, provider, this._suppressableValue);
         } else if (this._valueSerializer != null) {
            this.serializeFieldsUsing(value, gen, provider, this._valueSerializer);
         } else {
            this.serializeFields(value, gen, provider);
         }
      }

      gen.writeEndObject();
   }

   public void serializeWithType(Map<?, ?> value, JsonGenerator gen, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
      gen.setCurrentValue(value);
      WritableTypeId typeIdDef = typeSer.writeTypePrefix(gen, typeSer.typeId(value, JsonToken.START_OBJECT));
      if (!value.isEmpty()) {
         if (this._sortKeys || provider.isEnabled(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)) {
            value = this._orderEntries(value, gen, provider);
         }

         PropertyFilter pf;
         if (this._filterId != null && (pf = this.findPropertyFilter(provider, this._filterId, value)) != null) {
            this.serializeFilteredFields(value, gen, provider, pf, this._suppressableValue);
         } else if (this._suppressableValue != null || this._suppressNulls) {
            this.serializeOptionalFields(value, gen, provider, this._suppressableValue);
         } else if (this._valueSerializer != null) {
            this.serializeFieldsUsing(value, gen, provider, this._valueSerializer);
         } else {
            this.serializeFields(value, gen, provider);
         }
      }

      typeSer.writeTypeSuffix(gen, typeIdDef);
   }

   public void serializeFields(Map<?, ?> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
      if (this._valueTypeSerializer != null) {
         this.serializeTypedFields(value, gen, provider, null);
      } else {
         JsonSerializer<Object> keySerializer = this._keySerializer;
         Set<String> ignored = this._ignoredEntries;
         Object keyElem = null;

         try {
            for (Entry<?, ?> entry : value.entrySet()) {
               Object valueElem = entry.getValue();
               keyElem = entry.getKey();
               if (keyElem == null) {
                  provider.findNullKeySerializer(this._keyType, this._property).serialize(null, gen, provider);
               } else {
                  if (ignored != null && ignored.contains(keyElem)) {
                     continue;
                  }

                  keySerializer.serialize(keyElem, gen, provider);
               }

               if (valueElem == null) {
                  provider.defaultSerializeNull(gen);
               } else {
                  JsonSerializer<Object> serializer = this._valueSerializer;
                  if (serializer == null) {
                     serializer = this._findSerializer(provider, valueElem);
                  }

                  serializer.serialize(valueElem, gen, provider);
               }
            }
         } catch (Exception var11) {
            this.wrapAndThrow(provider, var11, value, String.valueOf(keyElem));
         }
      }
   }

   public void serializeOptionalFields(Map<?, ?> value, JsonGenerator gen, SerializerProvider provider, Object suppressableValue) throws IOException {
      if (this._valueTypeSerializer != null) {
         this.serializeTypedFields(value, gen, provider, suppressableValue);
      } else {
         Set<String> ignored = this._ignoredEntries;
         boolean checkEmpty = MARKER_FOR_EMPTY == suppressableValue;

         for (Entry<?, ?> entry : value.entrySet()) {
            Object keyElem = entry.getKey();
            JsonSerializer<Object> keySerializer;
            if (keyElem == null) {
               keySerializer = provider.findNullKeySerializer(this._keyType, this._property);
            } else {
               if (ignored != null && ignored.contains(keyElem)) {
                  continue;
               }

               keySerializer = this._keySerializer;
            }

            Object valueElem = entry.getValue();
            JsonSerializer<Object> valueSer;
            if (valueElem == null) {
               if (this._suppressNulls) {
                  continue;
               }

               valueSer = provider.getDefaultNullValueSerializer();
            } else {
               valueSer = this._valueSerializer;
               if (valueSer == null) {
                  valueSer = this._findSerializer(provider, valueElem);
               }

               if (checkEmpty ? valueSer.isEmpty(provider, valueElem) : suppressableValue != null && suppressableValue.equals(valueElem)) {
                  continue;
               }
            }

            try {
               keySerializer.serialize(keyElem, gen, provider);
               valueSer.serialize(valueElem, gen, provider);
            } catch (Exception var14) {
               this.wrapAndThrow(provider, var14, value, String.valueOf(keyElem));
            }
         }
      }
   }

   public void serializeFieldsUsing(Map<?, ?> value, JsonGenerator gen, SerializerProvider provider, JsonSerializer<Object> ser) throws IOException {
      JsonSerializer<Object> keySerializer = this._keySerializer;
      Set<String> ignored = this._ignoredEntries;
      TypeSerializer typeSer = this._valueTypeSerializer;

      for (Entry<?, ?> entry : value.entrySet()) {
         Object keyElem = entry.getKey();
         if (ignored == null || !ignored.contains(keyElem)) {
            if (keyElem == null) {
               provider.findNullKeySerializer(this._keyType, this._property).serialize(null, gen, provider);
            } else {
               keySerializer.serialize(keyElem, gen, provider);
            }

            Object valueElem = entry.getValue();
            if (valueElem == null) {
               provider.defaultSerializeNull(gen);
            } else {
               try {
                  if (typeSer == null) {
                     ser.serialize(valueElem, gen, provider);
                  } else {
                     ser.serializeWithType(valueElem, gen, provider, typeSer);
                  }
               } catch (Exception var13) {
                  this.wrapAndThrow(provider, var13, value, String.valueOf(keyElem));
               }
            }
         }
      }
   }

   public void serializeFilteredFields(Map<?, ?> value, JsonGenerator gen, SerializerProvider provider, PropertyFilter filter, Object suppressableValue) throws IOException {
      Set<String> ignored = this._ignoredEntries;
      MapProperty prop = new MapProperty(this._valueTypeSerializer, this._property);
      boolean checkEmpty = MARKER_FOR_EMPTY == suppressableValue;

      for (Entry<?, ?> entry : value.entrySet()) {
         Object keyElem = entry.getKey();
         if (ignored == null || !ignored.contains(keyElem)) {
            JsonSerializer<Object> keySerializer;
            if (keyElem == null) {
               keySerializer = provider.findNullKeySerializer(this._keyType, this._property);
            } else {
               keySerializer = this._keySerializer;
            }

            Object valueElem = entry.getValue();
            JsonSerializer<Object> valueSer;
            if (valueElem == null) {
               if (this._suppressNulls) {
                  continue;
               }

               valueSer = provider.getDefaultNullValueSerializer();
            } else {
               valueSer = this._valueSerializer;
               if (valueSer == null) {
                  valueSer = this._findSerializer(provider, valueElem);
               }

               if (checkEmpty ? valueSer.isEmpty(provider, valueElem) : suppressableValue != null && suppressableValue.equals(valueElem)) {
                  continue;
               }
            }

            prop.reset(keyElem, valueElem, keySerializer, valueSer);

            try {
               filter.serializeAsField(value, gen, provider, prop);
            } catch (Exception var16) {
               this.wrapAndThrow(provider, var16, value, String.valueOf(keyElem));
            }
         }
      }
   }

   public void serializeTypedFields(Map<?, ?> value, JsonGenerator gen, SerializerProvider provider, Object suppressableValue) throws IOException {
      Set<String> ignored = this._ignoredEntries;
      boolean checkEmpty = MARKER_FOR_EMPTY == suppressableValue;

      for (Entry<?, ?> entry : value.entrySet()) {
         Object keyElem = entry.getKey();
         JsonSerializer<Object> keySerializer;
         if (keyElem == null) {
            keySerializer = provider.findNullKeySerializer(this._keyType, this._property);
         } else {
            if (ignored != null && ignored.contains(keyElem)) {
               continue;
            }

            keySerializer = this._keySerializer;
         }

         Object valueElem = entry.getValue();
         JsonSerializer<Object> valueSer;
         if (valueElem == null) {
            if (this._suppressNulls) {
               continue;
            }

            valueSer = provider.getDefaultNullValueSerializer();
         } else {
            valueSer = this._valueSerializer;
            if (valueSer == null) {
               valueSer = this._findSerializer(provider, valueElem);
            }

            if (checkEmpty ? valueSer.isEmpty(provider, valueElem) : suppressableValue != null && suppressableValue.equals(valueElem)) {
               continue;
            }
         }

         keySerializer.serialize(keyElem, gen, provider);

         try {
            valueSer.serializeWithType(valueElem, gen, provider, this._valueTypeSerializer);
         } catch (Exception var14) {
            this.wrapAndThrow(provider, var14, value, String.valueOf(keyElem));
         }
      }
   }

   public void serializeFilteredAnyProperties(
      SerializerProvider provider, JsonGenerator gen, Object bean, Map<?, ?> value, PropertyFilter filter, Object suppressableValue
   ) throws IOException {
      Set<String> ignored = this._ignoredEntries;
      MapProperty prop = new MapProperty(this._valueTypeSerializer, this._property);
      boolean checkEmpty = MARKER_FOR_EMPTY == suppressableValue;

      for (Entry<?, ?> entry : value.entrySet()) {
         Object keyElem = entry.getKey();
         if (ignored == null || !ignored.contains(keyElem)) {
            JsonSerializer<Object> keySerializer;
            if (keyElem == null) {
               keySerializer = provider.findNullKeySerializer(this._keyType, this._property);
            } else {
               keySerializer = this._keySerializer;
            }

            Object valueElem = entry.getValue();
            JsonSerializer<Object> valueSer;
            if (valueElem == null) {
               if (this._suppressNulls) {
                  continue;
               }

               valueSer = provider.getDefaultNullValueSerializer();
            } else {
               valueSer = this._valueSerializer;
               if (valueSer == null) {
                  valueSer = this._findSerializer(provider, valueElem);
               }

               if (checkEmpty ? valueSer.isEmpty(provider, valueElem) : suppressableValue != null && suppressableValue.equals(valueElem)) {
                  continue;
               }
            }

            prop.reset(keyElem, valueElem, keySerializer, valueSer);

            try {
               filter.serializeAsField(bean, gen, provider, prop);
            } catch (Exception var17) {
               this.wrapAndThrow(provider, var17, value, String.valueOf(keyElem));
            }
         }
      }
   }

   @Override
   public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
      return this.createSchemaNode("object", true);
   }

   @Override
   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
      JsonMapFormatVisitor v2 = visitor.expectMapFormat(typeHint);
      if (v2 != null) {
         v2.keyFormat(this._keySerializer, this._keyType);
         JsonSerializer<?> valueSer = this._valueSerializer;
         if (valueSer == null) {
            valueSer = this._findAndAddDynamic(this._dynamicValueSerializers, this._valueType, visitor.getProvider());
         }

         v2.valueFormat(valueSer, this._valueType);
      }
   }

   protected final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap map, Class<?> type, SerializerProvider provider) throws JsonMappingException {
      PropertySerializerMap.SerializerAndMapResult result = map.findAndAddSecondarySerializer(type, provider, this._property);
      if (map != result.map) {
         this._dynamicValueSerializers = result.map;
      }

      return result.serializer;
   }

   protected final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap map, JavaType type, SerializerProvider provider) throws JsonMappingException {
      PropertySerializerMap.SerializerAndMapResult result = map.findAndAddSecondarySerializer(type, provider, this._property);
      if (map != result.map) {
         this._dynamicValueSerializers = result.map;
      }

      return result.serializer;
   }

   protected Map<?, ?> _orderEntries(Map<?, ?> input, JsonGenerator gen, SerializerProvider provider) throws IOException {
      if (input instanceof SortedMap) {
         return input;
      } else if (this._hasNullKey(input)) {
         TreeMap<Object, Object> result = new TreeMap<>();

         for (Entry<?, ?> entry : input.entrySet()) {
            Object key = entry.getKey();
            if (key == null) {
               this._writeNullKeyedEntry(gen, provider, entry.getValue());
            } else {
               result.put(key, entry.getValue());
            }
         }

         return result;
      } else {
         return new TreeMap<>(input);
      }
   }

   protected boolean _hasNullKey(Map<?, ?> input) {
      return input instanceof HashMap && input.containsKey(null);
   }

   protected void _writeNullKeyedEntry(JsonGenerator gen, SerializerProvider provider, Object value) throws IOException {
      JsonSerializer<Object> keySerializer = provider.findNullKeySerializer(this._keyType, this._property);
      JsonSerializer<Object> valueSer;
      if (value == null) {
         if (this._suppressNulls) {
            return;
         }

         valueSer = provider.getDefaultNullValueSerializer();
      } else {
         valueSer = this._valueSerializer;
         if (valueSer == null) {
            valueSer = this._findSerializer(provider, value);
         }

         if (this._suppressableValue == MARKER_FOR_EMPTY) {
            if (valueSer.isEmpty(provider, value)) {
               return;
            }
         } else if (this._suppressableValue != null && this._suppressableValue.equals(value)) {
            return;
         }
      }

      try {
         keySerializer.serialize(null, gen, provider);
         valueSer.serialize(value, gen, provider);
      } catch (Exception var7) {
         this.wrapAndThrow(provider, var7, value, "");
      }
   }

   private final JsonSerializer<Object> _findSerializer(SerializerProvider provider, Object value) throws JsonMappingException {
      Class<?> cc = value.getClass();
      JsonSerializer<Object> valueSer = this._dynamicValueSerializers.serializerFor(cc);
      if (valueSer != null) {
         return valueSer;
      } else {
         return this._valueType.hasGenericTypes()
            ? this._findAndAddDynamic(this._dynamicValueSerializers, provider.constructSpecializedType(this._valueType, cc), provider)
            : this._findAndAddDynamic(this._dynamicValueSerializers, cc, provider);
      }
   }
}

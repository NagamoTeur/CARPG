package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.ser;

import java.io.IOException;
import java.util.Set;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonGenerator;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JavaType;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JsonSerializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.SerializerProvider;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.ser.impl.BeanAsArraySerializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.ser.impl.ObjectIdWriter;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.ser.impl.UnwrappingBeanSerializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.ser.std.BeanSerializerBase;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.util.NameTransformer;

public class BeanSerializer extends BeanSerializerBase {
   private static final long serialVersionUID = 29L;

   public BeanSerializer(JavaType type, BeanSerializerBuilder builder, BeanPropertyWriter[] properties, BeanPropertyWriter[] filteredProperties) {
      super(type, builder, properties, filteredProperties);
   }

   protected BeanSerializer(BeanSerializerBase src) {
      super(src);
   }

   protected BeanSerializer(BeanSerializerBase src, ObjectIdWriter objectIdWriter) {
      super(src, objectIdWriter);
   }

   protected BeanSerializer(BeanSerializerBase src, ObjectIdWriter objectIdWriter, Object filterId) {
      super(src, objectIdWriter, filterId);
   }

   protected BeanSerializer(BeanSerializerBase src, Set<String> toIgnore) {
      super(src, toIgnore);
   }

   public static BeanSerializer createDummy(JavaType forType) {
      return new BeanSerializer(forType, null, NO_PROPS, null);
   }

   @Override
   public JsonSerializer<Object> unwrappingSerializer(NameTransformer unwrapper) {
      return new UnwrappingBeanSerializer(this, unwrapper);
   }

   @Override
   public BeanSerializerBase withObjectIdWriter(ObjectIdWriter objectIdWriter) {
      return new BeanSerializer(this, objectIdWriter, this._propertyFilterId);
   }

   @Override
   public BeanSerializerBase withFilterId(Object filterId) {
      return new BeanSerializer(this, this._objectIdWriter, filterId);
   }

   @Override
   protected BeanSerializerBase withIgnorals(Set<String> toIgnore) {
      return new BeanSerializer(this, toIgnore);
   }

   @Override
   protected BeanSerializerBase asArraySerializer() {
      return (BeanSerializerBase)(this._objectIdWriter == null && this._anyGetterWriter == null && this._propertyFilterId == null
         ? new BeanAsArraySerializer(this)
         : this);
   }

   @Override
   public final void serialize(Object bean, JsonGenerator gen, SerializerProvider provider) throws IOException {
      if (this._objectIdWriter != null) {
         gen.setCurrentValue(bean);
         this._serializeWithObjectId(bean, gen, provider, true);
      } else {
         gen.writeStartObject(bean);
         if (this._propertyFilterId != null) {
            this.serializeFieldsFiltered(bean, gen, provider);
         } else {
            this.serializeFields(bean, gen, provider);
         }

         gen.writeEndObject();
      }
   }

   @Override
   public String toString() {
      return "BeanSerializer for " + this.handledType().getName();
   }
}

package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.ser;

import java.io.IOException;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonGenerator;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonToken;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.type.WritableTypeId;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.SerializerProvider;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.ser.std.StdSerializer;

abstract class JSR310SerializerBase<T> extends StdSerializer<T> {
   private static final long serialVersionUID = 1L;

   protected JSR310SerializerBase(Class<?> supportedType) {
      super(supportedType, false);
   }

   @Override
   public void serializeWithType(T value, JsonGenerator g, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
      WritableTypeId typeIdDef = typeSer.writeTypePrefix(g, typeSer.typeId(value, this.serializationShape(provider)));
      this.serialize(value, g, provider);
      typeSer.writeTypeSuffix(g, typeIdDef);
   }

   protected abstract JsonToken serializationShape(SerializerProvider var1);
}

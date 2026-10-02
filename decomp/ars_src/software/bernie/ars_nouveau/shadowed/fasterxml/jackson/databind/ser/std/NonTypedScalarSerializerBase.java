package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.ser.std;

import java.io.IOException;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonGenerator;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.SerializerProvider;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;

@Deprecated
public abstract class NonTypedScalarSerializerBase<T> extends StdScalarSerializer<T> {
   protected NonTypedScalarSerializerBase(Class<T> t) {
      super(t);
   }

   protected NonTypedScalarSerializerBase(Class<?> t, boolean bogus) {
      super(t, bogus);
   }

   @Override
   public final void serializeWithType(T value, JsonGenerator gen, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
      this.serialize(value, gen, provider);
   }
}

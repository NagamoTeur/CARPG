package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.ser.std;

import java.io.IOException;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonGenerator;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.SerializerProvider;

@Deprecated
public class StdKeySerializer extends StdSerializer<Object> {
   public StdKeySerializer() {
      super(Object.class);
   }

   @Override
   public void serialize(Object value, JsonGenerator g, SerializerProvider provider) throws IOException {
      g.writeFieldName(value.toString());
   }
}

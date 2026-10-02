package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.ser;

import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JsonMappingException;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.SerializerProvider;

public interface ResolvableSerializer {
   void resolve(SerializerProvider var1) throws JsonMappingException;
}

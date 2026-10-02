package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.deser;

import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.BeanProperty;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.DeserializationContext;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JsonMappingException;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.KeyDeserializer;

public interface ContextualKeyDeserializer {
   KeyDeserializer createContextual(DeserializationContext var1, BeanProperty var2) throws JsonMappingException;
}

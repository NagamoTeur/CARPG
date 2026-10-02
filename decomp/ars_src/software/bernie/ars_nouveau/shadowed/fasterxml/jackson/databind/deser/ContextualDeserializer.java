package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.deser;

import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.BeanProperty;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.DeserializationContext;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JsonDeserializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JsonMappingException;

public interface ContextualDeserializer {
   JsonDeserializer<?> createContextual(DeserializationContext var1, BeanProperty var2) throws JsonMappingException;
}

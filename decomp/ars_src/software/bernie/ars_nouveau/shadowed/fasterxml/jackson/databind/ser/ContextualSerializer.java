package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.ser;

import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.BeanProperty;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JsonMappingException;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JsonSerializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.SerializerProvider;

public interface ContextualSerializer {
   JsonSerializer<?> createContextual(SerializerProvider var1, BeanProperty var2) throws JsonMappingException;
}

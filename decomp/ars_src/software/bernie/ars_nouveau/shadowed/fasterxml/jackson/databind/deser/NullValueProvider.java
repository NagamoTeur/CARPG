package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.deser;

import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.DeserializationContext;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JsonMappingException;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.util.AccessPattern;

public interface NullValueProvider {
   Object getNullValue(DeserializationContext var1) throws JsonMappingException;

   AccessPattern getNullAccessPattern();
}

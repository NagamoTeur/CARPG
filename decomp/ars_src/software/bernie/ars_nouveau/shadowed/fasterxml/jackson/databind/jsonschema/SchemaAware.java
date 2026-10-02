package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.jsonschema;

import java.lang.reflect.Type;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JsonMappingException;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JsonNode;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.SerializerProvider;

public interface SchemaAware {
   JsonNode getSchema(SerializerProvider var1, Type var2) throws JsonMappingException;

   JsonNode getSchema(SerializerProvider var1, Type var2, boolean var3) throws JsonMappingException;
}

package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.jsonFormatVisitors;

import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JavaType;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JsonMappingException;

public interface JsonFormatVisitable {
   void acceptJsonFormatVisitor(JsonFormatVisitorWrapper var1, JavaType var2) throws JsonMappingException;
}

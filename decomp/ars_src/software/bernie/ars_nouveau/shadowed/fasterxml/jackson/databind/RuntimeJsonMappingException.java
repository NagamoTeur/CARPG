package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind;

public class RuntimeJsonMappingException extends RuntimeException {
   public RuntimeJsonMappingException(JsonMappingException cause) {
      super(cause);
   }

   public RuntimeJsonMappingException(String message) {
      super(message);
   }

   public RuntimeJsonMappingException(String message, JsonMappingException cause) {
      super(message, cause);
   }
}

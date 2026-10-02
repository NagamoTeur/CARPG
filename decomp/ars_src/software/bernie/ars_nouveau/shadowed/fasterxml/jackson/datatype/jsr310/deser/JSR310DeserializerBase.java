package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.deser;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonParser;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonToken;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.DeserializationContext;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JsonMappingException;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;

abstract class JSR310DeserializerBase<T> extends StdScalarDeserializer<T> {
   private static final long serialVersionUID = 1L;

   protected JSR310DeserializerBase(Class<T> supportedType) {
      super(supportedType);
   }

   @Override
   public Object deserializeWithType(JsonParser parser, DeserializationContext context, TypeDeserializer typeDeserializer) throws IOException {
      return typeDeserializer.deserializeTypedFromAny(parser, context);
   }

   protected <BOGUS> BOGUS _reportWrongToken(DeserializationContext context, JsonToken exp, String unit) throws IOException {
      context.reportWrongTokenException(this, exp, "Expected %s for '%s' of %s value", exp.name(), unit, this.handledType().getName());
      return null;
   }

   protected <BOGUS> BOGUS _reportWrongToken(JsonParser parser, DeserializationContext context, JsonToken... expTypes) throws IOException {
      return context.reportInputMismatch(
         this.handledType(),
         "Unexpected token (%s), expected one of %s for %s value",
         parser.getCurrentToken(),
         Arrays.asList(expTypes).toString(),
         this.handledType().getName()
      );
   }

   protected <BOGUS> BOGUS _rethrowDateTimeException(JsonParser p, DeserializationContext context, DateTimeException e0, String value) throws JsonMappingException {
      if (e0 instanceof DateTimeParseException) {
         JsonMappingException e = context.weirdStringException(value, this.handledType(), e0.getMessage());
         e.initCause(e0);
         throw e;
      } else {
         if (e0 instanceof DateTimeException) {
            String msg = e0.getMessage();
            if (msg.contains("invalid format")) {
               JsonMappingException e = context.weirdStringException(value, this.handledType(), e0.getMessage());
               e.initCause(e0);
               throw e;
            }
         }

         return context.reportInputMismatch(
            this.handledType(), "Failed to deserialize %s: (%s) %s", this.handledType().getName(), e0.getClass().getName(), e0.getMessage()
         );
      }
   }

   protected DateTimeException _peelDTE(DateTimeException e) {
      while (true) {
         Throwable t = e.getCause();
         if (t == null || !(t instanceof DateTimeException)) {
            return e;
         }

         e = (DateTimeException)t;
      }
   }
}

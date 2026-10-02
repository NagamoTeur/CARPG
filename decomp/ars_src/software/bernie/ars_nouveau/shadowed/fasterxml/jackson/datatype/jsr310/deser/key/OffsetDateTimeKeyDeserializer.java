package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.deser.key;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.DeserializationContext;

public class OffsetDateTimeKeyDeserializer extends Jsr310KeyDeserializer {
   public static final OffsetDateTimeKeyDeserializer INSTANCE = new OffsetDateTimeKeyDeserializer();

   private OffsetDateTimeKeyDeserializer() {
   }

   protected OffsetDateTime deserialize(String key, DeserializationContext ctxt) throws IOException {
      try {
         return OffsetDateTime.parse(key, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
      } catch (DateTimeException var4) {
         return this._rethrowDateTimeException(ctxt, OffsetDateTime.class, var4, key);
      }
   }
}

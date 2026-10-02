package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.deser.key;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.DeserializationContext;

public class InstantKeyDeserializer extends Jsr310KeyDeserializer {
   public static final InstantKeyDeserializer INSTANCE = new InstantKeyDeserializer();

   private InstantKeyDeserializer() {
   }

   protected Instant deserialize(String key, DeserializationContext ctxt) throws IOException {
      try {
         return DateTimeFormatter.ISO_INSTANT.parse(key, Instant::from);
      } catch (DateTimeException var4) {
         return this._rethrowDateTimeException(ctxt, Instant.class, var4, key);
      }
   }
}

package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.deser.key;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.DeserializationContext;

public class YearMothKeyDeserializer extends Jsr310KeyDeserializer {
   public static final YearMothKeyDeserializer INSTANCE = new YearMothKeyDeserializer();
   private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
      .appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
      .appendLiteral('-')
      .appendValue(ChronoField.MONTH_OF_YEAR, 2)
      .toFormatter();

   private YearMothKeyDeserializer() {
   }

   protected YearMonth deserialize(String key, DeserializationContext ctxt) throws IOException {
      try {
         return YearMonth.parse(key, FORMATTER);
      } catch (DateTimeException var4) {
         return this._rethrowDateTimeException(ctxt, YearMonth.class, var4, key);
      }
   }
}

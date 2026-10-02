package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.deser;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonParser;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonToken;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.DeserializationContext;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JsonDeserializer;

public class MonthDayDeserializer extends JSR310DateTimeDeserializerBase<MonthDay> {
   private static final long serialVersionUID = 1L;
   public static final MonthDayDeserializer INSTANCE = new MonthDayDeserializer(null);

   public MonthDayDeserializer(DateTimeFormatter formatter) {
      super(MonthDay.class, formatter);
   }

   @Override
   protected JsonDeserializer<MonthDay> withDateFormat(DateTimeFormatter dtf) {
      return new MonthDayDeserializer(dtf);
   }

   public MonthDay deserialize(JsonParser parser, DeserializationContext context) throws IOException {
      if (parser.hasToken(JsonToken.VALUE_STRING)) {
         String string = parser.getValueAsString().trim();

         try {
            if (this._formatter == null) {
               return MonthDay.parse(string);
            }

            return MonthDay.parse(string, this._formatter);
         } catch (DateTimeException var5) {
            this._rethrowDateTimeException(parser, context, var5, string);
         }
      }

      if (parser.hasToken(JsonToken.VALUE_EMBEDDED_OBJECT)) {
         return (MonthDay)parser.getEmbeddedObject();
      } else {
         return parser.hasToken(JsonToken.START_ARRAY)
            ? this._deserializeFromArray(parser, context)
            : this._reportWrongToken(parser, context, new JsonToken[]{JsonToken.VALUE_STRING, JsonToken.VALUE_NUMBER_INT});
      }
   }
}

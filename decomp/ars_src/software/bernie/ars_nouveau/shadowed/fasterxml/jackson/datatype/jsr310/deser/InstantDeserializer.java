package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.deser;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Pattern;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.annotation.JsonFormat;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonParser;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonToken;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.BeanProperty;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.DeserializationContext;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.DeserializationFeature;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JsonDeserializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JsonMappingException;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.DecimalUtils;

public class InstantDeserializer<T extends Temporal> extends JSR310DateTimeDeserializerBase<T> {
   private static final long serialVersionUID = 1L;
   private static final Pattern ISO8601_UTC_ZERO_OFFSET_SUFFIX_REGEX = Pattern.compile("\\+00:?(00)?$");
   public static final InstantDeserializer<Instant> INSTANT = new InstantDeserializer<>(
      Instant.class,
      DateTimeFormatter.ISO_INSTANT,
      Instant::from,
      a -> Instant.ofEpochMilli(a.value),
      a -> Instant.ofEpochSecond(a.integer, (long)a.fraction),
      null,
      true
   );
   public static final InstantDeserializer<OffsetDateTime> OFFSET_DATE_TIME = new InstantDeserializer<>(
      OffsetDateTime.class,
      DateTimeFormatter.ISO_OFFSET_DATE_TIME,
      OffsetDateTime::from,
      a -> OffsetDateTime.ofInstant(Instant.ofEpochMilli(a.value), a.zoneId),
      a -> OffsetDateTime.ofInstant(Instant.ofEpochSecond(a.integer, (long)a.fraction), a.zoneId),
      (d, z) -> d.withOffsetSameInstant(z.getRules().getOffset(d.toLocalDateTime())),
      true
   );
   public static final InstantDeserializer<ZonedDateTime> ZONED_DATE_TIME = new InstantDeserializer<>(
      ZonedDateTime.class,
      DateTimeFormatter.ISO_ZONED_DATE_TIME,
      ZonedDateTime::from,
      a -> ZonedDateTime.ofInstant(Instant.ofEpochMilli(a.value), a.zoneId),
      a -> ZonedDateTime.ofInstant(Instant.ofEpochSecond(a.integer, (long)a.fraction), a.zoneId),
      ZonedDateTime::withZoneSameInstant,
      false
   );
   protected final Function<InstantDeserializer.FromIntegerArguments, T> fromMilliseconds;
   protected final Function<InstantDeserializer.FromDecimalArguments, T> fromNanoseconds;
   protected final Function<TemporalAccessor, T> parsedToValue;
   protected final BiFunction<T, ZoneId, T> adjust;
   protected final boolean replaceZeroOffsetAsZ;
   protected final Boolean _adjustToContextTZOverride;

   protected InstantDeserializer(
      Class<T> supportedType,
      DateTimeFormatter formatter,
      Function<TemporalAccessor, T> parsedToValue,
      Function<InstantDeserializer.FromIntegerArguments, T> fromMilliseconds,
      Function<InstantDeserializer.FromDecimalArguments, T> fromNanoseconds,
      BiFunction<T, ZoneId, T> adjust,
      boolean replaceZeroOffsetAsZ
   ) {
      super(supportedType, formatter);
      this.parsedToValue = parsedToValue;
      this.fromMilliseconds = fromMilliseconds;
      this.fromNanoseconds = fromNanoseconds;
      this.adjust = adjust == null ? (d, z) -> d : adjust;
      this.replaceZeroOffsetAsZ = replaceZeroOffsetAsZ;
      this._adjustToContextTZOverride = null;
   }

   protected InstantDeserializer(InstantDeserializer<T> base, DateTimeFormatter f) {
      super((Class<T>)base.handledType(), f);
      this.parsedToValue = base.parsedToValue;
      this.fromMilliseconds = base.fromMilliseconds;
      this.fromNanoseconds = base.fromNanoseconds;
      this.adjust = base.adjust;
      this.replaceZeroOffsetAsZ = this._formatter == DateTimeFormatter.ISO_INSTANT;
      this._adjustToContextTZOverride = base._adjustToContextTZOverride;
   }

   protected InstantDeserializer(InstantDeserializer<T> base, Boolean adjustToContextTimezoneOverride) {
      super((Class<T>)base.handledType(), base._formatter);
      this.parsedToValue = base.parsedToValue;
      this.fromMilliseconds = base.fromMilliseconds;
      this.fromNanoseconds = base.fromNanoseconds;
      this.adjust = base.adjust;
      this.replaceZeroOffsetAsZ = base.replaceZeroOffsetAsZ;
      this._adjustToContextTZOverride = adjustToContextTimezoneOverride;
   }

   @Override
   protected JsonDeserializer<T> withDateFormat(DateTimeFormatter dtf) {
      return dtf == this._formatter ? this : new InstantDeserializer<>(this, dtf);
   }

   public T deserialize(JsonParser parser, DeserializationContext context) throws IOException {
      switch (parser.getCurrentTokenId()) {
         case 3:
            return this._deserializeFromArray(parser, context);
         case 4:
         case 5:
         case 9:
         case 10:
         case 11:
         default:
            return this._reportWrongToken(parser, context, new JsonToken[]{JsonToken.VALUE_STRING, JsonToken.VALUE_NUMBER_INT, JsonToken.VALUE_NUMBER_FLOAT});
         case 6:
            String string = parser.getText().trim();
            if (string.length() == 0) {
               return null;
            } else {
               if (this._formatter == DateTimeFormatter.ISO_INSTANT
                  || this._formatter == DateTimeFormatter.ISO_OFFSET_DATE_TIME
                  || this._formatter == DateTimeFormatter.ISO_ZONED_DATE_TIME) {
                  int dots = this._countPeriods(string);
                  if (dots >= 0) {
                     try {
                        if (dots == 0) {
                           return this._fromLong(context, Long.parseLong(string));
                        }

                        if (dots == 1) {
                           return this._fromDecimal(context, new BigDecimal(string));
                        }
                     } catch (NumberFormatException var7) {
                     }
                  }

                  string = this.replaceZeroOffsetAsZIfNecessary(string);
               }

               T value;
               try {
                  TemporalAccessor acc = this._formatter.parse(string);
                  value = this.parsedToValue.apply(acc);
                  if (this.shouldAdjustToContextTimezone(context)) {
                     return this.adjust.apply(value, this.getZone(context));
                  }
               } catch (DateTimeException var6) {
                  value = this._rethrowDateTimeException(parser, context, var6, string);
               }

               return value;
            }
         case 7:
            return this._fromLong(context, parser.getLongValue());
         case 8:
            return this._fromDecimal(context, parser.getDecimalValue());
         case 12:
            return (T)parser.getEmbeddedObject();
      }
   }

   @Override
   public JsonDeserializer<T> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
      InstantDeserializer<T> deserializer = (InstantDeserializer<T>)super.createContextual(ctxt, property);
      if (deserializer != this) {
         JsonFormat.Value val = this.findFormatOverrides(ctxt, property, this.handledType());
         if (val != null) {
            return new InstantDeserializer<>(deserializer, val.getFeature(JsonFormat.Feature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE));
         }
      }

      return this;
   }

   protected boolean shouldAdjustToContextTimezone(DeserializationContext context) {
      return this._adjustToContextTZOverride != null
         ? this._adjustToContextTZOverride
         : context.isEnabled(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
   }

   protected int _countPeriods(String str) {
      int commas = 0;
      int i = 0;

      for (int end = str.length(); i < end; i++) {
         int ch = str.charAt(i);
         if (ch < 48 || ch > 57) {
            if (ch != 46) {
               return -1;
            }

            commas++;
         }
      }

      return commas;
   }

   protected T _fromLong(DeserializationContext context, long timestamp) {
      return context.isEnabled(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS)
         ? this.fromNanoseconds.apply(new InstantDeserializer.FromDecimalArguments(timestamp, 0, this.getZone(context)))
         : this.fromMilliseconds.apply(new InstantDeserializer.FromIntegerArguments(timestamp, this.getZone(context)));
   }

   protected T _fromDecimal(DeserializationContext context, BigDecimal value) {
      long seconds = value.longValue();
      int nanoseconds = DecimalUtils.extractNanosecondDecimal(value, seconds);
      return this.fromNanoseconds.apply(new InstantDeserializer.FromDecimalArguments(seconds, nanoseconds, this.getZone(context)));
   }

   private ZoneId getZone(DeserializationContext context) {
      return this._valueClass == Instant.class ? null : context.getTimeZone().toZoneId();
   }

   private String replaceZeroOffsetAsZIfNecessary(String text) {
      return this.replaceZeroOffsetAsZ ? ISO8601_UTC_ZERO_OFFSET_SUFFIX_REGEX.matcher(text).replaceFirst("Z") : text;
   }

   public static class FromDecimalArguments {
      public final long integer;
      public final int fraction;
      public final ZoneId zoneId;

      private FromDecimalArguments(long integer, int fraction, ZoneId zoneId) {
         this.integer = integer;
         this.fraction = fraction;
         this.zoneId = zoneId;
      }
   }

   public static class FromIntegerArguments {
      public final long value;
      public final ZoneId zoneId;

      private FromIntegerArguments(long value, ZoneId zoneId) {
         this.value = value;
         this.zoneId = zoneId;
      }
   }
}

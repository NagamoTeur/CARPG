package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.deser.std;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.TimeZone;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.annotation.JsonFormat;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonParser;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonToken;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.BeanProperty;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.DeserializationContext;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JsonDeserializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JsonMappingException;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.deser.ContextualDeserializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.util.ClassUtil;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.util.StdDateFormat;

public class DateDeserializers {
   private static final HashSet<String> _classNames = new HashSet<>();

   public static JsonDeserializer<?> find(Class<?> rawType, String clsName) {
      if (_classNames.contains(clsName)) {
         if (rawType == Calendar.class) {
            return new DateDeserializers.CalendarDeserializer();
         }

         if (rawType == Date.class) {
            return DateDeserializers.DateDeserializer.instance;
         }

         if (rawType == java.sql.Date.class) {
            return new DateDeserializers.SqlDateDeserializer();
         }

         if (rawType == Timestamp.class) {
            return new DateDeserializers.TimestampDeserializer();
         }

         if (rawType == GregorianCalendar.class) {
            return new DateDeserializers.CalendarDeserializer(GregorianCalendar.class);
         }
      }

      return null;
   }

   static {
      Class<?>[] numberTypes = new Class[]{Calendar.class, GregorianCalendar.class, java.sql.Date.class, Date.class, Timestamp.class};

      for (Class<?> cls : numberTypes) {
         _classNames.add(cls.getName());
      }
   }

   @JacksonStdImpl
   public static class CalendarDeserializer extends DateDeserializers.DateBasedDeserializer<Calendar> {
      protected final Constructor<Calendar> _defaultCtor;

      public CalendarDeserializer() {
         super(Calendar.class);
         this._defaultCtor = null;
      }

      public CalendarDeserializer(Class<? extends Calendar> cc) {
         super(cc);
         this._defaultCtor = ClassUtil.findConstructor(cc, false);
      }

      public CalendarDeserializer(DateDeserializers.CalendarDeserializer src, DateFormat df, String formatString) {
         super(src, df, formatString);
         this._defaultCtor = src._defaultCtor;
      }

      protected DateDeserializers.CalendarDeserializer withDateFormat(DateFormat df, String formatString) {
         return new DateDeserializers.CalendarDeserializer(this, df, formatString);
      }

      public Calendar deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
         Date d = this._parseDate(p, ctxt);
         if (d == null) {
            return null;
         } else if (this._defaultCtor == null) {
            return ctxt.constructCalendar(d);
         } else {
            try {
               Calendar c = this._defaultCtor.newInstance();
               c.setTimeInMillis(d.getTime());
               TimeZone tz = ctxt.getTimeZone();
               if (tz != null) {
                  c.setTimeZone(tz);
               }

               return c;
            } catch (Exception var6) {
               return (Calendar)ctxt.handleInstantiationProblem(this.handledType(), d, var6);
            }
         }
      }
   }

   protected abstract static class DateBasedDeserializer<T> extends StdScalarDeserializer<T> implements ContextualDeserializer {
      protected final DateFormat _customFormat;
      protected final String _formatString;

      protected DateBasedDeserializer(Class<?> clz) {
         super(clz);
         this._customFormat = null;
         this._formatString = null;
      }

      protected DateBasedDeserializer(DateDeserializers.DateBasedDeserializer<T> base, DateFormat format, String formatStr) {
         super(base._valueClass);
         this._customFormat = format;
         this._formatString = formatStr;
      }

      protected abstract DateDeserializers.DateBasedDeserializer<T> withDateFormat(DateFormat var1, String var2);

      @Override
      public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
         JsonFormat.Value format = this.findFormatOverrides(ctxt, property, this.handledType());
         if (format != null) {
            TimeZone tz = format.getTimeZone();
            Boolean lenient = format.getLenient();
            if (format.hasPattern()) {
               String pattern = format.getPattern();
               Locale loc = format.hasLocale() ? format.getLocale() : ctxt.getLocale();
               SimpleDateFormat df = new SimpleDateFormat(pattern, loc);
               if (tz == null) {
                  tz = ctxt.getTimeZone();
               }

               df.setTimeZone(tz);
               if (lenient != null) {
                  df.setLenient(lenient);
               }

               return this.withDateFormat(df, pattern);
            }

            if (tz != null) {
               DateFormat dfx = ctxt.getConfig().getDateFormat();
               if (dfx.getClass() == StdDateFormat.class) {
                  Locale locx = format.hasLocale() ? format.getLocale() : ctxt.getLocale();
                  StdDateFormat std = (StdDateFormat)dfx;
                  std = std.withTimeZone(tz);
                  std = std.withLocale(locx);
                  if (lenient != null) {
                     std = std.withLenient(lenient);
                  }

                  dfx = std;
               } else {
                  dfx = (DateFormat)dfx.clone();
                  dfx.setTimeZone(tz);
                  if (lenient != null) {
                     dfx.setLenient(lenient);
                  }
               }

               return this.withDateFormat(dfx, this._formatString);
            }

            if (lenient != null) {
               DateFormat dfx = ctxt.getConfig().getDateFormat();
               String patternx = this._formatString;
               if (dfx.getClass() == StdDateFormat.class) {
                  StdDateFormat std = (StdDateFormat)dfx;
                  std = std.withLenient(lenient);
                  dfx = std;
                  patternx = std.toPattern();
               } else {
                  dfx = (DateFormat)dfx.clone();
                  dfx.setLenient(lenient);
                  if (dfx instanceof SimpleDateFormat) {
                     ((SimpleDateFormat)dfx).toPattern();
                  }
               }

               if (patternx == null) {
                  patternx = "[unknown]";
               }

               return this.withDateFormat(dfx, patternx);
            }
         }

         return this;
      }

      @Override
      protected Date _parseDate(JsonParser p, DeserializationContext ctxt) throws IOException {
         if (this._customFormat != null && p.hasToken(JsonToken.VALUE_STRING)) {
            String str = p.getText().trim();
            if (str.length() == 0) {
               return (Date)this.getEmptyValue(ctxt);
            } else {
               synchronized (this._customFormat) {
                  Date var10000;
                  try {
                     var10000 = this._customFormat.parse(str);
                  } catch (ParseException var7) {
                     return (Date)ctxt.handleWeirdStringValue(this.handledType(), str, "expected format \"%s\"", this._formatString);
                  }

                  return var10000;
               }
            }
         } else {
            return super._parseDate(p, ctxt);
         }
      }
   }

   @JacksonStdImpl
   public static class DateDeserializer extends DateDeserializers.DateBasedDeserializer<Date> {
      public static final DateDeserializers.DateDeserializer instance = new DateDeserializers.DateDeserializer();

      public DateDeserializer() {
         super(Date.class);
      }

      public DateDeserializer(DateDeserializers.DateDeserializer base, DateFormat df, String formatString) {
         super(base, df, formatString);
      }

      protected DateDeserializers.DateDeserializer withDateFormat(DateFormat df, String formatString) {
         return new DateDeserializers.DateDeserializer(this, df, formatString);
      }

      public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
         return this._parseDate(p, ctxt);
      }
   }

   public static class SqlDateDeserializer extends DateDeserializers.DateBasedDeserializer<java.sql.Date> {
      public SqlDateDeserializer() {
         super(java.sql.Date.class);
      }

      public SqlDateDeserializer(DateDeserializers.SqlDateDeserializer src, DateFormat df, String formatString) {
         super(src, df, formatString);
      }

      protected DateDeserializers.SqlDateDeserializer withDateFormat(DateFormat df, String formatString) {
         return new DateDeserializers.SqlDateDeserializer(this, df, formatString);
      }

      public java.sql.Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
         Date d = this._parseDate(p, ctxt);
         return d == null ? null : new java.sql.Date(d.getTime());
      }
   }

   public static class TimestampDeserializer extends DateDeserializers.DateBasedDeserializer<Timestamp> {
      public TimestampDeserializer() {
         super(Timestamp.class);
      }

      public TimestampDeserializer(DateDeserializers.TimestampDeserializer src, DateFormat df, String formatString) {
         super(src, df, formatString);
      }

      protected DateDeserializers.TimestampDeserializer withDateFormat(DateFormat df, String formatString) {
         return new DateDeserializers.TimestampDeserializer(this, df, formatString);
      }

      public Timestamp deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
         Date d = this._parseDate(p, ctxt);
         return d == null ? null : new Timestamp(d.getTime());
      }
   }
}

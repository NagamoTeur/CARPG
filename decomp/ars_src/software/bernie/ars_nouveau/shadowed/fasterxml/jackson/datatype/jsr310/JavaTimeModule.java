package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.BeanDescription;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.DeserializationConfig;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JavaType;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.Module;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.deser.ValueInstantiator;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.deser.ValueInstantiators;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.deser.std.StdValueInstantiator;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.introspect.AnnotatedClass;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.introspect.AnnotatedClassResolver;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.module.SimpleModule;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.deser.DurationDeserializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.deser.JSR310StringParsableDeserializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.deser.MonthDayDeserializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.deser.OffsetTimeDeserializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.deser.YearDeserializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.deser.YearMonthDeserializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.deser.key.DurationKeyDeserializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.deser.key.InstantKeyDeserializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.deser.key.LocalDateKeyDeserializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.deser.key.LocalDateTimeKeyDeserializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.deser.key.LocalTimeKeyDeserializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.deser.key.MonthDayKeyDeserializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.deser.key.OffsetDateTimeKeyDeserializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.deser.key.OffsetTimeKeyDeserializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.deser.key.PeriodKeyDeserializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.deser.key.YearKeyDeserializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.deser.key.YearMothKeyDeserializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.deser.key.ZoneIdKeyDeserializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.deser.key.ZoneOffsetKeyDeserializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.deser.key.ZonedDateTimeKeyDeserializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.ser.DurationSerializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.ser.MonthDaySerializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.ser.OffsetTimeSerializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.ser.YearMonthSerializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.ser.YearSerializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.datatype.jsr310.ser.key.ZonedDateTimeKeySerializer;

public final class JavaTimeModule extends SimpleModule {
   private static final long serialVersionUID = 1L;

   public JavaTimeModule() {
      super(PackageVersion.VERSION);
      this.addDeserializer(Instant.class, InstantDeserializer.INSTANT);
      this.addDeserializer(OffsetDateTime.class, InstantDeserializer.OFFSET_DATE_TIME);
      this.addDeserializer(ZonedDateTime.class, InstantDeserializer.ZONED_DATE_TIME);
      this.addDeserializer(Duration.class, DurationDeserializer.INSTANCE);
      this.addDeserializer(LocalDateTime.class, LocalDateTimeDeserializer.INSTANCE);
      this.addDeserializer(LocalDate.class, LocalDateDeserializer.INSTANCE);
      this.addDeserializer(LocalTime.class, LocalTimeDeserializer.INSTANCE);
      this.addDeserializer(MonthDay.class, MonthDayDeserializer.INSTANCE);
      this.addDeserializer(OffsetTime.class, OffsetTimeDeserializer.INSTANCE);
      this.addDeserializer(Period.class, JSR310StringParsableDeserializer.PERIOD);
      this.addDeserializer(Year.class, YearDeserializer.INSTANCE);
      this.addDeserializer(YearMonth.class, YearMonthDeserializer.INSTANCE);
      this.addDeserializer(ZoneId.class, JSR310StringParsableDeserializer.ZONE_ID);
      this.addDeserializer(ZoneOffset.class, JSR310StringParsableDeserializer.ZONE_OFFSET);
      this.addSerializer(Duration.class, DurationSerializer.INSTANCE);
      this.addSerializer(Instant.class, InstantSerializer.INSTANCE);
      this.addSerializer(LocalDateTime.class, LocalDateTimeSerializer.INSTANCE);
      this.addSerializer(LocalDate.class, LocalDateSerializer.INSTANCE);
      this.addSerializer(LocalTime.class, LocalTimeSerializer.INSTANCE);
      this.addSerializer(MonthDay.class, MonthDaySerializer.INSTANCE);
      this.addSerializer(OffsetDateTime.class, OffsetDateTimeSerializer.INSTANCE);
      this.addSerializer(OffsetTime.class, OffsetTimeSerializer.INSTANCE);
      this.addSerializer(Period.class, new ToStringSerializer(Period.class));
      this.addSerializer(Year.class, YearSerializer.INSTANCE);
      this.addSerializer(YearMonth.class, YearMonthSerializer.INSTANCE);
      this.addSerializer(ZonedDateTime.class, ZonedDateTimeSerializer.INSTANCE);
      this.addSerializer(ZoneId.class, new ToStringSerializer(ZoneId.class));
      this.addSerializer(ZoneOffset.class, new ToStringSerializer(ZoneOffset.class));
      this.addKeySerializer(ZonedDateTime.class, ZonedDateTimeKeySerializer.INSTANCE);
      this.addKeyDeserializer(Duration.class, DurationKeyDeserializer.INSTANCE);
      this.addKeyDeserializer(Instant.class, InstantKeyDeserializer.INSTANCE);
      this.addKeyDeserializer(LocalDateTime.class, LocalDateTimeKeyDeserializer.INSTANCE);
      this.addKeyDeserializer(LocalDate.class, LocalDateKeyDeserializer.INSTANCE);
      this.addKeyDeserializer(LocalTime.class, LocalTimeKeyDeserializer.INSTANCE);
      this.addKeyDeserializer(MonthDay.class, MonthDayKeyDeserializer.INSTANCE);
      this.addKeyDeserializer(OffsetDateTime.class, OffsetDateTimeKeyDeserializer.INSTANCE);
      this.addKeyDeserializer(OffsetTime.class, OffsetTimeKeyDeserializer.INSTANCE);
      this.addKeyDeserializer(Period.class, PeriodKeyDeserializer.INSTANCE);
      this.addKeyDeserializer(Year.class, YearKeyDeserializer.INSTANCE);
      this.addKeyDeserializer(YearMonth.class, YearMothKeyDeserializer.INSTANCE);
      this.addKeyDeserializer(ZonedDateTime.class, ZonedDateTimeKeyDeserializer.INSTANCE);
      this.addKeyDeserializer(ZoneId.class, ZoneIdKeyDeserializer.INSTANCE);
      this.addKeyDeserializer(ZoneOffset.class, ZoneOffsetKeyDeserializer.INSTANCE);
   }

   @Override
   public void setupModule(Module.SetupContext context) {
      super.setupModule(context);
      context.addValueInstantiators(new ValueInstantiators.Base() {
         @Override
         public ValueInstantiator findValueInstantiator(DeserializationConfig config, BeanDescription beanDesc, ValueInstantiator defaultInstantiator) {
            JavaType type = beanDesc.getType();
            Class<?> raw = type.getRawClass();
            if (ZoneId.class.isAssignableFrom(raw) && defaultInstantiator instanceof StdValueInstantiator) {
               StdValueInstantiator inst = (StdValueInstantiator)defaultInstantiator;
               AnnotatedClass ac;
               if (raw == ZoneId.class) {
                  ac = beanDesc.getClassInfo();
               } else {
                  ac = AnnotatedClassResolver.resolve(config, config.constructType(ZoneId.class), config);
               }

               if (!inst.canCreateFromString()) {
                  AnnotatedMethod factory = JavaTimeModule.this._findFactory(ac, "of", String.class);
                  if (factory != null) {
                     inst.configureFromStringCreator(factory);
                  }
               }
            }

            return defaultInstantiator;
         }
      });
   }

   protected AnnotatedMethod _findFactory(AnnotatedClass cls, String name, Class<?>... argTypes) {
      int argCount = argTypes.length;

      for (AnnotatedMethod method : cls.getFactoryMethods()) {
         if (name.equals(method.getName()) && method.getParameterCount() == argCount) {
            for (int i = 0; i < argCount; i++) {
               Class<?> argType = method.getParameter(i).getRawType();
               if (!argType.isAssignableFrom(argTypes[i])) {
               }
            }

            return method;
         }
      }

      return null;
   }
}

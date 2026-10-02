package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.introspect;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.annotation.JsonFormat;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.annotation.JsonInclude;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.AnnotationIntrospector;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.BeanProperty;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.PropertyMetadata;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.PropertyName;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.cfg.MapperConfig;

public abstract class ConcreteBeanPropertyBase implements BeanProperty, Serializable {
   private static final long serialVersionUID = 1L;
   protected final PropertyMetadata _metadata;
   protected transient JsonFormat.Value _propertyFormat;
   protected transient List<PropertyName> _aliases;

   protected ConcreteBeanPropertyBase(PropertyMetadata md) {
      this._metadata = md == null ? PropertyMetadata.STD_REQUIRED_OR_OPTIONAL : md;
   }

   protected ConcreteBeanPropertyBase(ConcreteBeanPropertyBase src) {
      this._metadata = src._metadata;
      this._propertyFormat = src._propertyFormat;
   }

   @Override
   public boolean isRequired() {
      return this._metadata.isRequired();
   }

   @Override
   public PropertyMetadata getMetadata() {
      return this._metadata;
   }

   @Override
   public boolean isVirtual() {
      return false;
   }

   @Deprecated
   @Override
   public final JsonFormat.Value findFormatOverrides(AnnotationIntrospector intr) {
      JsonFormat.Value f = null;
      if (intr != null) {
         AnnotatedMember member = this.getMember();
         if (member != null) {
            f = intr.findFormat(member);
         }
      }

      if (f == null) {
         f = EMPTY_FORMAT;
      }

      return f;
   }

   @Override
   public JsonFormat.Value findPropertyFormat(MapperConfig<?> config, Class<?> baseType) {
      JsonFormat.Value v = this._propertyFormat;
      if (v == null) {
         JsonFormat.Value v1 = config.getDefaultPropertyFormat(baseType);
         JsonFormat.Value v2 = null;
         AnnotationIntrospector intr = config.getAnnotationIntrospector();
         if (intr != null) {
            AnnotatedMember member = this.getMember();
            if (member != null) {
               v2 = intr.findFormat(member);
            }
         }

         if (v1 == null) {
            v = v2 == null ? EMPTY_FORMAT : v2;
         } else {
            v = v2 == null ? v1 : v1.withOverrides(v2);
         }

         this._propertyFormat = v;
      }

      return v;
   }

   @Override
   public JsonInclude.Value findPropertyInclusion(MapperConfig<?> config, Class<?> baseType) {
      AnnotationIntrospector intr = config.getAnnotationIntrospector();
      AnnotatedMember member = this.getMember();
      if (member == null) {
         JsonInclude.Value def = config.getDefaultPropertyInclusion(baseType);
         return def;
      } else {
         JsonInclude.Value v0 = config.getDefaultInclusion(baseType, member.getRawType());
         if (intr == null) {
            return v0;
         } else {
            JsonInclude.Value v = intr.findPropertyInclusion(member);
            return v0 == null ? v : v0.withOverrides(v);
         }
      }
   }

   @Override
   public List<PropertyName> findAliases(MapperConfig<?> config) {
      List<PropertyName> aliases = this._aliases;
      if (aliases == null) {
         AnnotationIntrospector intr = config.getAnnotationIntrospector();
         if (intr != null) {
            aliases = intr.findPropertyAliases(this.getMember());
         }

         if (aliases == null) {
            aliases = Collections.emptyList();
         }

         this._aliases = aliases;
      }

      return aliases;
   }
}

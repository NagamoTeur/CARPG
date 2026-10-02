package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.jsontype;

import java.util.Collection;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.AnnotationIntrospector;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JavaType;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.cfg.MapperConfig;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.introspect.AnnotatedClass;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMember;

public abstract class SubtypeResolver {
   public abstract void registerSubtypes(NamedType... var1);

   public abstract void registerSubtypes(Class<?>... var1);

   public abstract void registerSubtypes(Collection<Class<?>> var1);

   public Collection<NamedType> collectAndResolveSubtypesByClass(MapperConfig<?> config, AnnotatedMember property, JavaType baseType) {
      return this.collectAndResolveSubtypes(property, config, config.getAnnotationIntrospector(), baseType);
   }

   public Collection<NamedType> collectAndResolveSubtypesByClass(MapperConfig<?> config, AnnotatedClass baseType) {
      return this.collectAndResolveSubtypes(baseType, config, config.getAnnotationIntrospector());
   }

   public Collection<NamedType> collectAndResolveSubtypesByTypeId(MapperConfig<?> config, AnnotatedMember property, JavaType baseType) {
      return this.collectAndResolveSubtypes(property, config, config.getAnnotationIntrospector(), baseType);
   }

   public Collection<NamedType> collectAndResolveSubtypesByTypeId(MapperConfig<?> config, AnnotatedClass baseType) {
      return this.collectAndResolveSubtypes(baseType, config, config.getAnnotationIntrospector());
   }

   @Deprecated
   public Collection<NamedType> collectAndResolveSubtypes(AnnotatedMember property, MapperConfig<?> config, AnnotationIntrospector ai, JavaType baseType) {
      return this.collectAndResolveSubtypesByClass(config, property, baseType);
   }

   @Deprecated
   public Collection<NamedType> collectAndResolveSubtypes(AnnotatedClass baseType, MapperConfig<?> config, AnnotationIntrospector ai) {
      return this.collectAndResolveSubtypesByClass(config, baseType);
   }
}

package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.ext;

import java.util.logging.Logger;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JsonDeserializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JsonSerializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.PropertyName;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.introspect.Annotated;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.util.ClassUtil;

public abstract class Java7Support {
   private static final Java7Support IMPL;

   public static Java7Support instance() {
      return IMPL;
   }

   public abstract Boolean findTransient(Annotated var1);

   public abstract Boolean hasCreatorAnnotation(Annotated var1);

   public abstract PropertyName findConstructorName(AnnotatedParameter var1);

   public abstract Class<?> getClassJavaNioFilePath();

   public abstract JsonDeserializer<?> getDeserializerForJavaNioFilePath(Class<?> var1);

   public abstract JsonSerializer<?> getSerializerForJavaNioFilePath(Class<?> var1);

   static {
      Java7Support impl = null;

      try {
         Class<?> cls = Class.forName("software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.ext.Java7SupportImpl");
         impl = ClassUtil.createInstance((Class<Java7Support>)cls, false);
      } catch (Throwable var2) {
         Logger.getLogger(Java7Support.class.getName()).warning("Unable to load JDK7 types (annotations, java.nio.file.Path): no Java7 support added");
      }

      IMPL = impl;
   }
}

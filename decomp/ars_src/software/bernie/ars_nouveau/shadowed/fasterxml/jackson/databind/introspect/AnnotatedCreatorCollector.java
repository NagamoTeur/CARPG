package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.introspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.AnnotationIntrospector;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JavaType;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.util.ClassUtil;

final class AnnotatedCreatorCollector extends CollectorBase {
   private final TypeResolutionContext _typeContext;
   private AnnotatedConstructor _defaultConstructor;

   AnnotatedCreatorCollector(AnnotationIntrospector intr, TypeResolutionContext tc) {
      super(intr);
      this._typeContext = tc;
   }

   public static AnnotatedClass.Creators collectCreators(AnnotationIntrospector intr, TypeResolutionContext tc, JavaType type, Class<?> primaryMixIn) {
      return new AnnotatedCreatorCollector(intr, tc).collect(type, primaryMixIn);
   }

   AnnotatedClass.Creators collect(JavaType type, Class<?> primaryMixIn) {
      List<AnnotatedConstructor> constructors = this._findPotentialConstructors(type, primaryMixIn);
      List<AnnotatedMethod> factories = this._findPotentialFactories(type, primaryMixIn);
      if (this._intr != null) {
         if (this._defaultConstructor != null && this._intr.hasIgnoreMarker(this._defaultConstructor)) {
            this._defaultConstructor = null;
         }

         int i = constructors.size();

         while (--i >= 0) {
            if (this._intr.hasIgnoreMarker(constructors.get(i))) {
               constructors.remove(i);
            }
         }

         i = factories.size();

         while (--i >= 0) {
            if (this._intr.hasIgnoreMarker(factories.get(i))) {
               factories.remove(i);
            }
         }
      }

      return new AnnotatedClass.Creators(this._defaultConstructor, constructors, factories);
   }

   private List<AnnotatedConstructor> _findPotentialConstructors(JavaType type, Class<?> primaryMixIn) {
      ClassUtil.Ctor defaultCtor = null;
      List<ClassUtil.Ctor> ctors = null;
      if (!type.isEnumType()) {
         ClassUtil.Ctor[] declaredCtors = ClassUtil.getConstructors(type.getRawClass());

         for (ClassUtil.Ctor ctor : declaredCtors) {
            if (isIncludableConstructor(ctor.getConstructor())) {
               if (ctor.getParamCount() == 0) {
                  defaultCtor = ctor;
               } else {
                  if (ctors == null) {
                     ctors = new ArrayList<>();
                  }

                  ctors.add(ctor);
               }
            }
         }
      }

      List<AnnotatedConstructor> result;
      int ctorCount;
      if (ctors == null) {
         result = Collections.emptyList();
         if (defaultCtor == null) {
            return result;
         }

         ctorCount = 0;
      } else {
         ctorCount = ctors.size();
         result = new ArrayList<>(ctorCount);

         for (int i = 0; i < ctorCount; i++) {
            result.add(null);
         }
      }

      if (primaryMixIn != null) {
         MemberKey[] ctorKeys = null;

         for (ClassUtil.Ctor mixinCtor : ClassUtil.getConstructors(primaryMixIn)) {
            if (mixinCtor.getParamCount() == 0) {
               if (defaultCtor != null) {
                  this._defaultConstructor = this.constructDefaultConstructor(defaultCtor, mixinCtor);
                  defaultCtor = null;
               }
            } else if (ctors != null) {
               if (ctorKeys == null) {
                  ctorKeys = new MemberKey[ctorCount];

                  for (int i = 0; i < ctorCount; i++) {
                     ctorKeys[i] = new MemberKey(ctors.get(i).getConstructor());
                  }
               }

               MemberKey key = new MemberKey(mixinCtor.getConstructor());

               for (int i = 0; i < ctorCount; i++) {
                  if (key.equals(ctorKeys[i])) {
                     result.set(i, this.constructNonDefaultConstructor(ctors.get(i), mixinCtor));
                     break;
                  }
               }
            }
         }
      }

      if (defaultCtor != null) {
         this._defaultConstructor = this.constructDefaultConstructor(defaultCtor, null);
      }

      for (int ix = 0; ix < ctorCount; ix++) {
         AnnotatedConstructor ctorx = result.get(ix);
         if (ctorx == null) {
            result.set(ix, this.constructNonDefaultConstructor(ctors.get(ix), null));
         }
      }

      return result;
   }

   private List<AnnotatedMethod> _findPotentialFactories(JavaType type, Class<?> primaryMixIn) {
      List<Method> candidates = null;

      for (Method m : ClassUtil.getClassMethods(type.getRawClass())) {
         if (Modifier.isStatic(m.getModifiers())) {
            if (candidates == null) {
               candidates = new ArrayList<>();
            }

            candidates.add(m);
         }
      }

      if (candidates == null) {
         return Collections.emptyList();
      } else {
         int factoryCount = candidates.size();
         List<AnnotatedMethod> result = new ArrayList<>(factoryCount);

         for (int i = 0; i < factoryCount; i++) {
            result.add(null);
         }

         if (primaryMixIn != null) {
            MemberKey[] methodKeys = null;

            for (Method mixinFactory : ClassUtil.getDeclaredMethods(primaryMixIn)) {
               if (Modifier.isStatic(mixinFactory.getModifiers())) {
                  if (methodKeys == null) {
                     methodKeys = new MemberKey[factoryCount];

                     for (int i = 0; i < factoryCount; i++) {
                        methodKeys[i] = new MemberKey(candidates.get(i));
                     }
                  }

                  MemberKey key = new MemberKey(mixinFactory);

                  for (int i = 0; i < factoryCount; i++) {
                     if (key.equals(methodKeys[i])) {
                        result.set(i, this.constructFactoryCreator(candidates.get(i), mixinFactory));
                        break;
                     }
                  }
               }
            }
         }

         for (int ix = 0; ix < factoryCount; ix++) {
            AnnotatedMethod factory = result.get(ix);
            if (factory == null) {
               result.set(ix, this.constructFactoryCreator(candidates.get(ix), null));
            }
         }

         return result;
      }
   }

   protected AnnotatedConstructor constructDefaultConstructor(ClassUtil.Ctor ctor, ClassUtil.Ctor mixin) {
      return this._intr == null
         ? new AnnotatedConstructor(this._typeContext, ctor.getConstructor(), _emptyAnnotationMap(), NO_ANNOTATION_MAPS)
         : new AnnotatedConstructor(
            this._typeContext,
            ctor.getConstructor(),
            this.collectAnnotations(ctor, mixin),
            this.collectAnnotations(
               ctor.getConstructor().getParameterAnnotations(), mixin == null ? (Annotation[][])null : mixin.getConstructor().getParameterAnnotations()
            )
         );
   }

   protected AnnotatedConstructor constructNonDefaultConstructor(ClassUtil.Ctor ctor, ClassUtil.Ctor mixin) {
      int paramCount = ctor.getParamCount();
      if (this._intr == null) {
         return new AnnotatedConstructor(this._typeContext, ctor.getConstructor(), _emptyAnnotationMap(), _emptyAnnotationMaps(paramCount));
      } else if (paramCount == 0) {
         return new AnnotatedConstructor(this._typeContext, ctor.getConstructor(), this.collectAnnotations(ctor, mixin), NO_ANNOTATION_MAPS);
      } else {
         Annotation[][] paramAnns = ctor.getParameterAnnotations();
         AnnotationMap[] resolvedAnnotations;
         if (paramCount != paramAnns.length) {
            resolvedAnnotations = null;
            Class<?> dc = ctor.getDeclaringClass();
            if (dc.isEnum() && paramCount == paramAnns.length + 2) {
               Annotation[][] old = paramAnns;
               paramAnns = new Annotation[paramAnns.length + 2][];
               System.arraycopy(old, 0, paramAnns, 2, old.length);
               resolvedAnnotations = this.collectAnnotations(paramAnns, (Annotation[][])null);
            } else if (dc.isMemberClass() && paramCount == paramAnns.length + 1) {
               Annotation[][] old = paramAnns;
               paramAnns = new Annotation[paramAnns.length + 1][];
               System.arraycopy(old, 0, paramAnns, 1, old.length);
               paramAnns[0] = NO_ANNOTATIONS;
               resolvedAnnotations = this.collectAnnotations(paramAnns, (Annotation[][])null);
            }

            if (resolvedAnnotations == null) {
               throw new IllegalStateException(
                  String.format(
                     "Internal error: constructor for %s has mismatch: %d parameters; %d sets of annotations",
                     ctor.getDeclaringClass().getName(),
                     paramCount,
                     paramAnns.length
                  )
               );
            }
         } else {
            resolvedAnnotations = this.collectAnnotations(paramAnns, mixin == null ? (Annotation[][])null : mixin.getParameterAnnotations());
         }

         return new AnnotatedConstructor(this._typeContext, ctor.getConstructor(), this.collectAnnotations(ctor, mixin), resolvedAnnotations);
      }
   }

   protected AnnotatedMethod constructFactoryCreator(Method m, Method mixin) {
      int paramCount = m.getParameterTypes().length;
      if (this._intr == null) {
         return new AnnotatedMethod(this._typeContext, m, _emptyAnnotationMap(), _emptyAnnotationMaps(paramCount));
      } else {
         return paramCount == 0
            ? new AnnotatedMethod(this._typeContext, m, this.collectAnnotations(m, mixin), NO_ANNOTATION_MAPS)
            : new AnnotatedMethod(
               this._typeContext,
               m,
               this.collectAnnotations(m, mixin),
               this.collectAnnotations(m.getParameterAnnotations(), mixin == null ? (Annotation[][])null : mixin.getParameterAnnotations())
            );
      }
   }

   private AnnotationMap[] collectAnnotations(Annotation[][] mainAnns, Annotation[][] mixinAnns) {
      int count = mainAnns.length;
      AnnotationMap[] result = new AnnotationMap[count];

      for (int i = 0; i < count; i++) {
         AnnotationCollector c = this.collectAnnotations(AnnotationCollector.emptyCollector(), mainAnns[i]);
         if (mixinAnns != null) {
            c = this.collectAnnotations(c, mixinAnns[i]);
         }

         result[i] = c.asAnnotationMap();
      }

      return result;
   }

   private AnnotationMap collectAnnotations(ClassUtil.Ctor main, ClassUtil.Ctor mixin) {
      AnnotationCollector c = this.collectAnnotations(main.getConstructor().getDeclaredAnnotations());
      if (mixin != null) {
         c = this.collectAnnotations(c, mixin.getConstructor().getDeclaredAnnotations());
      }

      return c.asAnnotationMap();
   }

   private final AnnotationMap collectAnnotations(AnnotatedElement main, AnnotatedElement mixin) {
      AnnotationCollector c = this.collectAnnotations(main.getDeclaredAnnotations());
      if (mixin != null) {
         c = this.collectAnnotations(c, mixin.getDeclaredAnnotations());
      }

      return c.asAnnotationMap();
   }

   private static boolean isIncludableConstructor(Constructor<?> c) {
      return !c.isSynthetic();
   }
}

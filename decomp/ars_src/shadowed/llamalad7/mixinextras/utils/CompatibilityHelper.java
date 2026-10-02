package shadowed.llamalad7.mixinextras.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.refmap.IMixinContext;

public class CompatibilityHelper {
   private static final Constructor<InvalidInjectionException> INVALID_INJECTION_EXCEPTION_CONSTRUCTOR = (Constructor<InvalidInjectionException>)Arrays.stream(
         InvalidInjectionException.class.getConstructors()
      )
      .filter(it -> {
         Class<?>[] parameters = it.getParameterTypes();
         return parameters.length == 2 && parameters[0].isAssignableFrom(InjectionInfo.class) && parameters[1] == String.class;
      })
      .findAny()
      .orElse(null);
   private static final Method INJECTION_INFO_GET_MIXIN_METHOD = Arrays.stream(InjectionInfo.class.getMethods())
      .filter(it -> it.getParameterTypes().length == 0 && it.getReturnType() == IMixinContext.class && it.getName().startsWith("get"))
      .findAny()
      .orElse(null);

   public static RuntimeException makeInvalidInjectionException(InjectionInfo info, String message) {
      try {
         return (RuntimeException)INVALID_INJECTION_EXCEPTION_CONSTRUCTOR.newInstance(info, message);
      } catch (Throwable var3) {
         throw new RuntimeException(var3);
      }
   }

   public static IMixinContext getMixin(InjectionInfo info) {
      try {
         return (IMixinContext)INJECTION_INFO_GET_MIXIN_METHOD.invoke(info);
      } catch (Throwable var2) {
         throw new RuntimeException(var2);
      }
   }
}

package shadowed.llamalad7.mixinextras;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.transformer.IMixinTransformer;
import org.spongepowered.asm.mixin.transformer.ext.Extensions;
import org.spongepowered.asm.mixin.transformer.ext.IExtension;
import shadowed.llamalad7.mixinextras.injector.ModifyExpressionValueInjectionInfo;
import shadowed.llamalad7.mixinextras.injector.ModifyReceiverInjectionInfo;
import shadowed.llamalad7.mixinextras.injector.ModifyReturnValueInjectionInfo;
import shadowed.llamalad7.mixinextras.injector.WrapWithConditionInjectionInfo;
import shadowed.llamalad7.mixinextras.injector.wrapoperation.WrapOperationApplicatorExtension;
import shadowed.llamalad7.mixinextras.injector.wrapoperation.WrapOperationInjectionInfo;

public class MixinExtrasBootstrap {
   private static boolean initialized = false;
   private static final String VERSION = "0.1.1";

   public static String getVersion() {
      return "0.1.1";
   }

   public static void init() {
      initialize(true);
   }

   static void initialize(boolean runtime) {
      if (!initialized) {
         initialized = true;
         InjectionInfo.register(ModifyExpressionValueInjectionInfo.class);
         InjectionInfo.register(ModifyReceiverInjectionInfo.class);
         InjectionInfo.register(ModifyReturnValueInjectionInfo.class);
         InjectionInfo.register(WrapOperationInjectionInfo.class);
         InjectionInfo.register(WrapWithConditionInjectionInfo.class);
         if (runtime) {
            registerExtension(new WrapOperationApplicatorExtension());
         }
      }
   }

   private static void registerExtension(IExtension extension) {
      IMixinTransformer transformer = (IMixinTransformer)MixinEnvironment.getDefaultEnvironment().getActiveTransformer();
      Extensions extensions = (Extensions)transformer.getExtensions();
      extensions.add(extension);

      try {
         Field activeExtensionsField = Extensions.class.getDeclaredField("activeExtensions");
         activeExtensionsField.setAccessible(true);
         List<IExtension> activeExtensions = new ArrayList<>((List)activeExtensionsField.get(extensions));
         activeExtensions.add(extension);
         activeExtensionsField.set(extensions, Collections.unmodifiableList(activeExtensions));
      } catch (IllegalAccessException | NoSuchFieldException var5) {
         throw new RuntimeException(String.format("Failed to inject extension %s. Please inform LlamaLad7!", extension), var5);
      }
   }
}

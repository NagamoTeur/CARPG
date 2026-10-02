package immersive_armors.cobalt.registration;

import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class Registration {
   private static Registration.Impl INSTANCE;

   public static <T> Supplier<T> register(Registry<? super T> registry, ResourceLocation id, Supplier<T> obj) {
      return INSTANCE.register(registry, id, obj);
   }

   public abstract static class Impl {
      protected Impl() {
         Registration.INSTANCE = this;
      }

      public abstract <T> Supplier<T> register(Registry<? super T> var1, ResourceLocation var2, Supplier<T> var3);

      public abstract CreativeModeTab itemGroup(ResourceLocation var1, Supplier<ItemStack> var2);
   }

   public static class ObjectBuilders {
      public static class ItemGroups {
         public static CreativeModeTab create(ResourceLocation id, Supplier<ItemStack> icon) {
            return Registration.INSTANCE.itemGroup(id, icon);
         }
      }
   }
}

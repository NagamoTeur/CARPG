package immersive_armors.forge.cobalt.registration;

import immersive_armors.cobalt.registration.Registration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.RegistryManager;

public class RegistrationImpl extends Registration.Impl {
   public static final RegistrationImpl IMPL = new RegistrationImpl();
   private final Map<String, RegistrationImpl.RegistryRepo> repos = new HashMap<>();

   public static void bootstrap() {
   }

   private RegistrationImpl.RegistryRepo getRepo(String namespace) {
      return this.repos.computeIfAbsent(namespace, RegistrationImpl.RegistryRepo::new);
   }

   @Override
   public <T> Supplier<T> register(Registry<? super T> registry, ResourceLocation id, Supplier<T> obj) {
      DeferredRegister reg = this.getRepo(id.m_135827_()).get(registry);
      return reg.register(id.m_135815_(), obj);
   }

   @Override
   public CreativeModeTab itemGroup(ResourceLocation id, Supplier<ItemStack> icon) {
      return new CreativeModeTab(CreativeModeTab.getGroupCountSafe(), String.format("%s.%s", id.m_135827_(), id.m_135815_())) {
         public ItemStack m_6976_() {
            return icon.get();
         }
      };
   }

   static class RegistryRepo {
      private final Set<ResourceLocation> skipped = new HashSet<>();
      private final Map<ResourceLocation, DeferredRegister<?>> registries = new HashMap<>();
      private final String namespace;

      public RegistryRepo(String namespace) {
         this.namespace = namespace;
      }

      public <T> DeferredRegister get(Registry<? super T> registry) {
         ResourceLocation id = registry.m_123023_().m_135782_();
         if (!this.registries.containsKey(id) && !this.skipped.contains(id)) {
            ForgeRegistry reg = RegistryManager.ACTIVE.getRegistry(id);
            if (reg == null) {
               this.skipped.add(id);
               return null;
            }

            DeferredRegister def = DeferredRegister.create(Objects.requireNonNull(reg, "Registry=" + id), this.namespace);
            def.register(FMLJavaModLoadingContext.get().getModEventBus());
            this.registries.put(id, def);
         }

         return this.registries.get(id);
      }
   }
}

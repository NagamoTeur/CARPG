package com.obscuria.aquamirae.registry;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AquamiraePotions {
   public static final DeferredRegister<Potion> REGISTRY = DeferredRegister.create(ForgeRegistries.POTIONS, "aquamirae");
   public static final RegistryObject<Potion> SPECTRAL_POTION = REGISTRY.register(
      "spectral_potion", () -> new Potion(new MobEffectInstance[]{new MobEffectInstance(MobEffects.f_19619_, 2400, 0, false, true)})
   );
   public static final RegistryObject<Potion> POTION_OF_TENACITY = REGISTRY.register(
      "potion_of_tenacity",
      () -> new Potion(
            new MobEffectInstance[]{
               new MobEffectInstance(MobEffects.f_19600_, 2400, 0, false, true),
               new MobEffectInstance(MobEffects.f_19617_, 2400, 2, false, true),
               new MobEffectInstance(MobEffects.f_19604_, 400, 1, false, true)
            }
         )
   );
}

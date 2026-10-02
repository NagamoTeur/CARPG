package com.bobmowzie.mowziesmobs.server.potion;

import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class PotionTypeHandler {
   public static final DeferredRegister<Potion> REG = DeferredRegister.create(ForgeRegistries.POTIONS, "mowziesmobs");
   public static final RegistryObject<Potion> POISON_RESIST = REG.register(
      "poison_resist", () -> new Potion("poison_resist", new MobEffectInstance[]{new MobEffectInstance((MobEffect)EffectHandler.POISON_RESIST.get(), 3600)})
   );
   public static final RegistryObject<Potion> LONG_POISON_RESIST = REG.register(
      "long_poison_resist",
      () -> new Potion("long_poison_resist", new MobEffectInstance[]{new MobEffectInstance((MobEffect)EffectHandler.POISON_RESIST.get(), 9600)})
   );

   private PotionTypeHandler() {
   }

   public static void init() {
      PotionBrewing.m_43513_(Potions.f_43602_, ItemHandler.NAGA_FANG, (Potion)POISON_RESIST.get());
      PotionBrewing.m_43513_((Potion)POISON_RESIST.get(), Items.f_42451_, (Potion)LONG_POISON_RESIST.get());
   }
}

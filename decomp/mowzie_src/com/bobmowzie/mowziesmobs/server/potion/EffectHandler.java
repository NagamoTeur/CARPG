package com.bobmowzie.mowziesmobs.server.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class EffectHandler {
   public static final DeferredRegister<MobEffect> REG = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, "mowziesmobs");
   public static final RegistryObject<EffectSunsBlessing> SUNS_BLESSING = REG.register("suns_blessing", () -> new EffectSunsBlessing());
   public static final RegistryObject<EffectGeomancy> GEOMANCY = REG.register("geomancy", () -> new EffectGeomancy());
   public static final RegistryObject<EffectFrozen> FROZEN = REG.register("frozen", () -> new EffectFrozen());
   public static final RegistryObject<EffectPoisonResist> POISON_RESIST = REG.register("poison_resist", () -> new EffectPoisonResist());
   public static final RegistryObject<EffectSunblock> SUNBLOCK = REG.register("sunblock", () -> new EffectSunblock());

   private EffectHandler() {
   }

   public static void addOrCombineEffect(LivingEntity entity, MobEffect effect, int duration, int amplifier, boolean ambient, boolean showParticles) {
      if (effect != null) {
         MobEffectInstance effectInst = entity.m_21124_(effect);
         MobEffectInstance newEffect = new MobEffectInstance(effect, duration, amplifier, ambient, showParticles);
         if (effectInst != null) {
            effectInst.m_19558_(newEffect);
         } else {
            entity.m_7292_(newEffect);
         }
      }
   }
}

package com.obscuria.aquamirae.common.effects;

import java.util.function.Consumer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraftforge.client.extensions.common.IClientMobEffectExtensions;
import org.jetbrains.annotations.NotNull;

public class HealthDecreaseMobEffect extends MobEffect {
   public HealthDecreaseMobEffect() {
      super(MobEffectCategory.NEUTRAL, -6750055);
      this.m_19472_(Attributes.f_22276_, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC", -0.05, Operation.MULTIPLY_TOTAL);
   }

   @NotNull
   public String m_19481_() {
      return "effect.aquamirae.health_decrease";
   }

   public boolean m_6584_(int duration, int amplifier) {
      return true;
   }

   public void m_6385_(@NotNull LivingEntity entity, @NotNull AttributeMap map, int level) {
      super.m_6385_(entity, map, level);
      if (entity.m_21223_() > entity.m_21233_()) {
         entity.m_21153_(entity.m_21233_());
      }
   }

   public void initializeClient(Consumer<IClientMobEffectExtensions> consumer) {
      consumer.accept(new IClientMobEffectExtensions() {
         public boolean isVisibleInGui(MobEffectInstance effect) {
            return false;
         }
      });
   }
}

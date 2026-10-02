package com.obscuria.aquamirae.common.effects;

import java.util.function.Consumer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraftforge.client.extensions.common.IClientMobEffectExtensions;
import org.jetbrains.annotations.NotNull;

public class ArmorDecreaseMobEffect extends MobEffect {
   public ArmorDecreaseMobEffect() {
      super(MobEffectCategory.NEUTRAL, -6750055);
      this.m_19472_(Attributes.f_22284_, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC", -0.1, Operation.MULTIPLY_TOTAL);
   }

   @NotNull
   public String m_19481_() {
      return "effect.aquamirae.armor_decrease";
   }

   public boolean m_6584_(int duration, int amplifier) {
      return true;
   }

   public void initializeClient(Consumer<IClientMobEffectExtensions> consumer) {
      consumer.accept(new IClientMobEffectExtensions() {
         public boolean isVisibleInGui(MobEffectInstance effect) {
            return false;
         }
      });
   }
}

package shadows.apotheosis.mixin;

import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import shadows.apotheosis.core.attributeslib.asm.ALCombatRules;
import shadows.apotheosis.core.mobfx.api.MFEffects;

@Mixin({LivingEntity.class})
public abstract class LivingEntityMixin extends Entity {
   public LivingEntityMixin(EntityType<?> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
   }

   @Redirect(
      at = @At(
         value = "INVOKE",
         target = "Ljava/lang/Math;max(FF)F"
      ),
      method = {"getDamageAfterMagicAbsorb(Lnet/minecraft/world/damagesource/DamageSource;F)F"}
   )
   public float apoth_sunderingApplyEffect(float value, float max, DamageSource source, float damage) {
      if (this.m_21023_((MobEffect)MFEffects.SUNDERING.get()) && source != DamageSource.f_19317_) {
         int level = this.m_21124_((MobEffect)MFEffects.SUNDERING.get()).m_19564_() + 1;
         value += damage * (float)level * 0.2F;
      }

      return Math.max(value, max);
   }

   @Redirect(
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/entity/LivingEntity;hasEffect(Lnet/minecraft/world/effect/MobEffect;)Z"
      ),
      method = {"getDamageAfterMagicAbsorb(Lnet/minecraft/world/damagesource/DamageSource;F)F"}
   )
   public boolean apoth_sunderingHasEffect(LivingEntity ths, MobEffect effect) {
      return true;
   }

   @Redirect(
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/effect/MobEffectInstance;getAmplifier()I"
      ),
      method = {"getDamageAfterMagicAbsorb(Lnet/minecraft/world/damagesource/DamageSource;F)F"}
   )
   public int apoth_sunderingGetAmplifier(@Nullable MobEffectInstance inst) {
      return inst == null ? -1 : inst.m_19564_();
   }

   @Shadow
   public abstract boolean m_21023_(MobEffect var1);

   @Shadow
   public abstract MobEffectInstance m_21124_(MobEffect var1);

   public int m_19876_() {
      int color = super.m_19876_();
      if (color == 16777215) {
         Component name = this.m_7770_();
         if (name != null && name.m_7383_().m_131135_() != null) {
            color = name.m_7383_().m_131135_().m_131265_();
         }
      }

      return color;
   }

   @Redirect(
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/damagesource/CombatRules;getDamageAfterAbsorb(FFF)F"
      ),
      method = {"getDamageAfterArmorAbsorb(Lnet/minecraft/world/damagesource/DamageSource;F)F"},
      require = 1
   )
   public float apoth_applyArmorPen(float amount, float armor, float toughness, DamageSource src, float amt2) {
      return ALCombatRules.getDamageAfterArmor((LivingEntity)this, src, amount, armor, toughness);
   }

   @Redirect(
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/damagesource/CombatRules;getDamageAfterMagicAbsorb(FF)F"
      ),
      method = {"getDamageAfterMagicAbsorb(Lnet/minecraft/world/damagesource/DamageSource;F)F"},
      require = 1
   )
   public float apoth_applyProtPen(float amount, float protPoints, DamageSource src, float amt2) {
      return ALCombatRules.getDamageAfterProtection((LivingEntity)this, src, amount, protPoints);
   }
}

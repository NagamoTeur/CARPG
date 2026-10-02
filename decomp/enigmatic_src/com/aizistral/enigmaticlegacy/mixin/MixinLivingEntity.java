package com.aizistral.enigmaticlegacy.mixin;

import com.aizistral.enigmaticlegacy.api.quack.IProperShieldUser;
import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({LivingEntity.class})
public abstract class MixinLivingEntity extends Entity implements IProperShieldUser {
   @Shadow
   protected ItemStack f_20935_;
   @Shadow
   protected int f_20936_;

   public MixinLivingEntity(EntityType<?> type, Level world) {
      super(type, world);
      throw new IllegalStateException("Can't touch this");
   }

   @Inject(
      method = {"isDamageSourceBlocked"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onDamageSourceBlocking(DamageSource source, CallbackInfoReturnable<Boolean> info) {
      SuperpositionHandler.onDamageSourceBlocking((LivingEntity)this, this.f_20935_, source, info);
   }

   @Override
   public boolean isActuallyReallyBlocking() {
      if (this.m_6117_() && !this.f_20935_.m_41619_()) {
         Item item = this.f_20935_.m_41720_();
         return item.m_6164_(this.f_20935_) != UseAnim.BLOCK ? false : item.m_8105_(this.f_20935_) - this.f_20936_ >= 0;
      } else {
         return false;
      }
   }

   @Shadow
   public abstract boolean m_6117_();
}

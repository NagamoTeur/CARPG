package com.github.L_Ender.cataclysm.mixin;

import com.github.L_Ender.cataclysm.init.ModTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ItemEntity.class})
public abstract class ItemMixin {
   @Shadow
   public abstract ItemStack m_32055_();

   @Inject(
      method = {"hurt"},
      remap = true,
      at = {@At("HEAD")},
      cancellable = true
   )
   public void Cmhurt(DamageSource damageSource, float p_32014_, CallbackInfoReturnable<Boolean> cir) {
      if (!this.m_32055_().m_41619_() && damageSource.m_19372_() && this.m_32055_().m_204117_(ModTag.EXPLOSION_IMMUNE_ITEM)) {
         cir.setReturnValue(false);
      }
   }
}

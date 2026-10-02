package com.hollingsworth.arsnouveau.common.mixin.perks;

import com.hollingsworth.arsnouveau.api.util.PerkUtil;
import com.hollingsworth.arsnouveau.common.perk.DepthsPerk;
import com.hollingsworth.arsnouveau.common.perk.JumpHeightPerk;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({LivingEntity.class})
public class PerkLivingEntity {
   @Inject(
      method = {"decreaseAirSupply"},
      at = {@At("HEAD")},
      cancellable = true
   )
   protected void decreaseAirSupply(int pCurrentAir, CallbackInfoReturnable<Integer> cir) {
      LivingEntity thisEntity = (LivingEntity)this;
      if (thisEntity instanceof Player player) {
         int numDepths = PerkUtil.countForPerk(DepthsPerk.INSTANCE, player);
         if (numDepths >= 3 || thisEntity.m_217043_().m_188500_() <= (double)numDepths * 0.33) {
            cir.setReturnValue(thisEntity.m_20146_());
         }
      }
   }

   @Inject(
      method = {"getJumpBoostPower"},
      at = {@At("RETURN")},
      cancellable = true
   )
   protected void getJumpPower(CallbackInfoReturnable<Double> cir) {
      LivingEntity thisEntity = (LivingEntity)this;
      if (thisEntity instanceof Player player) {
         cir.setReturnValue(cir.getReturnValueD() + (double)PerkUtil.countForPerk(JumpHeightPerk.INSTANCE, player) * 0.1);
      }
   }
}

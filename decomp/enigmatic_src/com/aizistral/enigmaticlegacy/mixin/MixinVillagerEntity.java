package com.aizistral.enigmaticlegacy.mixin;

import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.registries.EnigmaticItems;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.trading.MerchantOffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Villager.class})
public class MixinVillagerEntity {
   @Inject(
      at = {@At("RETURN")},
      method = {"updateSpecialPrices"}
   )
   private void onSpecialPrices(Player player, CallbackInfo info) {
      if (this instanceof Villager villager && SuperpositionHandler.hasCurio(player, EnigmaticItems.AVARICE_SCROLL)) {
         for (MerchantOffer trade : villager.m_6616_()) {
            double discountValue = 0.35;
            int discount = (int)Math.floor(discountValue * (double)trade.m_45352_().m_41613_());
            trade.m_45353_(-Math.max(discount, 1));
         }
      }
   }
}

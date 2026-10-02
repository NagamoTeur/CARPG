package com.aizistral.etherium.core;

import com.aizistral.enigmaticlegacy.config.EtheriumConfigHandler;
import com.aizistral.enigmaticlegacy.helpers.ItemNBTHelper;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IEtheriumTool {
   default IEtheriumConfig getConfig() {
      return EtheriumConfigHandler.instance();
   }

   default boolean areaEffectsEnabled(Player player, ItemStack stack) {
      return this.areaEffectsAllowed(stack) && (!player.m_6047_() || this.getConfig().disableAOEShiftInhibition());
   }

   default boolean areaEffectsAllowed(ItemStack stack) {
      return stack.m_41720_() instanceof IEtheriumTool ? ItemNBTHelper.getBoolean(stack, "MultiblockEffectsEnabled", true) : false;
   }

   default void enableAreaEffects(Player player, ItemStack stack) {
      if (stack.m_41720_() instanceof IEtheriumTool) {
         ItemNBTHelper.setBoolean(stack, "MultiblockEffectsEnabled", true);
         if (!player.f_19853_.f_46443_) {
            player.f_19853_.m_5594_(null, player.m_20183_(), this.getConfig().getAOESoundOn(), SoundSource.PLAYERS, 1.0F, (float)(0.8F + Math.random() * 0.2F));
         }
      }
   }

   default void disableAreaEffects(Player player, ItemStack stack) {
      if (stack.m_41720_() instanceof IEtheriumTool) {
         ItemNBTHelper.setBoolean(stack, "MultiblockEffectsEnabled", false);
         if (!player.f_19853_.f_46443_) {
            player.f_19853_
               .m_5594_(null, player.m_20183_(), this.getConfig().getAOESoundOff(), SoundSource.PLAYERS, 1.0F, (float)(0.8F + Math.random() * 0.2F));
         }
      }
   }

   default void toggleAreaEffects(Player player, ItemStack stack) {
      if (stack.m_41720_() instanceof IEtheriumTool) {
         if (this.areaEffectsAllowed(stack)) {
            this.disableAreaEffects(player, stack);
         } else {
            this.enableAreaEffects(player, stack);
         }
      }
   }
}

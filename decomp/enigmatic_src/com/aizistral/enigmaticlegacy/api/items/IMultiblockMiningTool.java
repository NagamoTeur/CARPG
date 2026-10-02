package com.aizistral.enigmaticlegacy.api.items;

import com.aizistral.enigmaticlegacy.config.OmniconfigHandler;
import com.aizistral.enigmaticlegacy.helpers.ItemNBTHelper;
import com.aizistral.enigmaticlegacy.registries.EnigmaticSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IMultiblockMiningTool {
   default boolean areaEffectsEnabled(Player player, ItemStack stack) {
      return this.areaEffectsAllowed(stack) && (!player.m_6047_() || OmniconfigHandler.disableAOEShiftSuppression.getValue());
   }

   default boolean areaEffectsAllowed(ItemStack stack) {
      return stack.m_41720_() instanceof IMultiblockMiningTool ? ItemNBTHelper.getBoolean(stack, "MultiblockEffectsEnabled", true) : false;
   }

   default void enableAreaEffects(Player player, ItemStack stack) {
      if (stack.m_41720_() instanceof IMultiblockMiningTool) {
         ItemNBTHelper.setBoolean(stack, "MultiblockEffectsEnabled", true);
         if (!player.f_19853_.f_46443_) {
            player.f_19853_.m_5594_(null, player.m_20183_(), EnigmaticSounds.CHARGED_ON, SoundSource.PLAYERS, 1.0F, (float)(0.8F + Math.random() * 0.2F));
         }
      }
   }

   default void disableAreaEffects(Player player, ItemStack stack) {
      if (stack.m_41720_() instanceof IMultiblockMiningTool) {
         ItemNBTHelper.setBoolean(stack, "MultiblockEffectsEnabled", false);
         if (!player.f_19853_.f_46443_) {
            player.f_19853_.m_5594_(null, player.m_20183_(), EnigmaticSounds.CHARGED_OFF, SoundSource.PLAYERS, 1.0F, (float)(0.8F + Math.random() * 0.2F));
         }
      }
   }

   default void toggleAreaEffects(Player player, ItemStack stack) {
      if (stack.m_41720_() instanceof IMultiblockMiningTool) {
         if (this.areaEffectsAllowed(stack)) {
            this.disableAreaEffects(player, stack);
         } else {
            this.enableAreaEffects(player, stack);
         }
      }
   }
}

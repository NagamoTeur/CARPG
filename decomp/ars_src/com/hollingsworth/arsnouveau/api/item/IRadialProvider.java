package com.hollingsworth.arsnouveau.api.item;

import com.hollingsworth.arsnouveau.client.keybindings.ModKeyBindings;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IRadialProvider {
   @OnlyIn(Dist.CLIENT)
   default int forKey() {
      return ModKeyBindings.OPEN_RADIAL_HUD.getKey().m_84873_();
   }

   @OnlyIn(Dist.CLIENT)
   void onRadialKeyPressed(ItemStack var1, Player var2);
}

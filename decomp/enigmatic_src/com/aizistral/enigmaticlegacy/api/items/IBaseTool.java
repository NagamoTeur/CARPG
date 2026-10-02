package com.aizistral.enigmaticlegacy.api.items;

import java.util.Set;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.ForgeHooks;

public interface IBaseTool {
   Set<Material> getEffectiveMaterials();

   float getEfficiency();

   default boolean canHarvestBlock(BlockState blockIn, Player player) {
      if (ForgeHooks.isCorrectToolForDrops(blockIn, player)) {
         return true;
      } else {
         Material material = blockIn.m_60767_();
         return this.getEffectiveMaterials().contains(material);
      }
   }
}

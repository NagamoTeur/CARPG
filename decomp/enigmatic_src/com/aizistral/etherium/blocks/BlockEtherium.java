package com.aizistral.etherium.blocks;

import java.util.List;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.storage.loot.LootContext.Builder;

public class BlockEtherium extends Block {
   public BlockEtherium() {
      super(Properties.m_60944_(Material.f_76279_, MaterialColor.f_76415_).m_60999_().m_60913_(5.0F, 1200.0F).m_60953_(arg -> 10));
   }

   public List<ItemStack> m_7381_(BlockState state, Builder builder) {
      return List.of(new ItemStack(this));
   }
}

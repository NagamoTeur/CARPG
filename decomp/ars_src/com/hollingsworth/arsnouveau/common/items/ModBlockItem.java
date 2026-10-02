package com.hollingsworth.arsnouveau.common.items;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ModBlockItem extends BlockItem {
   public List<Component> tooltip = new ArrayList<>();
   public Rarity f_41369_;

   public ModBlockItem(Block pBlock, Properties pProperties) {
      super(pBlock, pProperties);
   }

   public ModBlockItem withTooltip(Component tip) {
      this.tooltip.add(tip);
      return this;
   }

   public ModBlockItem withRarity(Rarity rarity) {
      this.f_41369_ = rarity;
      return this;
   }

   public Rarity m_41460_(ItemStack stack) {
      return this.f_41369_ != null ? this.f_41369_ : super.m_41460_(stack);
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip2, TooltipFlag flagIn) {
      super.m_7373_(stack, worldIn, tooltip2, flagIn);
      if (this.tooltip != null && !this.tooltip.isEmpty()) {
         tooltip2.addAll(this.tooltip);
      }
   }
}

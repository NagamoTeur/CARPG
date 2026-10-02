package com.github.L_Ender.cataclysm.items;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraftforge.registries.RegistryObject;

public class CMBlockItem extends BlockItem {
   private final RegistryObject<Block> blockSupplier;

   public CMBlockItem(RegistryObject<Block> blockSupplier, Properties props) {
      super((Block)null, props);
      this.blockSupplier = blockSupplier;
   }

   public Block m_40614_() {
      return (Block)this.blockSupplier.get();
   }

   public void m_142023_(ItemEntity p_150700_) {
      if (this.blockSupplier.get() instanceof ShulkerBoxBlock) {
         ItemStack itemstack = p_150700_.m_32055_();
         CompoundTag compoundtag = m_186336_(itemstack);
         if (compoundtag != null && compoundtag.m_128425_("Items", 9)) {
            ListTag listtag = compoundtag.m_128437_("Items", 10);
            ItemUtils.m_150952_(p_150700_, listtag.stream().map(CompoundTag.class::cast).map(ItemStack::m_41712_));
         }
      }
   }
}

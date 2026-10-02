package com.hollingsworth.arsnouveau.common.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;

public class FluidBlockItem extends BlockItem {
   public FluidBlockItem(Block blockIn, Properties builder) {
      super(blockIn, builder);
   }

   public InteractionResult m_6225_(UseOnContext context) {
      return InteractionResult.PASS;
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
      BlockHitResult blockraytraceresult = m_41435_(worldIn, playerIn, Fluid.ANY);
      BlockHitResult blockraytraceresult1 = blockraytraceresult.m_82430_(blockraytraceresult.m_82425_().m_7494_());
      if (worldIn.m_8055_(blockraytraceresult.m_82425_()).m_60795_()) {
         return new InteractionResultHolder(InteractionResult.SUCCESS, playerIn.m_21120_(handIn));
      } else {
         super.m_6225_(new UseOnContext(playerIn, handIn, blockraytraceresult1));
         return new InteractionResultHolder(InteractionResult.FAIL, playerIn.m_21120_(handIn));
      }
   }
}

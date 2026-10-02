package com.obscuria.aquamirae.common.items;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.blocks.WisteriaNiveisBlock;
import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.jetbrains.annotations.NotNull;

public class WisteriaNiveisItem extends Item {
   public WisteriaNiveisItem() {
      super(new Properties().m_41491_(Aquamirae.TAB).m_41487_(64).m_41497_(Rarity.COMMON));
   }

   @NotNull
   public InteractionResult m_6225_(UseOnContext context) {
      BlockPos pos = context.m_8083_();
      if (context.m_43719_() == Direction.UP
         && AquamiraeBlocks.WISTERIA_NIVEIS.get() instanceof WisteriaNiveisBlock wisteriaBlock
         && wisteriaBlock.canBePlacedOn(context.m_43725_(), pos)) {
         context.m_43725_().m_5594_(context.m_43723_(), context.m_8083_(), SoundEvents.f_12137_, SoundSource.BLOCKS, 1.0F, 1.0F);
         context.m_43725_()
            .m_7731_(
               pos.m_7494_(),
               (BlockState)((BlockState)((Block)AquamiraeBlocks.WISTERIA_NIVEIS.get())
                     .m_49966_()
                     .m_61124_(BlockStateProperties.f_61401_, DoubleBlockHalf.LOWER))
                  .m_61124_(WisteriaNiveisBlock.LOOT, false),
               3
            );
         context.m_43722_().m_41774_(1);
         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.FAIL;
      }
   }
}

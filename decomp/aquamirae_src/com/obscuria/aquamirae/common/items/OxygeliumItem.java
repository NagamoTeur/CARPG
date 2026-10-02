package com.obscuria.aquamirae.common.items;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.blocks.OxygeliumBlock;
import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

public class OxygeliumItem extends Item {
   public OxygeliumItem() {
      super(new Properties().m_41491_(Aquamirae.TAB).m_41487_(64).m_41497_(Rarity.COMMON));
   }

   @NotNull
   public InteractionResult m_6225_(UseOnContext context) {
      if (context.m_43719_() == Direction.UP) {
         if (context.m_43725_().m_8055_(context.m_8083_()).m_60767_().m_76333_()
            && context.m_43725_()
               .m_8055_(new BlockPos(context.m_8083_().m_123341_(), context.m_8083_().m_123342_() + 1, context.m_8083_().m_123343_()))
               .m_60767_()
               .m_76332_()) {
            context.m_43725_()
               .m_7731_(
                  new BlockPos(context.m_8083_().m_123341_(), context.m_8083_().m_123342_() + 1, context.m_8083_().m_123343_()),
                  (BlockState)((BlockState)((Block)AquamiraeBlocks.OXYGELIUM.get()).m_49966_().m_61124_(OxygeliumBlock.TYPE, OxygeliumBlock.Type.EMPTY_BUD))
                     .m_61124_(BlockStateProperties.f_61362_, Boolean.TRUE),
                  3
               );
            context.m_43722_().m_41774_(1);
            return InteractionResult.SUCCESS;
         }

         if (context.m_43725_()
               .m_8055_(new BlockPos(context.m_8083_().m_123341_(), context.m_8083_().m_123342_() + 1, context.m_8083_().m_123343_()))
               .m_60767_()
               .m_76332_()
            && context.m_43725_().m_8055_(context.m_8083_()).m_60734_() == AquamiraeBlocks.OXYGELIUM.get()) {
            context.m_43725_()
               .m_7731_(
                  new BlockPos(context.m_8083_().m_123341_(), context.m_8083_().m_123342_(), context.m_8083_().m_123343_()),
                  (BlockState)((Block)AquamiraeBlocks.OXYGELIUM.get()).m_49966_().m_61124_(BlockStateProperties.f_61362_, Boolean.TRUE),
                  3
               );
            context.m_43725_()
               .m_7731_(
                  new BlockPos(context.m_8083_().m_123341_(), context.m_8083_().m_123342_() + 1, context.m_8083_().m_123343_()),
                  (BlockState)((BlockState)((Block)AquamiraeBlocks.OXYGELIUM.get()).m_49966_().m_61124_(OxygeliumBlock.TYPE, OxygeliumBlock.Type.EMPTY_BUD))
                     .m_61124_(BlockStateProperties.f_61362_, Boolean.TRUE),
                  3
               );
            context.m_43722_().m_41774_(1);
            return InteractionResult.SUCCESS;
         }
      }

      return InteractionResult.FAIL;
   }
}

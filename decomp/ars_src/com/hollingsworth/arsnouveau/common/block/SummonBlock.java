package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.common.block.tile.SummoningTile;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.PushReaction;

public abstract class SummonBlock extends TickableModBlock {
   public SummonBlock(Properties properties) {
      super(properties);
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext context) {
      BlockState state = super.m_5573_(context);
      CompoundTag tag = context.m_43722_().m_41783_();
      if (tag != null && tag.m_128441_("BlockEntityTag")) {
         tag = tag.m_128469_("BlockEntityTag");
         if (tag.m_128441_("converted") && tag.m_128471_("converted")) {
            state = (BlockState)state.m_61124_(SummoningTile.CONVERTED, true);
         }
      }

      return state;
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{SummoningTile.CONVERTED});
   }

   public PushReaction m_5537_(BlockState p_149656_1_) {
      return PushReaction.BLOCK;
   }
}

package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.common.block.tile.MageBlockTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.Nullable;

public class MageBlock extends TickableModBlock {
   public static final BooleanProperty TEMPORARY = BooleanProperty.m_61465_("temporary");

   public MageBlock() {
      super(defaultProperties().m_60953_(bs -> 7).m_60955_().m_60988_());
      this.m_49959_((BlockState)this.m_49966_().m_61124_(TEMPORARY, false));
   }

   public BlockEntity m_142194_(BlockPos pos, BlockState state) {
      return new MageBlockTile(pos, state);
   }

   public boolean canDropFromExplosion(BlockState state, BlockGetter world, BlockPos pos, Explosion explosion) {
      return false;
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{TEMPORARY});
   }

   @Nullable
   @Override
   public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level level, BlockState state, BlockEntityType<T> type) {
      return state.m_61138_(TEMPORARY) && state.m_61143_(TEMPORARY) ? super.m_142354_(level, state, type) : null;
   }
}

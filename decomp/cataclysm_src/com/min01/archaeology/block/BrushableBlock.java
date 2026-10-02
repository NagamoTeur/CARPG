package com.min01.archaeology.block;

import com.min01.archaeology.blockentity.BrushableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;

public class BrushableBlock extends BaseEntityBlock implements Fallable {
   public static final IntegerProperty DUSTED = IntegerProperty.m_61631_("dusted", 0, 3);
   public static final int TICK_DELAY = 2;
   private final Block turnsInto;
   private final SoundEvent brushSound;
   private final SoundEvent brushCompletedSound;

   public BrushableBlock(Block turnsInto, Properties properties, SoundEvent brushSound, SoundEvent brushCompletedSound) {
      super(properties);
      this.turnsInto = turnsInto;
      this.brushSound = brushSound;
      this.brushCompletedSound = brushCompletedSound;
      this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(DUSTED, 0));
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{DUSTED});
   }

   public PushReaction m_5537_(BlockState state) {
      return PushReaction.DESTROY;
   }

   public RenderShape m_7514_(BlockState state) {
      return RenderShape.MODEL;
   }

   public void m_6807_(BlockState state, Level level, BlockPos position, BlockState oldState, boolean isMoving) {
      level.m_186460_(position, this, 2);
   }

   public BlockState m_7417_(
      BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPosition, BlockPos neighborPosition
   ) {
      level.m_186460_(currentPosition, this, 2);
      return state;
   }

   public void m_213897_(BlockState state, ServerLevel serverLevel, BlockPos position, RandomSource random) {
      if (serverLevel.m_7702_(position) instanceof BrushableBlockEntity brushableblockentity) {
         brushableblockentity.checkReset();
      }

      if (FallingBlock.m_53241_(serverLevel.m_8055_(position.m_7495_())) && position.m_123342_() >= serverLevel.m_141937_()) {
         FallingBlockEntity fallingblockentity = FallingBlockEntity.m_201971_(serverLevel, position, state);
         fallingblockentity.f_31943_ = false;
      }
   }

   public void m_142525_(Level level, BlockPos position, FallingBlockEntity fallingBlock) {
      Vec3 center = fallingBlock.m_20191_().m_82399_();
      level.m_46796_(2001, new BlockPos(center), Block.m_49956_(fallingBlock.m_31980_()));
      level.m_220400_(fallingBlock, GameEvent.f_157794_, center);
   }

   public void m_214162_(BlockState state, Level level, BlockPos position, RandomSource random) {
      if (random.m_188503_(16) == 0 && FallingBlock.m_53241_(level.m_8055_(position.m_7495_()))) {
         double x = (double)position.m_123341_() + random.m_188500_();
         double y = (double)position.m_123342_() - 0.05;
         double z = (double)position.m_123343_() + random.m_188500_();
         level.m_7106_(new BlockParticleOption(ParticleTypes.f_123814_, state), x, y, z, 0.0, 0.0, 0.0);
      }
   }

   public BlockEntity m_142194_(BlockPos position, BlockState state) {
      return new BrushableBlockEntity(position, state);
   }

   public Block getTurnsInto() {
      return this.turnsInto;
   }

   public SoundEvent getBrushSound() {
      return this.brushSound;
   }

   public SoundEvent getBrushCompletedSound() {
      return this.brushCompletedSound;
   }
}

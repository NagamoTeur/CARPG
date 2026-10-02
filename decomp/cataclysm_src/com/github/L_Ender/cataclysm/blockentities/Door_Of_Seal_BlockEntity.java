package com.github.L_Ender.cataclysm.blockentities;

import com.github.L_Ender.cataclysm.blocks.Door_of_Seal_Block;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.init.ModBlocks;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTileentites;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Door_Of_Seal_BlockEntity extends BlockEntity {
   public int Animaitonticks;
   public int tickCount;
   public int animation = 0;
   public Direction facing;
   public AnimationState openingAnimationState = new AnimationState();
   public AnimationState openAnimationState = new AnimationState();

   public Door_Of_Seal_BlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType)ModTileentites.DOOR_OF_SEAL.get(), pos, state);
      this.facing = (Direction)state.m_61143_(BlockStateProperties.f_61374_);
   }

   public AnimationState getAnimationState(String input) {
      if (input == "opening") {
         return this.openingAnimationState;
      } else {
         return input == "open" ? this.openAnimationState : new AnimationState();
      }
   }

   public boolean m_7531_(int p_58837_, int p_58838_) {
      if (p_58837_ == 1) {
         this.openingAnimationState.m_216977_(this.tickCount);
         return true;
      } else {
         return super.m_7531_(p_58837_, p_58838_);
      }
   }

   public static void tick(Level level, BlockPos pos, BlockState blockState, Door_Of_Seal_BlockEntity entity) {
      entity.tickCount++;
      if (blockState.m_60734_() instanceof Door_of_Seal_Block && (Boolean)blockState.m_61143_(Door_of_Seal_Block.LIT)) {
         entity.Animaitonticks++;
         if (!(Boolean)blockState.m_61143_(Door_of_Seal_Block.OPEN)) {
            if (entity.Animaitonticks == 1) {
               ScreenShake_Entity.ScreenShake(level, Vec3.m_82512_(pos), 20.0F, 0.05F, 0, 120);
            }

            if (entity.Animaitonticks == 28) {
               level.m_5594_(
                  (Player)null, pos, (SoundEvent)ModSounds.DOOR_OF_SEAL_OPEN.get(), SoundSource.BLOCKS, 4.0F, level.f_46441_.m_188501_() * 0.2F + 1.0F
               );
               float x = (float)pos.m_123341_() + 0.5F;
               float y = (float)pos.m_123342_();
               float z = (float)pos.m_123343_() + 0.5F;
               if (!level.f_46443_) {
                  level.m_46511_(null, (double)x, (double)(y + 1.0F), (double)z, 2.0F, BlockInteraction.NONE);
               }
            }

            if (entity.Animaitonticks >= 145 && !level.f_46443_) {
               level.m_7731_(pos, (BlockState)blockState.m_61124_(Door_of_Seal_Block.OPEN, true), 2);

               for (int i = 0; i <= 7; i++) {
                  BlockPos abovePos = pos.m_6630_(i);
                  BlockPos blockpos1 = abovePos.m_121945_(((Direction)blockState.m_61143_(Door_of_Seal_Block.FACING)).m_122427_());
                  BlockPos blockpos3 = abovePos.m_121945_(((Direction)blockState.m_61143_(Door_of_Seal_Block.FACING)).m_122428_());
                  BlockPos blockpos4 = abovePos.m_5484_(((Direction)blockState.m_61143_(Door_of_Seal_Block.FACING)).m_122427_(), 2);
                  BlockPos blockpos5 = abovePos.m_5484_(((Direction)blockState.m_61143_(Door_of_Seal_Block.FACING)).m_122428_(), 2);
                  BlockPos[] toBreakPoses = new BlockPos[]{blockpos1, abovePos, blockpos3, blockpos4, blockpos5};

                  for (BlockPos toBreakPos : toBreakPoses) {
                     BlockState blockstate = level.m_8055_(toBreakPos);
                     if (blockstate.m_60713_((Block)ModBlocks.DOOR_OF_SEAL_PART.get())) {
                        level.m_7731_(toBreakPos, (BlockState)blockstate.m_61124_(Door_of_Seal_Block.Door_Of_Seal_Part_Block.OPEN, true), 2);
                     }
                  }
               }
            }
         } else {
            entity.Animaitonticks = 0;
            if (level.f_46443_) {
               entity.openingAnimationState.m_216973_();
               entity.openAnimationState.m_216982_(entity.tickCount);
            }
         }
      }
   }

   public void onHit(Level level) {
      BlockPos blockpos = this.m_58899_();
      BlockState state = this.m_58900_();
      if (!(Boolean)state.m_61143_(Door_of_Seal_Block.LIT)) {
         level.m_7731_(blockpos, (BlockState)state.m_61124_(Door_of_Seal_Block.LIT, true), 2);
         this.f_58857_.m_7696_(blockpos, this.m_58900_().m_60734_(), 1, 0);
      }
   }

   public void m_142466_(CompoundTag compound) {
      super.m_142466_(compound);
      this.Animaitonticks = compound.m_128451_("animationTicks");
   }

   public void m_183515_(CompoundTag compound) {
      super.m_183515_(compound);
      compound.m_128405_("animationTicks", this.Animaitonticks);
   }

   public AABB getRenderBoundingBox() {
      AABB bounds = super.getRenderBoundingBox();
      bounds = bounds.m_82369_(new Vec3(this.facing.m_122427_().m_122432_()).m_82490_(3.0));
      bounds = bounds.m_82369_(new Vec3(this.facing.m_122428_().m_122432_()).m_82490_(3.0));
      return bounds.m_82363_(0.0, 8.0, 0.0);
   }
}

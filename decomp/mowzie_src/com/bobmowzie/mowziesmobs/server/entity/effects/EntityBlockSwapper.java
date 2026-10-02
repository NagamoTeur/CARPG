package com.bobmowzie.mowziesmobs.server.entity.effects;

import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.sculptor.EntitySculptor;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public class EntityBlockSwapper extends Entity {
   private static final EntityDataAccessor<Optional<BlockState>> ORIG_BLOCK_STATE = SynchedEntityData.m_135353_(
      EntityBlockSwapper.class, EntityDataSerializers.f_135034_
   );
   private static final EntityDataAccessor<Integer> RESTORE_TIME = SynchedEntityData.m_135353_(EntityBlockSwapper.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<BlockPos> POS = SynchedEntityData.m_135353_(EntityBlockSwapper.class, EntityDataSerializers.f_135038_);
   protected int duration;
   protected boolean breakParticlesEnd;
   private BlockPos pos;

   public EntityBlockSwapper(EntityType<? extends EntityBlockSwapper> type, Level world) {
      super(type, world);
      this.breakParticlesEnd = false;
   }

   public EntityBlockSwapper(
      EntityType<? extends EntityBlockSwapper> type,
      Level world,
      BlockPos pos,
      BlockState newBlock,
      int duration,
      boolean breakParticlesStart,
      boolean breakParticlesEnd
   ) {
      super(type, world);
      this.setStorePos(pos);
      this.setRestoreTime(duration);
      this.breakParticlesEnd = breakParticlesEnd;
      this.m_6034_((double)pos.m_123341_() + 0.5, (double)pos.m_123342_(), (double)pos.m_123343_() + 0.5);
      if (!world.f_46443_) {
         this.setOrigBlock(world.m_8055_(pos));
         if (breakParticlesStart) {
            world.m_46961_(pos, false);
         }

         world.m_7731_(pos, newBlock, 19);
      }

      for (EntityBlockSwapper swapper : world.m_45976_(EntityBlockSwapper.class, this.m_20191_())) {
         if (swapper != this) {
            if (swapper instanceof EntityBlockSwapper.EntityBlockSwapperSculptor swapperSculptor) {
               this.setOrigBlock(swapperSculptor.getOrigBlockAtLocation(pos));
            } else {
               this.setOrigBlock(swapper.getOrigBlock());
               swapper.m_146870_();
            }
         }
      }
   }

   public static void swapBlock(Level world, BlockPos pos, BlockState newBlock, int duration, boolean breakParticlesStart, boolean breakParticlesEnd) {
      if (!world.f_46443_) {
         EntityBlockSwapper swapper = new EntityBlockSwapper(
            (EntityType<? extends EntityBlockSwapper>)EntityHandler.BLOCK_SWAPPER.get(), world, pos, newBlock, duration, breakParticlesStart, breakParticlesEnd
         );
         world.m_7967_(swapper);
      }
   }

   public boolean isBlockPosInsideSwapper(BlockPos pos) {
      return pos.equals(this.getStorePos());
   }

   public boolean m_6000_(double p_145770_1_, double p_145770_3_, double p_145770_5_) {
      return false;
   }

   protected void m_8097_() {
      this.m_20088_().m_135372_(ORIG_BLOCK_STATE, Optional.of(Blocks.f_50493_.m_49966_()));
      this.m_20088_().m_135372_(RESTORE_TIME, 20);
      this.m_20088_().m_135372_(POS, new BlockPos(0, 0, 0));
   }

   public int getRestoreTime() {
      return (Integer)this.f_19804_.m_135370_(RESTORE_TIME);
   }

   public void setRestoreTime(int restoreTime) {
      this.f_19804_.m_135381_(RESTORE_TIME, restoreTime);
      this.duration = restoreTime;
   }

   public BlockPos getStorePos() {
      return (BlockPos)this.f_19804_.m_135370_(POS);
   }

   public void setStorePos(BlockPos bpos) {
      this.f_19804_.m_135381_(POS, bpos);
      this.pos = bpos;
   }

   @Nullable
   public BlockState getOrigBlock() {
      Optional<BlockState> opState = (Optional<BlockState>)this.m_20088_().m_135370_(ORIG_BLOCK_STATE);
      return opState.orElse(null);
   }

   public void setOrigBlock(BlockState block) {
      this.m_20088_().m_135381_(ORIG_BLOCK_STATE, Optional.of(block));
   }

   public void restoreBlock() {
      List<EntityBlockSwapper> swappers = this.f_19853_.m_45976_(EntityBlockSwapper.class, this.m_20191_());
      if (!this.f_19853_.f_46443_) {
         boolean canReplace = true;

         for (EntityBlockSwapper swapper : swappers) {
            if (swapper != this && swapper.isBlockPosInsideSwapper(this.pos)) {
               canReplace = false;
               break;
            }
         }

         if (canReplace) {
            if (this.breakParticlesEnd) {
               this.f_19853_.m_46961_(this.pos, false);
            }

            this.f_19853_.m_7731_(this.pos, this.getOrigBlock(), 19);
         }

         this.m_146870_();
      }
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.canRestoreBlock()) {
         this.restoreBlock();
      }
   }

   protected boolean canRestoreBlock() {
      return this.f_19797_ > this.duration && this.f_19853_.m_45976_(Player.class, this.m_20191_()).isEmpty();
   }

   public void m_7380_(CompoundTag compound) {
      Optional<BlockState> blockOption = (Optional<BlockState>)this.m_20088_().m_135370_(ORIG_BLOCK_STATE);
      blockOption.ifPresent(blockState -> compound.m_128365_("block", NbtUtils.m_129202_(blockState)));
      compound.m_128405_("restoreTime", this.getRestoreTime());
      compound.m_128405_("storePosX", this.getStorePos().m_123341_());
      compound.m_128405_("storePosY", this.getStorePos().m_123342_());
      compound.m_128405_("storePosZ", this.getStorePos().m_123343_());
   }

   public void m_7378_(CompoundTag compound) {
      Tag blockNBT = compound.m_128423_("block");
      if (blockNBT != null) {
         BlockState blockState = NbtUtils.m_129241_((CompoundTag)blockNBT);
         this.setOrigBlock(blockState);
      }

      this.setRestoreTime(compound.m_128451_("restoreTime"));
      this.setStorePos(new BlockPos(compound.m_128451_("storePosX"), compound.m_128451_("storePosY"), compound.m_128451_("storePosZ")));
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public static class EntityBlockSwapperSculptor extends EntityBlockSwapper {
      private int height;
      private int radius;
      private BlockState[][][] origStates;

      public EntityBlockSwapperSculptor(EntityType<? extends EntityBlockSwapper.EntityBlockSwapperSculptor> type, Level world) {
         super(type, world);
         this.breakParticlesEnd = false;
         this.height = EntitySculptor.TEST_HEIGHT + 3;
         this.radius = EntitySculptor.TEST_RADIUS;
         this.origStates = new BlockState[this.height][this.radius * 2][this.radius * 2];
         this.m_20011_(this.m_142242_());
      }

      public EntityBlockSwapperSculptor(
         EntityType<? extends EntityBlockSwapper.EntityBlockSwapperSculptor> type,
         Level world,
         BlockPos pos,
         BlockState newBlock,
         int duration,
         boolean breakParticlesStart,
         boolean breakParticlesEnd
      ) {
         super(type, world);
         this.height = EntitySculptor.TEST_HEIGHT + 3;
         this.radius = EntitySculptor.TEST_RADIUS;
         this.origStates = new BlockState[this.height][this.radius * 2][this.radius * 2];
         this.setStorePos(pos);
         this.setRestoreTime(duration);
         this.breakParticlesEnd = breakParticlesEnd;
         this.m_6034_((double)pos.m_123341_() + 0.5, (double)pos.m_123342_(), (double)pos.m_123343_() + 0.5);
         this.m_20011_(this.m_142242_());
         List<EntityBlockSwapper.EntityBlockSwapperSculptor> swapperSculptors = world.m_45976_(
            EntityBlockSwapper.EntityBlockSwapperSculptor.class, this.m_20191_()
         );
         if (!world.f_46443_) {
            for (int k = 0; k < this.height; k++) {
               for (int i = -this.radius; i < this.radius; i++) {
                  for (int j = -this.radius; j < this.radius; j++) {
                     BlockPos thisPos = pos.m_7918_(i, k, j);
                     if (this.isBlockPosInsideSwapper(thisPos) && world.m_8055_(thisPos).m_60734_() != Blocks.f_50752_) {
                        this.origStates[k][i + this.radius][j + this.radius] = world.m_8055_(thisPos);
                        if (breakParticlesStart) {
                           world.m_46961_(thisPos, false);
                        }

                        world.m_7731_(thisPos, newBlock, 19);

                        for (EntityBlockSwapper.EntityBlockSwapperSculptor swapper : swapperSculptors) {
                           if (swapper != this && swapper.getOrigBlockAtLocation(thisPos) != null) {
                              this.origStates[k][i + this.radius][j + this.radius] = swapper.getOrigBlockAtLocation(thisPos);
                              break;
                           }
                        }
                     }
                  }
               }
            }
         }

         List<EntityBlockSwapper.EntityBlockSwapperSculptor> swappers = world.m_45976_(EntityBlockSwapper.EntityBlockSwapperSculptor.class, this.m_20191_());
         if (!swappers.isEmpty()) {
            for (EntityBlockSwapper swapperx : swappers) {
               if (swapperx != this && !(swapperx instanceof EntityBlockSwapper.EntityBlockSwapperSculptor)) {
                  this.setOrigBlockAtLocation(swapperx.getStorePos(), swapperx.getOrigBlock());
               }
            }
         }
      }

      @Override
      public boolean isBlockPosInsideSwapper(BlockPos pos) {
         return (double)new Vec2((float)(pos.m_123341_() - this.getStorePos().m_123341_()), (float)(pos.m_123343_() - this.getStorePos().m_123343_()))
                  .m_165907_()
               < EntitySculptor.testRadiusAtHeight((double)pos.m_123342_() - this.m_20186_())
            && this.m_20191_().m_82390_(new Vec3((double)pos.m_123341_() + 0.5, (double)pos.m_123342_() + 0.5, (double)pos.m_123343_() + 0.5));
      }

      public void setOrigBlockAtLocation(BlockPos pos, BlockState state) {
         if (this.isBlockPosInsideSwapper(pos)) {
            BlockPos indices = this.posToArrayIndices(pos);
            this.origStates[indices.m_123342_()][indices.m_123341_()][indices.m_123343_()] = state;
         }
      }

      public BlockState getOrigBlockAtLocation(BlockPos pos) {
         if (this.isBlockPosInsideSwapper(pos)) {
            BlockPos indices = this.posToArrayIndices(pos);
            return this.origStates[indices.m_123342_()][indices.m_123341_()][indices.m_123343_()];
         } else {
            return null;
         }
      }

      protected BlockPos posToArrayIndices(BlockPos pos) {
         return pos.m_121996_(this.getStorePos()).m_7918_(this.radius, 0, this.radius);
      }

      protected AABB m_142242_() {
         return EntityDimensions.m_20395_((float)(this.radius * 2), (float)this.height).m_20393_(this.m_20182_());
      }

      @Override
      public void restoreBlock() {
         if (!this.f_19853_.f_46443_) {
            List<EntityBlockSwapper> swappers = this.f_19853_.m_45976_(EntityBlockSwapper.class, this.m_20191_());

            for (int k = 0; k < this.height; k++) {
               for (int i = -this.radius; i < this.radius; i++) {
                  for (int j = -this.radius; j < this.radius; j++) {
                     if (!this.f_19853_.f_46443_) {
                        BlockPos thisPos = this.getStorePos().m_7918_(i, k, j);
                        if (this.isBlockPosInsideSwapper(thisPos)) {
                           boolean canReplace = true;

                           for (EntityBlockSwapper swapper : swappers) {
                              if (swapper != this && swapper.isBlockPosInsideSwapper(thisPos)) {
                                 canReplace = false;
                                 break;
                              }
                           }

                           if (canReplace) {
                              BlockState restoreState = this.origStates[k][i + this.radius][j + this.radius];
                              if (restoreState != null) {
                                 if (this.breakParticlesEnd) {
                                    this.f_19853_.m_46961_(thisPos, false);
                                 }

                                 this.f_19853_.m_7731_(thisPos, restoreState, 19);
                              }
                           }
                        }
                     }
                  }
               }
            }

            this.m_146870_();
         }
      }

      @Override
      protected boolean canRestoreBlock() {
         return this.f_19797_ > this.duration && this.f_19853_.m_6443_(EntitySculptor.class, this.m_20191_(), EntitySculptor::isTesting).isEmpty();
      }

      @Override
      public void m_7380_(CompoundTag compound) {
         compound.m_128405_("restoreTime", this.getRestoreTime());
         compound.m_128405_("storePosX", this.getStorePos().m_123341_());
         compound.m_128405_("storePosY", this.getStorePos().m_123342_());
         compound.m_128405_("storePosZ", this.getStorePos().m_123343_());

         for (int i = 0; i < this.radius * 2; i++) {
            for (int j = 0; j < this.radius * 2; j++) {
               for (int k = 0; k < this.height; k++) {
                  BlockState block = this.origStates[k][i][j];
                  if (block != null) {
                     compound.m_128365_("block_" + i + "_" + j + "_" + k, NbtUtils.m_129202_(block));
                  }
               }
            }
         }
      }

      @Override
      public void m_7378_(CompoundTag compound) {
         this.setRestoreTime(compound.m_128451_("restoreTime"));
         this.setStorePos(new BlockPos(compound.m_128451_("storePosX"), compound.m_128451_("storePosY"), compound.m_128451_("storePosZ")));

         for (int i = 0; i < this.radius * 2; i++) {
            for (int j = 0; j < this.radius * 2; j++) {
               for (int k = 0; k < this.height; k++) {
                  Tag blockNBT = compound.m_128423_("block_" + i + "_" + j + "_" + k);
                  if (blockNBT != null) {
                     BlockState blockState = NbtUtils.m_129241_((CompoundTag)blockNBT);
                     this.origStates[k][i][j] = blockState;
                  }
               }
            }
         }
      }
   }
}

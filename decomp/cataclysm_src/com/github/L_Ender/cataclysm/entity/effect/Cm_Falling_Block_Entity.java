package com.github.L_Ender.cataclysm.entity.effect;

import com.github.L_Ender.cataclysm.init.ModEntities;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public class Cm_Falling_Block_Entity extends Entity {
   public int duration = 20;
   protected static final EntityDataAccessor<BlockPos> DATA_START_POS = SynchedEntityData.m_135353_(
      Cm_Falling_Block_Entity.class, EntityDataSerializers.f_135038_
   );
   private static final EntityDataAccessor<Optional<BlockState>> BLOCK_STATE = SynchedEntityData.m_135353_(
      Cm_Falling_Block_Entity.class, EntityDataSerializers.f_135034_
   );

   public Cm_Falling_Block_Entity(EntityType<Cm_Falling_Block_Entity> type, Level level) {
      super(type, level);
   }

   public Cm_Falling_Block_Entity(Level p_31953_, double p_31954_, double p_31955_, double p_31956_, BlockState p_31957_, int duration) {
      this((EntityType<Cm_Falling_Block_Entity>)ModEntities.CM_FALLING_BLOCK.get(), p_31953_);
      this.setBlockState(p_31957_);
      this.m_6034_(p_31954_, p_31955_ + (double)((1.0F - this.m_20206_()) / 2.0F), p_31956_);
      this.m_20256_(Vec3.f_82478_);
      this.duration = duration;
      this.f_19854_ = p_31954_;
      this.f_19855_ = p_31955_;
      this.f_19856_ = p_31956_;
      this.setStartPos(this.m_20183_());
   }

   public void setStartPos(BlockPos p_31960_) {
      this.f_19804_.m_135381_(DATA_START_POS, p_31960_);
   }

   public BlockPos getStartPos() {
      return (BlockPos)this.f_19804_.m_135370_(DATA_START_POS);
   }

   protected void m_8097_() {
      this.f_19804_.m_135372_(DATA_START_POS, BlockPos.f_121853_);
      this.f_19804_.m_135372_(BLOCK_STATE, Optional.of(Blocks.f_50016_.m_49966_()));
   }

   public BlockState getBlockState() {
      return ((Optional)this.f_19804_.m_135370_(BLOCK_STATE)).isPresent() ? (BlockState)((Optional)this.f_19804_.m_135370_(BLOCK_STATE)).get() : null;
   }

   public void setBlockState(BlockState p_270267_) {
      this.f_19804_.m_135381_(BLOCK_STATE, Optional.of(p_270267_));
   }

   public void m_8119_() {
      if (!this.m_20068_()) {
         this.m_20256_(this.m_20184_().m_82520_(0.0, -0.04, 0.0));
      }

      this.m_6478_(MoverType.SELF, this.m_20184_());
      this.m_20256_(this.m_20184_().m_82490_(0.98));
      if (this.m_20096_() && this.f_19797_ > this.duration) {
         this.m_146870_();
      }

      if (this.f_19797_ > 300) {
         this.m_146870_();
      }
   }

   protected void m_7380_(CompoundTag p_31973_) {
      BlockState blockState = this.getBlockState();
      if (blockState != null) {
         p_31973_.m_128365_("block_state", NbtUtils.m_129202_(blockState));
      }

      p_31973_.m_128405_("Time", this.duration);
   }

   protected void m_7378_(CompoundTag p_31964_) {
      this.setBlockState(NbtUtils.m_129241_(p_31964_.m_128469_("block_state")));
      this.duration = p_31964_.m_128451_("Time");
   }

   public boolean m_6051_() {
      return false;
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }
}

package com.bobmowzie.mowziesmobs.server.entity.effects;

import java.util.Optional;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
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
import net.minecraftforge.network.NetworkHooks;

public class EntityFallingBlock extends Entity {
   public static float GRAVITY = 0.1F;
   public double prevMotionX;
   public double prevMotionY;
   public double prevMotionZ;
   private static final EntityDataAccessor<Optional<BlockState>> BLOCK_STATE = SynchedEntityData.m_135353_(
      EntityFallingBlock.class, EntityDataSerializers.f_135034_
   );
   private static final EntityDataAccessor<Integer> DURATION = SynchedEntityData.m_135353_(EntityFallingBlock.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Integer> TICKS_EXISTED = SynchedEntityData.m_135353_(EntityFallingBlock.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<String> MODE = SynchedEntityData.m_135353_(EntityFallingBlock.class, EntityDataSerializers.f_135030_);
   private static final EntityDataAccessor<Float> ANIM_V_Y = SynchedEntityData.m_135353_(EntityFallingBlock.class, EntityDataSerializers.f_135029_);
   public float animY = 0.0F;
   public float prevAnimY = 0.0F;

   public EntityFallingBlock(EntityType<?> entityTypeIn, Level worldIn) {
      super(entityTypeIn, worldIn);
      this.setBlock(Blocks.f_50493_.m_49966_());
      this.setDuration(70);
   }

   public EntityFallingBlock(EntityType<?> entityTypeIn, Level worldIn, int duration, BlockState blockState) {
      super(entityTypeIn, worldIn);
      this.setBlock(blockState);
      this.setDuration(duration);
   }

   public EntityFallingBlock(EntityType<?> entityTypeIn, Level worldIn, BlockState blockState, float vy) {
      super(entityTypeIn, worldIn);
      this.setBlock(blockState);
      this.setMode(EntityFallingBlock.EnumFallingBlockMode.POPUP_ANIM);
      this.setAnimVY(vy);
   }

   public void onAddedToWorld() {
      if (this.m_20184_().m_7096_() > 0.0 || this.m_20184_().m_7094_() > 0.0) {
         this.m_146922_((float)((180.0 / Math.PI) * Math.atan2(this.m_20184_().m_7096_(), this.m_20184_().m_7094_())));
      }

      this.m_146926_(this.m_146909_() + this.f_19796_.m_188501_() * 360.0F);
      super.onAddedToWorld();
   }

   public void m_8119_() {
      if (this.getMode() == EntityFallingBlock.EnumFallingBlockMode.POPUP_ANIM) {
         this.m_20334_(0.0, 0.0, 0.0);
      }

      this.prevMotionX = this.m_20184_().f_82479_;
      this.prevMotionY = this.m_20184_().f_82480_;
      this.prevMotionZ = this.m_20184_().f_82481_;
      super.m_8119_();
      if (this.getMode() == EntityFallingBlock.EnumFallingBlockMode.MOBILE) {
         this.m_20256_(this.m_20184_().m_82492_(0.0, (double)GRAVITY, 0.0));
         if (this.f_19861_) {
            this.m_20256_(this.m_20184_().m_82490_(0.7));
         } else {
            this.m_146926_(this.m_146909_() + 15.0F);
         }

         this.m_6478_(MoverType.SELF, this.m_20184_());
         if (this.f_19797_ > this.getDuration()) {
            this.m_146870_();
         }
      } else {
         float animVY = this.getAnimVY();
         this.prevAnimY = this.animY;
         this.animY += animVY;
         this.setAnimVY(animVY - GRAVITY);
         if ((double)this.animY < -0.5) {
            this.m_146870_();
         }
      }
   }

   protected void m_8097_() {
      this.m_20088_().m_135372_(BLOCK_STATE, Optional.of(Blocks.f_50493_.m_49966_()));
      this.m_20088_().m_135372_(DURATION, 70);
      this.m_20088_().m_135372_(TICKS_EXISTED, 0);
      this.m_20088_().m_135372_(MODE, EntityFallingBlock.EnumFallingBlockMode.MOBILE.toString());
      this.m_20088_().m_135372_(ANIM_V_Y, 1.0F);
   }

   protected void m_7378_(CompoundTag compound) {
      Tag blockStateCompound = compound.m_128423_("block");
      if (blockStateCompound != null) {
         BlockState blockState = NbtUtils.m_129241_((CompoundTag)blockStateCompound);
         this.setBlock(blockState);
      }

      this.setDuration(compound.m_128451_("duration"));
      this.f_19797_ = compound.m_128451_("ticksExisted");
      this.m_20088_().m_135381_(MODE, compound.m_128461_("mode"));
      this.setAnimVY(compound.m_128457_("vy"));
   }

   protected void m_7380_(CompoundTag compound) {
      BlockState blockState = this.getBlock();
      if (blockState != null) {
         compound.m_128365_("block", NbtUtils.m_129202_(blockState));
      }

      compound.m_128405_("duration", this.getDuration());
      compound.m_128405_("ticksExisted", this.f_19797_);
      compound.m_128359_("mode", (String)this.m_20088_().m_135370_(MODE));
      compound.m_128350_("vy", (Float)this.m_20088_().m_135370_(ANIM_V_Y));
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public BlockState getBlock() {
      Optional<BlockState> bsOp = (Optional<BlockState>)this.m_20088_().m_135370_(BLOCK_STATE);
      return bsOp.orElse(null);
   }

   public void setBlock(BlockState block) {
      this.m_20088_().m_135381_(BLOCK_STATE, Optional.of(block));
   }

   public int getDuration() {
      return (Integer)this.m_20088_().m_135370_(DURATION);
   }

   public void setDuration(int duration) {
      this.m_20088_().m_135381_(DURATION, duration);
   }

   public int getTicksExisted() {
      return (Integer)this.m_20088_().m_135370_(TICKS_EXISTED);
   }

   public void setTicksExisted(int ticksExisted) {
      this.m_20088_().m_135381_(TICKS_EXISTED, ticksExisted);
   }

   public EntityFallingBlock.EnumFallingBlockMode getMode() {
      String mode = (String)this.m_20088_().m_135370_(MODE);
      return mode.isEmpty()
         ? EntityFallingBlock.EnumFallingBlockMode.MOBILE
         : EntityFallingBlock.EnumFallingBlockMode.valueOf((String)this.m_20088_().m_135370_(MODE));
   }

   private void setMode(EntityFallingBlock.EnumFallingBlockMode mode) {
      this.m_20088_().m_135381_(MODE, mode.toString());
   }

   public float getAnimVY() {
      return (Float)this.m_20088_().m_135370_(ANIM_V_Y);
   }

   private void setAnimVY(float vy) {
      this.m_20088_().m_135381_(ANIM_V_Y, vy);
   }

   public static enum EnumFallingBlockMode {
      MOBILE,
      POPUP_ANIM;
   }
}

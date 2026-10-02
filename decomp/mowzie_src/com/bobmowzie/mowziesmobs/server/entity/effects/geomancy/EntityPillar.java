package com.bobmowzie.mowziesmobs.server.entity.effects.geomancy;

import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityMagicEffect;
import com.bobmowzie.mowziesmobs.server.entity.sculptor.EntitySculptor;
import java.util.HashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class EntityPillar extends EntityGeomancyBase {
   private static final EntityDataAccessor<Float> HEIGHT = SynchedEntityData.m_135353_(EntityPillar.class, EntityDataSerializers.f_135029_);
   private static final EntityDataAccessor<Boolean> RISING = SynchedEntityData.m_135353_(EntityPillar.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Boolean> FALLING = SynchedEntityData.m_135353_(EntityPillar.class, EntityDataSerializers.f_135035_);
   public static final float RISING_SPEED = 0.25F;
   public float prevPrevHeight = 0.0F;
   public float prevHeight = 0.0F;
   public static final HashMap<EntityGeomancyBase.GeomancyTier, Integer> SIZE_MAP = new HashMap<>();
   private EntityPillarPiece currentPiece;

   public EntityPillar(EntityType<? extends EntityMagicEffect> type, Level worldIn) {
      super(type, worldIn);
   }

   public EntityPillar(EntityType<? extends EntityPillar> type, Level world, LivingEntity caster, BlockState blockState, BlockPos pos) {
      super(type, world, caster, blockState, pos);
      this.setDeathTime(300);
   }

   public boolean checkCanSpawn() {
      return !this.f_19853_.m_45976_(EntityPillar.class, this.m_20191_().m_82406_(0.01)).isEmpty()
         ? false
         : this.f_19853_.m_45756_(this, this.m_20191_().m_82406_(0.01));
   }

   public boolean m_7337_(Entity p_20303_) {
      return false;
   }

   @Override
   public boolean m_5829_() {
      return false;
   }

   @Override
   public void m_8119_() {
      if (this.caster instanceof EntitySculptor sculptor && sculptor.getPillar() == null) {
         sculptor.setPillar(this);
      }

      this.prevPrevHeight = this.prevHeight;
      this.prevHeight = this.getHeight();
      if (!this.f_19853_.m_5776_()) {
         if (this.isRising()) {
            float height = this.getHeight();
            if ((double)height == 0.0) {
               this.currentPiece = new EntityPillarPiece(
                  (EntityType<?>)EntityHandler.PILLAR_PIECE.get(), this.f_19853_, this, new Vec3(this.m_20185_(), this.m_20186_() - 1.0, this.m_20189_())
               );
               this.f_19853_.m_7967_(this.currentPiece);
            }

            height += 0.25F;
            this.setHeight(height);
            if (Math.floor((double)height) > Math.floor((double)this.prevHeight)) {
               this.currentPiece = new EntityPillarPiece(
                  (EntityType<?>)EntityHandler.PILLAR_PIECE.get(),
                  this.f_19853_,
                  this,
                  new Vec3(this.m_20185_(), this.m_20186_() + Math.floor((double)height) - 1.0, this.m_20189_())
               );
               this.f_19853_.m_7967_(this.currentPiece);
            }

            for (EntityBoulderProjectile boulder : this.f_19853_.m_45976_(EntityBoulderProjectile.class, this.m_20191_().m_82406_(0.1F))) {
               if (!boulder.isTravelling() && boulder.getTier().ordinal() > this.getTier().ordinal()) {
                  this.setTier(boulder.getTier());
                  boulder.explode();
               }
            }
         } else if (this.isFalling()) {
            float heightx = this.getHeight();
            heightx -= 0.25F;
            this.setHeight(heightx);
            if ((double)heightx <= 0.0) {
               this.m_142687_(RemovalReason.DISCARDED);
            }
         }
      }

      this.m_20011_(this.m_142242_());
      AABB popUpBounds = this.m_20191_().m_82406_(0.1F);

      for (Entity entity : this.f_19853_.m_45933_(this, popUpBounds)) {
         if (entity.m_6087_() && !(entity instanceof EntityBoulderBase) && !(entity instanceof EntityPillar) && !(entity instanceof EntityPillarPiece)) {
            double belowAmount = entity.m_20186_() - (this.m_20186_() + (double)this.getHeight());
            if (belowAmount < 0.0) {
               entity.m_6478_(MoverType.PISTON, new Vec3(0.0, -belowAmount, 0.0));
            }
         }
      }

      super.m_8119_();
      if (this.hasSyncedCaster && (this.caster == null || this.caster.m_213877_())) {
         this.explode();
      }
   }

   protected AABB m_142242_() {
      if (this.tickTimer() <= 1) {
         return super.m_142242_();
      } else {
         float f = (float)SIZE_MAP.get(this.getTier()).intValue() / 2.0F - 0.05F;
         return new AABB(
            this.m_20185_() - (double)f,
            this.m_20186_(),
            this.m_20189_() - (double)f,
            this.m_20185_() + (double)f,
            this.m_20186_() + (double)this.getHeight() - 0.05F,
            this.m_20189_() + (double)f
         );
      }
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
      this.m_20088_().m_135372_(HEIGHT, 0.0F);
      this.m_20088_().m_135372_(RISING, true);
      this.m_20088_().m_135372_(FALLING, false);
   }

   public float getHeight() {
      return (Float)this.m_20088_().m_135370_(HEIGHT);
   }

   public void setHeight(float height) {
      this.m_20088_().m_135381_(HEIGHT, height);
   }

   public void stopRising() {
      this.m_20088_().m_135381_(RISING, false);
      this.m_20011_(this.m_142242_());
      this.currentPiece = new EntityPillarPiece(
         (EntityType<?>)EntityHandler.PILLAR_PIECE.get(),
         this.f_19853_,
         this,
         new Vec3(this.m_20185_(), this.m_20186_() + (double)this.getHeight() - 1.0, this.m_20189_())
      );
      this.f_19853_.m_7967_(this.currentPiece);
   }

   public boolean isRising() {
      return (Boolean)this.m_20088_().m_135370_(RISING);
   }

   public void startFalling() {
      this.m_20088_().m_135381_(RISING, false);
      this.m_20088_().m_135381_(FALLING, true);
   }

   public boolean isFalling() {
      return (Boolean)this.m_20088_().m_135370_(FALLING);
   }

   @Override
   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128350_("height", this.getHeight());
      compound.m_128379_("rising", this.isRising());
      compound.m_128379_("falling", this.isFalling());
   }

   @Override
   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.setHeight(compound.m_128457_("height"));
      this.m_20088_().m_135381_(RISING, compound.m_128471_("rising"));
      this.m_20088_().m_135381_(FALLING, compound.m_128471_("falling"));
   }

   @Override
   public boolean doRemoveTimer() {
      return !(this.caster instanceof EntitySculptor);
   }

   static {
      SIZE_MAP.put(EntityGeomancyBase.GeomancyTier.NONE, 1);
      SIZE_MAP.put(EntityGeomancyBase.GeomancyTier.SMALL, 2);
      SIZE_MAP.put(EntityGeomancyBase.GeomancyTier.MEDIUM, 3);
      SIZE_MAP.put(EntityGeomancyBase.GeomancyTier.LARGE, 4);
      SIZE_MAP.put(EntityGeomancyBase.GeomancyTier.HUGE, 5);
   }
}

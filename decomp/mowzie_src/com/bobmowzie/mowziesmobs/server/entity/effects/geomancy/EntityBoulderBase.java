package com.bobmowzie.mowziesmobs.server.entity.effects.geomancy;

import com.bobmowzie.mowziesmobs.client.particle.ParticleHandler;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleBase;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleData;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleComponent;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityCameraShake;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import java.util.HashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class EntityBoulderBase extends EntityGeomancyBase {
   private static final byte ACTIVATE_ID = 67;
   public BlockState storedBlock;
   public float animationOffset = 0.0F;
   public EntityGeomancyBase.GeomancyTier boulderSize = EntityGeomancyBase.GeomancyTier.SMALL;
   protected int finishedRisingTick = 4;
   public int risingTick = 0;
   public boolean active = false;
   public static final HashMap<EntityGeomancyBase.GeomancyTier, EntityDimensions> SIZE_MAP = new HashMap<>();

   public EntityBoulderBase(EntityType<? extends EntityBoulderBase> type, Level world) {
      super(type, world);
      this.finishedRisingTick = 4;
      this.animationOffset = this.f_19796_.m_188501_() * 8.0F;
      this.setTier(EntityGeomancyBase.GeomancyTier.SMALL);
      this.m_20011_(this.m_142242_());
   }

   public EntityBoulderBase(
      EntityType<? extends EntityBoulderBase> type, Level world, LivingEntity caster, BlockState blockState, BlockPos pos, EntityGeomancyBase.GeomancyTier tier
   ) {
      super(type, world, caster, blockState, pos);
      this.finishedRisingTick = 4;
      this.animationOffset = this.f_19796_.m_188501_() * 8.0F;
      this.setTier(tier);
      this.setSizeParams();
      this.m_20011_(this.m_142242_());
   }

   @Override
   public boolean m_5829_() {
      return this.active;
   }

   public boolean checkCanSpawn() {
      return !this.f_19853_.m_45976_(EntityBoulderBase.class, this.m_20191_().m_82406_(0.01)).isEmpty()
         ? false
         : this.f_19853_.m_45756_(this, this.m_20191_().m_82406_(0.01));
   }

   public void setSizeParams() {
      EntityGeomancyBase.GeomancyTier size = this.getTier();
      if (size == EntityGeomancyBase.GeomancyTier.MEDIUM) {
         this.finishedRisingTick = 8;
      } else if (size == EntityGeomancyBase.GeomancyTier.LARGE) {
         this.finishedRisingTick = 12;
      } else if (size == EntityGeomancyBase.GeomancyTier.HUGE) {
         this.finishedRisingTick = 90;
      }
   }

   @NotNull
   protected AABB m_142242_() {
      EntityDimensions dim = SIZE_MAP.get(this.getTier());
      return dim.m_20393_(this.m_20182_());
   }

   public void activate() {
      this.active = true;
      this.f_19853_.m_7605_(this, (byte)67);
   }

   @Override
   public void m_7822_(byte id) {
      super.m_7822_(id);
      if (id == 67) {
         this.active = true;
      }
   }

   @Override
   public void m_8119_() {
      if (this.f_19803_) {
         this.setSizeParams();
         this.boulderSize = this.getTier();
      }

      if (this.storedBlock == null) {
         this.storedBlock = this.getBlock();
      }

      super.m_8119_();
      this.m_20011_(this.m_142242_());
      this.m_6478_(MoverType.SELF, this.m_20184_());
      if (this.boulderSize == EntityGeomancyBase.GeomancyTier.HUGE && this.risingTick < this.finishedRisingTick) {
         float f = this.m_20205_() / 2.0F;
         AABB aabb = new AABB(
            this.m_20185_() - (double)f,
            this.m_20186_() - 0.5,
            this.m_20189_() - (double)f,
            this.m_20185_() + (double)f,
            this.m_20186_() + (double)Math.min((float)this.risingTick / (float)this.finishedRisingTick * 3.5F, 3.5F),
            this.m_20189_() + (double)f
         );
         this.m_20011_(aabb);
      }

      if (this.risingTick < this.finishedRisingTick) {
         for (Entity entity : this.f_19853_.m_45933_(this, this.m_20191_())) {
            if (entity.m_6087_() && !(entity instanceof EntityBoulderBase)) {
               if (this.boulderSize != EntityGeomancyBase.GeomancyTier.HUGE) {
                  entity.m_6478_(
                     MoverType.SHULKER_BOX,
                     new Vec3(0.0, 2.0 * Math.pow(2.0, (double)(-this.risingTick) * (0.6 - 0.1 * (double)this.boulderSize.ordinal())), 0.0)
                  );
               } else {
                  entity.m_6478_(MoverType.SHULKER_BOX, new Vec3(0.0, 0.6F, 0.0));
               }
            }
         }
      }

      if (this.risingTick == 1) {
         for (int i = 0; (float)i < 20.0F * this.m_20205_(); i++) {
            Vec3 particlePos = new Vec3((double)this.f_19796_.m_188501_() * 1.3 * (double)this.m_20205_(), 0.0, 0.0);
            particlePos = particlePos.m_82524_((float)((double)(this.f_19796_.m_188501_() * 2.0F) * Math.PI));
            this.f_19853_
               .m_7106_(
                  new BlockParticleOption(ParticleTypes.f_123794_, this.storedBlock),
                  this.m_20185_() + particlePos.f_82479_,
                  this.m_20186_() - 1.0,
                  this.m_20189_() + particlePos.f_82481_,
                  particlePos.f_82479_,
                  2.0,
                  particlePos.f_82481_
               );
         }

         if (this.boulderSize == EntityGeomancyBase.GeomancyTier.SMALL) {
            this.m_5496_((SoundEvent)MMSounds.EFFECT_GEOMANCY_SMALL_CRASH.get(), 1.5F, 1.3F);
            this.m_5496_((SoundEvent)MMSounds.EFFECT_GEOMANCY_MAGIC_SMALL.get(), 1.5F, 1.0F);
         } else if (this.boulderSize == EntityGeomancyBase.GeomancyTier.MEDIUM) {
            this.m_5496_((SoundEvent)MMSounds.EFFECT_GEOMANCY_HIT_MEDIUM_2.get(), 1.5F, 1.5F);
            this.m_5496_((SoundEvent)MMSounds.EFFECT_GEOMANCY_MAGIC_SMALL.get(), 1.5F, 0.8F);
         } else if (this.boulderSize == EntityGeomancyBase.GeomancyTier.LARGE) {
            this.m_5496_((SoundEvent)MMSounds.EFFECT_GEOMANCY_HIT_MEDIUM_1.get(), 1.5F, 0.9F);
            this.m_5496_((SoundEvent)MMSounds.EFFECT_GEOMANCY_MAGIC_BIG.get(), 1.5F, 1.5F);
            EntityCameraShake.cameraShake(this.f_19853_, this.m_20182_(), 10.0F, 0.05F, 0, 20);
         } else if (this.boulderSize == EntityGeomancyBase.GeomancyTier.HUGE) {
            this.m_5496_((SoundEvent)MMSounds.EFFECT_GEOMANCY_MAGIC_BIG.get(), 2.0F, 0.5F);
            this.m_5496_((SoundEvent)MMSounds.EFFECT_GEOMANCY_RUMBLE_1.get(), 2.0F, 0.8F);
            EntityCameraShake.cameraShake(this.f_19853_, this.m_20182_(), 15.0F, 0.05F, 50, 30);
         }

         if (this.f_19853_.f_46443_) {
            AdvancedParticleBase.spawnParticle(
               this.f_19853_,
               (ParticleType<AdvancedParticleData>)ParticleHandler.RING2.get(),
               this.m_20185_(),
               this.m_20186_() - 0.9F,
               this.m_20189_(),
               0.0,
               0.0,
               0.0,
               false,
               0.0,
               Math.PI / 2,
               0.0,
               0.0,
               3.5,
               0.83F,
               1.0,
               0.39F,
               1.0,
               1.0,
               (double)((int)(5.0F + 2.0F * this.m_20205_())),
               true,
               true,
               new ParticleComponent[]{
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA, ParticleComponent.KeyTrack.startAndEnd(1.0F, 0.0F), false
                  ),
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.SCALE,
                     ParticleComponent.KeyTrack.startAndEnd(0.0F, (1.0F + 0.5F * this.m_20205_()) * 10.0F),
                     false
                  )
               }
            );
         }
      }

      if (this.risingTick == 30 && this.boulderSize == EntityGeomancyBase.GeomancyTier.HUGE) {
         this.m_5496_((SoundEvent)MMSounds.EFFECT_GEOMANCY_RUMBLE_2.get(), 2.0F, 0.7F);
      }

      int dripTick = this.risingTick - 2;
      if (this.boulderSize == EntityGeomancyBase.GeomancyTier.HUGE) {
         dripTick -= 20;
      }

      int dripNumber = (int)((double)(this.m_20205_() * 6.0F) * Math.pow(1.03 + 0.04 / (double)this.m_20205_(), (double)(-dripTick)));
      if (dripNumber >= 1 && dripTick > 0) {
         dripNumber = (int)((float)dripNumber * this.f_19796_.m_188501_());

         for (int i = 0; i < dripNumber; i++) {
            Vec3 particlePos = new Vec3((double)this.f_19796_.m_188501_() * 0.6 * (double)this.m_20205_(), 0.0, 0.0);
            particlePos = particlePos.m_82524_((float)((double)(this.f_19796_.m_188501_() * 2.0F) * Math.PI));
            float offsetY;
            if (this.boulderSize == EntityGeomancyBase.GeomancyTier.HUGE && this.risingTick < this.finishedRisingTick) {
               offsetY = this.f_19796_.m_188501_() * (this.m_20206_() - 1.0F)
                  - this.m_20206_() * (float)(this.finishedRisingTick - this.risingTick) / (float)this.finishedRisingTick;
            } else {
               offsetY = this.f_19796_.m_188501_() * (this.m_20206_() - 1.0F);
            }

            this.f_19853_
               .m_7106_(
                  new BlockParticleOption(ParticleTypes.f_123794_, this.storedBlock),
                  this.m_20185_() + particlePos.f_82479_,
                  this.m_20186_() + (double)offsetY,
                  this.m_20189_() + particlePos.f_82481_,
                  0.0,
                  -1.0,
                  0.0
               );
         }
      }

      if (this.active) {
         this.risingTick++;
      }
   }

   @Override
   protected void explode() {
      if (this.active) {
         super.explode();
      } else {
         this.m_146870_();
      }
   }

   static {
      SIZE_MAP.put(EntityGeomancyBase.GeomancyTier.NONE, EntityDimensions.m_20395_(1.0F, 1.0F));
      SIZE_MAP.put(EntityGeomancyBase.GeomancyTier.SMALL, EntityDimensions.m_20395_(1.0F, 1.0F));
      SIZE_MAP.put(EntityGeomancyBase.GeomancyTier.MEDIUM, EntityDimensions.m_20395_(2.0F, 1.5F));
      SIZE_MAP.put(EntityGeomancyBase.GeomancyTier.LARGE, EntityDimensions.m_20395_(3.0F, 2.5F));
      SIZE_MAP.put(EntityGeomancyBase.GeomancyTier.HUGE, EntityDimensions.m_20395_(4.0F, 3.5F));
   }
}

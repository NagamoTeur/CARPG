package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.client.particle.LightningParticle;
import com.github.L_Ender.cataclysm.client.tool.ControlledAnimation;
import com.github.L_Ender.cataclysm.entity.Pet.The_Baby_Leviathan_Entity;
import com.github.L_Ender.cataclysm.util.CMDamageTypes;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

public class Mini_Abyss_Blast_Entity extends Entity {
   public static final double RADIUS = 15.0;
   public LivingEntity caster;
   public double endPosX;
   public double endPosY;
   public double endPosZ;
   public double collidePosX;
   public double collidePosY;
   public double collidePosZ;
   public double prevCollidePosX;
   public double prevCollidePosY;
   public double prevCollidePosZ;
   public float renderYaw;
   public float renderPitch;
   public ControlledAnimation appear = new ControlledAnimation(3);
   public boolean on = true;
   public Direction blockSide = null;
   private static final EntityDataAccessor<Float> YAW = SynchedEntityData.m_135353_(Mini_Abyss_Blast_Entity.class, EntityDataSerializers.f_135029_);
   private static final EntityDataAccessor<Float> PITCH = SynchedEntityData.m_135353_(Mini_Abyss_Blast_Entity.class, EntityDataSerializers.f_135029_);
   private static final EntityDataAccessor<Integer> DURATION = SynchedEntityData.m_135353_(Mini_Abyss_Blast_Entity.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Integer> CASTER = SynchedEntityData.m_135353_(Mini_Abyss_Blast_Entity.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Float> BEAMDIRECTION = SynchedEntityData.m_135353_(Mini_Abyss_Blast_Entity.class, EntityDataSerializers.f_135029_);
   public float prevYaw;
   public float prevPitch;
   @OnlyIn(Dist.CLIENT)
   private Vec3[] attractorPos;

   public Mini_Abyss_Blast_Entity(EntityType<? extends Mini_Abyss_Blast_Entity> type, Level world) {
      super(type, world);
      this.f_19811_ = true;
      if (world.f_46443_) {
         this.attractorPos = new Vec3[]{new Vec3(0.0, 0.0, 0.0)};
      }
   }

   public Mini_Abyss_Blast_Entity(
      EntityType<? extends Mini_Abyss_Blast_Entity> type,
      Level world,
      LivingEntity caster,
      double x,
      double y,
      double z,
      float yaw,
      float pitch,
      int duration,
      float direction
   ) {
      this(type, world);
      this.caster = caster;
      this.setYaw(yaw);
      this.setPitch(pitch);
      this.setDuration(duration);
      this.setBeamDirection(direction);
      this.m_6034_(x, y, z);
      this.calculateEndPos();
      if (!world.f_46443_) {
         this.setCasterID(caster.m_19879_());
      }
   }

   public PushReaction m_7752_() {
      return PushReaction.IGNORE;
   }

   public void m_8119_() {
      super.m_8119_();
      this.prevCollidePosX = this.collidePosX;
      this.prevCollidePosY = this.collidePosY;
      this.prevCollidePosZ = this.collidePosZ;
      this.prevYaw = this.renderYaw;
      this.prevPitch = this.renderPitch;
      this.f_19854_ = this.m_20185_();
      this.f_19855_ = this.m_20186_();
      this.f_19856_ = this.m_20189_();
      if (this.f_19797_ == 1 && this.f_19853_.f_46443_) {
         this.caster = (LivingEntity)this.f_19853_.m_6815_(this.getCasterID());
      }

      if (!this.f_19853_.f_46443_ && this.caster instanceof The_Baby_Leviathan_Entity) {
         this.updateWithHarbinger();
      }

      if (this.caster != null) {
         this.renderYaw = (float)((double)(this.caster.f_20885_ + this.getBeamDirection()) * Math.PI / 180.0);
         this.renderPitch = (float)((double)(-this.caster.m_146909_()) * Math.PI / 180.0);
      }

      if (!this.on && this.appear.getTimer() == 0) {
         this.m_146870_();
      }

      if (this.on && this.f_19797_ > 20) {
         this.appear.increaseTimer();
      } else {
         this.appear.decreaseTimer();
      }

      if (this.caster != null && !this.caster.m_6084_()) {
         this.m_146870_();
      }

      if (this.f_19797_ > 20) {
         this.calculateEndPos();
         List<LivingEntity> hit = this.raytraceEntities(
               this.f_19853_, new Vec3(this.m_20185_(), this.m_20186_(), this.m_20189_()), new Vec3(this.endPosX, this.endPosY, this.endPosZ)
            )
            .entities;
         if (this.blockSide != null) {
            this.spawnExplosionParticles(3);
         }

         if (!this.f_19853_.f_46443_) {
            for (LivingEntity target : hit) {
               if (this.caster != null && !this.caster.m_7307_(target) && target != this.caster) {
                  target.m_6469_(CMDamageTypes.causeLaserDamage(this, this.caster).m_19380_(), 5.0F);
               }
            }
         }
      }

      if (this.f_19797_ - 20 > this.getDuration()) {
         this.on = false;
      }
   }

   private void spawnExplosionParticles(int amount) {
      for (int i = 0; i < amount; i++) {
         float velocity = 1.0F;
         float yaw = (float)((double)(this.f_19796_.m_188501_() * 2.0F) * Math.PI);
         float motionY = this.f_19796_.m_188501_() * 0.08F;
         float motionX = 1.0F * Mth.m_14089_(yaw);
         float motionZ = 1.0F * Mth.m_14031_(yaw);
         this.f_19853_
            .m_7106_(
               new LightningParticle.OrbData(102, 26, 204),
               this.collidePosX,
               this.collidePosY + 0.1,
               this.collidePosZ,
               (double)motionX,
               (double)motionY,
               (double)motionZ
            );
      }
   }

   protected void m_8097_() {
      this.f_19804_.m_135372_(YAW, 0.0F);
      this.f_19804_.m_135372_(PITCH, 0.0F);
      this.f_19804_.m_135372_(DURATION, 0);
      this.f_19804_.m_135372_(CASTER, -1);
      this.f_19804_.m_135372_(BEAMDIRECTION, 90.0F);
   }

   public float getYaw() {
      return (Float)this.f_19804_.m_135370_(YAW);
   }

   public void setYaw(float yaw) {
      this.f_19804_.m_135381_(YAW, yaw);
   }

   public float getPitch() {
      return (Float)this.f_19804_.m_135370_(PITCH);
   }

   public void setPitch(float pitch) {
      this.f_19804_.m_135381_(PITCH, pitch);
   }

   public int getDuration() {
      return (Integer)this.f_19804_.m_135370_(DURATION);
   }

   public void setDuration(int duration) {
      this.f_19804_.m_135381_(DURATION, duration);
   }

   public float getBeamDirection() {
      return (Float)this.f_19804_.m_135370_(BEAMDIRECTION);
   }

   public void setBeamDirection(float beamDirection) {
      this.f_19804_.m_135381_(BEAMDIRECTION, beamDirection);
   }

   public int getCasterID() {
      return (Integer)this.f_19804_.m_135370_(CASTER);
   }

   public void setCasterID(int id) {
      this.f_19804_.m_135381_(CASTER, id);
   }

   protected void m_7378_(CompoundTag compound) {
      this.setYaw(compound.m_128457_("Yaw"));
      this.setPitch(compound.m_128457_("Pitch"));
      this.setDuration(compound.m_128451_("Duration"));
      this.setBeamDirection(compound.m_128457_("BeamDirection"));
   }

   protected void m_7380_(CompoundTag compound) {
      compound.m_128350_("Yaw", this.getYaw());
      compound.m_128350_("Pitch", this.getPitch());
      compound.m_128405_("Duration", this.getDuration());
      compound.m_128350_("BeamDirection", this.getBeamDirection());
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   private void calculateEndPos() {
      if (this.f_19853_.m_5776_()) {
         this.endPosX = this.m_20185_() + 15.0 * Math.cos((double)this.renderYaw) * Math.cos((double)this.renderPitch);
         this.endPosZ = this.m_20189_() + 15.0 * Math.sin((double)this.renderYaw) * Math.cos((double)this.renderPitch);
         this.endPosY = this.m_20186_() + 15.0 * Math.sin((double)this.renderPitch);
      } else {
         this.endPosX = this.m_20185_() + 15.0 * Math.cos((double)this.getYaw()) * Math.cos((double)this.getPitch());
         this.endPosZ = this.m_20189_() + 15.0 * Math.sin((double)this.getYaw()) * Math.cos((double)this.getPitch());
         this.endPosY = this.m_20186_() + 15.0 * Math.sin((double)this.getPitch());
      }
   }

   public Mini_Abyss_Blast_Entity.LaserbeamHitResult raytraceEntities(Level world, Vec3 from, Vec3 to) {
      Mini_Abyss_Blast_Entity.LaserbeamHitResult result = new Mini_Abyss_Blast_Entity.LaserbeamHitResult();
      result.setBlockHit(world.m_45547_(new ClipContext(from, to, Block.COLLIDER, Fluid.NONE, this)));
      if (result.blockHit != null) {
         Vec3 hitVec = result.blockHit.m_82450_();
         this.collidePosX = hitVec.f_82479_;
         this.collidePosY = hitVec.f_82480_;
         this.collidePosZ = hitVec.f_82481_;
         this.blockSide = result.blockHit.m_82434_();
      } else {
         this.collidePosX = this.endPosX;
         this.collidePosY = this.endPosY;
         this.collidePosZ = this.endPosZ;
         this.blockSide = null;
      }

      for (LivingEntity entity : world.m_45976_(
         LivingEntity.class,
         new AABB(
               Math.min(this.m_20185_(), this.collidePosX),
               Math.min(this.m_20186_(), this.collidePosY),
               Math.min(this.m_20189_(), this.collidePosZ),
               Math.max(this.m_20185_(), this.collidePosX),
               Math.max(this.m_20186_(), this.collidePosY),
               Math.max(this.m_20189_(), this.collidePosZ)
            )
            .m_82377_(2.0, 2.0, 2.0)
      )) {
         if (entity != this.caster) {
            float pad = entity.m_6143_() + 0.15F;
            AABB aabb = entity.m_20191_().m_82377_((double)pad, (double)pad, (double)pad);
            Optional<Vec3> hit = aabb.m_82371_(from, to);
            if (aabb.m_82390_(from)) {
               result.addEntityHit(entity);
            } else if (hit.isPresent()) {
               result.addEntityHit(entity);
            }
         }
      }

      return result;
   }

   public void m_7334_(Entity entityIn) {
   }

   public boolean m_5829_() {
      return false;
   }

   public boolean m_6094_() {
      return false;
   }

   public boolean m_6000_(double p_37588_, double p_37589_, double p_37590_) {
      return true;
   }

   public boolean m_6783_(double distance) {
      return distance < 1024.0;
   }

   private void updateWithHarbinger() {
      this.setYaw((float)((double)(this.caster.f_20885_ + this.getBeamDirection()) * Math.PI / 180.0));
      this.setPitch((float)((double)(-this.caster.m_146909_()) * Math.PI / 180.0));
      float f = -Mth.m_14031_(this.caster.m_146908_() * (float) (Math.PI / 180.0)) * Mth.m_14089_(this.caster.m_146909_() * (float) (Math.PI / 180.0));
      float f2 = Mth.m_14089_(this.caster.m_146908_() * (float) (Math.PI / 180.0)) * Mth.m_14089_(this.caster.m_146909_() * (float) (Math.PI / 180.0));
      this.m_6034_(this.caster.m_20185_(), this.caster.m_20186_() + 0.125, this.caster.m_20189_());
   }

   public static class LaserbeamHitResult {
      private BlockHitResult blockHit;
      private final List<LivingEntity> entities = new ArrayList<>();

      public BlockHitResult getBlockHit() {
         return this.blockHit;
      }

      public void setBlockHit(HitResult rayTraceResult) {
         if (rayTraceResult.m_6662_() == Type.BLOCK) {
            this.blockHit = (BlockHitResult)rayTraceResult;
         }
      }

      public void addEntityHit(LivingEntity entity) {
         this.entities.add(entity);
      }
   }
}

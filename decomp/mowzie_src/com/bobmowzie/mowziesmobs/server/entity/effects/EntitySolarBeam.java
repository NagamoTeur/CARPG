package com.bobmowzie.mowziesmobs.server.entity.effects;

import com.bobmowzie.mowziesmobs.client.model.tools.ControlledAnimation;
import com.bobmowzie.mowziesmobs.client.particle.ParticleHandler;
import com.bobmowzie.mowziesmobs.client.particle.ParticleOrb;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleBase;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleData;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleComponent;
import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoPlayer;
import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoRenderPlayer;
import com.bobmowzie.mowziesmobs.server.capability.CapabilityHandler;
import com.bobmowzie.mowziesmobs.server.capability.PlayerCapability;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.damage.DamageUtil;
import com.bobmowzie.mowziesmobs.server.entity.LeaderSunstrikeImmune;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthi;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
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

public class EntitySolarBeam extends Entity {
   public static final double RADIUS_UMVUTHI = 30.0;
   public static final double RADIUS_PLAYER = 20.0;
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
   private static final EntityDataAccessor<Float> YAW = SynchedEntityData.m_135353_(EntitySolarBeam.class, EntityDataSerializers.f_135029_);
   private static final EntityDataAccessor<Float> PITCH = SynchedEntityData.m_135353_(EntitySolarBeam.class, EntityDataSerializers.f_135029_);
   private static final EntityDataAccessor<Integer> DURATION = SynchedEntityData.m_135353_(EntitySolarBeam.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Boolean> HAS_PLAYER = SynchedEntityData.m_135353_(EntitySolarBeam.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Integer> CASTER = SynchedEntityData.m_135353_(EntitySolarBeam.class, EntityDataSerializers.f_135028_);
   public float prevYaw;
   public float prevPitch;
   @OnlyIn(Dist.CLIENT)
   private Vec3[] attractorPos;

   public EntitySolarBeam(EntityType<? extends EntitySolarBeam> type, Level world) {
      super(type, world);
      this.f_19811_ = true;
      if (world.f_46443_) {
         this.attractorPos = new Vec3[]{new Vec3(0.0, 0.0, 0.0)};
      }
   }

   public EntitySolarBeam(
      EntityType<? extends EntitySolarBeam> type, Level world, LivingEntity caster, double x, double y, double z, float yaw, float pitch, int duration
   ) {
      this(type, world);
      this.caster = caster;
      this.setYaw(yaw);
      this.setPitch(pitch);
      this.setDuration(duration);
      this.m_6034_(x, y, z);
      this.calculateEndPos();
      this.m_5496_((SoundEvent)MMSounds.LASER.get(), 2.0F, 1.0F);
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

      if (!this.f_19853_.f_46443_) {
         if (this.getHasPlayer()) {
            this.updateWithPlayer();
         } else if (this.caster instanceof EntityUmvuthi) {
            this.updateWithUmvuthi();
         }
      }

      if (this.caster != null) {
         this.renderYaw = (float)(((double)this.caster.f_20885_ + 90.0) * Math.PI / 180.0);
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

      if (this.f_19853_.f_46443_ && this.f_19797_ <= 10 && this.caster != null) {
         int particleCount = 8;

         while (--particleCount != 0) {
            double radius = (double)(2.0F * this.caster.m_20205_());
            double yaw = (double)(this.f_19796_.m_188501_() * 2.0F) * Math.PI;
            double pitch = (double)(this.f_19796_.m_188501_() * 2.0F) * Math.PI;
            double ox = radius * Math.sin(yaw) * Math.sin(pitch);
            double oy = radius * Math.cos(pitch);
            double oz = radius * Math.cos(yaw) * Math.sin(pitch);
            double rootX = this.caster.m_20185_();
            double rootY = this.caster.m_20186_() + (double)(this.caster.m_20206_() / 2.0F) + 0.3F;
            double rootZ = this.caster.m_20189_();
            if (this.getHasPlayer()) {
               if (this.caster instanceof Player
                  && (this.caster != Minecraft.m_91087_().f_91074_ || Minecraft.m_91087_().f_91066_.m_92176_() != CameraType.FIRST_PERSON)) {
                  GeckoPlayer geckoPlayer = GeckoPlayer.getGeckoPlayer((Player)this.caster, GeckoPlayer.Perspective.THIRD_PERSON);
                  if (geckoPlayer != null) {
                     GeckoRenderPlayer renderPlayer = (GeckoRenderPlayer)geckoPlayer.getPlayerRenderer();
                     if (renderPlayer.betweenHandsPos != null) {
                        rootX += renderPlayer.betweenHandsPos.m_7096_();
                        rootY += renderPlayer.betweenHandsPos.m_7098_();
                        rootZ += renderPlayer.betweenHandsPos.m_7094_();
                     }
                  }
               }

               this.attractorPos[0] = new Vec3(rootX, rootY, rootZ);
            } else if (this.caster instanceof EntityUmvuthi umvuthi) {
               if (umvuthi.headPos != null && umvuthi.headPos[0] != null) {
                  this.attractorPos[0] = ((EntityUmvuthi)this.caster).headPos[0];
                  rootX = this.attractorPos[0].m_7096_();
                  rootY = this.attractorPos[0].m_7098_();
                  rootZ = this.attractorPos[0].m_7094_();
               }
            } else {
               this.attractorPos[0] = new Vec3(rootX, rootY, rootZ);
            }

            AdvancedParticleBase.spawnParticle(
               this.f_19853_,
               (ParticleType<AdvancedParticleData>)ParticleHandler.ORB2.get(),
               rootX + ox,
               rootY + oy,
               rootZ + oz,
               0.0,
               0.0,
               0.0,
               true,
               0.0,
               0.0,
               0.0,
               0.0,
               5.0,
               1.0,
               1.0,
               1.0,
               1.0,
               1.0,
               7.0,
               true,
               false,
               new ParticleComponent[]{
                  new ParticleComponent.Attractor(this.attractorPos, 1.7F, 0.0F, ParticleComponent.Attractor.EnumAttractorBehavior.EXPONENTIAL),
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA,
                     new ParticleComponent.KeyTrack(new float[]{0.0F, 0.8F}, new float[]{0.0F, 1.0F}),
                     false
                  ),
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.SCALE,
                     new ParticleComponent.KeyTrack(new float[]{3.0F, 6.0F}, new float[]{0.0F, 1.0F}),
                     false
                  )
               }
            );
         }
      }

      if (this.f_19797_ > 20) {
         this.calculateEndPos();
         List<LivingEntity> hit = this.raytraceEntities(
               this.f_19853_,
               new Vec3(this.m_20185_(), this.m_20186_(), this.m_20189_()),
               new Vec3(this.endPosX, this.endPosY, this.endPosZ),
               false,
               true,
               true
            )
            .entities;
         if (this.blockSide != null) {
            this.spawnExplosionParticles(2);
         }

         if (!this.f_19853_.f_46443_) {
            for (LivingEntity target : hit) {
               if (!(this.caster instanceof EntityUmvuthi) || !(target instanceof LeaderSunstrikeImmune)) {
                  float damageFire = 1.0F;
                  float damageMob = 1.5F;
                  if (this.caster instanceof EntityUmvuthi) {
                     damageFire = (float)((double)damageFire * (Double)ConfigHandler.COMMON.MOBS.UMVUTHI.combatConfig.attackMultiplier.get());
                     damageMob = (float)((double)damageMob * (Double)ConfigHandler.COMMON.MOBS.UMVUTHI.combatConfig.attackMultiplier.get());
                  }

                  if (this.caster instanceof Player) {
                     damageFire = (float)(
                        (double)damageFire * (Double)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SUNS_BLESSING.sunsBlessingAttackMultiplier.get() * 0.75
                     );
                     damageMob = (float)(
                        (double)damageMob * (Double)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SUNS_BLESSING.sunsBlessingAttackMultiplier.get() * 0.75
                     );
                  }

                  DamageUtil.dealMixedDamage(target, DamageSource.m_19340_(this, this.caster), damageMob, DamageSource.f_19307_, damageFire);
               }
            }
         } else if (this.f_19797_ - 15 < this.getDuration()) {
            int particleCount = 4;

            while (particleCount-- > 0) {
               double radius = 1.0;
               double yaw = (double)((float)((double)(this.f_19796_.m_188501_() * 2.0F) * Math.PI));
               double pitch = (double)((float)((double)(this.f_19796_.m_188501_() * 2.0F) * Math.PI));
               double ox = (double)((float)(radius * Math.sin(yaw) * Math.sin(pitch)));
               double oy = (double)((float)(radius * Math.cos(pitch)));
               double oz = (double)((float)(radius * Math.cos(yaw) * Math.sin(pitch)));
               double o2x = (double)((float)(-1.0 * Math.cos((double)this.getYaw()) * Math.cos((double)this.getPitch())));
               double o2y = (double)((float)(-1.0 * Math.sin((double)this.getPitch())));
               double o2z = (double)((float)(-1.0 * Math.sin((double)this.getYaw()) * Math.cos((double)this.getPitch())));
               this.f_19853_
                  .m_7106_(
                     new ParticleOrb.OrbData(
                        (float)(this.collidePosX + o2x + ox), (float)(this.collidePosY + o2y + oy), (float)(this.collidePosZ + o2z + oz), 15.0F
                     ),
                     this.m_20185_() + o2x + ox,
                     this.m_20186_() + o2y + oy,
                     this.m_20189_() + o2z + oz,
                     0.0,
                     0.0,
                     0.0
                  );
            }

            particleCount = 4;

            while (particleCount-- > 0) {
               double radius = 2.0;
               double yaw = (double)(this.f_19796_.m_188501_() * 2.0F) * Math.PI;
               double pitch = (double)(this.f_19796_.m_188501_() * 2.0F) * Math.PI;
               double ox = radius * Math.sin(yaw) * Math.sin(pitch);
               double oy = radius * Math.cos(pitch);
               double oz = radius * Math.cos(yaw) * Math.sin(pitch);
               double o2x = -1.0 * Math.cos((double)this.getYaw()) * Math.cos((double)this.getPitch());
               double o2y = -1.0 * Math.sin((double)this.getPitch());
               double o2z = -1.0 * Math.sin((double)this.getYaw()) * Math.cos((double)this.getPitch());
               this.f_19853_
                  .m_7106_(
                     new ParticleOrb.OrbData(
                        (float)(this.collidePosX + o2x + ox), (float)(this.collidePosY + o2y + oy), (float)(this.collidePosZ + o2z + oz), 20.0F
                     ),
                     this.collidePosX + o2x,
                     this.collidePosY + o2y,
                     this.collidePosZ + o2z,
                     0.0,
                     0.0,
                     0.0
                  );
            }
         }
      }

      if (this.f_19797_ - 20 > this.getDuration()) {
         this.on = false;
      }
   }

   private void spawnExplosionParticles(int amount) {
      for (int i = 0; i < amount; i++) {
         float velocity = 0.1F;
         float yaw = (float)((double)(this.f_19796_.m_188501_() * 2.0F) * Math.PI);
         float motionY = this.f_19796_.m_188501_() * 0.08F;
         float motionX = 0.1F * Mth.m_14089_(yaw);
         float motionZ = 0.1F * Mth.m_14031_(yaw);
         this.f_19853_
            .m_7106_(ParticleTypes.f_123744_, this.collidePosX, this.collidePosY + 0.1, this.collidePosZ, (double)motionX, (double)motionY, (double)motionZ);
      }

      for (int i = 0; i < amount / 2; i++) {
         this.f_19853_.m_7106_(ParticleTypes.f_123756_, this.collidePosX, this.collidePosY + 0.1, this.collidePosZ, 0.0, 0.0, 0.0);
      }
   }

   protected void m_8097_() {
      this.m_20088_().m_135372_(YAW, 0.0F);
      this.m_20088_().m_135372_(PITCH, 0.0F);
      this.m_20088_().m_135372_(DURATION, 0);
      this.m_20088_().m_135372_(HAS_PLAYER, false);
      this.m_20088_().m_135372_(CASTER, -1);
   }

   public float getYaw() {
      return (Float)this.m_20088_().m_135370_(YAW);
   }

   public void setYaw(float yaw) {
      this.m_20088_().m_135381_(YAW, yaw);
   }

   public float getPitch() {
      return (Float)this.m_20088_().m_135370_(PITCH);
   }

   public void setPitch(float pitch) {
      this.m_20088_().m_135381_(PITCH, pitch);
   }

   public int getDuration() {
      return (Integer)this.m_20088_().m_135370_(DURATION);
   }

   public void setDuration(int duration) {
      this.m_20088_().m_135381_(DURATION, duration);
   }

   public boolean getHasPlayer() {
      return (Boolean)this.m_20088_().m_135370_(HAS_PLAYER);
   }

   public void setHasPlayer(boolean player) {
      this.m_20088_().m_135381_(HAS_PLAYER, player);
   }

   public int getCasterID() {
      return (Integer)this.m_20088_().m_135370_(CASTER);
   }

   public void setCasterID(int id) {
      this.m_20088_().m_135381_(CASTER, id);
   }

   protected void m_7378_(CompoundTag nbt) {
   }

   protected void m_7380_(CompoundTag nbt) {
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   private void calculateEndPos() {
      double radius = this.caster instanceof EntityUmvuthi ? 30.0 : 20.0;
      if (this.f_19853_.m_5776_()) {
         this.endPosX = this.m_20185_() + radius * Math.cos((double)this.renderYaw) * Math.cos((double)this.renderPitch);
         this.endPosZ = this.m_20189_() + radius * Math.sin((double)this.renderYaw) * Math.cos((double)this.renderPitch);
         this.endPosY = this.m_20186_() + radius * Math.sin((double)this.renderPitch);
      } else {
         this.endPosX = this.m_20185_() + radius * Math.cos((double)this.getYaw()) * Math.cos((double)this.getPitch());
         this.endPosZ = this.m_20189_() + radius * Math.sin((double)this.getYaw()) * Math.cos((double)this.getPitch());
         this.endPosY = this.m_20186_() + radius * Math.sin((double)this.getPitch());
      }
   }

   public EntitySolarBeam.SolarbeamHitResult raytraceEntities(
      Level world, Vec3 from, Vec3 to, boolean stopOnLiquid, boolean ignoreBlockWithoutBoundingBox, boolean returnLastUncollidableBlock
   ) {
      EntitySolarBeam.SolarbeamHitResult result = new EntitySolarBeam.SolarbeamHitResult();
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
            .m_82377_(1.0, 1.0, 1.0)
      )) {
         if (entity != this.caster) {
            float pad = entity.m_6143_() + 0.5F;
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

   public boolean m_6087_() {
      return false;
   }

   public boolean m_6094_() {
      return false;
   }

   public boolean m_6783_(double distance) {
      return distance < 1024.0;
   }

   private void updateWithPlayer() {
      this.setYaw((float)((double)(this.caster.f_20885_ + 90.0F) * Math.PI / 180.0));
      this.setPitch((float)((double)(-this.caster.m_146909_()) * Math.PI / 180.0));
      Vec3 vecOffset = this.caster.m_20154_().m_82541_().m_82490_(1.0);
      this.m_6034_(
         this.caster.m_20185_() + vecOffset.m_7096_(), this.caster.m_20186_() + 1.2F + vecOffset.m_7098_(), this.caster.m_20189_() + vecOffset.m_7094_()
      );
   }

   private void updateWithUmvuthi() {
      this.setYaw((float)((double)(this.caster.f_20885_ + 90.0F) * Math.PI / 180.0));
      this.setPitch((float)((double)(-this.caster.m_146909_()) * Math.PI / 180.0));
      Vec3 vecOffset1 = new Vec3(0.0, 0.0, 0.6).m_82524_((float)Math.toRadians((double)(-this.caster.m_146908_())));
      Vec3 vecOffset2 = new Vec3(1.2, 0.0, 0.0).m_82524_(-this.getYaw()).m_82496_(this.getPitch());
      this.m_6034_(
         this.caster.m_20185_() + vecOffset1.m_7096_() + vecOffset2.m_7096_(),
         this.caster.m_20186_() + 1.5 + vecOffset1.m_7098_() + vecOffset2.m_7098_(),
         this.caster.m_20189_() + vecOffset1.m_7094_() + vecOffset2.m_7094_()
      );
   }

   public void m_142687_(RemovalReason reason) {
      super.m_142687_(reason);
      if (this.caster instanceof Player) {
         PlayerCapability.IPlayerCapability playerCapability = CapabilityHandler.getCapability(this.caster, CapabilityHandler.PLAYER_CAPABILITY);
         if (playerCapability != null) {
            playerCapability.setUsingSolarBeam(false);
         }
      }
   }

   public static class SolarbeamHitResult {
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

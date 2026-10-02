package com.aizistral.enigmaticlegacy.entities;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.helpers.AOEMiningHelper;
import com.aizistral.enigmaticlegacy.objects.Vector3;
import com.aizistral.enigmaticlegacy.objects.WitherExplosion;
import com.aizistral.enigmaticlegacy.packets.clients.PacketWitherParticles;
import com.aizistral.enigmaticlegacy.registries.EnigmaticEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.PacketDistributor.TargetPoint;

public class UltimateWitherSkullEntity extends AbstractHurtingProjectile {
   private static final EntityDataAccessor<Boolean> INVULNERABLE = SynchedEntityData.m_135353_(UltimateWitherSkullEntity.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Integer> BOUND_TARGET = SynchedEntityData.m_135353_(UltimateWitherSkullEntity.class, EntityDataSerializers.f_135028_);
   int targetID;
   LivingEntity target;

   public UltimateWitherSkullEntity(EntityType<? extends UltimateWitherSkullEntity> p_i50147_1_, Level p_i50147_2_) {
      super(p_i50147_1_, p_i50147_2_);
      this.targetID = -1;
      this.target = null;
   }

   public UltimateWitherSkullEntity(Level worldIn, LivingEntity shooter) {
      super(EnigmaticEntities.ULTIMATE_WITHER_SKULL, shooter, 0.0, 0.0, 0.0, worldIn);
      this.f_36813_ = 0.0;
      this.f_36814_ = 0.0;
      this.f_36815_ = 0.0;
      this.targetID = -1;
      this.target = null;
      this.m_20256_(new Vec3(0.0, 0.0, 0.0));
   }

   public UltimateWitherSkullEntity(Level worldIn, LivingEntity shooter, LivingEntity target) {
      this(worldIn, shooter);
      this.target = target;
      this.targetID = target.m_19879_();
      this.f_19804_.m_135381_(BOUND_TARGET, this.targetID);
   }

   public void initMotion(LivingEntity player, double accelX, double accelY, double accelZ, float modifier) {
      this.m_6034_(this.m_20185_(), this.m_20186_(), this.m_20189_());
      accelX += this.f_19796_.m_188583_() * 0.4;
      accelY += this.f_19796_.m_188583_() * 0.4;
      accelZ += this.f_19796_.m_188583_() * 0.4;
      double d0 = Math.sqrt(accelX * accelX + accelY * accelY + accelZ * accelZ);
      this.f_36813_ = accelX / d0 * 0.1;
      this.f_36814_ = accelY / d0 * 0.1;
      this.f_36815_ = accelZ / d0 * 0.1;
      this.f_36813_ *= (double)modifier;
      this.f_36814_ *= (double)modifier;
      this.f_36815_ *= (double)modifier;
   }

   protected float m_6884_() {
      return super.m_6884_();
   }

   public boolean m_6060_() {
      return false;
   }

   public float m_7077_(Explosion explosionIn, BlockGetter worldIn, BlockPos pos, BlockState blockStateIn, FluidState fluidState, float explosionPower) {
      return this.isSkullInvulnerable() && !blockStateIn.m_204336_(BlockTags.f_13070_) ? Math.min(0.8F, explosionPower) : explosionPower;
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.f_19797_ > 10 && this.f_19797_ < 400) {
         if (this.target == null) {
            int targetId = (Integer)this.f_19804_.m_135370_(BOUND_TARGET);
            if (targetId != -1) {
               try {
                  this.target = (LivingEntity)this.f_19853_.m_6815_(targetId);
               } catch (Exception var8) {
                  var8.printStackTrace();
               }
            }
         }

         if (this.target != null && this.target.m_6084_()) {
            Vector3 thisVec = Vector3.fromEntityCenter(this);
            Vector3 targetVec = Vector3.fromEntityCenter(this.target);
            Vector3 diffVec = targetVec.subtract(thisVec);
            Vector3 motionVec = diffVec.normalize().multiply(0.6);
            double distance = (double)this.m_20270_(this.target);
            Vector3 formerMotion = new Vector3(this.m_20184_()).multiply(0.6);
            if (distance < 6.0 && distance != 0.0) {
               this.m_20256_(
                  new Vec3(
                        formerMotion.x + motionVec.x * 2.0 / distance,
                        formerMotion.y + motionVec.y * 2.0 / distance,
                        formerMotion.z + motionVec.z * 2.0 / distance
                     )
                     .m_82541_()
                     .m_82542_(1.4, 1.4, 1.4)
               );
            }

            this.f_36813_ = motionVec.x / 3.0;
            this.f_36814_ = motionVec.y / 3.0;
            this.f_36815_ = motionVec.z / 3.0;
         }
      }

      if (!this.f_19853_.f_46443_ && this.m_37282_() != null) {
         if (this.f_19797_ < 10) {
            Vector3 res = AOEMiningHelper.calcRayTrace(this.f_19853_, (Player)this.m_37282_(), Fluid.NONE, 128.0);
            this.initMotion((LivingEntity)this.m_37282_(), res.x - this.m_20185_(), res.y - this.m_20186_(), res.z - this.m_20189_(), 0.1F);
         } else {
            if (this.f_19797_ == 10) {
               Vector3 res = AOEMiningHelper.calcRayTrace(this.f_19853_, (Player)this.m_37282_(), Fluid.NONE, 128.0);
               this.initMotion((LivingEntity)this.m_37282_(), res.x - this.m_20185_(), res.y - this.m_20186_(), res.z - this.m_20189_(), 1.5F);
            } else if (this.f_19797_ >= 400) {
               this.m_6532_(BlockHitResult.m_82426_(this.m_20182_(), Direction.DOWN, this.m_20183_()));
            }
         }
      }
   }

   protected void m_6532_(HitResult result) {
      if (!this.f_19853_.f_46443_) {
         this.clearInvincibility();
         if (result.m_6662_() == Type.ENTITY) {
            Entity entity = ((EntityHitResult)result).m_82443_();
            if (entity == this.m_37282_()) {
               return;
            }

            if (this.m_37282_() != null) {
               if (entity.m_6469_(DamageSource.m_19367_(this, this.m_37282_()), this.isSkullInvulnerable() ? 24.0F : 8.0F) && entity.m_6084_()) {
                  this.m_19970_((LivingEntity)this.m_37282_(), entity);
               }
            } else {
               entity.m_6469_(DamageSource.f_19319_, this.isSkullInvulnerable() ? 16.0F : 5.0F);
            }

            if (entity instanceof LivingEntity) {
               ((LivingEntity)entity).m_7292_(new MobEffectInstance(MobEffects.f_19615_, 600, 1));
            }
         }

         float explosionRadius = this.isSkullInvulnerable() ? 1.5F : 1.0F;
         WitherExplosion explosion = new WitherExplosion(
            this.f_19853_, this, this.m_20185_(), this.m_20186_(), this.m_20189_(), explosionRadius, false, BlockInteraction.DESTROY
         );
         if (!ForgeEventFactory.onExplosionStart(this.f_19853_, explosion)) {
            explosion.m_46061_();
            explosion.m_46075_(true);

            for (ServerPlayer serverPlayer : this.f_19853_.m_7654_().m_129880_(this.f_19853_.m_46472_()).m_6907_()) {
               if (serverPlayer.m_20275_(explosion.getPosition().f_82479_, explosion.getPosition().f_82480_, explosion.getPosition().f_82481_) < 4096.0) {
                  serverPlayer.f_8906_
                     .m_9829_(
                        new ClientboundExplodePacket(
                           explosion.getPosition().f_82479_,
                           explosion.getPosition().f_82480_,
                           explosion.getPosition().f_82481_,
                           explosionRadius,
                           explosion.m_46081_(),
                           (Vec3)explosion.m_46078_().get(serverPlayer)
                        )
                     );
               }
            }
         }

         EnigmaticLegacy.packetInstance
            .send(
               PacketDistributor.NEAR.with(() -> new TargetPoint(this.m_20185_(), this.m_20186_(), this.m_20189_(), 64.0, this.f_19853_.m_46472_())),
               new PacketWitherParticles(
                  this.m_20185_(), this.m_20186_() + (double)(this.m_20206_() / 2.0F), this.m_20189_(), this.isSkullInvulnerable() ? 20 : 16, false
               )
            );
         this.m_146870_();
      }
   }

   public void clearInvincibility() {
      for (LivingEntity entity : this.f_19853_.m_45976_(LivingEntity.class, SuperpositionHandler.getBoundingBoxAroundEntity(this, 3.0))) {
         entity.f_19802_ = 0;
      }
   }

   public boolean m_6087_() {
      return false;
   }

   public boolean m_6469_(DamageSource source, float amount) {
      return false;
   }

   protected void m_8097_() {
      this.f_19804_.m_135372_(INVULNERABLE, false);
      this.f_19804_.m_135372_(BOUND_TARGET, -1);
   }

   public boolean isSkullInvulnerable() {
      return (Boolean)this.f_19804_.m_135370_(INVULNERABLE);
   }

   public void setSkullInvulnerable(boolean invulnerable) {
      this.f_19804_.m_135381_(INVULNERABLE, invulnerable);
   }

   protected boolean m_5931_() {
      return false;
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }
}

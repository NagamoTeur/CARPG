package com.github.L_Ender.cataclysm.entity.effect;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.client.particle.StormParticle;
import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Ancient_Ancient_Remnant_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.Ancient_Remnant.Ancient_Remnant_Entity;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModEntities;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class Sandstorm_Entity extends Entity {
   private static final EntityDataAccessor<Optional<UUID>> CREATOR_ID = SynchedEntityData.m_135353_(Sandstorm_Entity.class, EntityDataSerializers.f_135041_);
   protected static final EntityDataAccessor<Integer> LIFESPAN = SynchedEntityData.m_135353_(Sandstorm_Entity.class, EntityDataSerializers.f_135028_);
   protected static final EntityDataAccessor<Float> OFFSET = SynchedEntityData.m_135353_(Sandstorm_Entity.class, EntityDataSerializers.f_135029_);
   private static final EntityDataAccessor<Boolean> POWERD = SynchedEntityData.m_135353_(Sandstorm_Entity.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Integer> STATE = SynchedEntityData.m_135353_(Sandstorm_Entity.class, EntityDataSerializers.f_135028_);
   public AnimationState SpawnAnimationState = new AnimationState();
   public AnimationState DespawnAnimationState = new AnimationState();

   public Sandstorm_Entity(EntityType<?> entityTypeIn, Level worldIn) {
      super(entityTypeIn, worldIn);
   }

   public Sandstorm_Entity(Level worldIn, double x, double y, double z, int lifespan, float offset, UUID casterIn) {
      this((EntityType<?>)ModEntities.SANDSTORM.get(), worldIn);
      this.setCreatorEntityUUID(casterIn);
      this.setLifespan(lifespan);
      this.m_6034_(x, y, z);
      this.setState(1);
      this.setOffset(offset);
   }

   public Packet<ClientGamePacketListener> m_5654_() {
      return new ClientboundAddEntityPacket(this);
   }

   public void m_8119_() {
      super.m_8119_();
      this.updateMotion();
      Entity owner = this.getCreatorEntity();
      if (owner != null && !owner.m_6084_()) {
         this.m_146870_();
      }

      if (this.f_19853_.f_46443_) {
         float ran = 0.04F;
         float r = 0.89F + this.f_19796_.m_188501_() * ran;
         float g = 0.85F + this.f_19796_.m_188501_() * ran;
         float b = 0.69F + this.f_19796_.m_188501_() * ran * 1.5F;
         this.f_19853_
            .m_7106_(
               new StormParticle.OrbData(r, g, b, 2.75F + this.f_19796_.m_188501_() * 0.6F, 3.75F + this.f_19796_.m_188501_() * 0.6F, this.m_19879_()),
               this.m_20185_(),
               this.m_20186_(),
               this.m_20189_(),
               0.0,
               0.0,
               0.0
            );
         this.f_19853_
            .m_7106_(
               new StormParticle.OrbData(r, g, b, 2.5F + this.f_19796_.m_188501_() * 0.45F, 3.0F + this.f_19796_.m_188501_() * 0.45F, this.m_19879_()),
               this.m_20185_(),
               this.m_20186_(),
               this.m_20189_(),
               0.0,
               0.0,
               0.0
            );
         this.f_19853_
            .m_7106_(
               new StormParticle.OrbData(r, g, b, 2.25F + this.f_19796_.m_188501_() * 0.45F, 2.25F + this.f_19796_.m_188501_() * 0.45F, this.m_19879_()),
               this.m_20185_(),
               this.m_20186_(),
               this.m_20189_(),
               0.0,
               0.0,
               0.0
            );
         this.f_19853_
            .m_7106_(
               new StormParticle.OrbData(r, g, b, 1.25F + this.f_19796_.m_188501_() * 0.45F, 1.25F + this.f_19796_.m_188501_() * 0.45F, this.m_19879_()),
               this.m_20185_(),
               this.m_20186_(),
               this.m_20189_(),
               0.0,
               0.0,
               0.0
            );
         if (this.getState() == 1 && this.getLifespan() < 295) {
            this.setState(0);
         }

         if (this.getState() == 0 && this.getLifespan() < 10) {
            this.setState(2);
         }
      }

      if (!this.m_20067_() && this.f_19853_.f_46443_) {
         Cataclysm.PROXY.playWorldSound(this, (byte)2);
      }

      for (LivingEntity entity : this.f_19853_.m_45976_(LivingEntity.class, this.m_20191_().m_82377_(0.2, 0.0, 0.2))) {
         if ((!(entity instanceof Player) || !((Player)entity).m_150110_().f_35934_)
            && entity != owner
            && entity.m_6084_()
            && !entity.m_20147_()
            && this.f_19797_ % 3 == 0) {
            if (owner == null) {
               boolean flag = entity.m_6469_(DamageSource.f_19319_, (float)CMConfig.Sandstormdamage);
               if (flag) {
                  MobEffectInstance effectinstance = new MobEffectInstance((MobEffect)ModEffect.EFFECTCURSE_OF_DESERT.get(), 200, 0);
                  entity.m_7292_(effectinstance);
               }
            } else {
               if (owner.m_7307_(entity)) {
                  return;
               }

               boolean flag = entity.m_6469_(DamageSource.m_19367_(this, owner), (float)CMConfig.Sandstormdamage);
               if (flag) {
                  MobEffectInstance effectinstance = new MobEffectInstance((MobEffect)ModEffect.EFFECTCURSE_OF_DESERT.get(), 200, 0);
                  entity.m_7292_(effectinstance);
               }
            }
         }
      }

      this.setLifespan(this.getLifespan() - 1);
      if (this.getLifespan() <= 0) {
         Cataclysm.PROXY.clearSoundCacheFor(this);
         this.m_142687_(RemovalReason.DISCARDED);
      }
   }

   public int getLifespan() {
      return (Integer)this.f_19804_.m_135370_(LIFESPAN);
   }

   public void setLifespan(int i) {
      this.f_19804_.m_135381_(LIFESPAN, i);
   }

   public float getOffset() {
      return (Float)this.f_19804_.m_135370_(OFFSET);
   }

   public void setOffset(float i) {
      this.f_19804_.m_135381_(OFFSET, i);
   }

   public UUID getCreatorEntityUUID() {
      return (UUID)((Optional)this.f_19804_.m_135370_(CREATOR_ID)).orElse(null);
   }

   public void setCreatorEntityUUID(UUID id) {
      this.f_19804_.m_135381_(CREATOR_ID, Optional.ofNullable(id));
   }

   public Entity getCreatorEntity() {
      UUID uuid = this.getCreatorEntityUUID();
      return uuid != null && !this.f_19853_.f_46443_ ? ((ServerLevel)this.f_19853_).m_8791_(uuid) : null;
   }

   private void updateMotion() {
      Entity owner = this.getCreatorEntity();
      if (owner != null) {
         if (owner instanceof Ancient_Ancient_Remnant_Entity || owner instanceof Ancient_Remnant_Entity) {
            Vec3 center = owner.m_20182_().m_82520_(0.0, 0.0, 0.0);
            float radius = 8.0F;
            float speed = (float)this.f_19797_ * 0.04F;
            float offset = this.getOffset();
            Vec3 orbit = new Vec3(
               center.f_82479_ + Math.cos((double)(speed + offset)) * (double)radius,
               center.f_82480_,
               center.f_82481_ + Math.sin((double)(speed + offset)) * (double)radius
            );
            this.m_20219_(orbit);
         }

         if (owner instanceof Player) {
            Vec3 center = owner.m_20182_().m_82520_(0.0, 0.0, 0.0);
            float radius = 6.0F;
            float speed = (float)this.f_19797_ * 0.04F;
            float offset = this.getOffset();
            Vec3 orbit = new Vec3(
               center.f_82479_ + Math.cos((double)(speed + offset)) * (double)radius,
               center.f_82480_,
               center.f_82481_ + Math.sin((double)(speed + offset)) * (double)radius
            );
            this.m_20219_(orbit);
         }
      }
   }

   protected void m_8097_() {
      this.f_19804_.m_135372_(CREATOR_ID, Optional.empty());
      this.f_19804_.m_135372_(LIFESPAN, 300);
      this.f_19804_.m_135372_(OFFSET, 0.0F);
      this.f_19804_.m_135372_(STATE, 0);
   }

   public AnimationState getAnimationState(String input) {
      if (input == "spawn") {
         return this.SpawnAnimationState;
      } else {
         return input == "despawn" ? this.DespawnAnimationState : new AnimationState();
      }
   }

   public void m_7350_(EntityDataAccessor<?> p_21104_) {
      if (STATE.equals(p_21104_) && this.f_19853_.f_46443_) {
         switch (this.getState()) {
            case 0:
               this.stopAllAnimationStates();
               break;
            case 1:
               this.stopAllAnimationStates();
               this.SpawnAnimationState.m_216982_(this.f_19797_);
               break;
            case 2:
               this.stopAllAnimationStates();
               this.DespawnAnimationState.m_216982_(this.f_19797_);
         }
      }

      super.m_7350_(p_21104_);
   }

   public void stopAllAnimationStates() {
      this.DespawnAnimationState.m_216973_();
      this.SpawnAnimationState.m_216973_();
   }

   public int getState() {
      return (Integer)this.f_19804_.m_135370_(STATE);
   }

   public void setState(int state) {
      this.f_19804_.m_135381_(STATE, state);
   }

   protected void m_7378_(CompoundTag compound) {
      this.setLifespan(compound.m_128451_("Lifespan"));
      UUID uuid;
      if (compound.m_128403_("Owner")) {
         uuid = compound.m_128342_("Owner");
      } else {
         String s = compound.m_128461_("Owner");
         uuid = OldUsersConverter.m_11083_(this.m_20194_(), s);
      }

      if (uuid != null) {
         try {
            this.setCreatorEntityUUID(uuid);
         } catch (Throwable var4) {
         }
      }
   }

   protected void m_7380_(CompoundTag compound) {
      compound.m_128405_("Lifespan", this.getLifespan());
      if (this.getCreatorEntityUUID() != null) {
         compound.m_128362_("Owner", this.getCreatorEntityUUID());
      }
   }
}

package com.github.L_Ender.cataclysm.entity.effect;

import com.github.L_Ender.cataclysm.client.particle.StormParticle;
import com.github.L_Ender.cataclysm.init.ModEntities;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Void_Vortex_Entity extends Entity {
   protected static final EntityDataAccessor<Integer> LIFESPAN = SynchedEntityData.m_135353_(Void_Vortex_Entity.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Integer> CASTER = SynchedEntityData.m_135353_(Void_Vortex_Entity.class, EntityDataSerializers.f_135028_);
   private boolean madeOpenNoise = false;
   private boolean madeCloseNoise = false;
   @Nullable
   private LivingEntity owner;

   public Void_Vortex_Entity(EntityType<?> entityTypeIn, Level worldIn) {
      super(entityTypeIn, worldIn);
   }

   public Void_Vortex_Entity(Level worldIn, double x, double y, double z, float p_i47276_8_, LivingEntity casterIn, int span) {
      this((EntityType<?>)ModEntities.VOID_VORTEX.get(), worldIn);
      this.setLifespan(span);
      this.setOwner(casterIn);
      this.m_146922_(p_i47276_8_ * (180.0F / (float)Math.PI));
      this.m_6034_(x, y, z);
   }

   public Packet<ClientGamePacketListener> m_5654_() {
      return new ClientboundAddEntityPacket(this);
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.f_19797_ == 1 && this.getLifespan() == 0) {
         this.setLifespan(60);
      }

      if (!this.madeOpenNoise) {
         this.m_146850_(GameEvent.f_157810_);
         this.m_5496_(SoundEvents.f_11860_, 1.0F, 1.0F + this.f_19796_.m_188501_() * 0.2F);
         this.madeOpenNoise = true;
      }

      if (Math.min(this.f_19797_, this.getLifespan()) >= 16) {
         if (this.f_19853_.f_46443_) {
            float r = 0.4F;
            float g = 0.1F;
            float b = 0.8F;
            this.f_19853_
               .m_7106_(
                  new StormParticle.OrbData(r, g, b, 2.5F + this.f_19796_.m_188501_() * 0.9F, 5.0F + this.f_19796_.m_188501_() * 0.9F, this.m_19879_()),
                  this.m_20185_(),
                  this.m_20186_(),
                  this.m_20189_(),
                  0.0,
                  0.0,
                  0.0
               );
            this.f_19853_
               .m_7106_(
                  new StormParticle.OrbData(r, g, b, 2.25F + this.f_19796_.m_188501_() * 0.6F, 4.25F + this.f_19796_.m_188501_() * 0.6F, this.m_19879_()),
                  this.m_20185_(),
                  this.m_20186_(),
                  this.m_20189_(),
                  0.0,
                  0.0,
                  0.0
               );
            this.f_19853_
               .m_7106_(
                  new StormParticle.OrbData(r, g, b, 2.0F + this.f_19796_.m_188501_() * 0.45F, 3.5F + this.f_19796_.m_188501_() * 0.45F, this.m_19879_()),
                  this.m_20185_(),
                  this.m_20186_(),
                  this.m_20189_(),
                  0.0,
                  0.0,
                  0.0
               );
            this.f_19853_
               .m_7106_(
                  new StormParticle.OrbData(r, g, b, 1.5F + this.f_19796_.m_188501_() * 0.25F, 2.75F + this.f_19796_.m_188501_() * 0.45F, this.m_19879_()),
                  this.m_20185_(),
                  this.m_20186_(),
                  this.m_20189_(),
                  0.0,
                  0.0,
                  0.0
               );
            this.f_19853_
               .m_7106_(
                  new StormParticle.OrbData(r, g, b, 1.25F + this.f_19796_.m_188501_() * 0.25F, 2.0F + this.f_19796_.m_188501_() * 0.45F, this.m_19879_()),
                  this.m_20185_(),
                  this.m_20186_(),
                  this.m_20189_(),
                  0.0,
                  0.0,
                  0.0
               );
            this.f_19853_
               .m_7106_(
                  new StormParticle.OrbData(r, g, b, 1.0F + this.f_19796_.m_188501_() * 0.25F, 1.25F + this.f_19796_.m_188501_() * 0.45F, this.m_19879_()),
                  this.m_20185_(),
                  this.m_20186_(),
                  this.m_20189_(),
                  0.0,
                  0.0,
                  0.0
               );
            this.f_19853_
               .m_7106_(
                  new StormParticle.OrbData(r, g, b, 0.75F + this.f_19796_.m_188501_() * 0.25F, 0.5F + this.f_19796_.m_188501_() * 0.45F, this.m_19879_()),
                  this.m_20185_(),
                  this.m_20186_(),
                  this.m_20189_(),
                  0.0,
                  0.0,
                  0.0
               );
         }

         AABB screamBox = new AABB(
            this.m_20185_() - 3.0, this.m_20186_(), this.m_20189_() - 3.0, this.m_20185_() + 3.0, this.m_20186_() + 15.0, this.m_20189_() + 3.0
         );

         for (LivingEntity entity : this.f_19853_.m_45976_(LivingEntity.class, screamBox)) {
            if (this.owner != null && entity != this.owner) {
               if (entity instanceof Player) {
                  Player player = (Player)entity;
                  if (player.m_150110_().f_35934_) {
                     continue;
                  }
               }

               Vec3 diff = entity.m_20182_().m_82546_(this.m_20182_().m_82520_(0.0, 0.0, 0.0));
               diff = diff.m_82541_().m_82490_(0.075);
               entity.m_20256_(entity.m_20184_().m_82520_(0.0, -2.0, 0.0).m_82546_(diff));
            }
         }
      }

      this.setLifespan(this.getLifespan() - 1);
      if (this.getLifespan() <= 16 && !this.madeCloseNoise) {
         this.m_146850_(GameEvent.f_157810_);
         this.madeCloseNoise = true;
      }

      if (this.getLifespan() <= 0) {
         this.f_19853_.m_46518_(this.owner, this.m_20185_(), this.m_20186_(), this.m_20189_(), 2.0F, false, BlockInteraction.NONE);
         this.m_142687_(RemovalReason.DISCARDED);
      }
   }

   public int getLifespan() {
      return (Integer)this.f_19804_.m_135370_(LIFESPAN);
   }

   public void setLifespan(int i) {
      this.f_19804_.m_135381_(LIFESPAN, i);
   }

   public int getCasterID() {
      return (Integer)this.f_19804_.m_135370_(CASTER);
   }

   public void setCasterID(int id) {
      this.f_19804_.m_135381_(CASTER, id);
   }

   public void setOwner(@Nullable LivingEntity p_19719_) {
      this.owner = p_19719_;
      this.setCasterID(p_19719_ == null ? 0 : p_19719_.m_19879_());
   }

   @Nullable
   public LivingEntity getOwner() {
      if (this.owner == null && this.getCasterID() != 0 && this.f_19853_ instanceof ServerLevel) {
         Entity entity = ((ServerLevel)this.f_19853_).m_6815_(this.getCasterID());
         if (entity instanceof LivingEntity) {
            this.owner = (LivingEntity)entity;
         }
      }

      return this.owner;
   }

   protected void m_8097_() {
      this.f_19804_.m_135372_(LIFESPAN, 300);
      this.f_19804_.m_135372_(CASTER, -1);
   }

   protected void m_7378_(CompoundTag compound) {
      this.setLifespan(compound.m_128451_("Lifespan"));
      this.setCasterID(compound.m_128451_("CasterId"));
   }

   protected void m_7380_(CompoundTag compound) {
      compound.m_128405_("Lifespan", this.getLifespan());
      compound.m_128405_("CasterId", this.getCasterID());
   }
}

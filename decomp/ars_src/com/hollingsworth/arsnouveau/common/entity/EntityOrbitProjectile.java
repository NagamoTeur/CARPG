package com.hollingsworth.arsnouveau.common.entity;

import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketANEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;

public class EntityOrbitProjectile extends EntityProjectileSpell {
   public Entity wardedEntity;
   public int ticksLeft;
   public static final EntityDataAccessor<Integer> OWNER_UUID = SynchedEntityData.m_135353_(EntityOrbitProjectile.class, EntityDataSerializers.f_135028_);
   public static final EntityDataAccessor<Integer> OFFSET = SynchedEntityData.m_135353_(EntityOrbitProjectile.class, EntityDataSerializers.f_135028_);
   public static final EntityDataAccessor<Integer> ACCELERATES = SynchedEntityData.m_135353_(EntityOrbitProjectile.class, EntityDataSerializers.f_135028_);
   public static final EntityDataAccessor<Float> AOE = SynchedEntityData.m_135353_(EntityOrbitProjectile.class, EntityDataSerializers.f_135029_);
   public static final EntityDataAccessor<Integer> TOTAL = SynchedEntityData.m_135353_(EntityOrbitProjectile.class, EntityDataSerializers.f_135028_);
   public int extendTimes;

   public EntityOrbitProjectile(Level worldIn, double x, double y, double z) {
      super((EntityType<? extends EntityProjectileSpell>)ModEntities.ORBIT_SPELL.get(), worldIn, x, y, z);
   }

   public EntityOrbitProjectile(Level worldIn, LivingEntity shooter) {
      super((EntityType<? extends EntityProjectileSpell>)ModEntities.ORBIT_SPELL.get(), worldIn, shooter);
   }

   public EntityOrbitProjectile(Level world, SpellResolver resolver) {
      super((EntityType<? extends EntityProjectileSpell>)ModEntities.ORBIT_SPELL.get(), world, resolver);
   }

   public EntityOrbitProjectile(EntityType<EntityOrbitProjectile> entityWardProjectileEntityType, Level world) {
      super(entityWardProjectileEntityType, world);
   }

   public void setOffset(int offset) {
      this.f_19804_.m_135381_(OFFSET, offset);
   }

   public int getOffset() {
      int val = 15;
      return (Integer)this.f_19804_.m_135370_(OFFSET) * val;
   }

   public void setTotal(int total) {
      this.f_19804_.m_135381_(TOTAL, total);
   }

   public int getTotal() {
      return this.f_19804_.m_135370_(TOTAL) > 0 ? (Integer)this.f_19804_.m_135370_(TOTAL) : 1;
   }

   public void setAccelerates(int accelerates) {
      this.f_19804_.m_135381_(ACCELERATES, accelerates);
   }

   public int getAccelerates() {
      return (Integer)this.f_19804_.m_135370_(ACCELERATES);
   }

   @Deprecated(
      forRemoval = true
   )
   public void setAoe(int aoe) {
      this.f_19804_.m_135381_(AOE, (float)aoe);
   }

   public void setAoe(float aoe) {
      this.f_19804_.m_135381_(AOE, aoe);
   }

   public float getAoe() {
      return (Float)this.f_19804_.m_135370_(AOE);
   }

   public double getRotateSpeed() {
      return 10.0 - (double)this.getAccelerates();
   }

   public double getRadiusMultiplier() {
      return 1.5 + 0.5 * (double)this.getAoe();
   }

   @Override
   public void m_8119_() {
      Entity owner = this.f_19853_.m_6815_(this.getOwnerID());
      if (!this.f_19853_.f_46443_ && owner == null) {
         this.m_142687_(RemovalReason.DISCARDED);
      } else if (owner != null) {
         super.m_8119_();
      }
   }

   @Override
   public Vec3 getNextHitPosition() {
      return this.getAngledPosition(this.f_19797_ + 3);
   }

   @Override
   public void tickNextPosition() {
      this.m_146884_(this.getAngledPosition(this.f_19797_));
   }

   public Vec3 getAngledPosition(int nextTick) {
      double rotateSpeed = this.getRotateSpeed();
      double radiusMultiplier = this.getRadiusMultiplier();
      Entity owner = this.f_19853_.m_6815_(this.getOwnerID());
      return new Vec3(
         owner.m_20185_() - radiusMultiplier * Math.sin((double)nextTick / rotateSpeed + (double)this.getOffset()),
         owner.m_20186_() + 1.0 - (owner.m_6144_() ? 0.25 : 0.0),
         owner.m_20189_() - radiusMultiplier * Math.cos((double)nextTick / rotateSpeed + (double)this.getOffset())
      );
   }

   @Override
   public boolean canTraversePortals() {
      return false;
   }

   @Override
   public int getExpirationTime() {
      return 1200 + 600 * this.extendTimes;
   }

   @Override
   protected void m_6532_(HitResult result) {
      if (!this.f_19853_.f_46443_ && result != null) {
         if (result.m_6662_() == Type.ENTITY) {
            if (((EntityHitResult)result).m_82443_().equals(this.m_37282_())) {
               return;
            }

            if (this.spellResolver != null) {
               this.spellResolver.onResolveEffect(this.f_19853_, result);
               Networking.sendToNearby(
                  this.f_19853_,
                  new BlockPos(result.m_82450_()),
                  new PacketANEffect(PacketANEffect.EffectType.BURST, new BlockPos(result.m_82450_()), this.getParticleColorWrapper())
               );
               this.attemptRemoval();
            }
         } else if (this.numSensitive > 0 && result instanceof BlockHitResult blockraytraceresult && !this.m_213877_()) {
            if (this.spellResolver != null) {
               this.spellResolver.onResolveEffect(this.f_19853_, blockraytraceresult);
            }

            Networking.sendToNearby(
               this.f_19853_,
               ((BlockHitResult)result).m_82425_(),
               new PacketANEffect(PacketANEffect.EffectType.BURST, new BlockPos(result.m_82450_()).m_7495_(), this.getParticleColorWrapper())
            );
            this.attemptRemoval();
         }
      }
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(OWNER_UUID, 0);
      this.f_19804_.m_135372_(OFFSET, 0);
      this.f_19804_.m_135372_(ACCELERATES, 0);
      this.f_19804_.m_135372_(AOE, 0.0F);
      this.f_19804_.m_135372_(TOTAL, 0);
   }

   @Override
   public void m_7380_(CompoundTag tag) {
      super.m_7380_(tag);
      tag.m_128405_("left", this.ticksLeft);
      tag.m_128405_("offset", this.getOffset());
      tag.m_128350_("aoe", this.getAoe());
      tag.m_128405_("accelerate", this.getAccelerates());
      tag.m_128405_("total", this.getTotal());
      tag.m_128405_("ownerID", this.getOwnerID());
   }

   @Override
   public void m_7378_(CompoundTag tag) {
      super.m_7378_(tag);
      this.ticksLeft = tag.m_128451_("left");
      this.setOffset(tag.m_128451_("offset"));
      this.setAoe(tag.m_128457_("aoe"));
      this.setAccelerates(tag.m_128451_("accelerate"));
      this.setOwnerID(tag.m_128451_("ownerID"));
      this.setTotal(tag.m_128451_("total"));
   }

   @Override
   public EntityType<?> m_6095_() {
      return (EntityType<?>)ModEntities.ORBIT_SPELL.get();
   }

   @Override
   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public EntityOrbitProjectile(SpawnEntity packet, Level world) {
      super((EntityType<? extends EntityProjectileSpell>)ModEntities.ORBIT_SPELL.get(), world);
   }

   public int getOwnerID() {
      return (Integer)this.m_20088_().m_135370_(OWNER_UUID);
   }

   public void setOwnerID(int uuid) {
      this.m_20088_().m_135381_(OWNER_UUID, uuid);
   }
}

package com.bobmowzie.mowziesmobs.server.entity.umvuthana;

import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public abstract class EntityUmvuthanaFollower<L extends LivingEntity> extends EntityUmvuthana {
   protected static final Optional<UUID> ABSENT_LEADER = Optional.empty();
   private static final EntityDataAccessor<Optional<UUID>> LEADER = SynchedEntityData.m_135353_(EntityUmvuthanaFollower.class, EntityDataSerializers.f_135041_);
   private final Class<L> leaderClass;
   public int index;
   protected L leader;
   public boolean shouldSetDead;

   public EntityUmvuthanaFollower(EntityType<? extends EntityUmvuthanaFollower> type, Level world, Class<L> leaderClass) {
      this(type, world, leaderClass, null);
   }

   public EntityUmvuthanaFollower(EntityType<? extends EntityUmvuthanaFollower> type, Level world, Class<L> leaderClass, L leader) {
      super(type, world);
      this.leaderClass = leaderClass;
      if (leader != null) {
         this.setLeaderUUID(leader.m_20148_());
      }

      this.shouldSetDead = false;
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
      this.m_20088_().m_135372_(LEADER, ABSENT_LEADER);
   }

   public Optional<UUID> getLeaderUUID() {
      return (Optional<UUID>)this.m_20088_().m_135370_(LEADER);
   }

   public void setLeaderUUID(UUID uuid) {
      this.setLeaderUUID(Optional.of(uuid));
   }

   public void setLeaderUUID(Optional<UUID> uuid) {
      this.m_20088_().m_135381_(LEADER, uuid);
   }

   public ItemStack getPickedResult(HitResult target) {
      return new ItemStack(ItemHandler.UMVUTHANA_SPAWN_EGG);
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      if (this.leader == null && this.getLeaderUUID().isPresent()) {
         this.leader = this.getLeader();
         if (this.leader != null) {
            this.addAsPackMember();
         }
      }

      if (this.shouldSetDead) {
         this.m_146870_();
      }
   }

   @Override
   protected Vec3 updateCirclingPosition(float radius, float speed) {
      LivingEntity target = this.m_5448_();
      return this.leader != null && target != null
         ? this.circleEntityPosition(
            target, radius, speed, true, this.getGroupCircleTick(), (float)((double)(this.index + 1) * (Math.PI * 2) / (double)(this.getPackSize() + 1))
         )
         : super.updateCirclingPosition(radius, speed);
   }

   @Override
   public void m_6667_(DamageSource source) {
      super.m_6667_(source);
      if (this.leader != null) {
         this.removeAsPackMember();
      }
   }

   public void setShouldSetDead() {
      this.shouldSetDead = true;
   }

   public void m_142687_(RemovalReason reason) {
      if (this.leader != null) {
         this.removeAsPackMember();
      }

      super.m_142687_(reason);
   }

   public L getLeader() {
      Optional<UUID> uuid = this.getLeaderUUID();
      if (uuid.isPresent()) {
         for (L entity : this.f_19853_.m_45976_(this.leaderClass, this.m_20191_().m_82377_(32.0, 32.0, 32.0))) {
            if (uuid.get().equals(entity.m_20148_())) {
               return entity;
            }
         }
      }

      return null;
   }

   public boolean m_8023_() {
      return this.leader != null;
   }

   protected abstract int getGroupCircleTick();

   protected abstract int getPackSize();

   protected abstract void addAsPackMember();

   protected abstract void removeAsPackMember();

   @Override
   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      Optional<UUID> leader = this.getLeaderUUID();
      if (leader.isPresent()) {
         compound.m_128359_("leaderUUID", leader.get().toString());
      }
   }

   @Override
   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      String uuid = compound.m_128461_("leaderUUID");
      if (uuid.isEmpty()) {
         this.setLeaderUUID(ABSENT_LEADER);
      } else {
         this.setLeaderUUID(UUID.fromString(uuid));
      }
   }
}

package com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan;

import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModTag;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;

public class The_Leviathan_Tongue_Entity extends Entity {
   private static final EntityDataAccessor<Optional<UUID>> CONTROLLER_UUID = SynchedEntityData.m_135353_(
      The_Leviathan_Tongue_Entity.class, EntityDataSerializers.f_135041_
   );
   private static final EntityDataAccessor<Integer> CONTROLLER_ID = SynchedEntityData.m_135353_(
      The_Leviathan_Tongue_Entity.class, EntityDataSerializers.f_135028_
   );
   private static final EntityDataAccessor<Integer> TARGET_ID = SynchedEntityData.m_135353_(The_Leviathan_Tongue_Entity.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Integer> DURATION = SynchedEntityData.m_135353_(The_Leviathan_Tongue_Entity.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Integer> MAX_DURATION = SynchedEntityData.m_135353_(
      The_Leviathan_Tongue_Entity.class, EntityDataSerializers.f_135028_
   );
   private static final EntityDataAccessor<Boolean> COMING_BACK = SynchedEntityData.m_135353_(
      The_Leviathan_Tongue_Entity.class, EntityDataSerializers.f_135035_
   );
   private int destroyBlocksTick;

   public The_Leviathan_Tongue_Entity(EntityType<?> entityType, Level level) {
      super(entityType, level);
   }

   public The_Leviathan_Tongue_Entity(SpawnEntity spawnEntity, Level level) {
      this((EntityType<?>)ModEntities.THE_LEVIATHAN_TONGUE.get(), level);
      this.m_20011_(this.m_142242_());
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   protected void m_8097_() {
      this.f_19804_.m_135372_(CONTROLLER_UUID, Optional.empty());
      this.f_19804_.m_135372_(CONTROLLER_ID, -1);
      this.f_19804_.m_135372_(TARGET_ID, -1);
      this.f_19804_.m_135372_(DURATION, 0);
      this.f_19804_.m_135372_(MAX_DURATION, 0);
      this.f_19804_.m_135372_(COMING_BACK, false);
   }

   public void m_8119_() {
      super.m_8119_();
      Entity controller = this.getController();
      Entity target = this.getTarget();
      if (!this.m_20197_().isEmpty() && ((Entity)this.m_20197_().get(0)).m_6144_()) {
         ((Entity)this.m_20197_().get(0)).m_20260_(false);
      }

      if (this.getDuration() <= this.getMaxDuration()) {
         this.setDuration(this.getDuration() + 1);
      }

      if (!this.f_19853_.f_46443_) {
         if (CMConfig.LeviathanBlockBreaking) {
            this.blockbreak(0.25, 0.25, 0.25);
         } else if (ForgeEventFactory.getMobGriefingEvent(this.f_19853_, this)) {
            this.blockbreak(0.25, 0.25, 0.25);
         }
      }

      if (controller instanceof The_Leviathan_Entity levi) {
         this.f_19804_.m_135381_(CONTROLLER_ID, levi.m_19879_());
         levi.setTongueUUID(this.m_20148_());
         if (!this.f_19853_.f_46443_) {
            Entity e = levi.m_5448_();
            this.f_19804_.m_135381_(TARGET_ID, e != null && e.m_6084_() ? e.m_19879_() : -1);
         }

         boolean attacking = !this.getComingBack() && target != null && target.m_6084_();
         Vec3 vec3 = attacking ? target.m_146892_() : levi.getTonguePosition();
         float speed = attacking ? 0.095F : 0.15F;
         Vec3 want = vec3.m_82546_(this.m_20182_());
         if (target != null && !this.getComingBack() && want.m_82553_() < (double)(target.m_20205_() + 1.0F)) {
            this.hurtEntity(levi, target);
            this.setComingBack(true);
         }

         this.directMovementTowards(vec3, speed);
         if (this.getDuration() >= this.getMaxDuration() / 2 && !this.getComingBack()) {
            this.setComingBack(true);
         }
      }

      this.m_6478_(MoverType.SELF, this.m_20184_());
      this.m_20256_(this.m_20184_().m_82490_(0.9F));
   }

   private void hurtEntity(LivingEntity holder, Entity target) {
      if (target.m_6469_(DamageSource.m_19370_(holder), 6.0F) && !this.f_19853_.f_46443_) {
         target.m_20329_(this);
      }
   }

   private void blockbreak(double x, double y, double z) {
      if (!this.f_19853_.f_46443_) {
         if (this.destroyBlocksTick > 0) {
            this.destroyBlocksTick--;
            return;
         }

         boolean flag = false;
         AABB aabb = this.m_20191_().m_82377_(x, y, z);

         for (BlockPos blockpos : BlockPos.m_121976_(
            Mth.m_14107_(aabb.f_82288_),
            Mth.m_14107_(aabb.f_82289_),
            Mth.m_14107_(aabb.f_82290_),
            Mth.m_14107_(aabb.f_82291_),
            Mth.m_14107_(aabb.f_82292_),
            Mth.m_14107_(aabb.f_82293_)
         )) {
            BlockState blockstate = this.f_19853_.m_8055_(blockpos);
            if (!blockstate.m_60795_() && blockstate.canEntityDestroy(this.f_19853_, blockpos, this) && !blockstate.m_204336_(ModTag.LEVIATHAN_IMMUNE)) {
               flag = this.f_19853_.m_46953_(blockpos, false, this) || flag;
            }
         }

         if (flag) {
            this.destroyBlocksTick = 15;
         }
      }
   }

   private boolean shouldDropItem(BlockEntity tileEntity) {
      return tileEntity == null ? this.f_19796_.m_188503_(3) + 1 == 3 : true;
   }

   private void directMovementTowards(Vec3 moveTo, float speed) {
      Vec3 want = moveTo.m_82546_(this.m_20182_());
      if (want.m_82553_() > 1.0) {
         want = want.m_82541_();
      }

      float targetXRot = (float)(-(Mth.m_14136_(want.f_82480_, want.m_165924_()) * 180.0F / (float)Math.PI));
      float targetYRot = (float)(-Mth.m_14136_(want.f_82479_, want.f_82481_) * 180.0F / (float)Math.PI);
      this.m_146926_(Mth.m_14148_(this.m_146909_(), targetXRot, 5.0F));
      this.m_146922_(Mth.m_14148_(this.m_146908_(), targetYRot, 5.0F));
      this.m_20256_(this.m_20184_().m_82549_(want.m_82490_((double)speed)));
   }

   public boolean shouldRiderSit() {
      return false;
   }

   public double m_6048_() {
      return -0.5;
   }

   protected void m_7378_(CompoundTag tag) {
      if (tag.m_128403_("ControllerUUID")) {
         this.setControllerUUID(tag.m_128342_("ControllerUUID"));
      }

      this.setDuration(tag.m_128451_("Duration"));
      this.setDuration(tag.m_128451_("Max_Duration"));
   }

   protected void m_7380_(CompoundTag tag) {
      if (this.getControllerUUID() != null) {
         tag.m_128362_("ControllerUUID", this.getControllerUUID());
      }

      tag.m_128405_("Duration", this.getDuration());
      tag.m_128405_("Max_Duration", this.getMaxDuration());
   }

   @Nullable
   public UUID getControllerUUID() {
      return (UUID)((Optional)this.f_19804_.m_135370_(CONTROLLER_UUID)).orElse(null);
   }

   public void setControllerUUID(@Nullable UUID uniqueId) {
      this.f_19804_.m_135381_(CONTROLLER_UUID, Optional.ofNullable(uniqueId));
   }

   public int getDuration() {
      return (Integer)this.f_19804_.m_135370_(DURATION);
   }

   public void setDuration(int i) {
      this.f_19804_.m_135381_(DURATION, i);
   }

   public int getMaxDuration() {
      return (Integer)this.f_19804_.m_135370_(MAX_DURATION);
   }

   public void setMaxDuration(int i) {
      this.f_19804_.m_135381_(MAX_DURATION, i);
   }

   public boolean getComingBack() {
      return (Boolean)this.f_19804_.m_135370_(COMING_BACK);
   }

   public void setComingBack(boolean i) {
      this.f_19804_.m_135381_(COMING_BACK, i);
   }

   public Entity getController() {
      if (!this.f_19853_.f_46443_) {
         UUID id = this.getControllerUUID();
         return id == null ? null : ((ServerLevel)this.f_19853_).m_8791_(id);
      } else {
         int id = (Integer)this.f_19804_.m_135370_(CONTROLLER_ID);
         return id == -1 ? null : this.f_19853_.m_6815_(id);
      }
   }

   public Entity getTarget() {
      int id = (Integer)this.f_19804_.m_135370_(TARGET_ID);
      return id == -1 ? null : this.f_19853_.m_6815_(id);
   }
}

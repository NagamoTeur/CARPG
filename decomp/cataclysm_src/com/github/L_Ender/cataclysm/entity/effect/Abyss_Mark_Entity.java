package com.github.L_Ender.cataclysm.entity.effect;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan.Abyss_Blast_Portal_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Abyss_Mark_Entity extends Entity {
   @Nullable
   private Entity finalTarget;
   @Nullable
   private UUID targetId;
   private static final EntityDataAccessor<Optional<UUID>> CREATOR_ID = SynchedEntityData.m_135353_(Abyss_Mark_Entity.class, EntityDataSerializers.f_135041_);
   protected static final EntityDataAccessor<Integer> LIFESPAN = SynchedEntityData.m_135353_(Abyss_Mark_Entity.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.m_135353_(Abyss_Mark_Entity.class, EntityDataSerializers.f_135029_);
   private static final EntityDataAccessor<Float> HPDAMAGE = SynchedEntityData.m_135353_(Abyss_Mark_Entity.class, EntityDataSerializers.f_135029_);

   public Abyss_Mark_Entity(EntityType<?> entityTypeIn, Level worldIn) {
      super(entityTypeIn, worldIn);
   }

   public Abyss_Mark_Entity(Level worldIn, double x, double y, double z, int lifespan, float damage, float hpdamage, UUID casterIn, LivingEntity finalTarget) {
      this((EntityType<?>)ModEntities.ABYSS_MARK.get(), worldIn);
      this.setCreatorEntityUUID(casterIn);
      this.setLifespan(lifespan);
      this.setDamage(damage);
      this.setHpDamage(hpdamage);
      this.finalTarget = finalTarget;
      this.m_6034_(x, y, z);
   }

   public Packet<?> m_5654_() {
      return new ClientboundAddEntityPacket(this);
   }

   public void m_8119_() {
      super.m_8119_();
      this.updateMotion();
      Entity owner = this.getCreatorEntity();
      if (owner != null && !owner.m_6084_()) {
         this.m_146870_();
      }

      this.setLifespan(this.getLifespan() - 1);
      if (!this.f_19853_.f_46443_ && this.finalTarget == null && this.targetId != null) {
         this.finalTarget = ((ServerLevel)this.f_19853_).m_8791_(this.targetId);
         if (this.finalTarget == null) {
            this.targetId = null;
         }
      }

      if (this.getLifespan() <= 0) {
         if (owner != null) {
            this.f_19853_
               .m_7967_(
                  new Abyss_Blast_Portal_Entity(
                     this.f_19853_,
                     this.m_20185_(),
                     this.m_20186_(),
                     this.m_20189_(),
                     this.m_146908_(),
                     0,
                     this.getDamage(),
                     this.getHpDamage(),
                     (LivingEntity)owner
                  )
               );
         }

         this.m_142687_(RemovalReason.DISCARDED);
      }
   }

   public int getLifespan() {
      return (Integer)this.f_19804_.m_135370_(LIFESPAN);
   }

   public void setLifespan(int i) {
      this.f_19804_.m_135381_(LIFESPAN, i);
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
      Vec3 vec3 = this.m_20184_();
      double h0 = this.m_20185_() + vec3.f_82479_;
      double h1 = this.m_20186_() + vec3.f_82480_;
      double h2 = this.m_20189_() + vec3.f_82481_;
      if (this.finalTarget != null && this.finalTarget.m_6084_() || this.finalTarget instanceof Player && !this.finalTarget.m_5833_()) {
         double dx = this.finalTarget.m_20185_() - this.m_20185_();
         double dz = this.finalTarget.m_20189_() - this.m_20189_();
         double p0 = Math.min(this.finalTarget.m_20186_(), this.m_20186_() - 50.0);
         double p1 = Math.max(this.finalTarget.m_20186_(), this.m_20186_());
         BlockPos blockpos = new BlockPos(this.finalTarget.m_20185_(), p1, this.finalTarget.m_20189_());
         double d0 = 0.0;

         do {
            BlockPos blockpos1 = blockpos.m_7495_();
            BlockState blockstate = this.f_19853_.m_8055_(blockpos1);
            if (blockstate.m_60783_(this.f_19853_, blockpos1, Direction.UP)) {
               if (!this.f_19853_.m_46859_(blockpos)) {
                  BlockState blockstate1 = this.f_19853_.m_8055_(blockpos);
                  VoxelShape voxelshape = blockstate1.m_60812_(this.f_19853_, blockpos);
                  if (!voxelshape.m_83281_()) {
                     d0 = voxelshape.m_83297_(Axis.Y);
                  }
               }
               break;
            }

            blockpos = blockpos.m_7495_();
         } while (blockpos.m_123342_() >= Mth.m_14107_(p0) - 1);

         this.m_6034_(h0, (double)blockpos.m_123342_() + d0, h2);
         this.m_20256_(vec3.m_82520_(dx, 0.0, dz).m_82490_(0.05));
      }
   }

   protected void m_8097_() {
      this.f_19804_.m_135372_(CREATOR_ID, Optional.empty());
      this.f_19804_.m_135372_(LIFESPAN, 300);
      this.f_19804_.m_135372_(DAMAGE, 0.0F);
      this.f_19804_.m_135372_(HPDAMAGE, 0.0F);
   }

   public float getDamage() {
      return (Float)this.f_19804_.m_135370_(DAMAGE);
   }

   public void setDamage(float damage) {
      this.f_19804_.m_135381_(DAMAGE, damage);
   }

   public float getHpDamage() {
      return (Float)this.f_19804_.m_135370_(HPDAMAGE);
   }

   public void setHpDamage(float damage) {
      this.f_19804_.m_135381_(HPDAMAGE, damage);
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

      if (compound.m_128403_("Target")) {
         this.targetId = compound.m_128342_("Target");
      }

      if (uuid != null) {
         try {
            this.setCreatorEntityUUID(uuid);
         } catch (Throwable var4) {
         }
      }

      this.setDamage(compound.m_128457_("damage"));
      this.setHpDamage(compound.m_128457_("Hpdamage"));
   }

   protected void m_7380_(CompoundTag compound) {
      compound.m_128405_("Lifespan", this.getLifespan());
      if (this.getCreatorEntityUUID() != null) {
         compound.m_128362_("Owner", this.getCreatorEntityUUID());
      }

      if (this.finalTarget != null) {
         compound.m_128362_("Target", this.finalTarget.m_20148_());
      }

      compound.m_128350_("damage", this.getDamage());
      compound.m_128350_("Hpdamage", this.getHpDamage());
   }
}

package com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan;

import com.github.L_Ender.cataclysm.init.ModEntities;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;

public class Abyss_Portal_Entity extends Entity {
   protected static final EntityDataAccessor<Integer> LIFESPAN = SynchedEntityData.m_135353_(Abyss_Portal_Entity.class, EntityDataSerializers.f_135028_);
   protected static final EntityDataAccessor<Boolean> ENTRANCE = SynchedEntityData.m_135353_(Abyss_Portal_Entity.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Optional<BlockPos>> DESTINATION = SynchedEntityData.m_135353_(
      Abyss_Portal_Entity.class, EntityDataSerializers.f_135039_
   );
   private static final EntityDataAccessor<Optional<UUID>> SISTER_UUID = SynchedEntityData.m_135353_(Abyss_Portal_Entity.class, EntityDataSerializers.f_135041_);
   private boolean madeOpenNoise = false;
   private boolean madeCloseNoise = false;
   private boolean isDummy = false;
   private boolean hasClearedObstructions;

   public Abyss_Portal_Entity(EntityType<?> entityTypeIn, Level worldIn) {
      super(entityTypeIn, worldIn);
   }

   public Abyss_Portal_Entity(SpawnEntity spawnEntity, Level level) {
      this((EntityType<?>)ModEntities.ABYSS_PORTAL.get(), level);
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.f_19797_ == 1 && this.getLifespan() == 0) {
         this.setLifespan(2000);
      }

      if (!this.madeOpenNoise) {
         this.m_146850_(GameEvent.f_157810_);
         this.m_5496_(SoundEvents.f_11860_, 1.0F, 1.0F + this.f_19796_.m_188501_() * 0.2F);
         this.madeOpenNoise = true;
      }

      if (this.f_19796_.m_188501_() < 0.5F && this.f_19853_.f_46443_ && Math.min(this.f_19797_, this.getLifespan()) >= 20) {
         double particleX = this.m_20191_().f_82288_ + (double)this.f_19796_.m_188501_() * (this.m_20191_().f_82291_ - this.m_20191_().f_82288_);
         double particleY = this.m_20191_().f_82289_ + (double)this.f_19796_.m_188501_() * (this.m_20191_().f_82292_ - this.m_20191_().f_82289_);
         double particleZ = this.m_20191_().f_82290_ + (double)this.f_19796_.m_188501_() * (this.m_20191_().f_82293_ - this.m_20191_().f_82290_);
         this.f_19853_
            .m_7106_(
               ParticleTypes.f_123760_,
               particleX,
               particleY,
               particleZ,
               0.1 * this.f_19796_.m_188583_(),
               0.1 * this.f_19796_.m_188583_(),
               0.1 * this.f_19796_.m_188583_()
            );
      }

      List<Entity> entities = new ArrayList<>();
      entities.addAll(this.f_19853_.m_45933_(this, this.m_20191_().m_82406_(0.2F)));
      entities.addAll(this.f_19853_.m_45976_(The_Leviathan_Entity.class, this.m_20191_().m_82400_(3.0)));
      if (!this.f_19853_.f_46443_ && this.getDestination() != null && this.getLifespan() > 20 && this.f_19797_ > 20 && this.getEntrance()) {
         for (Entity e : entities) {
            if (!e.m_20092_() && !e.m_6144_() && !(e instanceof Abyss_Portal_Entity)) {
               if (e instanceof The_Leviathan_Entity) {
                  ((The_Leviathan_Entity)e).teleportTo(Vec3.m_82512_(this.getDestination()));
                  e.m_20091_();
                  ((The_Leviathan_Entity)e).resetPortalLogic();
               } else {
                  e.m_20324_(
                     (double)((float)this.getDestination().m_123341_() + 0.5F),
                     (double)((float)this.getDestination().m_123342_() + 0.5F),
                     (double)((float)this.getDestination().m_123343_() + 0.5F)
                  );
                  e.m_20091_();
               }
            }
         }
      }

      this.setLifespan(this.getLifespan() - 1);
      if (this.getLifespan() <= 20 && !this.madeCloseNoise) {
         this.m_146850_(GameEvent.f_157810_);
         this.madeCloseNoise = true;
      }

      if (this.getLifespan() <= 0) {
         this.m_142687_(RemovalReason.DISCARDED);
      }
   }

   public int getLifespan() {
      return (Integer)this.f_19804_.m_135370_(LIFESPAN);
   }

   public void setLifespan(int i) {
      this.f_19804_.m_135381_(LIFESPAN, i);
   }

   public boolean getEntrance() {
      return (Boolean)this.f_19804_.m_135370_(ENTRANCE);
   }

   public void setEntrance(boolean entrance) {
      this.f_19804_.m_135381_(ENTRANCE, entrance);
   }

   public BlockPos getDestination() {
      return (BlockPos)((Optional)this.f_19804_.m_135370_(DESTINATION)).orElse(null);
   }

   public void setDestination(BlockPos destination) {
      this.f_19804_.m_135381_(DESTINATION, Optional.ofNullable(destination));
      if (this.getSisterId() == null) {
         this.createAndSetSister(this.f_19853_, null);
      }
   }

   public void createAndSetSister(Level world, Direction dir) {
      Abyss_Portal_Entity portal = (Abyss_Portal_Entity)((EntityType)ModEntities.ABYSS_PORTAL.get()).m_20615_(world);
      BlockPos safeDestination = this.getDestination();
      portal.m_20324_(
         (double)((float)safeDestination.m_123341_() + 0.5F),
         (double)((float)safeDestination.m_123342_() + 0.5F),
         (double)((float)safeDestination.m_123343_() + 0.5F)
      );
      portal.link(this);
      portal.setEntrance(false);
      world.m_7967_(portal);
   }

   public void setDestination(BlockPos destination, Direction dir) {
      this.f_19804_.m_135381_(DESTINATION, Optional.ofNullable(destination));
      if (this.getSisterId() == null) {
         this.createAndSetSister(this.f_19853_, dir);
      }
   }

   public void link(Abyss_Portal_Entity portal) {
      this.setSisterId(portal.m_20148_());
      portal.setSisterId(this.m_20148_());
      portal.setLifespan(this.getLifespan());
      this.setDestination(portal.m_20183_());
      portal.setDestination(this.m_20183_());
   }

   protected void m_8097_() {
      this.f_19804_.m_135372_(LIFESPAN, 300);
      this.f_19804_.m_135372_(SISTER_UUID, Optional.empty());
      this.f_19804_.m_135372_(DESTINATION, Optional.empty());
      this.f_19804_.m_135372_(ENTRANCE, true);
   }

   protected void m_7378_(CompoundTag compound) {
      this.setLifespan(compound.m_128451_("Lifespan"));
      if (compound.m_128441_("DX")) {
         int i = compound.m_128451_("DX");
         int j = compound.m_128451_("DY");
         int k = compound.m_128451_("DZ");
         this.f_19804_.m_135381_(DESTINATION, Optional.of(new BlockPos(i, j, k)));
      } else {
         this.f_19804_.m_135381_(DESTINATION, Optional.empty());
      }

      if (compound.m_128403_("SisterUUID")) {
         this.setSisterId(compound.m_128342_("SisterUUID"));
      }
   }

   protected void m_7380_(CompoundTag compound) {
      compound.m_128405_("Lifespan", this.getLifespan());
      BlockPos blockpos = this.getDestination();
      if (blockpos != null) {
         compound.m_128405_("DX", blockpos.m_123341_());
         compound.m_128405_("DY", blockpos.m_123342_());
         compound.m_128405_("DZ", blockpos.m_123343_());
      }

      if (this.getSisterId() != null) {
         compound.m_128362_("SisterUUID", this.getSisterId());
      }
   }

   public Entity getSister() {
      UUID id = this.getSisterId();
      return id != null && !this.f_19853_.f_46443_ ? ((ServerLevel)this.f_19853_).m_8791_(id) : null;
   }

   public boolean m_6783_(double distance) {
      return distance < 1024.0;
   }

   @Nullable
   public UUID getSisterId() {
      return (UUID)((Optional)this.f_19804_.m_135370_(SISTER_UUID)).orElse(null);
   }

   public void setSisterId(@Nullable UUID uniqueId) {
      this.f_19804_.m_135381_(SISTER_UUID, Optional.ofNullable(uniqueId));
   }
}

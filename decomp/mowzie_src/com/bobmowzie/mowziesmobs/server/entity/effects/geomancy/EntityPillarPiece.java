package com.bobmowzie.mowziesmobs.server.entity.effects.geomancy;

import java.util.Optional;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public class EntityPillarPiece extends Entity {
   private static final EntityDataAccessor<Optional<UUID>> PILLAR = SynchedEntityData.m_135353_(EntityPillarPiece.class, EntityDataSerializers.f_135041_);
   private static final EntityDataAccessor<Integer> TIER = SynchedEntityData.m_135353_(EntityPillarPiece.class, EntityDataSerializers.f_135028_);
   private EntityPillar pillar;

   public EntityPillarPiece(EntityType<?> type, Level level) {
      super(type, level);
   }

   public EntityPillarPiece(EntityType<?> type, Level level, EntityPillar pillar, Vec3 position) {
      super(type, level);
      this.pillar = pillar;
      this.setTier(pillar.getTier());
      this.m_20248_(position.f_82479_, position.f_82480_, position.f_82481_);
   }

   public boolean m_5829_() {
      return true;
   }

   public boolean m_7337_(Entity entity) {
      return !(entity instanceof EntityPillar) && !(entity instanceof EntityPillarPiece) ? super.m_7337_(entity) : false;
   }

   public void m_8119_() {
      if (!this.f_19853_.m_5776_()) {
         if (this.pillar == null) {
            this.pillar = this.getPillar();
            if (this.pillar == null) {
               this.m_142687_(RemovalReason.DISCARDED);
               return;
            }
         }

         if (this.pillar.m_213877_()) {
            this.m_142687_(RemovalReason.DISCARDED);
            return;
         }

         if (this.pillar.isFalling() && (double)this.pillar.getHeight() + this.pillar.m_20186_() < this.m_20186_() + 1.0) {
            this.m_142687_(RemovalReason.DISCARDED);
            return;
         }

         this.setTier(this.pillar.getTier());
      }

      super.m_8119_();
      this.m_20011_(this.m_142242_());
   }

   protected AABB m_142242_() {
      float f = (float)EntityPillar.SIZE_MAP.get(this.getTier()).intValue() / 2.0F;
      return new AABB(
         this.m_20185_() - (double)f,
         this.m_20186_(),
         this.m_20189_() - (double)f,
         this.m_20185_() + (double)f,
         this.m_20186_() + 1.0,
         this.m_20189_() + (double)f
      );
   }

   protected void m_8097_() {
      this.m_20088_().m_135372_(PILLAR, Optional.empty());
      this.m_20088_().m_135372_(TIER, 0);
   }

   protected void m_7378_(CompoundTag compound) {
      this.setPillarUUID(compound.m_128342_("pillar"));
   }

   protected void m_7380_(CompoundTag compound) {
      if (this.pillar != null) {
         compound.m_128362_("pillar", this.pillar.m_20148_());
      }
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public boolean m_6051_() {
      return false;
   }

   public PushReaction m_7752_() {
      return PushReaction.BLOCK;
   }

   public boolean m_6128_() {
      return true;
   }

   public Optional<UUID> getPillarUUID() {
      return (Optional<UUID>)this.m_20088_().m_135370_(PILLAR);
   }

   public void setPillarUUID(UUID uuid) {
      this.m_20088_().m_135381_(PILLAR, Optional.of(uuid));
   }

   public EntityPillar getPillar() {
      Optional<UUID> uuid = this.getPillarUUID();
      return uuid.isPresent() && !this.f_19853_.f_46443_ ? (EntityPillar)((ServerLevel)this.f_19853_).m_8791_(uuid.get()) : null;
   }

   public EntityGeomancyBase.GeomancyTier getTier() {
      return EntityGeomancyBase.GeomancyTier.values()[this.f_19804_.m_135370_(TIER)];
   }

   public void setTier(EntityGeomancyBase.GeomancyTier size) {
      this.f_19804_.m_135381_(TIER, size.ordinal());
   }
}

package com.bobmowzie.mowziesmobs.server.entity.effects;

import com.bobmowzie.mowziesmobs.MowziesMobs;
import com.bobmowzie.mowziesmobs.server.entity.ILinkedEntity;
import com.bobmowzie.mowziesmobs.server.message.MessageLinkEntities;
import java.util.List;
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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PacketDistributor;

public abstract class EntityMagicEffect extends Entity implements ILinkedEntity {
   public LivingEntity caster;
   protected boolean hasSyncedCaster = false;
   private static final EntityDataAccessor<Optional<UUID>> CASTER = SynchedEntityData.m_135353_(EntityMagicEffect.class, EntityDataSerializers.f_135041_);

   public EntityMagicEffect(EntityType<? extends EntityMagicEffect> type, Level worldIn) {
      super(type, worldIn);
   }

   public EntityMagicEffect(EntityType<? extends EntityMagicEffect> type, Level world, LivingEntity caster) {
      super(type, world);
      if (!world.f_46443_ && caster != null) {
         this.setCasterID(caster.m_20148_());
      }
   }

   public PushReaction m_7752_() {
      return PushReaction.IGNORE;
   }

   protected void m_8097_() {
      this.m_20088_().m_135372_(CASTER, Optional.empty());
   }

   public Optional<UUID> getCasterID() {
      return (Optional<UUID>)this.m_20088_().m_135370_(CASTER);
   }

   public void setCasterID(UUID id) {
      this.m_20088_().m_135381_(CASTER, Optional.of(id));
   }

   public boolean m_6087_() {
      return false;
   }

   public void m_7334_(Entity entityIn) {
   }

   public void m_8119_() {
      super.m_8119_();
   }

   public void onAddedToWorld() {
      super.onAddedToWorld();
      if (!this.f_19853_.m_5776_() && this.getCasterID().isPresent() && this.caster == null) {
         Entity casterEntity = ((ServerLevel)this.f_19853_).m_8791_(this.getCasterID().get());
         if (casterEntity instanceof LivingEntity) {
            this.caster = (LivingEntity)casterEntity;
            MowziesMobs.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this), new MessageLinkEntities(this, this.caster));
         }

         this.hasSyncedCaster = true;
      }
   }

   @Override
   public void link(Entity entity) {
      if (entity instanceof LivingEntity) {
         this.caster = (LivingEntity)entity;
      }

      this.hasSyncedCaster = true;
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   protected void m_7378_(CompoundTag compound) {
      this.setCasterID(compound.m_128342_("caster"));
   }

   protected void m_7380_(CompoundTag compound) {
      if (this.getCasterID().isPresent()) {
         compound.m_128362_("caster", this.getCasterID().get());
      }
   }

   public List<LivingEntity> getEntityLivingBaseNearby(double radius) {
      return this.getEntitiesNearby(LivingEntity.class, radius);
   }

   public <T extends Entity> List<T> getEntitiesNearby(Class<T> entityClass, double r) {
      return this.f_19853_
         .m_6443_(entityClass, this.m_20191_().m_82377_(r, r, r), e -> e != this && (double)this.m_20270_(e) <= r + (double)(e.m_20205_() / 2.0F));
   }

   public <T extends Entity> List<T> getEntitiesNearbyCube(Class<T> entityClass, double r) {
      return this.f_19853_.m_6443_(entityClass, this.m_20191_().m_82377_(r, r, r), e -> e != this);
   }

   public boolean raytraceCheckEntity(Entity entity) {
      Vec3 from = this.m_20182_();
      int numChecks = 3;

      for (int i = 0; i < numChecks; i++) {
         float increment = entity.m_20206_() / (float)(numChecks + 1);
         Vec3 to = entity.m_20182_().m_82520_(0.0, (double)(increment * (float)(i + 1)), 0.0);
         BlockHitResult result = this.f_19853_.m_45547_(new ClipContext(from, to, Block.COLLIDER, Fluid.NONE, this));
         if (result.m_6662_() != Type.BLOCK) {
            return true;
         }
      }

      return false;
   }
}

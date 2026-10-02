package com.hollingsworth.arsnouveau.common.entity;

import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.lib.EntityTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import org.jetbrains.annotations.Nullable;

public class EntityLingeringSpell extends EntityProjectileSpell {
   public static final EntityDataAccessor<Integer> ACCELERATES = SynchedEntityData.m_135353_(EntityLingeringSpell.class, EntityDataSerializers.f_135028_);
   public static final EntityDataAccessor<Float> AOE = SynchedEntityData.m_135353_(EntityLingeringSpell.class, EntityDataSerializers.f_135029_);
   public static final EntityDataAccessor<Boolean> LANDED = SynchedEntityData.m_135353_(EntityLingeringSpell.class, EntityDataSerializers.f_135035_);
   public static final EntityDataAccessor<Boolean> SENSITIVE = SynchedEntityData.m_135353_(EntityLingeringSpell.class, EntityDataSerializers.f_135035_);
   public static final EntityDataAccessor<Boolean> SHOULD_FALL = SynchedEntityData.m_135353_(EntityLingeringSpell.class, EntityDataSerializers.f_135035_);
   public double extendedTime;
   public int maxProcs = 100;
   public int totalProcs;

   public EntityLingeringSpell(EntityType<? extends EntityProjectileSpell> type, Level worldIn) {
      super((EntityType<? extends EntityProjectileSpell>)ModEntities.LINGER_SPELL.get(), worldIn);
   }

   public EntityLingeringSpell(Level worldIn, double x, double y, double z) {
      super((EntityType<? extends EntityProjectileSpell>)ModEntities.LINGER_SPELL.get(), worldIn, x, y, z);
   }

   public EntityLingeringSpell(Level worldIn, LivingEntity shooter) {
      super((EntityType<? extends EntityProjectileSpell>)ModEntities.LINGER_SPELL.get(), worldIn, shooter);
   }

   public void setAccelerates(int accelerates) {
      this.f_19804_.m_135381_(ACCELERATES, accelerates);
   }

   @Override
   public void m_8119_() {
      if (!this.f_19853_.f_46443_) {
         boolean isOnGround = this.f_19853_.m_8055_(this.m_20183_()).m_60767_().m_76334_();
         this.setLanded(isOnGround);
      }

      super.m_8119_();
      this.castSpells();
   }

   @Override
   public void traceAnyHit(@Nullable HitResult raytraceresult, Vec3 thisPosition, Vec3 nextPosition) {
   }

   @Override
   public void tickNextPosition() {
      if (this.shouldFall()) {
         if (!this.getLanded()) {
            this.m_20334_(0.0, -0.2, 0.0);
         } else {
            this.m_20334_(0.0, 0.0, 0.0);
         }

         super.tickNextPosition();
      }
   }

   public void castSpells() {
      float aoe = this.getAoe();
      int flatAoe = Math.round(aoe);
      if (!this.f_19853_.f_46443_ && this.age % (20 - 2 * this.getAccelerates()) == 0) {
         if (this.isSensitive()) {
            for (BlockPos p : BlockPos.m_121940_(this.m_20183_().m_122030_(flatAoe).m_122013_(flatAoe), this.m_20183_().m_122025_(flatAoe).m_122020_(flatAoe))) {
               p = p.m_7949_();
               this.spellResolver
                  .onResolveEffect(
                     this.f_19853_, new BlockHitResult(new Vec3((double)p.m_123341_(), (double)p.m_123342_(), (double)p.m_123343_()), Direction.UP, p, false)
                  );
            }
         } else {
            int i = 0;

            for (Entity entity : this.f_19853_.m_45933_(null, new AABB(this.m_20183_()).m_82400_((double)this.getAoe()))) {
               if (!entity.equals(this) && !entity.m_6095_().m_204039_(EntityTags.LINGERING_BLACKLIST)) {
                  this.spellResolver.onResolveEffect(this.f_19853_, new EntityHitResult(entity));
                  if (++i > 5) {
                     break;
                  }
               }
            }

            this.totalProcs += i;
            if (this.totalProcs >= this.maxProcs) {
               this.m_142687_(RemovalReason.DISCARDED);
            }
         }
      }
   }

   @Override
   public int getExpirationTime() {
      return (int)(70.0 + this.extendedTime * 20.0);
   }

   @Override
   public int getParticleDelay() {
      return 0;
   }

   @Override
   public void playParticles() {
      ParticleUtil.spawnRitualAreaEffect(this.m_20097_(), this.f_19853_, this.f_19796_, this.getParticleColor(), Math.round(this.getAoe()), 5, 20);
      ParticleUtil.spawnLight(this.f_19853_, this.getParticleColor(), this.f_19825_.m_82520_(0.0, 0.5, 0.0), 10);
   }

   public EntityLingeringSpell(SpawnEntity packet, Level world) {
      super((EntityType<? extends EntityProjectileSpell>)ModEntities.LINGER_SPELL.get(), world);
   }

   @Override
   public EntityType<?> m_6095_() {
      return (EntityType<?>)ModEntities.LINGER_SPELL.get();
   }

   @Override
   protected void m_6532_(HitResult result) {
      if (!this.f_19853_.f_46443_ && result instanceof BlockHitResult && !this.m_213877_()) {
         BlockState state = this.f_19853_.m_8055_(((BlockHitResult)result).m_82425_());
         if (state.m_60767_() == Material.f_76298_) {
            state.m_60734_().m_7892_(state, this.f_19853_, ((BlockHitResult)result).m_82425_(), this);
            return;
         }

         this.setLanded(true);
      }
   }

   public int getAccelerates() {
      return (Integer)this.f_19804_.m_135370_(ACCELERATES);
   }

   public void setAoe(float aoe) {
      this.f_19804_.m_135381_(AOE, aoe);
   }

   public float getAoe() {
      return (float)(this.isSensitive() ? 1 : 3) + (Float)this.f_19804_.m_135370_(AOE);
   }

   public void setLanded(boolean landed) {
      this.f_19804_.m_135381_(LANDED, landed);
   }

   public boolean getLanded() {
      return (Boolean)this.f_19804_.m_135370_(LANDED);
   }

   public void setSensitive(boolean sensitive) {
      this.f_19804_.m_135381_(SENSITIVE, sensitive);
   }

   public boolean isSensitive() {
      return (Boolean)this.f_19804_.m_135370_(SENSITIVE);
   }

   public void setShouldFall(boolean shouldFall) {
      this.f_19804_.m_135381_(SHOULD_FALL, shouldFall);
   }

   public boolean shouldFall() {
      return (Boolean)this.f_19804_.m_135370_(SHOULD_FALL);
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(ACCELERATES, 0);
      this.f_19804_.m_135372_(AOE, 0.0F);
      this.f_19804_.m_135372_(LANDED, false);
      this.f_19804_.m_135372_(SENSITIVE, false);
      this.f_19804_.m_135372_(SHOULD_FALL, true);
   }

   @Override
   public void m_7380_(CompoundTag tag) {
      super.m_7380_(tag);
      tag.m_128379_("sensitive", this.isSensitive());
      tag.m_128379_("shouldFall", this.shouldFall());
   }

   @Override
   public void m_20258_(CompoundTag compound) {
      super.m_20258_(compound);
      this.setSensitive(compound.m_128471_("sensitive"));
      this.setShouldFall(compound.m_128471_("shouldFall"));
   }
}

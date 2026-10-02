package com.hollingsworth.arsnouveau.common.entity;

import com.hollingsworth.arsnouveau.client.particle.ParticleLineData;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.lib.EntityTags;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.RandomSource;
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

public class EntityWallSpell extends EntityProjectileSpell {
   public static final EntityDataAccessor<Integer> ACCELERATES = SynchedEntityData.m_135353_(EntityWallSpell.class, EntityDataSerializers.f_135028_);
   public static final EntityDataAccessor<Float> AOE = SynchedEntityData.m_135353_(EntityWallSpell.class, EntityDataSerializers.f_135029_);
   public static final EntityDataAccessor<Boolean> LANDED = SynchedEntityData.m_135353_(EntityWallSpell.class, EntityDataSerializers.f_135035_);
   public static final EntityDataAccessor<Boolean> SENSITIVE = SynchedEntityData.m_135353_(EntityWallSpell.class, EntityDataSerializers.f_135035_);
   public static final EntityDataAccessor<Direction> DIRECTION = SynchedEntityData.m_135353_(EntityWallSpell.class, EntityDataSerializers.f_135040_);
   public static final EntityDataAccessor<Boolean> SHOULD_FALL = SynchedEntityData.m_135353_(EntityWallSpell.class, EntityDataSerializers.f_135035_);
   public double extendedTime;
   public int maxProcs = 100;
   public int totalProcs;
   List<EntityWallSpell.EntityHit> hitEntities = new ArrayList<>();
   public float growthFactor = 1.0F;

   public EntityWallSpell(EntityType<? extends EntityProjectileSpell> type, Level worldIn) {
      super((EntityType<? extends EntityProjectileSpell>)ModEntities.WALL_SPELL.get(), worldIn);
   }

   public EntityWallSpell(Level worldIn, double x, double y, double z) {
      super((EntityType<? extends EntityProjectileSpell>)ModEntities.WALL_SPELL.get(), worldIn, x, y, z);
   }

   public EntityWallSpell(Level worldIn, LivingEntity shooter) {
      super((EntityType<? extends EntityProjectileSpell>)ModEntities.WALL_SPELL.get(), worldIn, shooter);
   }

   public void setAccelerates(int accelerates) {
      this.f_19804_.m_135381_(ACCELERATES, accelerates);
   }

   @Override
   public void m_8119_() {
      if (!this.f_19853_.f_46443_) {
         if (this.spellResolver == null) {
            return;
         }

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
      if (!this.f_19853_.f_46443_) {
         float aoe = this.getAoe();
         int flatAoe = Math.round(aoe);
         BlockPos start = this.m_20183_().m_7918_(flatAoe * this.m_6350_().m_122429_(), 0, flatAoe * this.m_6350_().m_122431_());
         BlockPos end = this.m_20183_().m_7918_(-flatAoe * this.m_6350_().m_122429_(), flatAoe, -flatAoe * this.m_6350_().m_122431_());
         if (this.isSensitive()) {
            if (this.age % (20 - 2 * this.getAccelerates()) != 0 && this.age != 1) {
               return;
            }

            for (BlockPos p : BlockPos.m_121940_(start, end)) {
               p = p.m_7949_();
               this.spellResolver
                  .onResolveEffect(
                     this.f_19853_, new BlockHitResult(new Vec3((double)p.m_123341_(), (double)p.m_123342_(), (double)p.m_123343_()), Direction.UP, p, false)
                  );
            }
         } else {
            int i = 0;
            AABB aabb = new AABB(start, end);
            if (aabb.f_82291_ == aabb.f_82288_) {
               aabb = aabb.m_82377_((double)this.growthFactor, 0.0, 0.0);
            }

            if (aabb.f_82292_ == aabb.f_82289_) {
               aabb = aabb.m_82377_(0.0, (double)this.growthFactor, 0.0);
            }

            if (aabb.f_82293_ == aabb.f_82290_) {
               aabb = aabb.m_82377_(0.0, 0.0, (double)this.growthFactor);
            }

            for (Entity entity : this.f_19853_.m_45933_(null, aabb)) {
               if (!entity.equals(this) && !entity.m_6095_().m_204039_(EntityTags.LINGERING_BLACKLIST)) {
                  Optional<EntityWallSpell.EntityHit> hit = this.hitEntities.stream().filter(e -> e.entity.refersTo(entity)).findFirst();
                  boolean skipEntity = hit.isPresent();
                  if (hit.isPresent() && this.f_19853_.m_46467_() - hit.get().gameTime > 20L) {
                     this.hitEntities.remove(hit.get());
                     skipEntity = false;
                  }

                  if (!skipEntity) {
                     this.spellResolver.onResolveEffect(this.f_19853_, new EntityHitResult(entity));
                     i++;
                     if (hit.isEmpty()) {
                        this.hitEntities.add(new EntityWallSpell.EntityHit(entity));
                     }

                     if (i > 5) {
                        break;
                     }
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
      BlockPos pos = this.m_20097_();
      int range = Math.round(this.getAoe());
      int chance = 5;
      int numParticles = 20;
      RandomSource rand = this.f_19796_;
      BlockPos.m_121990_(
            pos.m_7918_(range * this.m_6350_().m_122429_(), 0, range * this.m_6350_().m_122431_()),
            pos.m_7918_(-range * this.m_6350_().m_122429_(), range, -range * this.m_6350_().m_122431_())
         )
         .forEach(blockPos -> {
            if (rand.m_188503_(chance) == 0) {
               for (int i = 0; i < rand.m_188503_(numParticles); i++) {
                  double x = (double)blockPos.m_123341_() + ParticleUtil.inRange((double)(-this.growthFactor), (double)this.growthFactor) + 0.5;
                  double y = (double)blockPos.m_123342_() + ParticleUtil.inRange((double)(-this.growthFactor), (double)this.growthFactor);
                  double z = (double)blockPos.m_123343_() + ParticleUtil.inRange((double)(-this.growthFactor), (double)this.growthFactor) + 0.5;
                  this.f_19853_.m_7106_(ParticleLineData.createData(this.getParticleColor()), x, y, z, x, y + ParticleUtil.inRange(0.5, 5.0), z);
               }
            }
         });
      ParticleUtil.spawnLight(this.f_19853_, this.getParticleColor(), this.f_19825_.m_82520_(0.0, 0.5, 0.0), 10);
   }

   public EntityWallSpell(SpawnEntity packet, Level world) {
      super((EntityType<? extends EntityProjectileSpell>)ModEntities.WALL_SPELL.get(), world);
   }

   @Override
   public EntityType<?> m_6095_() {
      return (EntityType<?>)ModEntities.WALL_SPELL.get();
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
      return 3.0F + (Float)this.f_19804_.m_135370_(AOE);
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

   public void setDirection(Direction direction) {
      this.f_19804_.m_135381_(DIRECTION, direction);
   }

   public Direction m_6350_() {
      return (Direction)this.f_19804_.m_135370_(DIRECTION);
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
      this.f_19804_.m_135372_(DIRECTION, Direction.NORTH);
      this.f_19804_.m_135372_(SHOULD_FALL, true);
   }

   @Override
   public void m_7380_(CompoundTag tag) {
      super.m_7380_(tag);
      tag.m_128379_("sensitive", this.isSensitive());
      tag.m_128359_("direction", this.m_6350_().name());
      tag.m_128379_("should_fall", this.shouldFall());
   }

   @Override
   public void m_20258_(CompoundTag compound) {
      super.m_20258_(compound);
      this.setSensitive(compound.m_128471_("sensitive"));
      this.setDirection(Direction.valueOf(compound.m_128461_("direction")));
      this.setShouldFall(compound.m_128471_("should_fall"));
   }

   public static class EntityHit {
      long gameTime;
      WeakReference<Entity> entity;

      public EntityHit(Entity entity) {
         this.entity = new WeakReference<>(entity);
         this.gameTime = entity.f_19853_.m_46467_();
      }
   }
}

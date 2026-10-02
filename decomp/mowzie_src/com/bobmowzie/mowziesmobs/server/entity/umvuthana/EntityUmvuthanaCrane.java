package com.bobmowzie.mowziesmobs.server.entity.umvuthana;

import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.ai.AvoidProjectilesGoal;
import com.bobmowzie.mowziesmobs.server.ai.NearestAttackableTargetPredicateGoal;
import com.bobmowzie.mowziesmobs.server.ai.UmvuthanaHurtByTargetAI;
import com.bobmowzie.mowziesmobs.server.item.UmvuthanaMask;
import com.bobmowzie.mowziesmobs.server.potion.EffectHandler;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class EntityUmvuthanaCrane extends EntityUmvuthanaMinion {
   public boolean hasTriedOrSucceededTeleport = true;
   private int teleportAttempts = 0;

   public EntityUmvuthanaCrane(EntityType<? extends EntityUmvuthanaMinion> type, Level world) {
      super(type, world);
      this.setWeapon(3);
      this.setMask(MaskType.FAITH);
   }

   @Override
   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(1, new EntityUmvuthanaCrane.TeleportToSafeSpotGoal(this));
      this.f_21345_.m_25352_(1, new AvoidProjectilesGoal(this, Projectile.class, target -> this.getActiveAbilityType() == HEAL_ABILITY, 5.0F, 0.8, 0.6));
      this.f_21345_.m_25352_(4, new EntityUmvuthanaCrane.HealTargetGoal(this));
      this.f_21345_.m_25352_(6, new AvoidEntityGoal<Player>(this, Player.class, 7.0F, 0.8, 0.6, target -> {
         if (!(target instanceof Player)) {
            return true;
         } else if (this.f_19853_.m_46791_() == Difficulty.PEACEFUL) {
            return false;
         } else if (this.m_5448_() == target) {
            return true;
         } else if (this.m_5448_() instanceof EntityUmvuthi) {
            return false;
         } else if (this.getActiveAbilityType() != null) {
            return false;
         } else {
            ItemStack headArmorStack = (ItemStack)((Player)target).m_150109_().f_35975_.get(3);
            return !(headArmorStack.m_41720_() instanceof UmvuthanaMask) || target == this.getMisbehavedPlayer();
         }
      }) {
         public void m_8041_() {
            super.m_8041_();
            EntityUmvuthanaCrane.this.setMisbehavedPlayerId(null);
         }
      });
      this.f_21345_.m_25352_(6, new AvoidEntityGoal(this, Zombie.class, 7.0F, 0.8, 0.6));
      this.f_21345_.m_25352_(6, new AvoidEntityGoal(this, AbstractSkeleton.class, 7.0F, 0.8, 0.6));
   }

   @Override
   protected void registerTargetGoals() {
      this.f_21346_.m_25352_(3, new UmvuthanaHurtByTargetAI(this));
      this.f_21346_
         .m_25352_(
            2,
            new NearestAttackableTargetPredicateGoal<EntityUmvuthi>(
               this,
               EntityUmvuthi.class,
               0,
               false,
               false,
               TargetingConditions.m_148353_().m_26883_(this.m_21133_(Attributes.f_22277_) * 2.0).m_26888_(target -> {
                  if (!this.active) {
                     return false;
                  } else {
                     return !(target instanceof Mob) ? false : ((Mob)target).m_5448_() != null || target.m_21223_() < target.m_21233_();
                  }
               }).m_148355_().m_26893_()
            ) {
               public boolean m_8045_() {
                  LivingEntity livingentity = this.f_26135_.m_5448_();
                  if (livingentity == null) {
                     livingentity = this.f_26137_;
                  }

                  boolean targetHasTarget = false;
                  if (livingentity instanceof Mob) {
                     targetHasTarget = ((Mob)livingentity).m_5448_() != null;
                  }

                  boolean canHeal = true;
                  if (this.f_26135_ instanceof EntityUmvuthana) {
                     canHeal = ((EntityUmvuthana)this.f_26135_).canHeal(livingentity);
                  }

                  return super.m_8045_() && (livingentity.m_21223_() < livingentity.m_21233_() || targetHasTarget) && canHeal;
               }

               protected double m_7623_() {
                  return super.m_7623_() * 2.0;
               }
            }
         );
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      if (this.active && this.teleportAttempts > 3 && (this.m_5448_() == null || !this.m_5448_().m_6084_())) {
         this.hasTriedOrSucceededTeleport = true;
      }
   }

   @Override
   public boolean canHeal(LivingEntity entity) {
      return entity instanceof EntityUmvuthi;
   }

   @Override
   public void m_6667_(DamageSource cause) {
      if (this.m_5448_() instanceof EntityUmvuthi) {
         List<Mob> targetingMobs = this.f_19853_.m_6443_(Mob.class, this.m_20191_().m_82400_(30.0), e -> e.m_5448_() == this);
         if (cause.m_7639_() instanceof Mob) {
            Mob damagingMob = (Mob)cause.m_7639_();
            if (damagingMob.m_5448_() == this && !targetingMobs.contains(damagingMob)) {
               targetingMobs.add(damagingMob);
            }
         }

         for (Mob mob : targetingMobs) {
            mob.m_6710_(this.m_5448_());
         }
      }

      super.m_6667_(cause);
   }

   @Override
   protected void sunBlockTarget() {
      LivingEntity target = this.m_5448_();
      if (target != null) {
         EffectHandler.addOrCombineEffect(target, (MobEffect)EffectHandler.SUNBLOCK.get(), 20, 0, true, false);
         if (target.f_19797_ % 20 == 0) {
            target.m_5634_(0.15F);
         }
      }
   }

   public boolean m_6673_(DamageSource source) {
      boolean teleporting = this.getActiveAbilityType() == TELEPORT_ABILITY && this.getAnimationTick() <= 16;
      return super.m_6673_(source)
         || (!this.active || teleporting || !this.hasTriedOrSucceededTeleport) && source != DamageSource.f_19317_ && this.timeUntilDeath != 0;
   }

   public void m_20254_(int seconds) {
      boolean teleporting = this.getActiveAbilityType() == TELEPORT_ABILITY && this.getAnimationTick() <= 16;
      if (this.active && !teleporting && this.hasTriedOrSucceededTeleport) {
         super.m_20254_(seconds);
      }
   }

   @Override
   public SpawnGroupData m_6518_(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, SpawnGroupData livingData, CompoundTag compound) {
      this.setMask(MaskType.FAITH);
      this.setWeapon(3);
      return super.m_6518_(world, difficulty, reason, livingData, compound);
   }

   public static class HealTargetGoal extends Goal {
      private final EntityUmvuthana entity;

      public HealTargetGoal(EntityUmvuthana entityIn) {
         this.entity = entityIn;
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      public boolean m_8045_() {
         return this.entity.canHeal(this.entity.m_5448_());
      }

      public boolean m_8036_() {
         return !this.entity.active ? false : this.entity.canHeal(this.entity.m_5448_());
      }

      public void m_8056_() {
         super.m_8056_();
         AbilityHandler.INSTANCE.sendAbilityMessage(this.entity, EntityUmvuthana.HEAL_ABILITY);
      }
   }

   public class TeleportToSafeSpotGoal extends Goal {
      private final EntityUmvuthanaCrane entity;

      public TeleportToSafeSpotGoal(EntityUmvuthanaCrane entityIn) {
         this.entity = entityIn;
         this.m_7021_(EnumSet.of(Flag.MOVE));
      }

      public boolean m_8036_() {
         if (!this.entity.active) {
            return false;
         } else if (this.entity.getActiveAbilityType() == EntityUmvuthana.TELEPORT_ABILITY) {
            return false;
         } else {
            return this.entity.m_5448_() == null
                  || !this.entity.canHeal(this.entity.m_5448_())
                  || (!(this.entity.targetDistance >= 0.0F) || !(this.entity.targetDistance < 11.0F)) && EntityUmvuthanaCrane.this.hasTriedOrSucceededTeleport
               ? false
               : this.findTeleportLocation();
         }
      }

      public void m_8056_() {
         super.m_8056_();
         EntityUmvuthanaCrane.this.hasTriedOrSucceededTeleport = true;
         AbilityHandler.INSTANCE.sendAbilityMessage(this.entity, EntityUmvuthana.TELEPORT_ABILITY);
      }

      private boolean findTeleportLocation() {
         int i;
         int j;
         int k;
         if (this.entity.m_21535_() > -1.0F) {
            i = Mth.m_14143_((float)this.entity.m_21534_().m_123341_());
            j = Mth.m_14143_((float)this.entity.m_21534_().m_123342_());
            k = Mth.m_14143_((float)this.entity.m_21534_().m_123343_());
         } else if (this.entity.m_5448_() != null) {
            i = Mth.m_14107_(this.entity.m_5448_().m_20185_());
            j = Mth.m_14107_(this.entity.m_5448_().m_20186_());
            k = Mth.m_14107_(this.entity.m_5448_().m_20189_());
         } else {
            i = Mth.m_14107_(this.entity.m_20185_());
            j = Mth.m_14107_(this.entity.m_20186_());
            k = Mth.m_14107_(this.entity.m_20189_());
         }

         boolean foundPosition = false;

         for (int l = 0; l < 50; l++) {
            double radius = Math.pow((double)EntityUmvuthanaCrane.this.f_19796_.m_188501_(), 1.35) * 25.0;
            double angle = (double)EntityUmvuthanaCrane.this.f_19796_.m_188501_() * Math.PI * 2.0;
            int i1 = i + (int)(Math.cos(angle) * radius);
            int j1 = j + Mth.m_216271_(this.entity.f_19796_, 0, 15) * Mth.m_216271_(this.entity.f_19796_, -1, 1);
            int k1 = k + (int)(Math.sin(angle) * radius);
            BlockPos blockpos = new BlockPos(i1, j1, k1);
            Vec3 newPos = new Vec3((double)i1, (double)j1, (double)k1);
            Vec3 offset = newPos.m_82546_(this.entity.m_20182_());
            AABB newBB = this.entity.m_20191_().m_82383_(offset);
            if (this.testBlock(blockpos, newBB) && this.entity.f_19853_.m_45976_(EntityUmvuthi.class, newBB.m_82400_(7.0)).isEmpty()) {
               this.entity.teleportDestination = newPos.m_82520_(0.0, 0.0, 0.0);
               if (this.entity.teleportAttempts >= 3) {
                  foundPosition = true;
               }

               if (this.entity.f_19853_.m_45976_(EntityUmvuthanaCrane.class, newBB.m_82400_(5.0)).isEmpty()) {
                  if (this.entity.teleportAttempts >= 2) {
                     foundPosition = true;
                  }

                  if (!this.entity.f_19853_.m_45914_((double)i1, (double)j1, (double)k1, 5.0) && !this.entity.f_19853_.m_46855_(newBB)) {
                     if (this.entity.teleportAttempts >= 1) {
                        foundPosition = true;
                     }

                     LivingEntity target = EntityUmvuthanaCrane.this.m_5448_();
                     if (!(target instanceof Mob) || ((Mob)target).m_5448_() == null) {
                        return true;
                     }

                     if (!this.canEntityBeSeenFromLocation(((Mob)target).m_5448_(), newPos)) {
                        return true;
                     }
                  }
               }
            }
         }

         this.entity.teleportAttempts++;
         if (this.entity.teleportAttempts > 3) {
            EntityUmvuthanaCrane.this.hasTriedOrSucceededTeleport = true;
         }

         return foundPosition;
      }

      public boolean canEntityBeSeenFromLocation(Entity entityIn, Vec3 location) {
         Vec3 vector3d = new Vec3(location.m_7096_(), location.m_7098_() + (double)this.entity.m_20192_(), location.m_7094_());
         Vec3 vector3d1 = new Vec3(entityIn.m_20185_(), entityIn.m_20188_(), entityIn.m_20189_());
         return this.entity.f_19853_.m_45547_(new ClipContext(vector3d, vector3d1, Block.COLLIDER, Fluid.NONE, this.entity)).m_6662_() != Type.BLOCK;
      }

      public boolean testBlock(BlockPos blockpos, AABB aabb) {
         Level world = this.entity.f_19853_;
         if (!world.m_46805_(blockpos)) {
            return false;
         } else {
            BlockPos blockpos1 = blockpos.m_7495_();
            BlockState blockstate = world.m_8055_(blockpos1);
            return blockstate.m_60767_().m_76333_() && blockstate.m_60767_().m_76334_() && world.m_45772_(aabb);
         }
      }
   }
}

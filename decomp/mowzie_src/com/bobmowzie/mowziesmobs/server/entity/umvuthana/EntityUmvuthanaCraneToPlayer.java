package com.bobmowzie.mowziesmobs.server.entity.umvuthana;

import com.bobmowzie.mowziesmobs.server.ai.NearestAttackableTargetPredicateGoal;
import com.bobmowzie.mowziesmobs.server.potion.EffectHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class EntityUmvuthanaCraneToPlayer extends EntityUmvuthanaFollowerToPlayer {
   public EntityUmvuthanaCraneToPlayer(EntityType<? extends EntityUmvuthanaCraneToPlayer> type, Level world) {
      this(type, world, null);
   }

   public EntityUmvuthanaCraneToPlayer(EntityType<? extends EntityUmvuthanaCraneToPlayer> type, Level world, Player leader) {
      super(type, world, leader);
      this.setMask(MaskType.FAITH);
      this.setWeapon(3);
   }

   @Override
   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(4, new EntityUmvuthanaCrane.HealTargetGoal(this));
   }

   @Override
   protected void registerTargetGoals() {
      super.registerTargetGoals();
      this.f_21346_
         .m_25352_(
            2,
            new NearestAttackableTargetPredicateGoal<Player>(
               this, Player.class, 0, true, true, TargetingConditions.m_148353_().m_26883_(this.m_21133_(Attributes.f_22277_)).m_26888_(target -> {
                  if (!this.active) {
                     return false;
                  } else {
                     return target != this.getLeader() ? false : this.healAICheckTarget(target);
                  }
               }).m_26893_()
            ) {
               public boolean m_8045_() {
                  LivingEntity livingentity = this.f_26135_.m_5448_();
                  if (livingentity == null) {
                     livingentity = this.f_26137_;
                  }

                  return super.m_8045_()
                     && this.f_26135_ instanceof EntityUmvuthanaCraneToPlayer
                     && ((EntityUmvuthanaCraneToPlayer)this.f_26135_).healAICheckTarget(livingentity);
               }
            }
         );
   }

   private boolean healAICheckTarget(LivingEntity livingentity) {
      if (livingentity != this.getLeader()) {
         return false;
      } else {
         boolean targetHasTarget = livingentity.m_21214_() != null
            && (livingentity.f_19797_ - livingentity.m_21215_() < 120 || livingentity.m_20280_(livingentity.m_21214_()) < 256.0);
         if (livingentity.m_21214_() instanceof EntityUmvuthanaFollowerToPlayer) {
            targetHasTarget = false;
         }

         boolean canHeal = this.canHeal(livingentity);
         boolean survivalMode = !livingentity.m_5833_() && !((Player)livingentity).m_7500_();
         return (livingentity.m_21223_() < livingentity.m_21233_() || targetHasTarget) && canHeal && survivalMode;
      }
   }

   @Override
   public boolean canHeal(LivingEntity entity) {
      return entity == this.leader && entity != null && this.m_20280_(entity) < 256.0;
   }

   @Override
   protected void sunBlockTarget() {
      LivingEntity target = this.m_5448_();
      if (target != null && target == this.getLeader()) {
         EffectHandler.addOrCombineEffect(target, (MobEffect)EffectHandler.SUNBLOCK.get(), 20, 0, true, false);
      }
   }

   @Override
   public SpawnGroupData m_6518_(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, SpawnGroupData livingData, CompoundTag compound) {
      this.setMask(MaskType.FAITH);
      this.setWeapon(3);
      return super.m_6518_(world, difficulty, reason, livingData, compound);
   }
}

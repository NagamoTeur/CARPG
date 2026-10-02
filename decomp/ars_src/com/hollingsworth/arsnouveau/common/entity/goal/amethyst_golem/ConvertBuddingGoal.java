package com.hollingsworth.arsnouveau.common.entity.goal.amethyst_golem;

import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.entity.AmethystGolem;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.Blocks;

public class ConvertBuddingGoal extends Goal {
   public AmethystGolem golem;
   public Supplier<Boolean> canUse;
   BlockPos targetCluster;
   int usingTicks;
   boolean isDone;

   public ConvertBuddingGoal(AmethystGolem golem, Supplier<Boolean> canUse) {
      this.golem = golem;
      this.canUse = canUse;
   }

   public boolean m_8045_() {
      return this.targetCluster != null && !this.isDone;
   }

   public void m_8037_() {
      super.m_8037_();
      this.usingTicks--;
      this.golem.getNavigation().tryMoveToBlockPos(this.targetCluster, 1.0);
      if (this.usingTicks <= 0) {
         this.isDone = true;
         this.convert();
      } else {
         if (BlockUtil.distanceFrom(this.golem.m_20183_(), this.targetCluster) <= 2.0) {
            this.golem.setImbuePos(this.targetCluster);
            this.golem.setImbueing(true);
            this.usingTicks = Math.min(this.usingTicks, 40);
         }
      }
   }

   public void convert() {
      if (this.targetCluster != null && this.golem.f_19853_.m_8055_(this.targetCluster).m_60734_() == Blocks.f_152490_) {
         this.golem.f_19853_.m_7731_(this.targetCluster, Blocks.f_152491_.m_49966_(), 3);
         ParticleUtil.spawnTouchPacket(this.golem.f_19853_, this.targetCluster, ParticleUtil.defaultParticleColorWrapper());
      }

      this.golem.convertCooldown = 6000;
      this.golem.setImbueing(false);
      this.golem.setImbuePos(BlockPos.f_121853_);
   }

   public void m_8056_() {
      this.isDone = false;
      this.usingTicks = 120;

      for (BlockPos pos : this.golem.amethystBlocks) {
         if (this.golem.f_19853_.m_8055_(pos).m_60734_() == Blocks.f_152490_) {
            this.targetCluster = pos;
            break;
         }
      }

      this.golem.goalState = AmethystGolem.AmethystGolemGoalState.CONVERT;
   }

   public void m_8041_() {
      this.golem.setImbueing(false);
      this.golem.goalState = AmethystGolem.AmethystGolemGoalState.NONE;
   }

   public boolean m_6767_() {
      return false;
   }

   public boolean m_8036_() {
      return this.canUse.get() && this.golem.convertCooldown <= 0 && !this.golem.amethystBlocks.isEmpty();
   }
}

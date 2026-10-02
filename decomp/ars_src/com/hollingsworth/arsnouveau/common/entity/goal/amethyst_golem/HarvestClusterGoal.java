package com.hollingsworth.arsnouveau.common.entity.goal.amethyst_golem;

import com.hollingsworth.arsnouveau.api.ANFakePlayer;
import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.common.datagen.BlockTagProvider;
import com.hollingsworth.arsnouveau.common.entity.AmethystGolem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;

public class HarvestClusterGoal extends Goal {
   public AmethystGolem golem;
   public Supplier<Boolean> canUse;
   int tickTime;
   boolean isDone;
   List<BlockPos> harvestableList = new ArrayList<>();

   public HarvestClusterGoal(AmethystGolem golem, Supplier<Boolean> canUse) {
      this.golem = golem;
      this.canUse = canUse;
   }

   public void m_8037_() {
      super.m_8037_();
      this.tickTime--;
      this.golem.getNavigation().m_26573_();
      if (this.tickTime % 40 == 0) {
         this.tryDropAmethyst();
      }

      if (this.tickTime <= 0 || this.harvestableList.isEmpty()) {
         this.isDone = true;
         this.golem.setStomping(false);
         this.golem.harvestCooldown = 1200;
      }
   }

   public void tryDropAmethyst() {
      List<BlockPos> harvested = new ArrayList<>();

      for (BlockPos p : this.harvestableList) {
         if (this.hasCluster(p)) {
            harvested.add(p);
            this.harvest(p);
            break;
         }
      }

      for (BlockPos px : harvested) {
         this.harvestableList.remove(px);
      }
   }

   public void harvest(BlockPos p) {
      if (this.golem.f_19853_ instanceof ServerLevel level) {
         for (Direction d : Direction.values()) {
            BlockState state = level.m_8055_(p.m_121945_(d));
            if (state.m_204336_(BlockTagProvider.CLUSTER_BLOCKS)) {
               ItemStack stack = new ItemStack(Items.f_42390_);
               state.m_60734_().m_6240_(level, ANFakePlayer.getPlayer(level), p.m_121945_(d), state, level.m_7702_(p), stack);
               BlockUtil.destroyBlockSafely(level, p.m_121945_(d), false, ANFakePlayer.getPlayer(level));
            }
         }
      }
   }

   public boolean hasCluster(BlockPos p) {
      for (Direction d : Direction.values()) {
         if (this.golem.f_19853_.m_8055_(p.m_121945_(d)).m_204336_(BlockTagProvider.CLUSTER_BLOCKS)) {
            return true;
         }
      }

      return false;
   }

   public boolean m_6767_() {
      return false;
   }

   public void m_8056_() {
      super.m_8056_();
      this.golem.setStomping(true);
      this.golem.getNavigation().m_26573_();
      this.isDone = false;
      this.harvestableList = new ArrayList<>(this.golem.buddingBlocks);
      Collections.shuffle(this.harvestableList);
      this.tickTime = 130;
      this.golem.goalState = AmethystGolem.AmethystGolemGoalState.HARVEST;
   }

   public void m_8041_() {
      this.golem.setStomping(false);
      this.golem.goalState = AmethystGolem.AmethystGolemGoalState.NONE;
   }

   public boolean m_8045_() {
      return !this.isDone;
   }

   public boolean m_8036_() {
      return this.canUse.get() && !this.golem.buddingBlocks.isEmpty();
   }
}

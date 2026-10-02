package com.hollingsworth.arsnouveau.common.entity.goal.amethyst_golem;

import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.common.datagen.ItemTagProvider;
import com.hollingsworth.arsnouveau.common.entity.AmethystGolem;
import java.util.Iterator;
import java.util.function.Supplier;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

public class PickupAmethystGoal extends Goal {
   public AmethystGolem golem;
   public Supplier<Boolean> canUse;
   ItemEntity targetEntity;
   int usingTicks;
   boolean isDone;

   public PickupAmethystGoal(AmethystGolem golem, Supplier<Boolean> canUse) {
      this.golem = golem;
      this.canUse = canUse;
   }

   public boolean m_8045_() {
      return this.targetEntity != null && !this.isDone;
   }

   public void m_8037_() {
      super.m_8037_();
      this.usingTicks--;
      if (this.usingTicks <= 0) {
         this.isDone = true;
         this.collectStacks();
      } else if (this.targetEntity != null && !this.targetEntity.m_213877_() && this.targetEntity.m_32055_().m_204117_(ItemTagProvider.SHARD_TAG)) {
         this.golem.getNavigation().tryMoveToBlockPos(this.targetEntity.m_20183_(), 1.0);
         if (BlockUtil.distanceFrom(this.golem.m_20183_(), this.targetEntity.m_20183_()) <= 1.5) {
            this.collectStacks();
            this.isDone = true;
            this.golem.pickupCooldown = 60 + this.golem.m_217043_().m_188503_(10);
         }
      } else {
         this.isDone = true;
      }
   }

   public void collectStacks() {
      if (this.golem.getHome() != null) {
         for (ItemEntity i : this.golem
            .f_19853_
            .m_6443_(ItemEntity.class, new AABB(this.golem.getHome()).m_82400_(10.0), ix -> ix.m_32055_().m_204117_(ItemTagProvider.SHARD_TAG))) {
            if (this.golem.getHeldStack().m_41619_() || i.m_32055_().m_41720_() == this.golem.getHeldStack().m_41720_()) {
               int maxTake = this.golem.getHeldStack().m_41741_() - this.golem.getHeldStack().m_41613_();
               if (this.golem.getHeldStack().m_41619_()) {
                  this.golem.setHeldStack(i.m_32055_().m_41777_());
                  i.m_32055_().m_41764_(0);
               } else {
                  int toTake = Math.min(i.m_32055_().m_41613_(), maxTake);
                  i.m_32055_().m_41774_(toTake);
                  this.golem.getHeldStack().m_41769_(toTake);
               }
            }
         }
      }
   }

   public void m_8041_() {
      this.isDone = false;
      this.usingTicks = 80;
      this.golem.goalState = AmethystGolem.AmethystGolemGoalState.NONE;
      this.golem.pickupCooldown = 60 + this.golem.m_217043_().m_188503_(10);
   }

   public void m_8056_() {
      this.isDone = false;
      this.usingTicks = 80;
      Iterator var1 = this.golem
         .f_19853_
         .m_6443_(ItemEntity.class, new AABB(this.golem.getHome()).m_82400_(10.0), entityx -> entityx.m_32055_().m_204117_(ItemTagProvider.SHARD_TAG))
         .iterator();
      if (var1.hasNext()) {
         ItemEntity entity = (ItemEntity)var1.next();
         this.golem.getNavigation().tryMoveToBlockPos(entity.m_20183_(), 1.0);
         this.targetEntity = entity;
      }

      if (this.targetEntity == null) {
         this.isDone = true;
      }

      this.golem.goalState = AmethystGolem.AmethystGolemGoalState.PICKUP;
   }

   public boolean m_6767_() {
      return false;
   }

   public boolean m_8036_() {
      if (this.golem.getHome() == null) {
         return false;
      } else {
         BlockEntity entity = this.golem.m_9236_().m_7702_(this.golem.getHome());
         return this.canUse.get() && this.golem.pickupCooldown <= 0 && entity != null && entity.getCapability(ForgeCapabilities.ITEM_HANDLER).isPresent();
      }
   }
}

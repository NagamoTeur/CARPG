package com.hollingsworth.arsnouveau.common.entity.goal.carbuncle;

import com.hollingsworth.arsnouveau.common.entity.Starbuncle;
import com.hollingsworth.arsnouveau.common.entity.debug.DebugEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.pathfinder.Path;

public class FindItem extends Goal {
   protected Starbuncle starbuncle;
   boolean itemStuck;
   int timeFinding;
   int stuckTicks;
   List<ItemEntity> destList = new ArrayList<>();
   ItemEntity dest;
   public StarbyTransportBehavior behavior;
   private final Predicate<ItemEntity> TRUSTED_TARGET_SELECTOR = itemEntity -> !itemEntity.m_32063_()
         && itemEntity.m_6084_()
         && this.behavior.getValidStorePos(itemEntity.m_32055_()) != null;

   public void m_8041_() {
      super.m_8041_();
      this.itemStuck = false;
      this.timeFinding = 0;
      this.destList = new ArrayList<>();
      this.dest = null;
      this.stuckTicks = 0;
      this.starbuncle.goalState = Starbuncle.StarbuncleGoalState.NONE;
   }

   public void m_8056_() {
      super.m_8056_();
      this.timeFinding = 0;
      this.itemStuck = false;
      this.stuckTicks = 0;
      this.starbuncle.goalState = Starbuncle.StarbuncleGoalState.HUNTING_ITEM;
   }

   public FindItem(Starbuncle starbuncle, StarbyTransportBehavior transportBehavior) {
      this.starbuncle = starbuncle;
      this.behavior = transportBehavior;
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public List<ItemEntity> nearbyItems() {
      return this.starbuncle.f_19853_.m_6443_(ItemEntity.class, this.starbuncle.getAABB(), this.TRUSTED_TARGET_SELECTOR);
   }

   public boolean m_8045_() {
      if (this.behavior.isPickupDisabled()) {
         return false;
      } else if (this.timeFinding > 300) {
         this.starbuncle.addGoalDebug(this, new DebugEvent("TooLong", "Stopped finding item, time finding expired"));
         return false;
      } else {
         return !this.itemStuck && this.starbuncle.getHeldStack().m_41619_();
      }
   }

   public boolean m_8036_() {
      if (!this.behavior.isPickupDisabled() && this.starbuncle.getHeldStack().m_41619_()) {
         ItemStack itemstack = this.starbuncle.getHeldStack();
         List<ItemEntity> list = this.nearbyItems();
         this.itemStuck = false;
         this.destList = new ArrayList<>();
         if (itemstack.m_41619_() && !list.isEmpty()) {
            for (ItemEntity entity : list) {
               if (this.behavior.getValidStorePos(entity.m_32055_()) != null) {
                  this.destList.add(entity);
               }
            }
         }

         if (this.destList.isEmpty()) {
            this.starbuncle.addGoalDebug(this, new DebugEvent("NoStacks", "No storable items nearby"));
            return false;
         } else {
            Collections.shuffle(this.destList);

            for (ItemEntity e : this.destList) {
               Path path = this.starbuncle.minecraftPathNav.m_148218_(new BlockPos(e.m_20182_()), 1, 9);
               if (path != null && path.m_77403_()) {
                  this.dest = e;
                  this.starbuncle.addGoalDebug(this, new DebugEvent("DestSet", "Dest set to " + e));
                  break;
               }
            }

            if (this.dest == null) {
               this.starbuncle.setBackOff(30 + this.starbuncle.f_19853_.f_46441_.m_188503_(30));
               this.starbuncle.addGoalDebug(this, new DebugEvent("NotReachable", "No pathable items nearby"));
               return false;
            } else if (this.behavior.isBedPowered()) {
               this.starbuncle.addGoalDebug(this, new DebugEvent("BedPowered", "Bed powered, cannot pickup items"));
               return false;
            } else {
               return this.dest != null && !this.nearbyItems().isEmpty();
            }
         }
      } else {
         return false;
      }
   }

   public void m_8037_() {
      super.m_8037_();
      if (this.dest != null && !this.dest.m_32055_().m_41619_() && !this.dest.m_213877_()) {
         this.timeFinding++;
         this.starbuncle.minecraftPathNav.m_26573_();
         Path path = this.starbuncle.minecraftPathNav.m_148218_(new BlockPos(this.dest.m_20182_()), 1, 9);
         if (path != null && path.m_77403_()) {
            ItemStack itemstack = this.starbuncle.getHeldStack();
            if (!itemstack.m_41619_()) {
               this.itemStuck = true;
               this.starbuncle.addGoalDebug(this, new DebugEvent("ItemPickup", "Received item, ending."));
            } else {
               this.starbuncle.getNavigation().m_5624_(this.dest, 1.4);
               this.starbuncle.addGoalDebug(this, new DebugEvent("PathTo", "Pathing to " + this.dest));
            }
         } else {
            this.stuckTicks++;
            if (this.stuckTicks > 100) {
               this.itemStuck = true;
               this.starbuncle.addGoalDebug(this, new DebugEvent("ItemStuck", "Item stuck for 5 seconds. Ending goal"));
            }
         }
      } else {
         this.itemStuck = true;
         this.starbuncle.addGoalDebug(this, new DebugEvent("ItemRemoved", "Item removed during goal"));
      }
   }

   public boolean m_6767_() {
      return false;
   }
}

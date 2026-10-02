package com.hollingsworth.arsnouveau.common.entity.goal.carbuncle;

import com.hollingsworth.arsnouveau.common.entity.Starbuncle;
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
import net.minecraftforge.common.Tags.Items;

public class UntamedFindItem extends Goal {
   private Starbuncle starbuncle;
   boolean itemStuck;
   int timeFinding;
   int stuckTicks;
   List<ItemEntity> destList = new ArrayList<>();
   ItemEntity dest;
   private final Predicate<ItemEntity> NONTAMED_TARGET_SELECTOR = itemEntity -> !itemEntity.m_32063_()
         && itemEntity.m_6084_()
         && itemEntity.m_32055_().m_204117_(Items.NUGGETS_GOLD);

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

   public UntamedFindItem(Starbuncle starbuncle) {
      this.starbuncle = starbuncle;
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public List<ItemEntity> nearbyItems() {
      return this.starbuncle.f_19853_.m_6443_(ItemEntity.class, this.starbuncle.getAABB(), this.NONTAMED_TARGET_SELECTOR);
   }

   public boolean m_8045_() {
      return this.timeFinding <= 300 && !this.itemStuck && this.starbuncle.getHeldStack().m_41619_();
   }

   public boolean m_8036_() {
      if (!this.starbuncle.getHeldStack().m_41619_()) {
         return false;
      } else {
         ItemStack itemstack = this.starbuncle.getHeldStack();
         List<ItemEntity> list = this.nearbyItems();
         this.itemStuck = false;
         this.destList = new ArrayList<>();
         if (itemstack.m_41619_() && !list.isEmpty()) {
            this.destList.addAll(list);
         }

         if (this.destList.isEmpty()) {
            return false;
         } else {
            Collections.shuffle(this.destList);

            for (ItemEntity e : this.destList) {
               Path path = this.starbuncle.minecraftPathNav.m_148218_(new BlockPos(e.m_20182_()), 1, 9);
               if (path != null && path.m_77403_()) {
                  this.dest = e;
                  break;
               }
            }

            if (this.dest == null) {
               this.starbuncle.setBackOff(30 + this.starbuncle.f_19853_.f_46441_.m_188503_(30));
            }

            return this.dest != null && !this.nearbyItems().isEmpty();
         }
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
            } else {
               this.starbuncle.getNavigation().m_5624_(this.dest, 1.4);
            }
         } else {
            this.stuckTicks++;
            if (this.stuckTicks > 100) {
               this.itemStuck = true;
            }
         }
      } else {
         this.itemStuck = true;
      }
   }

   public boolean m_6767_() {
      return false;
   }
}

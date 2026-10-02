package com.hollingsworth.arsnouveau.common.entity.goal.amethyst_golem;

import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.common.entity.AmethystGolem;
import java.util.function.Supplier;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class DepositAmethystGoal extends Goal {
   public AmethystGolem golem;
   public Supplier<Boolean> canUse;
   int usingTicks;
   boolean isDone;

   public DepositAmethystGoal(AmethystGolem golem, Supplier<Boolean> canUse) {
      this.golem = golem;
      this.canUse = canUse;
   }

   public boolean m_8045_() {
      return this.golem.getHome() != null && !this.isDone;
   }

   public void m_8037_() {
      super.m_8037_();
      this.usingTicks--;
      if (this.usingTicks <= 0) {
         this.isDone = true;
         this.deposit();
      } else if (this.golem.getHome() != null) {
         if (BlockUtil.distanceFrom(this.golem.m_20183_(), this.golem.getHome()) <= 2.0) {
            this.isDone = true;
            this.deposit();
         }

         this.golem.getNavigation().tryMoveToBlockPos(this.golem.getHome(), 1.0);
      }
   }

   public void deposit() {
      BlockEntity tileEntity = this.golem.m_9236_().m_7702_(this.golem.getHome());
      if (tileEntity != null) {
         IItemHandler iItemHandler = (IItemHandler)tileEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(null);
         if (iItemHandler != null) {
            ItemStack oldStack = new ItemStack(this.golem.getHeldStack().m_41720_(), this.golem.getHeldStack().m_41613_());
            ItemStack left = ItemHandlerHelper.insertItemStacked(iItemHandler, this.golem.getHeldStack(), false);
            if (left.equals(oldStack)) {
               return;
            }

            this.golem.setHeldStack(left);
         }
      }
   }

   public void m_8056_() {
      this.isDone = false;
      this.usingTicks = 80;
      this.golem.goalState = AmethystGolem.AmethystGolemGoalState.DEPOSIT;
   }

   public void m_8041_() {
      super.m_8041_();
      this.golem.goalState = AmethystGolem.AmethystGolemGoalState.NONE;
   }

   public boolean m_6767_() {
      return false;
   }

   public boolean m_8036_() {
      if (this.golem.getHome() != null && !this.golem.getHeldStack().m_41619_()) {
         BlockEntity entity = this.golem.m_9236_().m_7702_(this.golem.getHome());
         return this.canUse.get() && entity != null && entity.getCapability(ForgeCapabilities.ITEM_HANDLER).isPresent();
      } else {
         return false;
      }
   }
}

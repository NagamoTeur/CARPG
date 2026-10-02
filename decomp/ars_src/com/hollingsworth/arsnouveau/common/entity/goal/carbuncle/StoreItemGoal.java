package com.hollingsworth.arsnouveau.common.entity.goal.carbuncle;

import com.hollingsworth.arsnouveau.api.event.EventQueue;
import com.hollingsworth.arsnouveau.common.entity.Starbuncle;
import com.hollingsworth.arsnouveau.common.entity.debug.DebugEvent;
import com.hollingsworth.arsnouveau.common.event.OpenChestEvent;
import com.hollingsworth.arsnouveau.common.items.ItemScroll;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class StoreItemGoal<T extends StarbyTransportBehavior> extends GoToPosGoal<T> {
   public StoreItemGoal(Starbuncle starbuncle, T behavior) {
      super(starbuncle, behavior, () -> !starbuncle.getHeldStack().m_41619_());
   }

   @Override
   public void m_8056_() {
      super.m_8056_();
      this.starbuncle.goalState = Starbuncle.StarbuncleGoalState.STORING_ITEM;
   }

   @Override
   public BlockPos getDestination() {
      return this.behavior.getValidStorePos(this.starbuncle.getHeldStack());
   }

   @Override
   public boolean onDestinationReached() {
      this.starbuncle.getNavigation().m_26573_();
      Level world = this.starbuncle.f_19853_;
      BlockEntity tileEntity = world.m_7702_(this.targetPos);
      if (tileEntity == null) {
         this.starbuncle.addGoalDebug(this, new DebugEvent("missing_tile", "store pos broken " + this.targetPos.toString()));
         this.starbuncle.setBackOff(5 + this.starbuncle.f_19853_.f_46441_.m_188503_(20));
         return true;
      } else {
         IItemHandler iItemHandler = this.behavior.getItemCapFromTile(tileEntity);
         if (iItemHandler == null) {
            this.starbuncle.addGoalDebug(this, new DebugEvent("NoItemHandler", "No item handler at " + this.targetPos.toString()));
            return true;
         } else {
            ItemStack oldStack = new ItemStack(this.starbuncle.getHeldStack().m_41720_(), this.starbuncle.getHeldStack().m_41613_());
            ItemStack left = ItemHandlerHelper.insertItemStacked(iItemHandler, this.starbuncle.getHeldStack(), false);
            if (left.equals(oldStack)) {
               this.starbuncle.setBackOff(5 + this.starbuncle.f_19853_.f_46441_.m_188503_(20));
               this.starbuncle.addGoalDebug(this, new DebugEvent("no_room", this.targetPos.toString()));
               return true;
            } else {
               try {
                  OpenChestEvent event = new OpenChestEvent((ServerLevel)this.starbuncle.f_19853_, this.targetPos, 20);
                  event.open();
                  EventQueue.getServerInstance().addEvent(event);
               } catch (Exception var7) {
               }

               this.starbuncle.setHeldStack(left);
               this.starbuncle
                  .addGoalDebug(
                     this,
                     new DebugEvent(
                        "stored_item", "successful at " + this.targetPos.toString() + "set stack to " + left.m_41613_() + "x " + left.m_41786_().getString()
                     )
                  );
               return true;
            }
         }
      }
   }

   @Override
   public boolean isDestinationStillValid(BlockPos pos) {
      return this.behavior.isValidStorePos(pos, this.starbuncle.getHeldStack()) != ItemScroll.SortPref.INVALID;
   }
}

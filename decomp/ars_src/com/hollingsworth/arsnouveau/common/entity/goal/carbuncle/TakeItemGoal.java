package com.hollingsworth.arsnouveau.common.entity.goal.carbuncle;

import com.hollingsworth.arsnouveau.api.event.EventQueue;
import com.hollingsworth.arsnouveau.common.entity.Starbuncle;
import com.hollingsworth.arsnouveau.common.entity.debug.DebugEvent;
import com.hollingsworth.arsnouveau.common.event.OpenChestEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

public class TakeItemGoal<T extends StarbyTransportBehavior> extends GoToPosGoal<T> {
   public TakeItemGoal(Starbuncle starbuncle, T behavior) {
      super(starbuncle, behavior, () -> starbuncle.getHeldStack().m_41619_());
   }

   @Override
   public boolean m_8036_() {
      boolean superCan = super.m_8036_();
      if (!superCan || this.behavior.FROM_LIST.isEmpty()) {
         return false;
      } else if (this.getDestination() == null) {
         this.starbuncle.addGoalDebug(this, new DebugEvent("NoTakeDestination", "No valid take destination"));
         this.starbuncle.setBackOff(5 + this.starbuncle.f_19853_.f_46441_.m_188503_(20));
         return false;
      } else if (this.behavior.isBedPowered()) {
         this.starbuncle.addGoalDebug(this, new DebugEvent("BedPowered", "Bed Powered, cannot take items"));
         return false;
      } else {
         return true;
      }
   }

   @Override
   public boolean m_8045_() {
      return super.m_8045_() && !this.behavior.isBedPowered();
   }

   @Override
   public void m_8056_() {
      super.m_8056_();
      this.starbuncle.goalState = Starbuncle.StarbuncleGoalState.TAKING_ITEM;
   }

   @Override
   public boolean isDestinationStillValid(BlockPos pos) {
      return this.behavior.isPositionValidTake(pos);
   }

   @Nullable
   @Override
   public BlockPos getDestination() {
      return this.behavior.getValidTakePos();
   }

   @Override
   public boolean onDestinationReached() {
      Level world = this.starbuncle.f_19853_;
      BlockEntity tileEntity = world.m_7702_(this.targetPos);
      if (tileEntity == null) {
         this.starbuncle.addGoalDebug(this, new DebugEvent("TakePosBroken", "Take Tile Broken"));
         return true;
      } else {
         IItemHandler iItemHandler = this.behavior.getItemCapFromTile(tileEntity);
         if (iItemHandler == null) {
            this.starbuncle.addGoalDebug(this, new DebugEvent("NoItemHandler", "No item handler at " + this.targetPos.toString()));
            return true;
         } else {
            for (int j = 0; j < iItemHandler.getSlots() && this.starbuncle.getHeldStack().m_41619_(); j++) {
               ItemStack stack = iItemHandler.getStackInSlot(j);
               if (!stack.m_41619_()) {
                  int count = this.behavior.getMaxTake(iItemHandler.getStackInSlot(j));
                  if (count > 0) {
                     this.starbuncle.setHeldStack(iItemHandler.extractItem(j, Math.min(count, stack.m_41741_()), false));
                     this.starbuncle
                        .addGoalDebug(
                           this,
                           new DebugEvent(
                              "SetHeld",
                              "Taking " + count + "x " + this.starbuncle.getHeldStack().m_41786_().getString() + " from " + this.targetPos.toString()
                           )
                        );
                     this.starbuncle
                        .f_19853_
                        .m_6263_(
                           null,
                           this.starbuncle.m_20185_(),
                           this.starbuncle.m_20186_(),
                           this.starbuncle.m_20189_(),
                           SoundEvents.f_12019_,
                           this.starbuncle.m_5720_(),
                           1.0F,
                           1.0F
                        );
                     if (world instanceof ServerLevel) {
                        ServerLevel serverLevel = (ServerLevel)world;

                        try {
                           OpenChestEvent event = new OpenChestEvent(serverLevel, this.targetPos, 20);
                           event.open();
                           EventQueue.getServerInstance().addEvent(event);
                        } catch (Exception var9) {
                        }
                     }
                  }
               }
            }

            if (this.starbuncle.getHeldStack().m_41619_()) {
               this.starbuncle.addGoalDebug(this, new DebugEvent("TakeFromChest", "No items to take? Cancelling goal."));
            }

            return true;
         }
      }
   }
}

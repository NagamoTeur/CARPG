package com.hollingsworth.arsnouveau.common.entity.goal.wixie;

import com.hollingsworth.arsnouveau.api.event.EventQueue;
import com.hollingsworth.arsnouveau.api.event.FlyingItemEvent;
import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.block.tile.StorageLecternTile;
import com.hollingsworth.arsnouveau.common.block.tile.WixieCauldronTile;
import com.hollingsworth.arsnouveau.common.entity.EntityFlyingItem;
import com.hollingsworth.arsnouveau.common.entity.EntityWixie;
import com.hollingsworth.arsnouveau.common.entity.goal.ExtendedRangeGoal;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketAnimEntity;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;

public class FindNextItemGoal extends ExtendedRangeGoal {
   EntityWixie wixie;
   BlockPos movePos;
   ItemStack getStack;
   boolean found;

   public FindNextItemGoal(EntityWixie wixie) {
      super(10);
      this.wixie = wixie;
      this.m_7021_(EnumSet.of(Flag.LOOK, Flag.MOVE));
   }

   @Override
   public void m_8056_() {
      super.m_8056_();
      this.movePos = null;
      Level world = this.wixie.m_20193_();
      WixieCauldronTile tile = (WixieCauldronTile)world.m_7702_(this.wixie.cauldronPos);
      if (tile != null && tile.getInventories() != null) {
         this.getStack = tile.craftManager.getNextItem();
         if (this.getStack.m_41619_()) {
            this.found = true;
         } else {
            Set<Item> itemSet = new HashSet<>();
            itemSet.add(this.getStack.m_41720_());

            label41:
            for (BlockPos b : tile.getInventories()) {
               BlockEntity blockEntity = world.m_7702_(b);
               if (blockEntity != null) {
                  IItemHandler itemHandler = (IItemHandler)blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(null);
                  if (itemHandler != null) {
                     int i = 0;

                     while (true) {
                        if (i < itemHandler.getSlots()) {
                           ItemStack stack = itemHandler.getStackInSlot(i);
                           if (stack.m_41720_() != this.getStack.m_41720_()) {
                              i++;
                              continue;
                           }

                           this.movePos = b.m_7949_();
                           this.startDistance = BlockUtil.distanceFrom(this.wixie.f_19825_, this.movePos);
                        }

                        if (this.movePos != null) {
                           break label41;
                        }
                        break;
                     }
                  }
               }
            }

            this.found = false;
         }
      } else {
         this.found = true;
      }
   }

   @Override
   public void m_8041_() {
      super.m_8041_();
   }

   @Override
   public boolean m_8036_() {
      if (this.wixie.cauldronPos == null) {
         return false;
      } else {
         return !(this.wixie.f_19853_.m_7702_(this.wixie.cauldronPos) instanceof WixieCauldronTile cauldronTile)
            ? false
            : this.wixie.inventoryBackoff == 0
               && cauldronTile.hasSource
               && !cauldronTile.isCraftingDone()
               && !cauldronTile.isOff
               && !cauldronTile.craftManager.getNextItem().m_41619_();
      }
   }

   public boolean m_8045_() {
      return !this.found;
   }

   @Override
   public void m_8037_() {
      super.m_8037_();
      if (!this.found && this.movePos != null && BlockUtil.distanceFrom(this.wixie.m_20182_(), this.movePos.m_7494_()) < 2.0 + this.extendedRange) {
         WixieCauldronTile tile = (WixieCauldronTile)this.wixie.m_20193_().m_7702_(this.wixie.cauldronPos);
         Level world = this.wixie.m_20193_();
         if (tile == null) {
            this.found = true;
         } else {
            List<ItemStack> neededStacks = new ArrayList<>(tile.craftManager.neededItems);
            boolean anyFound = false;
            int spawnDelay = 0;
            List<StorageLecternTile.HandlerPos> handlers = new ArrayList<>();

            for (BlockPos b : tile.getInventories()) {
               BlockEntity blockEntity = world.m_7702_(b);
               if (blockEntity != null) {
                  IItemHandler itemHandler = (IItemHandler)blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(null);
                  if (itemHandler != null) {
                     handlers.add(new StorageLecternTile.HandlerPos(b.m_7949_(), itemHandler));
                  }
               }
            }

            for (ItemStack needed : neededStacks) {
               for (StorageLecternTile.HandlerPos handler : handlers) {
                  if (tile.craftManager.neededItems.isEmpty()) {
                     this.found = true;
                     return;
                  }

                  IItemHandler itemHandler = handler.handler();

                  for (int j = 0; j < itemHandler.getSlots(); j++) {
                     ItemStack slotStack = itemHandler.getStackInSlot(j);
                     if (slotStack.m_41720_() == needed.m_41720_()) {
                        int size = slotStack.m_41613_();
                        int numNeeded = (int)tile.craftManager.neededItems.stream().filter(stack -> stack.m_41720_() == slotStack.m_41720_()).count();
                        int canExtract = Math.min(size, numNeeded);

                        for (int count = 0; count < canExtract; count++) {
                           ItemStack stackToGive = itemHandler.extractItem(j, 1, false);
                           this.spawnFlyingItem(tile.m_58904_(), tile.m_58899_(), handler.pos(), stackToGive, 1 + 3 * spawnDelay++);
                           tile.giveItem(stackToGive);
                           if (!anyFound) {
                              Networking.sendToNearby(
                                 world, this.wixie, new PacketAnimEntity(this.wixie.m_19879_(), EntityWixie.Animations.SUMMON_ITEM.ordinal())
                              );
                              this.wixie.inventoryBackoff = 60;
                              anyFound = true;
                           }
                        }
                     }
                  }
               }
            }

            this.found = true;
         }
      } else {
         if (this.movePos != null && !this.found) {
            this.setPath((double)this.movePos.m_123341_(), (double)(this.movePos.m_123342_() + 1), (double)this.movePos.m_123343_(), 1.2);
         }
      }
   }

   public void spawnFlyingItem(Level level, BlockPos worldPosition, BlockPos from, ItemStack stack, int delay) {
      BlockPos above = from.m_7494_();
      EntityFlyingItem flyingItem = new EntityFlyingItem(
         level,
         new Vec3((double)above.m_123341_() + 0.5, (double)above.m_123342_(), (double)above.m_123343_() + 0.5)
            .m_82520_(ParticleUtil.inRange(-0.25, 0.25), 0.0, ParticleUtil.inRange(-0.25, 0.25)),
         new Vec3((double)worldPosition.m_123341_() + 0.5, (double)worldPosition.m_123342_(), (double)worldPosition.m_123343_() + 0.5)
            .m_82520_(ParticleUtil.inRange(-0.25, 0.25), 0.0, ParticleUtil.inRange(-0.25, 0.25))
      );
      flyingItem.m_20088_().m_135381_(EntityFlyingItem.HELD_ITEM, stack.m_41777_());
      EventQueue.getServerInstance().addEvent(new FlyingItemEvent(level, flyingItem, delay));
   }

   public void setPath(double x, double y, double z, double speedIn) {
      this.wixie.m_21573_().m_26536_(this.wixie.m_21573_().m_26524_(x + 0.5, y + 0.5, z + 0.5, 0), speedIn);
   }
}

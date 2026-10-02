package com.hollingsworth.arsnouveau.common.entity.goal.carbuncle;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.hollingsworth.arsnouveau.common.entity.Starbuncle;
import com.hollingsworth.arsnouveau.common.items.ItemScroll;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class StarbyTransportBehavior extends StarbyListBehavior {
   public static Cache<BlockPos, List<ItemEntity>> frameCache = CacheBuilder.newBuilder().expireAfterAccess(20L, TimeUnit.SECONDS).build();
   public static final ResourceLocation TRANSPORT_ID = new ResourceLocation("ars_nouveau", "starby_transport");
   public ItemStack itemScroll;

   public StarbyTransportBehavior(Starbuncle entity, CompoundTag tag) {
      super(entity, tag);
      if (entity.isTamed()) {
         if (tag.m_128441_("itemScroll")) {
            this.itemScroll = ItemStack.m_41712_(tag.m_128469_("itemScroll"));
         }

         this.goals.add(new WrappedGoal(1, new FindItem(this.starbuncle, this)));
         this.goals.add(new WrappedGoal(2, new ForageManaBerries(this.starbuncle, this)));
         this.goals.add(new WrappedGoal(3, new StoreItemGoal<>(this.starbuncle, this)));
         this.goals.add(new WrappedGoal(3, new TakeItemGoal<>(this.starbuncle, this)));
      }
   }

   @Override
   public InteractionResult mobInteract(Player player, InteractionHand hand) {
      ItemStack stack = player.m_21120_(hand);
      if (stack.m_41720_() instanceof ItemScroll scroll) {
         this.itemScroll = stack.m_41777_();
         PortUtil.sendMessage(player, Component.m_237115_("ars_nouveau.filter_set"));
         this.syncTag();
      }

      return super.mobInteract(player, hand);
   }

   @Override
   public void pickUpItem(ItemEntity itemEntity) {
      super.pickUpItem(itemEntity);
      if (this.getValidStorePos(itemEntity.m_32055_()) != null && !this.isPickupDisabled()) {
         this.starbuncle.setHeldStack(itemEntity.m_32055_());
         itemEntity.m_142687_(RemovalReason.DISCARDED);
         this.level.m_6263_(null, this.getX(), this.getY(), this.getZ(), SoundEvents.f_12019_, this.starbuncle.m_5720_(), 1.0F, 1.0F);

         for (ItemEntity i : this.level.m_45976_(ItemEntity.class, this.starbuncle.m_20191_().m_82400_(3.0))) {
            if (itemEntity.m_32055_().m_41613_() >= itemEntity.m_32055_().m_41741_()) {
               break;
            }

            int maxTake = this.starbuncle.getHeldStack().m_41741_() - this.starbuncle.getHeldStack().m_41613_();
            if (ItemStack.m_150942_(i.m_32055_(), this.starbuncle.getHeldStack())) {
               int toTake = Math.min(i.m_32055_().m_41613_(), maxTake);
               i.m_32055_().m_41774_(toTake);
               this.starbuncle.getHeldStack().m_41769_(toTake);
            }
         }
      }
   }

   public BlockPos getValidStorePos(ItemStack stack) {
      if (!this.TO_LIST.isEmpty() && !stack.m_41619_()) {
         BlockPos returnPos = null;
         ItemScroll.SortPref foundPref = ItemScroll.SortPref.INVALID;

         for (BlockPos b : this.TO_LIST) {
            ItemScroll.SortPref pref = this.isValidStorePos(b, stack);
            if (pref.ordinal() > foundPref.ordinal()) {
               foundPref = pref;
               returnPos = b;
               if (pref == ItemScroll.SortPref.HIGHEST) {
                  return b;
               }
            }
         }

         return returnPos;
      } else {
         return null;
      }
   }

   public ItemScroll.SortPref isValidStorePos(@Nullable BlockPos b, ItemStack stack) {
      return stack != null && !stack.m_41619_() && b != null && this.level.m_46749_(b)
         ? this.canDepositItem(this.level.m_7702_(b), stack)
         : ItemScroll.SortPref.INVALID;
   }

   public boolean isPickupDisabled() {
      return this.starbuncle.getCosmeticItem().m_41720_() == ItemsRegistry.STARBUNCLE_SHADES.get();
   }

   @Nullable
   public IItemHandler getItemCapFromTile(BlockEntity blockEntity) {
      if (blockEntity != null && blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).isPresent()) {
         Optional<IItemHandler> lazy = blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).resolve();
         if (lazy.isPresent()) {
            return lazy.get();
         }
      }

      return null;
   }

   @Nullable
   public BlockPos getValidTakePos() {
      if (this.FROM_LIST.isEmpty()) {
         return null;
      } else {
         for (BlockPos p : this.FROM_LIST) {
            if (this.isPositionValidTake(p)) {
               return p;
            }
         }

         return null;
      }
   }

   public boolean isPositionValidTake(BlockPos p) {
      if (p != null && this.level.m_46749_(p)) {
         IItemHandler iItemHandler = this.getItemCapFromTile(this.level.m_7702_(p));
         if (iItemHandler == null) {
            return false;
         } else {
            for (int j = 0; j < iItemHandler.getSlots(); j++) {
               ItemStack stack = iItemHandler.getStackInSlot(j);
               if (!stack.m_41619_() && this.getValidStorePos(stack) != null) {
                  return true;
               }
            }

            return false;
         }
      } else {
         return false;
      }
   }

   public int getMaxTake(ItemStack stack) {
      if (this.getValidStorePos(stack) == null) {
         return -1;
      } else {
         BlockPos validStorePos = this.getValidStorePos(stack);
         if (validStorePos == null) {
            return -1;
         } else {
            IItemHandler handler = this.getItemCapFromTile(this.level.m_7702_(validStorePos));
            if (handler == null) {
               return -1;
            } else {
               for (int i = 0; i < handler.getSlots(); i++) {
                  ItemStack handlerStack = handler.getStackInSlot(i);
                  if (handlerStack.m_41619_()) {
                     return handler.getSlotLimit(i);
                  }

                  if (ItemHandlerHelper.canItemStacksStack(handler.getStackInSlot(i), stack)) {
                     int originalCount = stack.m_41613_();
                     ItemStack simStack = handler.insertItem(i, stack, true);
                     int maxRoom = originalCount - simStack.m_41613_();
                     if (maxRoom > 0) {
                        return Math.min(maxRoom, handler.getSlotLimit(i));
                     }
                  }
               }

               return -1;
            }
         }
      }
   }

   private ItemScroll.SortPref canDepositItem(BlockEntity tile, ItemStack stack) {
      ItemScroll.SortPref pref = ItemScroll.SortPref.LOW;
      if (tile != null && stack != null && !stack.m_41619_()) {
         IItemHandler handler = this.getItemCapFromTile(tile);
         if (handler == null) {
            return ItemScroll.SortPref.INVALID;
         } else {
            for (ItemFrame i : this.level.m_45976_(ItemFrame.class, new AABB(tile.m_58899_()).m_82400_(1.0))) {
               BlockEntity adjTile = this.level.m_7702_(i.m_20183_().m_121945_(i.m_6350_().m_122424_()));
               if (adjTile != null && adjTile.equals(tile) && !i.m_31822_().m_41619_()) {
                  ItemStack stackInFrame = i.m_31822_();
                  if (stackInFrame.m_41720_() instanceof ItemScroll scrollItem) {
                     pref = scrollItem.getSortPref(stack, stackInFrame, handler);
                  } else {
                     if (i.m_31822_().m_41720_() != stack.m_41720_()) {
                        return ItemScroll.SortPref.INVALID;
                     }

                     if (i.m_31822_().m_41720_() == stack.m_41720_()) {
                        pref = ItemScroll.SortPref.HIGHEST;
                     }
                  }
               }
            }

            if (this.itemScroll != null
               && this.itemScroll.m_41720_() instanceof ItemScroll scrollItem
               && scrollItem.getSortPref(stack, this.itemScroll, handler) == ItemScroll.SortPref.INVALID) {
               return ItemScroll.SortPref.INVALID;
            } else {
               return !ItemStack.m_41728_(ItemHandlerHelper.insertItemStacked(handler, stack.m_41777_(), true), stack) ? pref : ItemScroll.SortPref.INVALID;
            }
         }
      } else {
         return ItemScroll.SortPref.INVALID;
      }
   }

   @Override
   public boolean canGoToBed() {
      return this.isBedPowered()
         || this.getValidTakePos() == null && (this.starbuncle.getHeldStack().m_41619_() || this.getValidStorePos(this.starbuncle.getHeldStack()) == null);
   }

   @Override
   public void onFinishedConnectionFirst(
      @org.jetbrains.annotations.Nullable BlockPos storedPos, @org.jetbrains.annotations.Nullable LivingEntity storedEntity, Player playerEntity
   ) {
      super.onFinishedConnectionFirst(storedPos, storedEntity, playerEntity);
      if (storedPos != null) {
         BlockEntity blockEntity = this.level.m_7702_(storedPos);
         if (blockEntity != null && blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).isPresent()) {
            PortUtil.sendMessage(playerEntity, Component.m_237115_("ars_nouveau.starbuncle.store"));
            this.addToPos(storedPos);
         }
      }
   }

   @Override
   public void onFinishedConnectionLast(
      @org.jetbrains.annotations.Nullable BlockPos storedPos, @org.jetbrains.annotations.Nullable LivingEntity storedEntity, Player playerEntity
   ) {
      super.onFinishedConnectionLast(storedPos, storedEntity, playerEntity);
      if (storedPos != null) {
         BlockEntity blockEntity = this.level.m_7702_(storedPos);
         if (blockEntity != null && blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).isPresent()) {
            PortUtil.sendMessage(playerEntity, Component.m_237115_("ars_nouveau.starbuncle.take"));
            this.addFromPos(storedPos);
         }
      }
   }

   @Override
   public void onWanded(Player playerEntity) {
      this.itemScroll = ItemStack.f_41583_;
      super.onWanded(playerEntity);
   }

   @Override
   public CompoundTag toTag(CompoundTag tag) {
      super.toTag(tag);
      if (this.itemScroll != null) {
         tag.m_128365_("itemScroll", this.itemScroll.serializeNBT());
      }

      return tag;
   }

   @Override
   public void getTooltip(List<Component> tooltip) {
      super.getTooltip(tooltip);
      tooltip.add(Component.m_237110_("ars_nouveau.starbuncle.storing", new Object[]{this.TO_LIST.size()}));
      tooltip.add(Component.m_237110_("ars_nouveau.starbuncle.taking", new Object[]{this.FROM_LIST.size()}));
      if (this.itemScroll != null && !this.itemScroll.m_41619_()) {
         tooltip.add(Component.m_237110_("ars_nouveau.filtering_with", new Object[]{this.itemScroll.m_41786_().getString()}));
      }
   }

   @Override
   protected ResourceLocation getRegistryName() {
      return TRANSPORT_ID;
   }
}

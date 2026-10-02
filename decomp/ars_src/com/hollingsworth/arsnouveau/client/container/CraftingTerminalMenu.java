package com.hollingsworth.arsnouveau.client.container;

import com.google.common.collect.Lists;
import com.hollingsworth.arsnouveau.common.block.tile.CraftingLecternTile;
import com.hollingsworth.arsnouveau.common.menu.MenuRegistry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.recipebook.ServerPlaceRecipe;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

public class CraftingTerminalMenu extends StorageTerminalMenu implements IAutoFillTerminal {
   protected List<CraftingTerminalMenu.SlotCrafting> craftSlotList = new ArrayList<>();
   private final CraftingContainer craftMatrix;
   private final ResultContainer craftResult;
   private CraftingTerminalMenu.ActiveResultSlot craftingResultSlot;
   private final List<ContainerListener> listeners = Lists.newArrayList();

   public void m_38893_(ContainerListener listener) {
      super.m_38893_(listener);
      this.listeners.add(listener);
   }

   public void m_38943_(ContainerListener listener) {
      super.m_38943_(listener);
      this.listeners.remove(listener);
   }

   public CraftingTerminalMenu(int id, Inventory inv, CraftingLecternTile te) {
      super((MenuType<?>)MenuRegistry.STORAGE.get(), id, inv, te);
      this.craftMatrix = te.getCraftingInv();
      this.craftResult = te.getCraftResult();
      this.init();
      this.addPlayerSlots(inv, 13, 157);
      te.registerCrafting(this);
   }

   public CraftingTerminalMenu(int id, Inventory inv) {
      super((MenuType<?>)MenuRegistry.STORAGE.get(), id, inv);
      this.craftMatrix = new CraftingContainer(this, 3, 3);
      this.craftResult = new ResultContainer();
      this.init();
      this.addPlayerSlots(inv, 13, 157);
   }

   public void m_6877_(Player playerIn) {
      super.m_6877_(playerIn);
      if (this.te instanceof CraftingLecternTile craftingLecternTile) {
         craftingLecternTile.unregisterCrafting(this);
      }
   }

   private void init() {
      int x = -4;
      int y = 70;
      this.m_38897_(
         this.craftingResultSlot = new CraftingTerminalMenu.ActiveResultSlot(this.pinv.f_35978_, this.craftMatrix, this.craftResult, 0, x + 130, y + 37) {
            public void m_142406_(Player thePlayer, ItemStack stack) {
               if (!thePlayer.f_19853_.f_46443_) {
                  this.m_5845_(stack);
                  if (!CraftingTerminalMenu.this.pinv.f_35978_.m_20193_().f_46443_) {
                     ((CraftingLecternTile)CraftingTerminalMenu.this.te).craft(thePlayer, CraftingTerminalMenu.this.selectedTab);
                  }
               }
            }
         }
      );
      if (this.terminalData == null || !this.terminalData.expanded) {
         for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
               CraftingTerminalMenu.SlotCrafting slot = new CraftingTerminalMenu.SlotCrafting(this.craftMatrix, j + i * 3, x + 36 + j * 18, 89 + i * 18);
               this.m_38897_(slot);
               this.craftSlotList.add(slot);
            }
         }
      }
   }

   @Override
   protected void addStorageSlots() {
      this.addStorageSlots(13, 21);
   }

   @Override
   public void addStorageSlots(int slotOffsetX, int slotOffsetY) {
      super.addStorageSlots(slotOffsetX, slotOffsetY);
      if (this.craftSlotList != null && this.terminalData != null) {
         for (CraftingTerminalMenu.SlotCrafting slot : this.craftSlotList) {
            slot.active = !this.terminalData.expanded;
         }

         this.craftingResultSlot.active = !this.terminalData.expanded;
      }
   }

   public boolean m_5882_(ItemStack stack, Slot slotIn) {
      return slotIn.f_40218_ != this.craftResult && super.m_5882_(stack, slotIn);
   }

   @Override
   public ItemStack shiftClickItems(Player playerIn, int index) {
      ItemStack itemstack = ItemStack.f_41583_;
      Slot slot = (Slot)this.f_38839_.get(index);
      if (slot != null && slot.m_6657_()) {
         ItemStack itemstack1 = slot.m_7993_();
         itemstack = itemstack1.m_41777_();
         if (index == 0) {
            if (this.te == null) {
               return ItemStack.f_41583_;
            }

            ((CraftingLecternTile)this.te).craftShift(playerIn, this.selectedTab);
            if (!playerIn.f_19853_.f_46443_) {
               this.m_38946_();
            }

            return ItemStack.f_41583_;
         }

         if (index > 0 && index < 10) {
            if (this.te == null) {
               return ItemStack.f_41583_;
            }

            ItemStack stack = this.te.pushStack(itemstack, this.selectedTab);
            slot.m_5852_(stack);
            if (!playerIn.f_19853_.f_46443_) {
               this.m_38946_();
            }
         }

         slot.m_142406_(playerIn, itemstack1);
      }

      return ItemStack.f_41583_;
   }

   public void onCraftMatrixChanged() {
      for (int i = 0; i < this.f_38839_.size(); i++) {
         Slot slot = (Slot)this.f_38839_.get(i);
         if (slot instanceof CraftingTerminalMenu.SlotCrafting || slot == this.craftingResultSlot) {
            for (ContainerListener listener : this.listeners) {
               if (listener instanceof ServerPlayer) {
                  ((ServerPlayer)listener).f_8906_.m_9829_(new ClientboundContainerSetSlotPacket(this.f_38840_, this.m_182425_(), i, slot.m_7993_()));
               }
            }
         }
      }
   }

   public boolean m_6366_(Player playerIn, int id) {
      if (this.te != null && id == 0) {
         ((CraftingLecternTile)this.te).clear(this.selectedTab);
      } else {
         super.m_6366_(playerIn, id);
      }

      return false;
   }

   @Override
   public void m_5816_(StackedContents itemHelperIn) {
      this.craftMatrix.m_5809_(itemHelperIn);
   }

   @Override
   public void m_6650_() {
      this.craftMatrix.m_6211_();
      this.craftResult.m_6211_();
   }

   @Override
   public boolean m_6032_(Recipe<? super CraftingContainer> recipeIn) {
      return recipeIn.m_5818_(this.craftMatrix, this.pinv.f_35978_.f_19853_);
   }

   @Override
   public int m_6636_() {
      return 0;
   }

   @Override
   public int m_6635_() {
      return this.craftMatrix.m_39347_();
   }

   @Override
   public int m_6656_() {
      return this.craftMatrix.m_39346_();
   }

   @Override
   public int m_6653_() {
      return 10;
   }

   public List<RecipeBookCategories> getRecipeBookCategories() {
      return Lists.newArrayList(
         new RecipeBookCategories[]{
            RecipeBookCategories.CRAFTING_SEARCH,
            RecipeBookCategories.CRAFTING_EQUIPMENT,
            RecipeBookCategories.CRAFTING_BUILDING_BLOCKS,
            RecipeBookCategories.CRAFTING_MISC,
            RecipeBookCategories.CRAFTING_REDSTONE
         }
      );
   }

   public void m_6951_(boolean p_217056_1_, Recipe<?> p_217056_2_, ServerPlayer p_217056_3_) {
      (new ServerPlaceRecipe(this) {
         {
            this.f_135426_ = CraftingTerminalMenu.this.new TerminalRecipeItemHelper();
         }

         public void m_5817_(Iterator pIngredients, int pSlot, int pMaxAmount, int pY, int pX) {
            Slot slot = this.f_135428_.m_38853_(pSlot);
            ItemStack itemstack = StackedContents.m_36454_((Integer)pIngredients.next());
            if (!itemstack.m_41619_()) {
               for (int i = 0; i < pMaxAmount; i++) {
                  this.m_135438_(slot, itemstack);
               }
            }
         }

         protected void m_135438_(Slot slotToFill, ItemStack ingredientIn) {
            int i = this.f_135427_.m_36043_(ingredientIn);
            if (i != -1) {
               ItemStack itemstack = this.f_135427_.m_8020_(i).m_41777_();
               if (!itemstack.m_41619_()) {
                  if (itemstack.m_41613_() > 1) {
                     this.f_135427_.m_7407_(i, 1);
                  } else {
                     this.f_135427_.m_8016_(i);
                  }

                  itemstack.m_41764_(1);
                  if (slotToFill.m_7993_().m_41619_()) {
                     slotToFill.m_5852_(itemstack);
                  } else {
                     slotToFill.m_7993_().m_41769_(1);
                  }
               }
            } else if (CraftingTerminalMenu.this.te != null) {
               StoredItemStack st = CraftingTerminalMenu.this.te.pullStack(new StoredItemStack(ingredientIn), 1, CraftingTerminalMenu.this.selectedTab);
               if (st != null) {
                  if (slotToFill.m_7993_().m_41619_()) {
                     slotToFill.m_5852_(st.getActualStack());
                  } else {
                     slotToFill.m_7993_().m_41769_(1);
                  }
               }
            }
         }

         protected void m_179844_(boolean bool) {
            ((CraftingLecternTile)CraftingTerminalMenu.this.te).clear(CraftingTerminalMenu.this.selectedTab);
            this.f_135428_.m_6650_();
         }
      }).m_135434_(p_217056_3_, p_217056_2_, p_217056_1_);
   }

   @Override
   public void receive(CompoundTag message) {
      super.receive(message);
      if (message.m_128441_("i")) {
         ItemStack[][] stacks = new ItemStack[9][];
         ListTag list = message.m_128437_("i", 10);

         for (int i = 0; i < list.size(); i++) {
            CompoundTag nbttagcompound = list.m_128728_(i);
            byte slot = nbttagcompound.m_128445_("s");
            byte l = nbttagcompound.m_128445_("l");
            stacks[slot] = new ItemStack[l];

            for (int j = 0; j < l; j++) {
               CompoundTag tag = nbttagcompound.m_128469_("i" + j);
               stacks[slot][j] = ItemStack.m_41712_(tag);
            }
         }

         ((CraftingLecternTile)this.te).transferToGrid(this.pinv.f_35978_, stacks, this.selectedTab);
      }
   }

   @Override
   public List<StoredItemStack> getStoredItems() {
      return this.itemList;
   }

   public static class ActiveResultSlot extends ResultSlot {
      public boolean active = true;

      public ActiveResultSlot(Player pPlayer, CraftingContainer pCraftSlots, Container pContainer, int pSlot, int pXPosition, int pYPosition) {
         super(pPlayer, pCraftSlots, pContainer, pSlot, pXPosition, pYPosition);
      }

      public boolean m_6659_() {
         return this.active;
      }
   }

   public static class SlotCrafting extends Slot {
      public boolean active = true;

      public SlotCrafting(Container inventoryIn, int index, int xPosition, int yPosition) {
         super(inventoryIn, index, xPosition, yPosition);
      }

      public boolean m_6659_() {
         return super.m_6659_() && this.active;
      }
   }

   public class TerminalRecipeItemHelper extends StackedContents {
      public void m_36453_() {
         super.m_36453_();
         CraftingTerminalMenu.this.itemList.forEach(e -> this.m_36466_(e.getActualStack()));
      }
   }
}

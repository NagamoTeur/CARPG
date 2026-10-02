package com.hollingsworth.arsnouveau.client.container;

import com.hollingsworth.arsnouveau.common.block.tile.StorageLecternTile;
import com.hollingsworth.arsnouveau.common.menu.MenuRegistry;
import com.hollingsworth.arsnouveau.common.network.ClientToServerStoragePacket;
import com.hollingsworth.arsnouveau.common.network.Networking;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

public class StorageTerminalMenu extends RecipeBookMenu<CraftingContainer> {
   protected StorageLecternTile te;
   protected int playerSlotsStart;
   protected List<SlotStorage> storageSlotList = new ArrayList<>();
   public List<StoredItemStack> itemList = new ArrayList<>();
   public List<StoredItemStack> itemListClient = new ArrayList<>();
   public List<StoredItemStack> itemListClientSorted = new ArrayList<>();
   public TerminalSyncManager sync = new TerminalSyncManager();
   private int lines;
   protected Inventory pinv;
   public Runnable onPacket;
   public SortSettings terminalData = null;
   public String search;
   public boolean noSort;
   public List<String> tabNames = new ArrayList<>();
   public String selectedTab = null;

   public StorageTerminalMenu(int id, Inventory inv, StorageLecternTile te) {
      this((MenuType<?>)MenuRegistry.STORAGE.get(), id, inv, te);
      this.addPlayerSlots(inv, 8, 120);
   }

   public StorageTerminalMenu(MenuType<?> type, int id, Inventory inv, StorageLecternTile te) {
      super(type, id);
      this.te = te;
      this.pinv = inv;
      this.addStorageSlots();
   }

   public StorageTerminalMenu(MenuType<?> type, int id, Inventory inv) {
      this(type, id, inv, null);
   }

   protected void addStorageSlots() {
      this.addStorageSlots(8, 18);
   }

   protected void addPlayerSlots(Inventory playerInventory, int x, int y) {
      this.playerSlotsStart = this.f_38839_.size() - 1;

      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 9; j++) {
            this.m_38897_(new Slot(playerInventory, j + i * 9 + 9, x + j * 18, y + i * 18));
         }
      }

      for (int i = 0; i < 9; i++) {
         this.m_38897_(new Slot(playerInventory, i, x + i * 18, y + 58));
      }
   }

   public void addStorageSlots(int x, int y) {
      this.storageSlotList.clear();
      this.lines = this.terminalData != null && this.terminalData.expanded ? 7 : 3;

      for (int i = 0; i < this.lines; i++) {
         for (int j = 0; j < 9; j++) {
            this.addSlotToContainer(new SlotStorage(this.te, i * 9 + j, x + j * 18, y + i * 18));
         }
      }

      this.scrollTo(0.0F);
   }

   protected final void addSlotToContainer(SlotStorage slotStorage) {
      this.storageSlotList.add(slotStorage);
   }

   public boolean m_6875_(Player playerIn) {
      return this.te != null && this.te.canInteractWith(playerIn);
   }

   public final void scrollTo(float p_148329_1_) {
      int i = (this.itemListClientSorted.size() + 9 - 1) / 9 - this.lines;
      int j = (int)((double)(p_148329_1_ * (float)i) + 0.5);
      if (j < 0) {
         j = 0;
      }

      for (int k = 0; k < this.lines; k++) {
         for (int l = 0; l < 9; l++) {
            int i1 = l + (k + j) * 9;
            if (i1 >= 0 && i1 < this.itemListClientSorted.size()) {
               this.setSlotContents(l + k * 9, this.itemListClientSorted.get(i1));
            } else {
               this.setSlotContents(l + k * 9, null);
            }
         }
      }
   }

   public final void setSlotContents(int id, StoredItemStack stack) {
      this.storageSlotList.get(id).stack = stack;
   }

   public final SlotStorage getSlotByID(int id) {
      return this.storageSlotList.get(id);
   }

   public void m_38946_() {
      if (this.te != null) {
         Map<StoredItemStack, Long> itemsCount = this.te.getStacks(this.selectedTab);
         this.sync.update(itemsCount, (ServerPlayer)this.pinv.f_35978_, tag -> {
            if (!this.te.getLastSearch().equals(this.search)) {
               this.search = this.te.getLastSearch();
               tag.m_128359_("search", this.search);
            }

            ListTag tabs = new ListTag();

            for (String s : this.te.getTabNames()) {
               CompoundTag nameTag = new CompoundTag();
               nameTag.m_128359_("name", s);
               tabs.add(nameTag);
            }

            tag.m_128365_("tabs", tabs);
            tag.m_128365_("sortSettings", this.te.sortSettings.toTag());
         });
         super.m_38946_();
      }
   }

   public final ItemStack m_7648_(Player playerIn, int index) {
      if (this.f_38839_.size() <= index) {
         return ItemStack.f_41583_;
      } else if (index > this.playerSlotsStart && this.te != null) {
         if (this.f_38839_.get(index) != null && ((Slot)this.f_38839_.get(index)).m_6657_()) {
            Slot slot = (Slot)this.f_38839_.get(index);
            ItemStack slotStack = slot.m_7993_();
            StoredItemStack c = this.te.pushStack(new StoredItemStack(slotStack, (long)slotStack.m_41613_()), this.selectedTab);
            ItemStack itemstack = c != null ? c.getActualStack() : ItemStack.f_41583_;
            slot.m_5852_(itemstack);
            if (!playerIn.f_19853_.f_46443_) {
               this.m_38946_();
            }
         }

         return ItemStack.f_41583_;
      } else {
         return this.shiftClickItems(playerIn, index);
      }
   }

   protected ItemStack shiftClickItems(Player playerIn, int index) {
      return ItemStack.f_41583_;
   }

   public void m_5816_(StackedContents itemHelperIn) {
   }

   public void m_6650_() {
   }

   public boolean m_6032_(Recipe<? super CraftingContainer> recipeIn) {
      return false;
   }

   public int m_6636_() {
      return 0;
   }

   public int m_6635_() {
      return 0;
   }

   public int m_6656_() {
      return 0;
   }

   public int m_6653_() {
      return 0;
   }

   public void sendMessage(CompoundTag compound) {
      Networking.sendToServer(new ClientToServerStoragePacket(compound));
   }

   public final void receiveClientNBTPacket(CompoundTag message) {
      if (this.sync.receiveUpdate(message)) {
         this.itemList = this.sync.getAsList();
         if (this.noSort) {
            this.itemListClient.forEach(s -> s.setCount(this.sync.getAmount(s)));
         } else {
            this.itemListClient = new ArrayList<>(this.itemList);
         }

         this.pinv.m_6596_();
      }

      if (message.m_128441_("search")) {
         this.search = message.m_128461_("search");
      }

      if (message.m_128441_("sortSettings")) {
         boolean isExpanded = this.terminalData != null && this.terminalData.expanded;
         this.terminalData = SortSettings.fromTag(message.m_128469_("sortSettings"));
         if (isExpanded != this.terminalData.expanded) {
            this.addStorageSlots();
         }
      }

      if (message.m_128441_("tabs")) {
         ListTag tabs = message.m_128437_("tabs", 10);
         this.tabNames = new ArrayList<>();

         for (int i = 0; i < tabs.size(); i++) {
            this.tabNames.add(tabs.m_128728_(i).m_128461_("name"));
         }

         Collections.sort(this.tabNames);
      }

      if (this.onPacket != null) {
         this.onPacket.run();
      }
   }

   public void receive(CompoundTag message) {
      if (!this.pinv.f_35978_.m_5833_()) {
         if (message.m_128441_("search")) {
            this.te.setLastSearch(message.m_128461_("search"));
         }

         this.sync.receiveInteract(message, this);
         if (message.m_128441_("termData")) {
            CompoundTag d = message.m_128469_("termData");
            this.te.setSorting(SortSettings.fromTag(d.m_128469_("sortSettings")));
            this.selectedTab = null;
            if (d.m_128441_("selectedTab")) {
               this.selectedTab = d.m_128461_("selectedTab");
            }
         }
      }
   }

   public RecipeBookType m_5867_() {
      return RecipeBookType.CRAFTING;
   }

   public boolean m_142157_(int p_150635_) {
      return false;
   }

   public void onInteract(StoredItemStack clicked, StorageTerminalMenu.SlotAction act, boolean pullOne) {
      ServerPlayer player = (ServerPlayer)this.pinv.f_35978_;
      player.m_9243_();
      if (act == StorageTerminalMenu.SlotAction.SPACE_CLICK) {
         for (int i = this.playerSlotsStart + 1; i < this.playerSlotsStart + 28; i++) {
            this.m_7648_(player, i);
         }
      } else if (act == StorageTerminalMenu.SlotAction.PULL_OR_PUSH_STACK) {
         ItemStack stack = this.m_142621_();
         if (!stack.m_41619_()) {
            StoredItemStack rem = this.te.pushStack(new StoredItemStack(stack), this.selectedTab);
            ItemStack itemstack = rem == null ? ItemStack.f_41583_ : rem.getActualStack();
            this.m_142503_(itemstack);
         } else {
            if (clicked == null) {
               return;
            }

            StoredItemStack pulled = this.te.pullStack(clicked, clicked.getMaxStackSize(), this.selectedTab);
            if (pulled != null) {
               this.m_142503_(pulled.getActualStack());
            }
         }
      } else if (act == StorageTerminalMenu.SlotAction.PULL_ONE) {
         ItemStack stack = this.m_142621_();
         if (clicked == null) {
            return;
         }

         if (pullOne) {
            StoredItemStack pulled = this.te.pullStack(clicked, 1, this.selectedTab);
            if (pulled != null) {
               ItemStack itemstack = pulled.getActualStack();
               this.m_38903_(itemstack, this.playerSlotsStart + 1, this.f_38839_.size(), true);
               if (itemstack.m_41613_() > 0) {
                  this.te.pushOrDrop(itemstack, this.selectedTab);
               }

               player.m_150109_().m_6596_();
            }
         } else if (!stack.m_41619_()) {
            if (ItemStack.m_150942_(stack, clicked.getStack()) && stack.m_41613_() + 1 <= stack.m_41741_()) {
               StoredItemStack pulled = this.te.pullStack(clicked, 1, this.selectedTab);
               if (pulled != null) {
                  stack.m_41769_(1);
               }
            }
         } else {
            StoredItemStack pulled = this.te.pullStack(clicked, 1, this.selectedTab);
            if (pulled != null) {
               this.m_142503_(pulled.getActualStack());
            }
         }
      } else if (act == StorageTerminalMenu.SlotAction.GET_HALF) {
         ItemStack stackx = this.m_142621_();
         if (!stackx.m_41619_()) {
            ItemStack stack1 = stackx.m_41620_(Math.max(Math.min(stackx.m_41613_(), stackx.m_41741_()) / 2, 1));
            ItemStack itemstack = this.te.pushStack(stack1, this.selectedTab);
            stackx.m_41769_(!itemstack.m_41619_() ? itemstack.m_41613_() : 0);
            this.m_142503_(stackx);
         } else {
            if (clicked == null) {
               return;
            }

            StoredItemStack pulled = this.te
               .pullStack(clicked, (int)Math.max(Math.min(clicked.getQuantity() / 2L, (long)(clicked.getMaxStackSize() / 2)), 1L), this.selectedTab);
            if (pulled != null) {
               this.m_142503_(pulled.getActualStack());
            }
         }
      } else if (act == StorageTerminalMenu.SlotAction.GET_QUARTER) {
         ItemStack stackx = this.m_142621_();
         if (!stackx.m_41619_()) {
            ItemStack stack1 = stackx.m_41620_(Math.max(Math.min(stackx.m_41613_(), stackx.m_41741_()) / 4, 1));
            ItemStack itemstack = this.te.pushStack(stack1, this.selectedTab);
            stackx.m_41769_(!itemstack.m_41619_() ? itemstack.m_41613_() : 0);
            this.m_142503_(stackx);
         } else {
            if (clicked == null) {
               return;
            }

            long maxCount = 64L;

            for (StoredItemStack e : this.itemList) {
               if (e.equals(clicked)) {
                  maxCount = e.getQuantity();
               }
            }

            StoredItemStack pulled = this.te.pullStack(clicked, (int)Math.max(Math.min(maxCount, (long)clicked.getMaxStackSize()) / 4L, 1L), this.selectedTab);
            if (pulled != null) {
               this.m_142503_(pulled.getActualStack());
            }
         }
      } else {
         if (clicked == null) {
            return;
         }

         StoredItemStack pulled = this.te.pullStack(clicked, clicked.getMaxStackSize(), this.selectedTab);
         if (pulled != null) {
            ItemStack itemstack = pulled.getActualStack();
            this.m_38903_(itemstack, this.playerSlotsStart + 1, this.f_38839_.size(), true);
            if (itemstack.m_41613_() > 0) {
               this.te.pushOrDrop(itemstack, this.selectedTab);
            }

            player.m_150109_().m_6596_();
         }
      }
   }

   public static enum SlotAction {
      PULL_OR_PUSH_STACK,
      PULL_ONE,
      SPACE_CLICK,
      SHIFT_PULL,
      GET_HALF,
      GET_QUARTER;
   }
}

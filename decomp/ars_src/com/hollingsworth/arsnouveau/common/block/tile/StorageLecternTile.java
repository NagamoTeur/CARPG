package com.hollingsworth.arsnouveau.common.block.tile;

import com.google.common.collect.EvictingQueue;
import com.hollingsworth.arsnouveau.api.client.ITooltipProvider;
import com.hollingsworth.arsnouveau.api.item.IWandable;
import com.hollingsworth.arsnouveau.api.item.inv.ExtractedStack;
import com.hollingsworth.arsnouveau.api.item.inv.FilterableItemHandler;
import com.hollingsworth.arsnouveau.api.item.inv.InventoryManager;
import com.hollingsworth.arsnouveau.api.item.inv.MultiExtractedReference;
import com.hollingsworth.arsnouveau.api.item.inv.MultiInsertReference;
import com.hollingsworth.arsnouveau.api.item.inv.SlotReference;
import com.hollingsworth.arsnouveau.api.item.inv.StorageItemHandler;
import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.api.util.InvUtil;
import com.hollingsworth.arsnouveau.client.container.SortSettings;
import com.hollingsworth.arsnouveau.client.container.StorageTerminalMenu;
import com.hollingsworth.arsnouveau.client.container.StoredItemStack;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.util.ColorPos;
import com.hollingsworth.arsnouveau.common.block.ITickable;
import com.hollingsworth.arsnouveau.common.datagen.BlockTagProvider;
import com.hollingsworth.arsnouveau.common.entity.EntityBookwyrm;
import com.hollingsworth.arsnouveau.common.entity.goal.bookwyrm.TransferTask;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.hollingsworth.arsnouveau.setup.Config;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

public class StorageLecternTile extends ModdedTile implements MenuProvider, ITickable, IWandable, ITooltipProvider {
   public Map<String, InventoryManager> tabManagerMap = new HashMap<>();
   public Map<String, Map<StoredItemStack, Long>> itemsByTab = new HashMap<>();
   public Map<Item, Long> itemCounts = new HashMap<>();
   public String lastSearch = "";
   public boolean updateItems;
   public List<BlockPos> connectedInventories = new ArrayList<>();
   public List<String> tabNames = new ArrayList<>();
   public List<StorageLecternTile.HandlerPos> handlerPosList = new ArrayList<>();
   public SortSettings sortSettings = new SortSettings();
   public BlockPos mainLecternPos;
   public List<UUID> bookwyrmUUIDs = new ArrayList<>();
   public int backoffTicks;
   public int checkPlayerRangeTicks;
   public boolean canCreateTasks = false;
   public static final String TAB_ALL = "8f6fe318-4ca6-4b29-ab63-15ec5289f5c9";
   public Queue<TransferTask> transferTasks = EvictingQueue.create(10);
   LazyOptional<IItemHandler> lecternInvWrapper;

   public StorageLecternTile(BlockPos pos, BlockState state) {
      super((BlockEntityType<?>)BlockRegistry.CRAFTING_LECTERN_TILE.get(), pos, state);
   }

   public StorageLecternTile(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
      super(tileEntityTypeIn, pos, state);
   }

   public InventoryManager getInvManager(@Nullable String tab) {
      return tab != null && !tab.isEmpty()
         ? this.tabManagerMap.getOrDefault(tab, this.tabManagerMap.getOrDefault("8f6fe318-4ca6-4b29-ab63-15ec5289f5c9", new InventoryManager()))
         : this.tabManagerMap.getOrDefault("8f6fe318-4ca6-4b29-ab63-15ec5289f5c9", new InventoryManager());
   }

   public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
      if (!this.f_58859_ && cap == ForgeCapabilities.ITEM_HANDLER) {
         StorageLecternTile lecternTile = this.getMainLectern();
         if (lecternTile == null) {
            this.lecternInvWrapper = LazyOptional.of(() -> new LecternInvWrapper(this));
            return this.lecternInvWrapper.cast();
         } else {
            List<IItemHandler> modifiables = new ArrayList<>();

            for (BlockPos pos : lecternTile.connectedInventories) {
               BlockEntity invTile = lecternTile.f_58857_.m_7702_(pos);
               if (invTile != null) {
                  IItemHandler lih = (IItemHandler)invTile.getCapability(ForgeCapabilities.ITEM_HANDLER, null).orElse(null);
                  if (lih != null) {
                     modifiables.add(lih);
                  }
               }
            }

            lecternTile.lecternInvWrapper = LazyOptional.of(() -> new LecternInvWrapper(this, modifiables.toArray(new IItemHandler[0])));
            return lecternTile.lecternInvWrapper.cast();
         }
      } else {
         return super.getCapability(cap, side);
      }
   }

   public void m_7651_() {
      super.m_7651_();
      if (this.lecternInvWrapper != null) {
         this.lecternInvWrapper.invalidate();
      }
   }

   public AbstractContainerMenu m_7208_(int id, Inventory plInv, Player arg2) {
      return new StorageTerminalMenu(id, plInv, this);
   }

   public Component m_5446_() {
      return Component.m_237115_("ars_nouveau.storage_lectern");
   }

   public Map<StoredItemStack, Long> getStacks(@Nullable String tabName) {
      this.updateItems = true;
      return tabName != null && !tabName.isEmpty()
         ? this.itemsByTab.getOrDefault(tabName, this.itemsByTab.getOrDefault("8f6fe318-4ca6-4b29-ab63-15ec5289f5c9", new HashMap<>()))
         : this.itemsByTab.getOrDefault("8f6fe318-4ca6-4b29-ab63-15ec5289f5c9", new HashMap<>());
   }

   public List<String> getTabNames() {
      this.tabNames = new ArrayList<>();

      for (BlockPos pos : this.connectedInventories) {
         BlockEntity tile = this.f_58857_.m_7702_(pos);
         if (tile instanceof Nameable) {
            Nameable provider = (Nameable)tile;
            if (provider.m_8077_()) {
               String tabName = provider.m_7770_().getString().trim();
               if (!tabName.isEmpty()) {
                  this.tabNames.add(provider.m_5446_().getString());
               }
            }
         }
      }

      return this.tabNames;
   }

   public StoredItemStack pullStack(StoredItemStack stack, int max, @Nullable String tabName) {
      if (stack != null && max > 0) {
         ItemStack st = stack.getStack();
         MultiExtractedReference pulled = this.getInvManager(tabName).extractItemFromAll(st, max, true);
         if (pulled.getExtracted().m_41619_()) {
            return null;
         } else {
            this.addExtractTasks(pulled);
            return new StoredItemStack(pulled.getExtracted());
         }
      } else {
         return null;
      }
   }

   private void addExtractTasks(MultiExtractedReference multiSlotReference) {
      if (!multiSlotReference.getExtracted().m_41619_()) {
         for (ExtractedStack extractedStack : multiSlotReference.getSlots()) {
            BlockPos pos = this.handlerPosList
               .stream()
               .filter(handlerPos -> handlerPos.handler().equals(extractedStack.getHandler()))
               .findFirst()
               .map(StorageLecternTile.HandlerPos::pos)
               .orElse(null);
            if (pos != null) {
               this.addTransferTask(new TransferTask(pos.m_7494_(), this.m_58899_().m_7494_(), extractedStack.stack, this.f_58857_.m_46467_()));
            }
         }
      }
   }

   private void addInsertTasks(ItemStack stack, MultiInsertReference reference) {
      if (!reference.isEmpty() && !stack.m_41619_()) {
         for (SlotReference extractedStack : reference.getSlots()) {
            BlockPos pos = this.handlerPosList
               .stream()
               .filter(handlerPos -> handlerPos.handler().equals(extractedStack.getHandler()))
               .findFirst()
               .map(StorageLecternTile.HandlerPos::pos)
               .orElse(null);
            if (pos != null) {
               this.addTransferTask(new TransferTask(this.m_58899_().m_7494_(), pos.m_7494_(), stack, this.f_58857_.m_46467_()));
            }
         }
      }
   }

   public void addTransferTask(TransferTask task) {
      if (this.canCreateTasks) {
         this.transferTasks.add(task);
      }
   }

   @Nullable
   public TransferTask getTransferTask() {
      List<TransferTask> staleTasks = new ArrayList<>();
      TransferTask task = null;
      Iterator var3 = this.transferTasks.iterator();
      if (var3.hasNext()) {
         TransferTask transferTask = (TransferTask)var3.next();
         if (this.f_58857_.m_46467_() - transferTask.gameTime > 200L) {
            staleTasks.add(transferTask);
         }

         task = transferTask;
         staleTasks.add(transferTask);
      }

      this.transferTasks.removeAll(staleTasks);
      return task;
   }

   public StoredItemStack pushStack(StoredItemStack stack, @Nullable String tab) {
      if (stack == null) {
         return null;
      } else {
         ItemStack copyStack = stack.getActualStack().m_41777_();
         MultiInsertReference reference = this.getInvManager(tab).insertStackWithReference(stack.getActualStack());
         ItemStack remaining = reference.getRemainder();
         if (!reference.isEmpty()) {
            this.addInsertTasks(copyStack, reference);
         }

         return remaining.m_41619_() ? null : new StoredItemStack(remaining);
      }
   }

   public ItemStack pushStack(ItemStack itemstack, @Nullable String tab) {
      StorageLecternTile mainLectern = this.getMainLectern();
      if (mainLectern == null) {
         return itemstack;
      } else {
         StoredItemStack is = mainLectern.pushStack(new StoredItemStack(itemstack), tab);
         return is == null ? ItemStack.f_41583_ : is.getActualStack();
      }
   }

   public void pushOrDrop(ItemStack st, @Nullable String tabName) {
      if (!st.m_41619_()) {
         StoredItemStack st0 = this.pushStack(new StoredItemStack(st), tabName);
         if (st0 != null) {
            Containers.m_18992_(
               this.f_58857_,
               (double)((float)this.f_58858_.m_123341_() + 0.5F),
               (double)((float)this.f_58858_.m_123342_() + 0.5F),
               (double)((float)this.f_58858_.m_123343_() + 0.5F),
               st0.getActualStack()
            );
         }
      }
   }

   @Override
   public void onWanded(Player playerEntity) {
      this.mainLecternPos = null;
      this.updateBlock();
   }

   @Override
   public void onFinishedConnectionLast(@Nullable BlockPos storedPos, @Nullable LivingEntity storedEntity, Player playerEntity) {
      if (storedPos != null) {
         BlockEntity tile = this.f_58857_.m_7702_(storedPos);
         if (!(tile instanceof StorageLecternTile)) {
            if (tile == null) {
               PortUtil.sendMessage(playerEntity, Component.m_237115_("ars_nouveau.storage.no_tile"));
            } else {
               IItemHandler handler = (IItemHandler)tile.getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(null);
               if (handler == null) {
                  PortUtil.sendMessage(playerEntity, Component.m_237115_("ars_nouveau.storage.no_tile"));
               } else if (BlockUtil.distanceFrom(storedPos, this.f_58858_) > 30.0) {
                  PortUtil.sendMessage(playerEntity, Component.m_237115_("ars_nouveau.storage.inv_too_far"));
               } else {
                  if (this.connectedInventories.contains(storedPos)) {
                     PortUtil.sendMessage(playerEntity, Component.m_237115_("ars_nouveau.storage.removed"));
                     this.connectedInventories.remove(storedPos);
                  } else {
                     if (this.connectedInventories.size() >= this.getMaxConnectedInventories()) {
                        PortUtil.sendMessage(playerEntity, Component.m_237115_("ars_nouveau.storage.too_many"));
                        return;
                     }

                     this.connectedInventories.add(storedPos.m_7949_());
                     PortUtil.sendMessage(playerEntity, Component.m_237115_("ars_nouveau.storage.from_set"));
                  }

                  this.mainLecternPos = null;
                  this.updateBlock();
                  this.updateItems = true;
               }
            }
         }
      }
   }

   @Override
   public void onFinishedConnectionFirst(@Nullable BlockPos storedPos, @Nullable LivingEntity storedEntity, Player playerEntity) {
      if (storedPos != null && !storedPos.equals(this.f_58858_)) {
         BlockEntity tile = this.f_58857_.m_7702_(storedPos);
         if (tile instanceof StorageLecternTile) {
            if (BlockUtil.distanceFrom(storedPos, this.f_58858_) > 30.0) {
               PortUtil.sendMessage(playerEntity, Component.m_237115_("ars_nouveau.storage.lectern_too_far"));
            } else {
               this.mainLecternPos = storedPos.m_7949_();
               this.connectedInventories = new ArrayList<>();
               PortUtil.sendMessage(
                  playerEntity,
                  Component.m_237110_("ars_nouveau.storage.lectern_chained", new Object[]{storedPos.m_123341_(), storedPos.m_123342_(), storedPos.m_123343_()})
               );
               this.updateBlock();
            }
         }
      }
   }

   @Override
   public List<ColorPos> getWandHighlight(List<ColorPos> list) {
      if (this.mainLecternPos != null) {
         list.add(ColorPos.centered(this.mainLecternPos, ParticleColor.TO_HIGHLIGHT));
         return list;
      } else {
         for (BlockPos pos : this.connectedInventories) {
            list.add(ColorPos.centered(pos, ParticleColor.FROM_HIGHLIGHT));
         }

         for (EntityBookwyrm bookwyrm : this.getBookwyrmEntities()) {
            list.add(ColorPos.centered(bookwyrm.m_20183_(), ParticleColor.GREEN));
         }

         return list;
      }
   }

   @Override
   public void tick() {
      if (!this.f_58857_.f_46443_) {
         if (this.backoffTicks > 0) {
            this.backoffTicks--;
         }

         if (this.backoffTicks <= 0 && this.f_58857_.m_46467_() % 20L == 0L) {
            this.insertNearbyItems();
         }

         if (this.checkPlayerRangeTicks > 0) {
            this.checkPlayerRangeTicks--;
         }

         if (this.checkPlayerRangeTicks <= 0) {
            this.checkPlayerRangeTicks = 60 + this.f_58857_.f_46441_.m_188503_(5);
            this.canCreateTasks = false;
            ServerLevel serverLevel = (ServerLevel)this.f_58857_;

            for (ServerPlayer serverPlayer : serverLevel.m_6907_()) {
               if (BlockUtil.distanceFrom(serverPlayer.m_20182_(), this.m_58899_()) < 40.0) {
                  this.canCreateTasks = true;
                  break;
               }
            }
         }

         if (this.updateItems) {
            this.updateItems();
            this.updateItems = false;
         }
      }
   }

   public void updateItems() {
      this.itemsByTab.clear();
      this.tabManagerMap.clear();
      this.handlerPosList = new ArrayList<>();
      Map<String, List<FilterableItemHandler>> mappedFilterables = new HashMap<>();
      this.itemsByTab.put("8f6fe318-4ca6-4b29-ab63-15ec5289f5c9", new HashMap<>());

      for (BlockPos pos : this.connectedInventories) {
         BlockEntity invTile = this.f_58857_.m_7702_(pos);
         if (invTile != null) {
            IItemHandler handler = (IItemHandler)invTile.getCapability(ForgeCapabilities.ITEM_HANDLER, null).orElse(null);
            if (handler != null) {
               StorageItemHandler storageItemHandler = new StorageItemHandler(handler, InvUtil.filtersOnTile(invTile));
               mappedFilterables.computeIfAbsent("8f6fe318-4ca6-4b29-ab63-15ec5289f5c9", s -> new ArrayList<>()).add(storageItemHandler);
               this.handlerPosList.add(new StorageLecternTile.HandlerPos(pos, handler));
               if (invTile instanceof Nameable) {
                  Nameable nameable = (Nameable)invTile;
                  if (nameable.m_8077_()) {
                     String tabName = nameable.m_7770_().getString();
                     mappedFilterables.computeIfAbsent(tabName, s -> new ArrayList<>()).add(storageItemHandler);
                  }
               }
            }
         }
      }

      for (String tabName : mappedFilterables.keySet()) {
         this.itemsByTab.put(tabName, new HashMap<>());

         for (FilterableItemHandler filterableItemHandler : mappedFilterables.get(tabName)) {
            IItemHandler handler = filterableItemHandler.getHandler();

            for (int i = 0; i < handler.getSlots(); i++) {
               ItemStack stack = handler.getStackInSlot(i);
               if (!stack.m_41619_()) {
                  StoredItemStack storedItemStack = new StoredItemStack(stack);
                  this.itemsByTab.get(tabName).merge(storedItemStack, storedItemStack.getQuantity(), Long::sum);
               }
            }
         }

         this.tabManagerMap.put(tabName, new InventoryManager(mappedFilterables.get(tabName)));
      }

      this.itemCounts = new HashMap<>();
      Map<StoredItemStack, Long> allItems = this.itemsByTab.get("8f6fe318-4ca6-4b29-ab63-15ec5289f5c9");

      for (StoredItemStack stack : allItems.keySet()) {
         this.itemCounts.put(stack.getStack().m_41720_(), allItems.get(stack));
      }
   }

   public List<EntityBookwyrm> getBookwyrmEntities() {
      List<EntityBookwyrm> bookwyrmEntities = new ArrayList<>();
      List<UUID> staleUUIDs = new ArrayList<>();

      for (UUID uuid : this.bookwyrmUUIDs) {
         Level entity = this.f_58857_;
         if (entity instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)entity;
            if (serverLevel.m_8791_(uuid) instanceof EntityBookwyrm bookwyrm) {
               bookwyrmEntities.add(bookwyrm);
            } else {
               staleUUIDs.add(uuid);
            }
         }
      }

      this.bookwyrmUUIDs.removeAll(staleUUIDs);
      return bookwyrmEntities;
   }

   public void insertNearbyItems() {
      StorageLecternTile mainLectern = this.getMainLectern();
      if (mainLectern != null) {
         for (Direction dir : Direction.values()) {
            BlockPos pos = this.f_58858_.m_121945_(dir);
            if (!this.f_58857_.m_8055_(pos).m_204336_(BlockTagProvider.AUTOPULL_DISABLED)) {
               BlockEntity tile = this.f_58857_.m_7702_(pos);
               if (tile != null && !mainLectern.connectedInventories.contains(pos)) {
                  IItemHandler handler = (IItemHandler)tile.getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(null);
                  if (handler != null) {
                     for (int i = 0; i < handler.getSlots(); i++) {
                        ItemStack stack = handler.getStackInSlot(i);
                        if (!stack.m_41619_()) {
                           ItemStack extractedStack = handler.extractItem(i, stack.m_41741_(), false);
                           ItemStack remaining = mainLectern.pushStack(extractedStack, null);
                           if (!remaining.m_41619_()) {
                              ItemStack remainder = handler.insertItem(i, remaining, false);
                              if (!remainder.m_41619_()) {
                                 Containers.m_18992_(
                                    this.f_58857_,
                                    (double)((float)this.f_58858_.m_123341_() + 0.5F),
                                    (double)((float)this.f_58858_.m_123342_() + 0.5F),
                                    (double)((float)this.f_58858_.m_123343_() + 0.5F),
                                    remainder
                                 );
                              }
                           }

                           return;
                        }
                     }
                  }
               }
            }
         }

         this.backoffTicks = 100 + this.f_58857_.f_46441_.m_188503_(20);
      }
   }

   public void removeBookwyrm(EntityBookwyrm bookwyrm) {
      this.bookwyrmUUIDs.remove(bookwyrm.m_20148_());
      this.updateBlock();
   }

   public boolean canInteractWith(Player player) {
      return !this.m_58901_();
   }

   public boolean openMenu(Player player) {
      StorageLecternTile mainLectern = this.getMainLectern();
      if (mainLectern == null) {
         return false;
      } else {
         player.m_5893_(mainLectern);
         return true;
      }
   }

   @Nullable
   public StorageLecternTile getMainLectern() {
      return this.getMainLectern(new ArrayList<>());
   }

   @Nullable
   public StorageLecternTile getMainLectern(List<BlockPos> visitedPos) {
      if (this.mainLecternPos == null) {
         return this;
      } else if (visitedPos.contains(this.mainLecternPos)) {
         return null;
      } else {
         visitedPos.add(this.mainLecternPos);
         return this.f_58857_.m_46749_(this.mainLecternPos)
               && this.f_58857_.m_7702_(this.mainLecternPos) instanceof StorageLecternTile storageTerminalBlockEntity
            ? storageTerminalBlockEntity.getMainLectern(visitedPos)
            : null;
      }
   }

   public void setSorting(SortSettings sortSettings) {
      this.sortSettings = sortSettings;
      this.updateBlock();
   }

   public int getMaxConnectedInventories() {
      return this.getBookwyrmEntities().size() * (Integer)Config.BOOKWYRM_LIMIT.get();
   }

   @Nullable
   public EntityBookwyrm addBookwyrm() {
      if (this.f_58857_.f_46443_) {
         return null;
      } else {
         EntityBookwyrm bookwyrm = new EntityBookwyrm(this.f_58857_, this.m_58899_());
         bookwyrm.m_6034_((double)this.m_58899_().m_123341_() + 0.5, (double)(this.m_58899_().m_123342_() + 1), (double)this.m_58899_().m_123343_() + 0.5);
         this.f_58857_.m_7967_(bookwyrm);
         this.bookwyrmUUIDs.add(bookwyrm.m_20148_());
         this.updateBlock();
         return bookwyrm;
      }
   }

   @Override
   public void m_183515_(CompoundTag compound) {
      compound.m_128365_("sortSettings", this.sortSettings.toTag());
      ListTag list = new ListTag();

      for (BlockPos pos : this.connectedInventories) {
         CompoundTag c = new CompoundTag();
         c.m_128405_("x", pos.m_123341_());
         c.m_128405_("y", pos.m_123342_());
         c.m_128405_("z", pos.m_123343_());
         list.add(c);
      }

      compound.m_128365_("invs", list);
      if (this.mainLecternPos != null) {
         compound.m_128356_("mainLecternPos", this.mainLecternPos.m_121878_());
      }

      ListTag bookwyrmList = new ListTag();

      for (UUID uuid : this.bookwyrmUUIDs) {
         bookwyrmList.add(NbtUtils.m_129226_(uuid));
      }

      compound.m_128365_("bookwyrmUUIDs", bookwyrmList);
   }

   public void m_142466_(CompoundTag compound) {
      if (compound.m_128441_("sortSettings")) {
         this.sortSettings = SortSettings.fromTag(compound.m_128469_("sortSettings"));
      }

      ListTag list = compound.m_128437_("invs", 10);
      this.connectedInventories.clear();

      for (int i = 0; i < list.size(); i++) {
         CompoundTag c = list.m_128728_(i);
         this.connectedInventories.add(new BlockPos(c.m_128451_("x"), c.m_128451_("y"), c.m_128451_("z")));
      }

      if (compound.m_128441_("mainLecternPos")) {
         this.mainLecternPos = BlockPos.m_122022_(compound.m_128454_("mainLecternPos"));
      }

      if (compound.m_128441_("bookwyrmUUIDs")) {
         this.bookwyrmUUIDs.clear();

         for (Tag tag : compound.m_128437_("bookwyrmUUIDs", 11)) {
            this.bookwyrmUUIDs.add(NbtUtils.m_129233_(tag));
         }
      }

      this.updateItems = true;
      super.m_142466_(compound);
   }

   public String getLastSearch() {
      return this.lastSearch;
   }

   public void setLastSearch(String string) {
      this.lastSearch = string;
   }

   @Override
   public void getTooltip(List<Component> tooltip) {
      if (this.mainLecternPos != null) {
         tooltip.add(
            Component.m_237110_(
               "ars_nouveau.storage.lectern_chained",
               new Object[]{this.mainLecternPos.m_123341_(), this.mainLecternPos.m_123342_(), this.mainLecternPos.m_123343_()}
            )
         );
      } else {
         tooltip.add(Component.m_237110_("ars_nouveau.storage.num_connected", new Object[]{this.connectedInventories.size()}));
         tooltip.add(Component.m_237110_("ars_nouveau.storage.num_bookwyrms", new Object[]{this.bookwyrmUUIDs.size()}));
      }
   }

   public static record HandlerPos(BlockPos pos, IItemHandler handler) {
      @Nullable
      public static StorageLecternTile.HandlerPos fromLevel(Level level, BlockPos pos) {
         BlockEntity tile = level.m_7702_(pos);
         if (tile == null) {
            return null;
         } else {
            IItemHandler handler = (IItemHandler)tile.getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(null);
            return handler == null ? null : new StorageLecternTile.HandlerPos(pos, handler);
         }
      }
   }
}

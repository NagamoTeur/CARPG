package com.hollingsworth.arsnouveau.api.item.inv;

import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.IWrappedCaster;
import com.hollingsworth.arsnouveau.api.util.InvUtil;
import com.hollingsworth.arsnouveau.common.items.ItemScroll;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class InventoryManager {
   private static final Random random = new Random();
   private List<FilterableItemHandler> filterables;
   private int extractSlotMax = -1;
   private int insertSlotMax = -1;

   public InventoryManager() {
      this(new ArrayList<>());
   }

   public InventoryManager(List<FilterableItemHandler> filterables) {
      this.filterables = filterables;
   }

   public InventoryManager(IWrappedCaster wrappedCaster) {
      this(wrappedCaster.getInventory());
   }

   public static InventoryManager fromTile(BlockEntity blockEntity) {
      return new InventoryManager(InvUtil.adjacentInventories(blockEntity.m_58904_(), blockEntity.m_58899_()));
   }

   public InventoryManager extractSlotMax(int slotMax) {
      this.extractSlotMax = slotMax;
      return this;
   }

   public InventoryManager insertSlotMax(int slotMax) {
      this.insertSlotMax = slotMax;
      return this;
   }

   public boolean addFilterable(FilterableItemHandler filterable) {
      return this.filterables.add(filterable);
   }

   public List<FilterableItemHandler> getInventory() {
      return this.filterables;
   }

   public void insertOrDrop(ItemStack stack, Level level, BlockPos pos) {
      ItemStack remainder = this.insertStack(stack);
      if (!remainder.m_41619_()) {
         level.m_7967_(new ItemEntity(level, (double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_(), remainder.m_41777_()));
         remainder.m_41764_(0);
      }
   }

   public ItemStack insertStack(ItemStack stack) {
      return this.insertStackWithReference(stack).getRemainder();
   }

   public MultiInsertReference insertStackWithReference(ItemStack stack) {
      List<SlotReference> references = new ArrayList<>();

      for (FilterableItemHandler filterable : this.preferredForStack(stack, false)) {
         int count = stack.m_41613_();
         stack = ItemHandlerHelper.insertItemStacked(filterable.getHandler(), stack, false);
         if (count != stack.m_41613_()) {
            references.add(new SlotReference(filterable.getHandler(), filterable.getHandler().getSlots()));
         }

         if (stack.m_41619_()) {
            break;
         }
      }

      return new MultiInsertReference(stack, references);
   }

   public ExtractedStack extractByAmount(ToIntFunction<ItemStack> getExtractAmount) {
      ItemScroll.SortPref highestPref = ItemScroll.SortPref.INVALID;
      FilterableItemHandler highestHandler = null;
      int toExtract = 0;
      int slot = -1;

      for (FilterableItemHandler wrapper : this.getInventory()) {
         ItemScroll.SortPref pref = ItemScroll.SortPref.INVALID;
         int forAmount = 0;
         int forSlot = 0;

         for (int i = 0; i < this.getExtractSlotMax(wrapper); i++) {
            ItemStack stack = wrapper.getHandler().getStackInSlot(i);
            if (!stack.m_41619_()) {
               int amount = getExtractAmount.applyAsInt(stack);
               if (amount > 0) {
                  ItemScroll.SortPref foundPref = wrapper.getHighestPreference(stack);
                  if (pref == ItemScroll.SortPref.HIGHEST) {
                     return this.extractItem(wrapper, stack1 -> true, amount);
                  }

                  if (foundPref != ItemScroll.SortPref.INVALID && foundPref.ordinal() > pref.ordinal()) {
                     pref = foundPref;
                     forAmount = amount;
                     forSlot = i;
                  }
               }
            }
         }

         if (pref.ordinal() > highestPref.ordinal()) {
            highestHandler = wrapper;
            highestPref = pref;
            toExtract = forAmount;
            slot = forSlot;
         }
      }

      return highestHandler == null ? ExtractedStack.empty() : ExtractedStack.from(highestHandler.getHandler(), slot, toExtract);
   }

   public ExtractedStack extractItem(Predicate<ItemStack> predicate, int count) {
      FilterableItemHandler highestHandler = this.highestPrefInventory(this.getInventory(), predicate, InteractType.EXTRACT);
      return highestHandler == null ? ExtractedStack.empty() : this.extractItem(highestHandler, predicate, count);
   }

   public ExtractedStack extractItem(FilterableItemHandler filteredHandler, Predicate<ItemStack> stackPredicate, int count) {
      SlotReference slotRef = this.findItem(filteredHandler, stackPredicate, InteractType.EXTRACT);
      return slotRef.isEmpty() ? ExtractedStack.empty() : ExtractedStack.from(slotRef, count);
   }

   public ExtractedStack extractRandomItem(Predicate<ItemStack> predicate, int count) {
      FilterableItemHandler highestHandler = this.highestPrefInventory(this.getInventory(), predicate, InteractType.EXTRACT);
      return highestHandler == null ? ExtractedStack.empty() : this.extractRandomItem(highestHandler, predicate, count);
   }

   public ExtractedStack extractRandomItem(FilterableItemHandler filteredHandler, Predicate<ItemStack> stackPredicate, int count) {
      SlotReference slotRef = this.findItemR(filteredHandler, stackPredicate, InteractType.EXTRACT);
      return slotRef.isEmpty() ? ExtractedStack.empty() : ExtractedStack.from(slotRef, count);
   }

   public MultiExtractedReference extractAllFromHandler(FilterableItemHandler filterableItemHandler, ItemStack desiredStack, int count) {
      ItemStack merged = ItemStack.f_41583_;
      int remaining = Math.min(desiredStack.m_41741_(), count);
      List<ExtractedStack> extractedStacks = new ArrayList<>();
      IItemHandler itemHandler = filterableItemHandler.getHandler();

      for (int i = 0; i < itemHandler.getSlots(); i++) {
         ItemStack stack = itemHandler.extractItem(i, remaining, true);
         if (ItemStack.m_41746_(stack, desiredStack) && ItemStack.m_41658_(stack, desiredStack)) {
            int toExtract = Math.min(stack.m_41613_(), remaining);
            remaining -= toExtract;
            if (merged.m_41619_()) {
               merged = stack.m_41777_();
               merged.m_41764_(toExtract);
            } else {
               merged.m_41769_(toExtract);
            }

            extractedStacks.add(ExtractedStack.from(filterableItemHandler.getHandler(), i, toExtract));
            if (remaining <= 0) {
               break;
            }
         }
      }

      return new MultiExtractedReference(merged, extractedStacks);
   }

   public MultiExtractedReference extractItemFromAll(ItemStack desiredStack, int count, boolean includeInvalidInvs) {
      ItemStack merged = ItemStack.f_41583_;
      int remaining = count;
      List<FilterableItemHandler> preferred = this.preferredForStack(desiredStack, includeInvalidInvs);
      List<ExtractedStack> extracted = new ArrayList<>();

      for (FilterableItemHandler filterable : preferred) {
         if (remaining <= 0) {
            break;
         }

         MultiExtractedReference extractedFromHandler = this.extractAllFromHandler(filterable, desiredStack, remaining);
         if (!extractedFromHandler.isEmpty()) {
            remaining -= extractedFromHandler.extracted.m_41613_();
            if (merged.m_41619_()) {
               merged = extractedFromHandler.extracted;
            } else {
               merged.m_41769_(extractedFromHandler.extracted.m_41613_());
            }

            extracted.addAll(extractedFromHandler.slots);
         }
      }

      return new MultiExtractedReference(merged, extracted);
   }

   public SlotReference findItem(Predicate<ItemStack> predicate, InteractType type) {
      FilterableItemHandler highestHandler = this.highestPrefInventory(this.getInventory(), predicate, type);
      return highestHandler == null ? SlotReference.empty() : this.findItem(highestHandler, predicate, type);
   }

   public SlotReference findItem(FilterableItemHandler itemHandler, Predicate<ItemStack> stackPredicate, InteractType type) {
      for (int slot = 0; slot < this.maxSlotForType(itemHandler, type); slot++) {
         ItemStack stackInSlot = itemHandler.getHandler().getStackInSlot(slot);
         if (!stackInSlot.m_41619_() && stackPredicate.test(stackInSlot) && itemHandler.canInteractFor(stackInSlot, type).valid()) {
            return new SlotReference(itemHandler.getHandler(), slot);
         }
      }

      return SlotReference.empty();
   }

   public SlotReference findItemR(FilterableItemHandler itemHandler, Predicate<ItemStack> stackPredicate, InteractType type) {
      List<Integer> validSlots = new ArrayList<>();

      for (int slot = 0; slot < this.maxSlotForType(itemHandler, type); slot++) {
         ItemStack stackInSlot = itemHandler.getHandler().getStackInSlot(slot);
         if (!stackInSlot.m_41619_() && stackPredicate.test(stackInSlot) && itemHandler.canInteractFor(stackInSlot, type).valid()) {
            validSlots.add(slot);
         }
      }

      return validSlots.isEmpty() ? SlotReference.empty() : new SlotReference(itemHandler.getHandler(), validSlots.get(random.nextInt(validSlots.size())));
   }

   public List<SlotReference> findItems(FilterableItemHandler itemHandler, Predicate<ItemStack> stackPredicate, InteractType type, int maxSlots) {
      List<SlotReference> slots = new ArrayList<>();
      int numSlots = Math.min(this.maxSlotForType(itemHandler, type), maxSlots);

      for (int slot = 0; slot < numSlots; slot++) {
         ItemStack stackInSlot = itemHandler.getHandler().getStackInSlot(slot);
         if (!stackInSlot.m_41619_() && stackPredicate.test(stackInSlot) && itemHandler.canInteractFor(stackInSlot, type).valid()) {
            slots.add(new SlotReference(itemHandler.getHandler(), slot));
         }
      }

      return slots;
   }

   public List<FilterableItemHandler> preferredForStack(ItemStack stack, boolean includeInvalid) {
      List<FilterableItemHandler> filtered = new ArrayList<>(this.getInventory());
      filtered = filtered.stream()
         .filter(filterableItemHandler -> includeInvalid || filterableItemHandler.getHighestPreference(stack) != ItemScroll.SortPref.INVALID)
         .collect(Collectors.toCollection(ArrayList::new));
      filtered.sort((o1, o2) -> o2.getHighestPreference(stack).ordinal() - o1.getHighestPreference(stack).ordinal());
      return filtered;
   }

   public FilterableItemHandler highestPrefInventory(List<FilterableItemHandler> inventories, Predicate<ItemStack> predicate, InteractType type) {
      ItemScroll.SortPref highestPref = ItemScroll.SortPref.INVALID;
      FilterableItemHandler highestHandler = null;

      for (FilterableItemHandler wrapper : inventories) {
         ItemScroll.SortPref pref = ItemScroll.SortPref.LOW;

         for (int i = 0; i < this.maxSlotForType(wrapper, type); i++) {
            ItemStack stack = wrapper.getHandler().extractItem(i, 1, true);
            if (!stack.m_41619_() && predicate.test(stack)) {
               InteractResult result = wrapper.canInteractFor(stack, type);
               ItemScroll.SortPref foundPref = result.sortPref();
               if (result.valid()) {
                  if (foundPref.ordinal() > pref.ordinal()) {
                     pref = foundPref;
                  }

                  if (pref == ItemScroll.SortPref.HIGHEST) {
                     return wrapper;
                  }
               }
            }
         }

         if (pref.ordinal() > highestPref.ordinal()) {
            highestHandler = wrapper;
            highestPref = pref;
         }
      }

      return highestHandler;
   }

   private int maxSlotForType(FilterableItemHandler filterableItemHandler, InteractType interactType) {
      return interactType == InteractType.EXTRACT ? this.getExtractSlotMax(filterableItemHandler) : this.getInsertSlotMax(filterableItemHandler);
   }

   private int getExtractSlotMax(FilterableItemHandler handler) {
      return this.extractSlotMax == -1 ? handler.getHandler().getSlots() : Math.min(this.extractSlotMax, handler.getHandler().getSlots());
   }

   private int getInsertSlotMax(FilterableItemHandler handler) {
      return this.insertSlotMax == -1 ? handler.getHandler().getSlots() : Math.min(this.insertSlotMax, handler.getHandler().getSlots());
   }
}

package com.hollingsworth.arsnouveau.api.item.inv;

import com.hollingsworth.arsnouveau.common.items.ItemScroll;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class FilterableItemHandler {
   private IItemHandler handler;
   private List<Function<ItemStack, ItemScroll.SortPref>> filters;
   @Nullable
   private Supplier<BlockPos> posSupplier;

   public FilterableItemHandler(IItemHandler handler) {
      this(handler, new ArrayList<>());
   }

   public FilterableItemHandler(IItemHandler handler, List<Function<ItemStack, ItemScroll.SortPref>> filters) {
      this(handler, filters, null);
   }

   public FilterableItemHandler(IItemHandler handler, List<Function<ItemStack, ItemScroll.SortPref>> filters, @Nullable Supplier<BlockPos> posSupplier) {
      this.handler = handler;
      this.filters = filters;
      this.posSupplier = posSupplier;
   }

   @Nullable
   public BlockPos getPos() {
      return this.posSupplier == null ? null : this.posSupplier.get();
   }

   public InteractResult canInsert(ItemStack stack) {
      ItemScroll.SortPref pref = this.getHighestPreference(stack);
      return new InteractResult(pref, pref != ItemScroll.SortPref.INVALID);
   }

   public InteractResult canExtract(ItemStack stack) {
      ItemScroll.SortPref pref = this.getHighestPreference(stack);
      return new InteractResult(pref, pref != ItemScroll.SortPref.INVALID);
   }

   public InteractResult canInteractFor(ItemStack stack, InteractType type) {
      return type == InteractType.EXTRACT ? this.canExtract(stack) : this.canInsert(stack);
   }

   public ItemScroll.SortPref getHighestPreference(ItemStack stack) {
      ItemScroll.SortPref pref = ItemScroll.SortPref.LOW;

      for (Function<ItemStack, ItemScroll.SortPref> filter : this.filters) {
         ItemScroll.SortPref newPref = filter.apply(stack);
         if (newPref == ItemScroll.SortPref.INVALID) {
            return ItemScroll.SortPref.INVALID;
         }

         if (newPref.ordinal() > pref.ordinal()) {
            pref = newPref;
         }
      }

      return pref;
   }

   public IItemHandler getHandler() {
      return this.handler;
   }

   public List<Function<ItemStack, ItemScroll.SortPref>> getFilters() {
      return this.filters;
   }

   public boolean addFilterScroll(ItemStack scrollStack) {
      return scrollStack.m_41720_() instanceof ItemScroll itemScroll
         ? this.filters.add(stackIn -> itemScroll.getSortPref(stackIn, scrollStack, this.handler))
         : false;
   }
}

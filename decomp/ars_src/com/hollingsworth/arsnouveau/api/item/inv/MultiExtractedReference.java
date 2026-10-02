package com.hollingsworth.arsnouveau.api.item.inv;

import java.util.List;
import net.minecraft.world.item.ItemStack;

public class MultiExtractedReference extends MultiSlotReference<ExtractedStack> {
   protected ItemStack extracted;

   public MultiExtractedReference(ItemStack extracted, List<ExtractedStack> slots) {
      super(slots);
      this.extracted = extracted;
   }

   public ItemStack getExtracted() {
      return this.extracted;
   }

   @Override
   public boolean isEmpty() {
      return this.extracted.m_41619_();
   }
}

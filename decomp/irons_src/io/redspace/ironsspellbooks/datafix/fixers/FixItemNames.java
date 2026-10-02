package io.redspace.ironsspellbooks.datafix.fixers;

import io.redspace.ironsspellbooks.datafix.DataFixerElement;
import io.redspace.ironsspellbooks.datafix.DataFixerHelpers;
import java.util.List;
import net.minecraft.nbt.CompoundTag;

public class FixItemNames extends DataFixerElement {
   @Override
   public List<String> preScanValuesToMatch() {
      return DataFixerHelpers.LEGACY_ITEM_IDS.keySet().stream().toList();
   }

   @Override
   public boolean runFixer(CompoundTag tag) {
      if (tag != null && tag.m_128425_("id", 8)) {
         String itemName = tag.m_128461_("id");
         String newName = DataFixerHelpers.LEGACY_ITEM_IDS.get(itemName);
         if (newName != null) {
            tag.m_128359_("id", newName);
            return true;
         }
      }

      return false;
   }
}

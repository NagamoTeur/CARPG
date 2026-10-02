package io.redspace.ironsspellbooks.datafix.fixers;

import io.redspace.ironsspellbooks.datafix.DataFixerElement;
import io.redspace.ironsspellbooks.datafix.DataFixerHelpers;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;

public class FixIsbEnhance extends DataFixerElement {
   @Override
   public List<String> preScanValuesToMatch() {
      return List.of("ISBEnhance");
   }

   @Override
   public boolean runFixer(CompoundTag tag) {
      if (tag != null && tag.m_128441_("ISBEnhance")) {
         Tag ringTag = tag.m_128423_("ISBEnhance");
         if (ringTag instanceof IntTag legacyRingTag) {
            tag.m_128473_("ISBEnhance");
            tag.m_128359_("ISBEnhance", DataFixerHelpers.LEGACY_SPELL_MAPPING.getOrDefault(legacyRingTag.m_7047_(), "irons_spellbooks:none"));
            return true;
         }

         if (ringTag instanceof StringTag stringTag) {
            String newName = DataFixerHelpers.NEW_SPELL_IDS.get(stringTag.m_7916_());
            if (newName != null) {
               tag.m_128359_("ISBEnhance", newName);
               return true;
            }
         }
      }

      return false;
   }
}

package io.redspace.ironsspellbooks.datafix.fixers;

import io.redspace.ironsspellbooks.datafix.DataFixerElement;
import io.redspace.ironsspellbooks.datafix.DataFixerHelpers;
import java.util.List;
import net.minecraft.nbt.CompoundTag;

public class FixIsbSpell extends DataFixerElement {
   @Override
   public List<String> preScanValuesToMatch() {
      return List.of("ISB_spell");
   }

   @Override
   public boolean runFixer(CompoundTag tag) {
      if (tag != null) {
         CompoundTag spellTag = (CompoundTag)tag.m_128423_("ISB_spell");
         if (spellTag != null) {
            boolean fixed = false;
            if (spellTag.m_128441_("type")) {
               this.fixScrollData(spellTag);
               fixed = true;
            }

            if (spellTag.m_128441_("id")) {
               String newName = DataFixerHelpers.NEW_SPELL_IDS.get(spellTag.m_128423_("id").m_7916_());
               if (newName != null) {
                  spellTag.m_128359_("id", newName);
                  fixed = true;
               }
            }

            return fixed;
         }
      }

      return false;
   }

   private void fixScrollData(CompoundTag tag) {
      int legacySpellId = tag.m_128451_("type");
      tag.m_128473_("type");
      tag.m_128359_("id", DataFixerHelpers.LEGACY_SPELL_MAPPING.getOrDefault(legacySpellId, "irons_spellbooks:none"));
   }
}

package io.redspace.ironsspellbooks.datafix.fixers;

import io.redspace.ironsspellbooks.datafix.DataFixerElement;
import io.redspace.ironsspellbooks.datafix.DataFixerHelpers;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

public class FixIsbSpellbook extends DataFixerElement {
   @Override
   public List<String> preScanValuesToMatch() {
      return List.of("ISB_spellbook");
   }

   @Override
   public boolean runFixer(CompoundTag tag) {
      if (tag != null) {
         CompoundTag spellBookTag = (CompoundTag)tag.m_128423_("ISB_spellbook");
         if (spellBookTag != null) {
            ListTag listTagSpells = (ListTag)spellBookTag.m_128423_("spells");
            if (listTagSpells != null && !listTagSpells.isEmpty()) {
               boolean fixed = false;
               if (((CompoundTag)listTagSpells.get(0)).m_128441_("id")) {
                  this.fixSpellbookData(listTagSpells);
                  fixed = true;
               }

               if (this.fixSpellbookSpellIds(listTagSpells)) {
                  fixed = true;
               }

               return fixed;
            }
         }
      }

      return false;
   }

   private void fixSpellbookData(ListTag listTag) {
      listTag.forEach(tag -> {
         CompoundTag t = (CompoundTag)tag;
         int legacySpellId = t.m_128451_("id");
         t.m_128359_("sid", DataFixerHelpers.LEGACY_SPELL_MAPPING.getOrDefault(legacySpellId, "irons_spellbooks:none"));
         t.m_128473_("id");
      });
   }

   private boolean fixSpellbookSpellIds(ListTag listTagSpells) {
      AtomicBoolean fixed = new AtomicBoolean(false);
      listTagSpells.forEach(tag -> {
         CompoundTag spellTag = (CompoundTag)tag;
         if (spellTag.m_128441_("sid")) {
            String newName = DataFixerHelpers.NEW_SPELL_IDS.get(spellTag.m_128423_("sid").m_7916_());
            if (newName != null) {
               spellTag.m_128359_("sid", newName);
               fixed.set(true);
            }
         }
      });
      return fixed.get();
   }
}

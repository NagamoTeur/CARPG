package io.redspace.ironsspellbooks.datafix.fixers;

import io.redspace.ironsspellbooks.datafix.DataFixerElement;
import io.redspace.ironsspellbooks.datafix.DataFixerHelpers;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

public class FixUpgradeType extends DataFixerElement {
   @Override
   public List<String> preScanValuesToMatch() {
      return List.of("ISBUpgrades");
   }

   @Override
   public boolean runFixer(CompoundTag tag) {
      if (tag != null && tag.m_128441_("ISBUpgrades")) {
         for (Tag t : tag.m_128437_("ISBUpgrades", 10)) {
            CompoundTag upgrade = (CompoundTag)t;
            String upgradeKey = upgrade.m_128461_("id");
            String newKey = DataFixerHelpers.LEGACY_UPGRADE_TYPE_IDS.get(upgradeKey);
            if (newKey != null) {
               upgrade.m_128359_("id", newKey);
               return true;
            }
         }
      }

      return false;
   }
}

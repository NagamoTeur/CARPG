package io.redspace.ironsspellbooks.item.armor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item.Properties;

public class PlaguedArmorItem extends ImbuableChestplateArmorItem {
   public PlaguedArmorItem(EquipmentSlot slot, Properties settings) {
      super(ExtendedArmorMaterials.PLAGUED, slot, settings);
   }
}

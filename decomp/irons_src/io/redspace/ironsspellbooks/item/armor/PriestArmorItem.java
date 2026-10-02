package io.redspace.ironsspellbooks.item.armor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item.Properties;

public class PriestArmorItem extends ImbuableChestplateArmorItem {
   public PriestArmorItem(EquipmentSlot slot, Properties settings) {
      super(ExtendedArmorMaterials.PRIEST, slot, settings);
   }
}

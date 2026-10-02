package io.redspace.ironsspellbooks.item.armor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item.Properties;

public class ArchevokerArmorItem extends ImbuableChestplateArmorItem {
   public ArchevokerArmorItem(EquipmentSlot slot, Properties settings) {
      super(ExtendedArmorMaterials.ARCHEVOKER, slot, settings);
   }
}

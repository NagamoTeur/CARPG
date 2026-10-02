package io.redspace.ironsspellbooks.item.armor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item.Properties;

public class NetheriteMageArmorItem extends ImbuableChestplateArmorItem {
   public NetheriteMageArmorItem(EquipmentSlot slot, Properties settings) {
      super(ExtendedArmorMaterials.NETHERITE_BATTLEMAGE, slot, settings);
   }
}

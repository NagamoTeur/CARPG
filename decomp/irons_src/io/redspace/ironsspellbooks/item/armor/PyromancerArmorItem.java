package io.redspace.ironsspellbooks.item.armor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item.Properties;

public class PyromancerArmorItem extends ImbuableChestplateArmorItem {
   public PyromancerArmorItem(EquipmentSlot slot, Properties settings) {
      super(ExtendedArmorMaterials.PYROMANCER, slot, settings);
   }
}

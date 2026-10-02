package io.redspace.ironsspellbooks.item.armor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item.Properties;

public class CryomancerArmorItem extends ImbuableChestplateArmorItem {
   public CryomancerArmorItem(EquipmentSlot slot, Properties settings) {
      super(ExtendedArmorMaterials.CRYOMANCER, slot, settings);
   }
}

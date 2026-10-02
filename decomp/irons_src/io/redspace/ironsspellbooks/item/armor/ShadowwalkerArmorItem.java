package io.redspace.ironsspellbooks.item.armor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item.Properties;

public class ShadowwalkerArmorItem extends ImbuableChestplateArmorItem {
   public ShadowwalkerArmorItem(EquipmentSlot slot, Properties settings) {
      super(ExtendedArmorMaterials.SHADOWWALKER, slot, settings);
   }
}

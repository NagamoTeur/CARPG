package io.redspace.ironsspellbooks.item.armor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item.Properties;

public class CultistArmorItem extends ImbuableChestplateArmorItem {
   public CultistArmorItem(EquipmentSlot slot, Properties settings) {
      super(ExtendedArmorMaterials.CULTIST, slot, settings);
   }
}

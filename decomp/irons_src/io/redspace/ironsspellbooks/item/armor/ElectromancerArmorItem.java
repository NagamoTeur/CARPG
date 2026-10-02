package io.redspace.ironsspellbooks.item.armor;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item.Properties;

public class ElectromancerArmorItem extends ImbuableChestplateArmorItem implements ArmorCapeProvider {
   public ElectromancerArmorItem(EquipmentSlot slot, Properties settings) {
      super(ExtendedArmorMaterials.ELECTROMANCER, slot, settings);
   }

   @Override
   public ResourceLocation getCapeResourceLocation() {
      return new ResourceLocation("irons_spellbooks", "textures/models/armor/electromancer_cape.png");
   }
}

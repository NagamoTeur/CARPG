package io.redspace.ironsspellbooks.item.armor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;

public class PumpkinArmorItem extends ExtendedArmorItem {
   public PumpkinArmorItem(EquipmentSlot slot, Properties settings) {
      super(ExtendedArmorMaterials.PUMPKIN, slot, settings);
   }

   public boolean isEnderMask(ItemStack stack, Player player, EnderMan endermanEntity) {
      return player.m_6844_(EquipmentSlot.HEAD).m_150930_(this);
   }
}

package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.items.generic.ItemBaseArmor;
import javax.annotation.Nonnull;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class DarkArmor extends ItemBaseArmor {
   public final String TEXTURE = "enigmaticlegacy:textures/models/armor/dark_armor.png";

   public DarkArmor(ArmorMaterial materialIn, EquipmentSlot slot) {
      super(materialIn, slot, ItemBaseArmor.getDefaultProperties().m_41497_(Rarity.RARE).m_41486_().m_41491_(null));
   }

   @Nonnull
   @Override
   public final String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
      return "enigmaticlegacy:textures/models/armor/dark_armor.png";
   }
}
